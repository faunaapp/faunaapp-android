package com.example.faunaapp.Dagger.Room.Database;


import androidx.room.RoomDatabase;
import com.example.faunaapp.DTO.TaskEntry;
import com.example.faunaapp.Dagger.Room.DAO.Entry.TaskEntryDao;


@androidx.room.Database(entities = {TaskEntry.class}, version = FaunaappDatabase.VERSION, exportSchema = false)

public abstract class FaunaappDatabase extends RoomDatabase {
  static final int VERSION = 1;
  public abstract TaskEntryDao entryDao();
}
