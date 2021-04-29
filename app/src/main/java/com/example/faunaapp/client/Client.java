package com.example.faunaapp.client;

import android.app.Activity;
import android.content.SharedPreferences;

import com.apollographql.apollo.ApolloClient;
import com.example.faunaapp.view.activities.MainActivity;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Client {
    private static final String path = "https://fauna-android-feature-t-cillum.herokuapp.com/";
    private static WeakReference<Activity> mActivityRef;

    public static void updateActivity(Activity activity) {
        mActivityRef = new WeakReference<Activity>(activity);
    }

    @Inject
    public Client() {

    }
    public ApolloClient getClient(){
       return ApolloClient.builder().serverUrl(path).build();
    }
}
