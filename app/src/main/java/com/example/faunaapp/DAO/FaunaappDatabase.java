package com.example.faunaapp.DAO;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.faunaapp.DTO.Entry;

@androidx.room.Database(entities = {Entry.class}, version = 1)
public abstract class FaunaappDatabase extends RoomDatabase {
  public static FaunaappDatabase instance;
  public abstract EntryDao entryDao();


  public static synchronized FaunaappDatabase getInstance(Context context)
  {
      if(instance == null)
      {
        instance = Room.databaseBuilder(context.getApplicationContext(), FaunaappDatabase.class, "faunaapp_database").fallbackToDestructiveMigration().build();
      }
      return instance;
  }

}
