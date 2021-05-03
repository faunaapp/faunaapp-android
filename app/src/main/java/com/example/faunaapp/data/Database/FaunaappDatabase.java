package com.example.faunaapp.data.Database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.faunaapp.data.DTO.TaskEntry;
import com.example.faunaapp.data.Entry.TaskEntryDao;


@Database(entities = {TaskEntry.class}, version = FaunaappDatabase.VERSION, exportSchema = false)

public abstract class FaunaappDatabase extends RoomDatabase {
  static final int VERSION = 1;
  public abstract TaskEntryDao taskEntryDao();
}
