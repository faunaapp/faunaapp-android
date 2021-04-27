package com.example.faunaapp.Repository;

import android.app.Application;

import com.example.faunaapp.Repository.DAO.EntryDao;
import com.example.faunaapp.Repository.DAO.FaunaappDatabase;
import com.example.faunaapp.Repository.DAO.IEntryRepository;
import com.example.faunaapp.Repository.Moldule.AppModule;
import com.example.faunaapp.Repository.Moldule.RoomModule;
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
