package com.example.faunaapp.MVVM.view.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.faunaapp.Dagger.ApolloClient.ClientApollo;
import com.example.faunaapp.R;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs;

    public SharedPreferences getTokenStorage() {
        return prefs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClientApollo.updateActivity(this);
        prefs = getSharedPreferences("tokenStorage", MODE_PRIVATE);
    }

}