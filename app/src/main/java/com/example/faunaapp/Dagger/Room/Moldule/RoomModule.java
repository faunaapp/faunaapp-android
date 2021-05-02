package com.example.faunaapp.Dagger.Room.Moldule;

import android.app.Application;

import androidx.room.Room;
import com.example.faunaapp.Dagger.Room.DAO.Entry.EntryDao;
import com.example.faunaapp.Dagger.Room.DAO.Entry.EntryDataSource;
import com.example.faunaapp.Dagger.Room.Database.FaunaappDatabase;
import com.example.faunaapp.Dagger.Room.DAO.Entry.IEntryRepository;

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
    EntryDao providesEntryDao(FaunaappDatabase faunaappDatabase)
    {
        return faunaappDatabase.entryDao();
    }

    @Singleton
    @Provides
    IEntryRepository entryRepository(EntryDao entryDao)
    {
        return new EntryDataSource(entryDao);
    }
}
