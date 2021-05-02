package com.example.faunaapp.Dagger.Room.Database;


import androidx.room.RoomDatabase;
import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.Dagger.Room.DAO.Entry.EntryDao;


@androidx.room.Database(entities = {Entry.class}, version = FaunaappDatabase.VERSION, exportSchema = false)

public abstract class FaunaappDatabase extends RoomDatabase {
  static final int VERSION = 1;
  public abstract EntryDao entryDao();
}
