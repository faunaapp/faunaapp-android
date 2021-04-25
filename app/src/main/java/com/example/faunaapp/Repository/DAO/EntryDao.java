package com.example.faunaapp.Repository.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.faunaapp.DTO.Entry;

import java.util.List;

@Dao
public interface EntryDao {
    @Insert
    void insert(Entry entry);

    @Query("SELECT * FROM entry_table")
    LiveData<List<Entry>> getAllEntries();

}
