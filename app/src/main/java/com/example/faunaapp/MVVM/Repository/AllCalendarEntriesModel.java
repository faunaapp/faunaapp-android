package com.example.faunaapp.MVVM.Repository;

import android.os.AsyncTask;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.request.RequestHeaders;
import com.example.faunaapp.DTO.TaskEntry;
import com.example.faunaapp.Dagger.ApolloClient.ClientApollo;
import com.example.faunaapp.EventBusObjects.EntriesEvent;
import com.example.faunaapp.Helpers.Helper;
import com.faunaapp.graphql.GetTasksQuery;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AllCalendarEntriesModel implements IAllCalendarEntriesModel {
    private static ApolloClient apolloClient;
    private AllCalendarAsync allCalendarTaskAsync;


    public AllCalendarEntriesModel(ClientApollo clientApollo) {
        AllCalendarEntriesModel.apolloClient = clientApollo.getClient();
        allCalendarTaskAsync = new AllCalendarAsync();
    }

    @Override
    public void getAllEntries(String token) {
        allCalendarTaskAsync.execute(token);
    }

    private static class AllCalendarAsync extends AsyncTask<String, Void, Void> {

        private  List<GetTasksQuery.Task> tasks;
        public enum TaskEntryIterator {
            Token(0);
            private final int value;

            TaskEntryIterator(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }


        }
        @Override
        protected Void doInBackground(String ... strings) {
            ArrayList<TaskEntry> entries = new ArrayList<>();
            apolloClient.query(new GetTasksQuery()).toBuilder().requestHeaders(RequestHeaders.builder().addHeader("authorization", strings[TaskEntryIterator.Token.getValue()]).build()).build().enqueue(new ApolloCall.Callback<GetTasksQuery.Data>() {
                @Override
                public void onResponse(@NotNull Response<GetTasksQuery.Data> response) {
                     tasks = response.getData().tasks();
                    if(tasks != null)
                    {

                        Log.i("Tag", response.toString());
                        for (GetTasksQuery.Task task : tasks) {
                            System.out.println(task.dateTime());
                            String date = Helper.getDateAndTimeFromISO8601(task.dateTime()).first;
                            String time = Helper.getDateAndTimeFromISO8601(task.dateTime()).second;
                            entries.add(new TaskEntry(task.content(), task.title(), date, time));
                        }
                        EntriesEvent event = new EntriesEvent();
                        event.setEntries(entries);
                        EventBus.getDefault().post(event);
                    }
                }


                @Override
                public void onFailure(@NotNull ApolloException e) {
                    Log.i("Tag", e.getMessage());
                }
            });
            return null;
        }
    }
}
