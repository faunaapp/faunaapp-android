package com.example.faunaapp.repository;

import android.app.Application;


import com.example.faunaapp.repository.DAO.EntryDao;
import com.example.faunaapp.repository.DAO.FaunaappDatabase;
import com.example.faunaapp.repository.DAO.IEntryRepository;
import com.example.faunaapp.repository.Moldule.AppModule;
import com.example.faunaapp.repository.Moldule.RoomModule;
import com.example.faunaapp.view.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

@Singleton
@Component(dependencies = {}, modules = {AppModule.class, RoomModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
    EntryDao entryDao();
    FaunaappDatabase faunaDatabase();
    IEntryRepository entryRepository();
    Application application();
}
