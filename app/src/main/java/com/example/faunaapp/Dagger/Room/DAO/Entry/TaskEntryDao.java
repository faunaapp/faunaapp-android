package com.example.faunaapp.Dagger.Room.DAO.Entry;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.faunaapp.DTO.TaskEntry;

import java.util.List;

@Dao
public interface TaskEntryDao {
    @Insert
    void insert(TaskEntry taskEntry);

    @Query("SELECT * FROM TaskEntry")
    LiveData<List<TaskEntry>> getAllEntries();

}
