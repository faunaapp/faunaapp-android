package com.example.faunaapp.model;

import android.os.AsyncTask;
import android.util.Log;

import androidx.core.util.Pair;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.client.Client;
import com.faunaapp.graphql.GetTasksQuery;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllCalendarEntriesModel implements IAllCalendarEntriesModel {
    private static ApolloClient apolloClient;
    private AllCalendarAsync allCalendarTaskAsync;


    public AllCalendarEntriesModel(Client client) {
        apolloClient = client.getClient();
        allCalendarTaskAsync = new AllCalendarAsync();
    }

    @Override
    public ArrayList<Entry> getAllEntries() {
        return allCalendarTaskAsync.doInBackground();
    }

    private static class AllCalendarAsync extends AsyncTask<Void, Void, ArrayList<Entry>> {


        @Override
        protected ArrayList<Entry> doInBackground(Void... voids) {
            ArrayList<Entry> entries = new ArrayList<>();
            apolloClient.query(new GetTasksQuery()).enqueue(new ApolloCall.Callback<GetTasksQuery.Data>() {
                @Override
                public void onResponse(@NotNull Response<GetTasksQuery.Data> response) {


                    List<GetTasksQuery.Task> tasks = response.getData().tasks();
                    for (GetTasksQuery.Task task : tasks) {
                        System.out.println(task.dateTime());
                       String date =  getDateAndTimeFromISO8601(task.dateTime()).first;
                       String time = getDateAndTimeFromISO8601(task.dateTime()).second;
                       entries.add(new Entry(task.content(), task.title(), date, time));
                    }

                }

                @Override
                public void onFailure(@NotNull ApolloException e) {
                    Log.i("Tag", e.getMessage());
                }
            });
            System.out.println(entries.size());
            return entries;
        }

        private Pair<String, String> getDateAndTimeFromISO8601(String dateAndTime) {
            Pattern patternDate = Pattern.compile("(.*?)T");
            Pattern patternTime = Pattern.compile("([0-9]+:[0-9]+)");
            String[] dates;
            String date = "", time = "";
            Matcher matcherDate = patternDate.matcher(dateAndTime);
            if (matcherDate.find()) {
                dates = matcherDate.group(1).split("-");
                date = dates[2] + "/" + dates[1] + "/" + dates[0];
            }
            Matcher matcherTime = patternTime.matcher(dateAndTime);
            if (matcherTime.find()) {
                time = matcherTime.group(1);
            }
            Pair<String, String> dateTimePair = new Pair<>(date, time);

            return dateTimePair;
        }
    }
}
