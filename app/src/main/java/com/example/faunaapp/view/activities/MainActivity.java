package com.example.faunaapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.R;
import com.example.faunaapp.Repository.DAO.IEntryRepository;
import com.example.faunaapp.Repository.DaggerAppComponent;
import com.example.faunaapp.Repository.Moldule.AppModule;
import com.example.faunaapp.Repository.Moldule.RoomModule;
import com.example.faunaapp.client.Client;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
   private SharedPreferences prefs;
   @Inject
   public IEntryRepository entryRepository;


    public SharedPreferences getTokenStorage() {
        return prefs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Client.updateActivity(this);
        prefs = getSharedPreferences("tokenStorage", MODE_PRIVATE);

        DaggerAppComponent.builder().appModule(new AppModule(getApplication())).roomModule(new RoomModule(getApplication())).build().inject(this);


    }

    public void insertEntry(Entry entry){
        entryRepository.insertEntry(entry);
    }
}