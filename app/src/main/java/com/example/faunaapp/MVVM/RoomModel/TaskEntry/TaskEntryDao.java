package com.example.faunaapp.MVVM.RoomModel.TaskEntry;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.faunaapp.DTO.TaskEntry;

import java.util.List;

@Dao
public interface TaskEntryDao {
    @Insert
    void insert(TaskEntry taskEntry);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertAll(List<TaskEntry> tasksEntry);

    @Query("SELECT * FROM entry_table")
    List<TaskEntry> getAllEntries();

}
