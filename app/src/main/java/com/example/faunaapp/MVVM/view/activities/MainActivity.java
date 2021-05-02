package com.example.faunaapp.MVVM.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.R;

import com.example.faunaapp.Dagger.ApolloClient.ClientApollo;
import com.example.faunaapp.Dagger.Room.DAO.IEntryRepository;
import com.example.faunaapp.repository.DaggerAppComponent;
import com.example.faunaapp.Dagger.Room.Moldule.AppModule;
import com.example.faunaapp.Dagger.Room.Moldule.RoomModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
   private SharedPreferences prefs;
   @Inject
    IEntryRepository entryRepository;

    public SharedPreferences getTokenStorage() {
        return prefs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClientApollo.updateActivity(this);
        prefs = getSharedPreferences("tokenStorage", MODE_PRIVATE);
        DaggerAppComponent.builder().appModule(new AppModule(getApplication())).roomModule(new RoomModule(getApplication())).build().inject(this);
    }

    public void insertEntry(Entry entry){
        entryRepository.insertEntry(entry);
    }
}