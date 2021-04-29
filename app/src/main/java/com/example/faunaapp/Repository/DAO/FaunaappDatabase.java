package com.example.faunaapp.Repository.DAO;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.faunaapp.DTO.Entry;

@androidx.room.Database(entities = {Entry.class}, version = 1, exportSchema = false)
public abstract class FaunaappDatabase extends RoomDatabase {
  public abstract EntryDao entryDao();

}
