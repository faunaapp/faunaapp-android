package com.example.faunaapp.Repository.DAO;

import androidx.lifecycle.LiveData;

import com.example.faunaapp.DTO.Entry;

import java.util.List;

public interface IEntryRepository {
     void insertEntry(Entry entry);
     LiveData<List<Entry>> getAllEntries();
}
