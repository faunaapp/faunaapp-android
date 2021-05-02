package com.example.faunaapp.Dagger.Room.Component;

import android.app.Application;


import com.example.faunaapp.Dagger.Room.DAO.Entry.TaskEntryDao;
import com.example.faunaapp.Dagger.Room.Database.FaunaappDatabase;
import com.example.faunaapp.Dagger.Room.DAO.Entry.ITaskEntryRepository;
import com.example.faunaapp.Dagger.Room.Moldule.AppModule;
import com.example.faunaapp.Dagger.Room.Moldule.RoomModule;
import com.example.faunaapp.MVVM.view.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(dependencies = {}, modules = {AppModule.class, RoomModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
    TaskEntryDao entryDao();
    FaunaappDatabase faunaDatabase();
    ITaskEntryRepository entryRepository();
    Application application();
}
