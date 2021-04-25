package com.example.faunaapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.faunaapp.DTO.Entry;

@Dao
public interface EntryDao {
    @Insert
    void insert(Entry entry);

}
