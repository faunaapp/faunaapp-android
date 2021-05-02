package com.example.faunaapp.Dagger.Room.DAO;

import androidx.lifecycle.LiveData;

import com.example.faunaapp.DTO.Entry;

import java.util.List;

public interface IEntryRepository {
     void insertEntry(Entry entry);
     LiveData<List<Entry>> getAllEntries();
}
