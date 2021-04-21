package com.example.faunaapp.model;

import android.os.AsyncTask;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.faunaapp.client.Client;
import com.faunaapp.graphql.LogInMutation;


import org.jetbrains.annotations.NotNull;


public class LogInModel implements ILogInModel {
    private LogInAsyncTask logInAsyncTask;
    private static ApolloClient apolloClient;
    private boolean isNotified = false;


    public LogInModel(Client client) {
        apolloClient = client.getClient();
        logInAsyncTask = new LogInAsyncTask();
    }

    @Override
    public void logIn(String email, String password) {
               logInAsyncTask.doInBackground(email.trim(), password.trim());
    }

    @Override
    public String getToken() {
        synchronized (LogInModel.this) {
            String token = logInAsyncTask.getToken();
            isNotified = false;
            return token;
        }
    }

    private class LogInAsyncTask extends AsyncTask<String, Void, Void> {
        private String token = "";

        @Override
        protected synchronized Void doInBackground(String... strings) {
            apolloClient.mutate(new LogInMutation(strings[0], strings[1])).enqueue(new ApolloCall.Callback<LogInMutation.Data>() {
                @Override
                public void onResponse(@NotNull Response<LogInMutation.Data> response) {
                    Log.i("Tag", strings[0] + " : " + strings[1]);
                    Log.i("Tag", response + "");

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
            return null;
        }

        public synchronized String getToken() {
            return token;
        }
    }
}
