package com.example.faunaapp.Dagger.Room.Moldule;

import android.app.Application;

import androidx.room.Room;

import com.example.faunaapp.Dagger.Room.DAO.Entry.ITaskEntryRepository;
import com.example.faunaapp.Dagger.Room.DAO.Entry.TaskEntryDao;
import com.example.faunaapp.Dagger.Room.DAO.Entry.TaskEntryDataSource;
import com.example.faunaapp.Dagger.Room.Database.FaunaappDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    private FaunaappDatabase faunaappDatabase;

    public RoomModule(Application application) {
        faunaappDatabase = Room.databaseBuilder(application, FaunaappDatabase.class, "faunaapp_database").build();
    }

    @Singleton
    @Provides
    FaunaappDatabase providesRoomDatabase(){
        return faunaappDatabase;
    }

    @Singleton
    @Provides
    TaskEntryDao providesEntryDao(FaunaappDatabase faunaappDatabase)
    {
        return faunaappDatabase.entryDao();
    }

    @Singleton
    @Provides
    ITaskEntryRepository entryRepository(TaskEntryDao taskEntryDao)
    {
        return new TaskEntryDataSource(taskEntryDao);
    }
}
