package com.example.faunaapp.MVVM.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.faunaapp.Dagger.Room.Component.DaggerAppComponent;
import com.example.faunaapp.Dagger.Room.Moldule.AppModule;
import com.example.faunaapp.Dagger.Room.Moldule.RoomModule;
import com.example.faunaapp.MainApplication;
import com.example.faunaapp.R;

import com.example.faunaapp.Dagger.ApolloClient.ClientApollo;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs;
//   @Inject
//    public ITaskEntryRepository entryRepository;

    public SharedPreferences getTokenStorage() {
        return prefs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClientApollo.updateActivity(this);
        prefs = getSharedPreferences("tokenStorage", MODE_PRIVATE);
//        ((MainApplication) getApplicationContext()).appComponent.inject(this);
    }

}