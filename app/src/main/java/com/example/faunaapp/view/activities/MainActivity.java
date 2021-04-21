package com.example.faunaapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.faunaapp.R;
import com.example.faunaapp.client.Client;

public class MainActivity extends AppCompatActivity {
   private SharedPreferences prefs;




    public SharedPreferences getTokenStorage() {
        return prefs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Client.updateActivity(this);
        prefs = getSharedPreferences("tokenStorage", MODE_PRIVATE);
    }
}