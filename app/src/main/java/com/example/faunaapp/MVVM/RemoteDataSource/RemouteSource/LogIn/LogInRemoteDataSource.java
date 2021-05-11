package com.example.faunaapp.MVVM.RemoteDataSource.RemouteSource.LogIn;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.faunaapp.EventBusObjects.TokenEvent;
import com.faunaapp.graphql.LogInMutation;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.transform.Result;

public class LogInRemoteDataSource implements ILogInRemoteDataSource {
    private static ApolloClient apolloClient = null;
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private LogInAsyncTask logInAsyncTask;

    public LogInRemoteDataSource(ApolloClient otherApolloClient) {
        apolloClient = otherApolloClient;
        logInAsyncTask = new LogInAsyncTask();
    }
    @Override
    public boolean logIn(String email, String password) {
        if (email.equals("") || password.equals("")) {
            return false;
        }
        executorService.execute(()->{
            try
            {
                logInAsyncTask.execute(email.trim(), password.trim());
            }
            catch (IllegalStateException ex)
            {

            }

        });

        return true;
    }


    private static class LogInAsyncTask extends AsyncTask<String, Void, Void> {
        private String token;
        public enum LogInIterator {
            Email(0),
            Password(1);

            private final int value;

            LogInIterator(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }
        }


        @Override
        protected synchronized Void doInBackground(String... strings) {

            apolloClient.mutate(new LogInMutation(strings[LogInAsyncTask.LogInIterator.Email.getValue()], strings[LogInAsyncTask.LogInIterator.Password.getValue()])).enqueue(new ApolloCall.Callback<LogInMutation.Data>() {
                @Override
                public void onResponse(@NotNull Response<LogInMutation.Data> response) {
                    Log.i("Tag", strings[0] + " : " + strings[1]);
                    Log.i("Tag", response + "");

                    TokenEvent tokenEvent = new TokenEvent();
                    if (response.getData() == null) {
                        token = "No token provided";
                    }
                    else {
                        token = response.getData().login().token();
                    }
                    tokenEvent.setToken(token);
                    EventBus.getDefault().post(tokenEvent);
                }

                @Override
                public void onFailure(@NotNull ApolloException e) {

                }
            });
            return null;
        }
    }
}
