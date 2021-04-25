package com.example.faunaapp.model;

import android.os.AsyncTask;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.request.RequestHeaders;
import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.Helper.EntriesAdapter;
import com.example.faunaapp.client.Client;
import com.faunaapp.graphql.CreateTaskMutation;
import com.faunaapp.graphql.type.Category;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.text.Regex;

public class AddEntryModel implements IAddEntryModel {

    private static ApolloClient apolloClient;
    private CreateTaskAsync createTaskAsync;


    public AddEntryModel(Client client) {
        apolloClient = client.getClient();
        createTaskAsync = new CreateTaskAsync();
    }


    @Override
    public void submit(Entry entry) {
      createTaskAsync.doInBackground(getISO8601Format(entry.getDate(), entry.getTime()), entry.getHeading(),entry.getTitle(),Category.MEDICINE.toString());
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

    private static class CreateTaskAsync extends AsyncTask<String, Void, Void> {
        public enum Entry{
            DateTime(0),
            Content(1),
            Title(2);
            private final int value;

            Entry(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }
        }

        @Override
        protected synchronized Void doInBackground(String... strings) {

            apolloClient.mutate(new CreateTaskMutation(strings[Entry.DateTime.getValue()], strings[Entry.Content.getValue()], strings[Entry.Title.getValue()], Category.APPOINTMENT))
                   .enqueue(new ApolloCall.Callback<CreateTaskMutation.Data>(){

                @Override
                public void onResponse(@NotNull Response<CreateTaskMutation.Data> response) {

                }

                @Override
                public void onFailure(@NotNull ApolloException e) {
                    Log.i("Tag", "Exception: " + e.getLocalizedMessage());
                }
            });
            return null;
        }
}
}
