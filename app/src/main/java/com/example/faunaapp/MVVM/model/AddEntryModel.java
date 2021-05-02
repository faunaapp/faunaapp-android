package com.example.faunaapp.MVVM.model;

import android.os.AsyncTask;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.request.RequestHeaders;
import com.example.faunaapp.DTO.TaskEntry;
import com.example.faunaapp.Dagger.ApolloClient.ClientApollo;
import com.example.faunaapp.EventBusObjects.EntryEvent;
import com.example.faunaapp.Helpers.Helper;
import com.faunaapp.graphql.CreateTaskMutation;
import com.faunaapp.graphql.type.Category;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.TimeZone;

public class AddEntryModel implements IAddEntryModel {

    private static com.apollographql.apollo.ApolloClient apolloClient;
    private CreateTaskAsync createTaskAsync;


    public AddEntryModel(ClientApollo clientApollo) {
        AddEntryModel.apolloClient = clientApollo.getClient();
        createTaskAsync = new CreateTaskAsync();
    }


    @Override
    public void submit(TaskEntry taskEntry, String token) {
      createTaskAsync.execute(token, getISO8601Format(taskEntry.getDate(), taskEntry.getTime()), taskEntry.getHeading(), taskEntry.getTitle(),Category.MEDICINE.toString());
    }

    private String getISO8601Format(String date, String time) {
        StringBuilder dateTimeBuilder = new StringBuilder();
        String[] dates = date.split("/");
        String[] times = time.split(":");
        String hours ="", minutes="";
        if(times[0].trim().length() == 1)
        {
            hours = "0"+times[0].trim();
        }
        else{
            hours = times[0].trim();
        }
        if(times[1].trim().length() == 1)
        {
            minutes = "0"+times[1].trim();
        }
        else
        {
            minutes = times[1].trim();
        }
        TimeZone tz = TimeZone.getDefault();
        String timeZ = tz.getDisplayName(false, TimeZone.SHORT);
        String[] tim = timeZ.split("\\+");

        dateTimeBuilder.append(dates[2]).append("-").append(dates[1]+"-").append(dates[0]).append("T").append(hours).append(":").append(minutes).append(":00.000+").append(tim[1]);

        Log.i("Tag", dateTimeBuilder.toString());
        return dateTimeBuilder.toString();
    }

    private static class CreateTaskAsync extends AsyncTask<String, Void, TaskEntry> {
        private TaskEntry newTaskEntry;
        public enum EntryIterator {
            Token(0),
            DateTime(1),
            Content(2),
            Title(3);
            private final int value;

            EntryIterator(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }


        }

        @Override
        protected synchronized TaskEntry doInBackground(String... strings) {
            Log.i("Tag", strings[EntryIterator.Token.getValue()] + " : " + strings[EntryIterator.DateTime.getValue()] + " : " + strings[EntryIterator.Content.getValue()] + " : " + strings[EntryIterator.Title.getValue()]);
            apolloClient.mutate(new CreateTaskMutation(strings[EntryIterator.DateTime.getValue()], strings[EntryIterator.Content.getValue()], strings[EntryIterator.Title.getValue()], Category.APPOINTMENT))
                    .toBuilder().requestHeaders(RequestHeaders.builder().addHeader("authorization", strings[0]).build()).build()
                   .enqueue(new ApolloCall.Callback<CreateTaskMutation.Data>(){

                @Override
                public void onResponse(@NotNull Response<CreateTaskMutation.Data> response) {
                    String date = Helper.getDateAndTimeFromISO8601(strings[EntryIterator.DateTime.getValue()]).first;
                    String time = Helper.getDateAndTimeFromISO8601(strings[EntryIterator.DateTime.getValue()]).second;
                      newTaskEntry = new TaskEntry(strings[EntryIterator.Content.getValue()], strings[EntryIterator.Title.getValue()], "Something important", date,time);
                }

                @Override
                public void onFailure(@NotNull ApolloException e) {
                    Log.i("Tag", "Exception: " + e.getLocalizedMessage());
                }
            });

            while (newTaskEntry == null)
            {

            }
            return newTaskEntry;

        }
        @Override
        protected void onPostExecute(TaskEntry taskEntry) {
            super.onPostExecute(taskEntry);
            EntryEvent entryEvent = new EntryEvent();
            entryEvent.setTaskEntry(taskEntry);
            EventBus.getDefault().post(entryEvent);
        }
}
}
