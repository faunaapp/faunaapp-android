package com.example.faunaapp.MVVM.RemoteDataSource.RemouteSource.AddTaskEntry;

import android.os.AsyncTask;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.request.RequestHeaders;
import com.example.faunaapp.DTO_and_Room_tables.TaskEntry;
import com.example.faunaapp.Enum.CategoryEnum;
import com.example.faunaapp.EventBusObjects.EntryEvent;
import com.example.faunaapp.Helpers.Helper;
import com.faunaapp.graphql.CreateTaskMutation;
import com.faunaapp.graphql.type.Category;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.TimeZone;

public class AddTaskEntryRemoteDataSource implements IAddTaskEntryRemoteDataSource {
    private static ApolloClient apolloClient = null;
    private final CreateTaskAsync createTaskAsync;

    public AddTaskEntryRemoteDataSource(ApolloClient otherApolloClient) {
        apolloClient = otherApolloClient;
        createTaskAsync = new CreateTaskAsync();
    }

    @Override
    public void submit(TaskEntry taskEntry, String token) {
        createTaskAsync.execute(token, getISO8601Format(taskEntry.getDate(), taskEntry.getTime()), taskEntry.getHeading(), taskEntry.getTitle(), taskEntry.getCategoryId()+"");
    }

    private String getISO8601Format(String date, String time) {
        StringBuilder dateTimeBuilder = new StringBuilder();
        String[] dates = date.split("/");
        String[] times = time.split(":");
        String hours = "", minutes = "";
        if (times[0].trim().length() == 1) {
            hours = "0" + times[0].trim();
        } else {
            hours = times[0].trim();
        }
        if (times[1].trim().length() == 1) {
            minutes = "0" + times[1].trim();
        } else {
            minutes = times[1].trim();
        }
        TimeZone tz = TimeZone.getDefault();
        String timeZ = tz.getDisplayName(false, TimeZone.SHORT);
        String[] tim = timeZ.split("\\+");

        dateTimeBuilder.append(dates[2]).append("-").append(dates[1] + "-").append(dates[0]).append("T").append(hours).append(":").append(minutes).append(":00.000+").append(tim[1]);

        Log.i("Tag", dateTimeBuilder.toString());
        return dateTimeBuilder.toString();
    }

    private static class CreateTaskAsync extends AsyncTask<String, Void, Void> {
        private TaskEntry newTaskEntry;

        public enum TaskEntryIterator {
            Token(0),
            DateTime(1),
            Content(2),
            Title(3),
            Category(4);
            private final int value;

            TaskEntryIterator(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }


        }

        @Override
        protected synchronized Void doInBackground(String... strings) {

            apolloClient.mutate(new CreateTaskMutation(strings[TaskEntryIterator.DateTime.getValue()], strings[TaskEntryIterator.Content.getValue()], strings[TaskEntryIterator.Title.getValue()], getCategory(strings[TaskEntryIterator.Category.value])))
                    .toBuilder().requestHeaders(RequestHeaders.builder().addHeader("authorization", strings[0]).build()).build()
                    .enqueue(new ApolloCall.Callback<CreateTaskMutation.Data>()
                    {

                        @Override
                        public void onResponse(@NotNull Response<CreateTaskMutation.Data> response) {
                            String date = Helper.getDateAndTimeFromISO8601(strings[TaskEntryIterator.DateTime.getValue()]).first;
                            String time = Helper.getDateAndTimeFromISO8601(strings[TaskEntryIterator.DateTime.getValue()]).second;
                            newTaskEntry = new TaskEntry(strings[TaskEntryIterator.Content.getValue()], strings[TaskEntryIterator.Title.getValue()], "Something important", date, time, strings[TaskEntryIterator.Category.value]);
                            EntryEvent entryEvent = new EntryEvent();
                            entryEvent.setTaskEntry(newTaskEntry);
                            EventBus.getDefault().post(entryEvent);
                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {
                            Log.i("Tag", "Exception: " + e.getLocalizedMessage());
                        }
                    });
            return null;

        }
        private Category getCategory(String enumeration)
        {
            System.out.println("Enumiration: " + enumeration);
            if(enumeration.equals("0"))
                return Category.MEDICINE;
            if(enumeration.equals("1"))
                return Category.FOOD;
            if(enumeration.equals("2"))
                return Category.ACTIVITY;
            if(enumeration.equals("3"))
                return Category.APPOINTMENT;
            return Category.HEAT;
        }
    }
}
