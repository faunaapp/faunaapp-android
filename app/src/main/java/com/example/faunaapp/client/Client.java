package com.example.faunaapp.client;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

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
        SharedPreferences prefs = ((MainActivity)mActivityRef.get()).getTokenStorage();
        String token = prefs.getString("token", "No token provided");

        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(
                chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                    builder.header("authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhbGljZUBmYXVuYWFwcC5kayIsImlhdCI6MTYxOTAxNTEzNSwiZXhwIjoxNjUwMTE5MTM1fQ.tj40NKmt6VyR5cwHabyQauO2lrzaieafRa95ZDvMlCs");
                    return chain.proceed(builder.build());
                }
        ).build();
       return ApolloClient.builder().serverUrl(path).okHttpClient(httpClient).build();
    }
}
