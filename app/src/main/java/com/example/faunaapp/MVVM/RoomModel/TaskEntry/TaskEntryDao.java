package com.example.faunaapp.MVVM.RoomModel.TaskEntry;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.faunaapp.DTO_and_Room_tables.TaskEntry;

import java.util.List;

@Dao
public interface TaskEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskEntry taskEntry);

    @Query("SELECT * FROM entry_table")
    List<TaskEntry> getAllEntries();

}
