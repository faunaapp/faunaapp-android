package com.example.faunaapp.Dagger.Room;

import android.app.Application;


import com.example.faunaapp.Dagger.Room.DAO.EntryDao;
import com.example.faunaapp.Dagger.Room.DAO.FaunaappDatabase;
import com.example.faunaapp.Dagger.Room.DAO.IEntryRepository;
import com.example.faunaapp.Dagger.Room.Moldule.AppModule;
import com.example.faunaapp.Dagger.Room.Moldule.RoomModule;
import com.example.faunaapp.MVVM.view.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(dependencies = {}, modules = {AppModule.class, RoomModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
    EntryDao entryDao();
    FaunaappDatabase faunaDatabase();
    IEntryRepository entryRepository();
    Application application();
}
