package com.example.faunaapp.repository.DAO;


import androidx.room.RoomDatabase;
import com.example.faunaapp.DTO.Entry;


@androidx.room.Database(entities = {Entry.class}, version = FaunaappDatabase.VERSION, exportSchema = false)

public abstract class FaunaappDatabase extends RoomDatabase {
  static final int VERSION = 1;
  public abstract EntryDao entryDao();
}
