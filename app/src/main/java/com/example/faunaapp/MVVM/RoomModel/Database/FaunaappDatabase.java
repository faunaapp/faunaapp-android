package com.example.faunaapp.MVVM.RoomModel.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.faunaapp.DTO_and_Room_tables.Category;
import com.example.faunaapp.DTO_and_Room_tables.TaskEntry;
import com.example.faunaapp.MVVM.RoomModel.Category.CategoryDAO;
import com.example.faunaapp.MVVM.RoomModel.Category.CategoryModel;
import com.example.faunaapp.MVVM.RoomModel.TaskEntry.TaskEntryDao;


@Database(entities = {TaskEntry.class, Category.class}, version = FaunaappDatabase.VERSION, exportSchema = false)

public abstract class FaunaappDatabase extends RoomDatabase {
    static final int VERSION = 2;
    private static FaunaappDatabase instance;

    public abstract TaskEntryDao taskEntryDao();
    public abstract CategoryDAO categoryDAO();

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
