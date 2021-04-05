package com.example.faunaapp.repository;

import android.os.AsyncTask;
import android.os.Handler;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.faunaapp.graphql.LogInMutation;

import org.jetbrains.annotations.NotNull;


public class Repository {
    private static Repository instance;
    private LogInAsyncTask logInAsyncTask;
    private static ApolloClient apolloClient;

    public Repository() {
        apolloClient = ApolloClient.builder().serverUrl("https://fauna-android-feature-t-cillum.herokuapp.com/").build();

        logInAsyncTask = new LogInAsyncTask();
    }

    public static Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }


    // log in
    public void logIn(String email, String password) {
        logInAsyncTask.doInBackground(email, password);
    }

    public synchronized String getToken() {
        String token;
        token = logInAsyncTask.getToken();

        return token;
    }


    private static class LogInAsyncTask extends AsyncTask<String, Void, Void> {
        private static String token = "";

        @Override
        protected synchronized Void doInBackground(String... strings) {
            apolloClient.mutate(new LogInMutation(strings[0], strings[1])).enqueue(new ApolloCall.Callback<LogInMutation.Data>() {
                @Override
                public void onResponse(@NotNull Response<LogInMutation.Data> response) {
                    if (response.getData() == null) {
                        token = "No token provided";
                        return;
                    }
                    token = response.getData().login().token();
                }

                @Override
                public void onFailure(@NotNull ApolloException e) {

                }
            });
            notify();
            return null;
        }

        public synchronized String getToken() {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return token;
        }
    }
}

