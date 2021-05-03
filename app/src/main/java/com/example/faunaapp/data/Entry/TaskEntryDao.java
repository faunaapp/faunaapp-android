package com.example.faunaapp.data.Entry;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.faunaapp.data.DTO.TaskEntry;

import java.util.List;

@Dao
public interface TaskEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskEntry taskEntry);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TaskEntry> tasksEntry);

    @Query("SELECT * FROM entry_table")
    LiveData<List<TaskEntry>> getAllEntries();

}
