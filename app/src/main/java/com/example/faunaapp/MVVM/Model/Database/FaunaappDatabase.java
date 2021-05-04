package com.example.faunaapp.MVVM.Model.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.faunaapp.DTO.TaskEntry;
import com.example.faunaapp.MVVM.Model.TaskEntryDao;


@Database(entities = {TaskEntry.class}, version = FaunaappDatabase.VERSION, exportSchema = false)

public abstract class FaunaappDatabase extends RoomDatabase {
    static final int VERSION = 1;
    private static FaunaappDatabase instance;

    public abstract TaskEntryDao taskEntryDao();

    public static synchronized FaunaappDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    FaunaappDatabase.class, "faunapp_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
