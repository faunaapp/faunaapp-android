package com.example.faunaapp.Dagger.ApolloClient;

import android.app.Activity;

import com.apollographql.apollo.ApolloClient;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class ClientApollo {
    private static final String path = "https://fauna-android-feature-t-cillum.herokuapp.com/";
    private static WeakReference<Activity> mActivityRef;

    public static void updateActivity(Activity activity) {
        mActivityRef = new WeakReference<Activity>(activity);
    }

    @Inject
    public ClientApollo() {

    }
    public com.apollographql.apollo.ApolloClient getClient(){
       return com.apollographql.apollo.ApolloClient.builder().serverUrl(path).build();
    }
}
