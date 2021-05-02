package com.example.faunaapp.MVVM.model;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.faunaapp.Dagger.ApolloClient.ClientApollo;
import com.example.faunaapp.EventBusObjects.TokenEvent;
import com.faunaapp.graphql.LogInMutation;


import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LogInModel implements ILogInModel {
    private LogInAsyncTask logInAsyncTask;
    private static com.apollographql.apollo.ApolloClient apolloClient;

    private ExecutorService executorService;


    public LogInModel(ClientApollo clientApollo) {
        LogInModel.apolloClient = clientApollo.getClient();
        logInAsyncTask = new LogInAsyncTask();
        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public boolean logIn(String email, String password) {
        if (email.equals("") || password.equals(""))
        {
            return false;
        }
        logInAsyncTask.execute(email.trim(), password.trim());
        return true;
    }


    private static class LogInAsyncTask extends AsyncTask<String, Void, String> {

        private Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        private String token;

        @Override
        protected synchronized String doInBackground(String... strings) {

            apolloClient.mutate(new LogInMutation(strings[0], strings[1])).enqueue(new ApolloCall.Callback<LogInMutation.Data>() {
                @Override
                public void onResponse(@NotNull Response<LogInMutation.Data> response) {
                    Log.i("Tag", strings[0] + " : " + strings[1]);
                    Log.i("Tag", response + "");

                    if (response.getData() == null) {
                        token = "No token provided";
                        //    notify();

                        return;
                    }
                    token = response.getData().login().token();
                    //notify();

                }

                @Override
                public void onFailure(@NotNull ApolloException e) {

                }
            });
            while (token == null) {

            }
            return token;
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            TokenEvent event = new TokenEvent();
            event.setToken(string);
            EventBus.getDefault().post(event);
        }
    }
}
