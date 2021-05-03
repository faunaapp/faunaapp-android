package com.example.faunaapp.Dagger.Room.Moldule;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.faunaapp.data.Entry.ITaskEntryRepository;
import com.example.faunaapp.data.Entry.TaskEntryDao;
import com.example.faunaapp.data.Entry.TaskEntryDataSource;
import com.example.faunaapp.data.Database.FaunaappDatabase;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    @Singleton
    @Provides
    FaunaappDatabase providesRoomDatabase(Context context){
        return Room.databaseBuilder(context, FaunaappDatabase.class, "faunaapp_database").build();
    }

    @Singleton
    @Provides
    TaskEntryDao providesEntryDao(FaunaappDatabase faunaappDatabase)
    {
        return faunaappDatabase.taskEntryDao();
    }

    @Singleton
    @Provides
    ITaskEntryRepository providesEntryRepository(TaskEntryDao taskEntryDao)
    {
        return new TaskEntryDataSource(taskEntryDao);
    }
}
