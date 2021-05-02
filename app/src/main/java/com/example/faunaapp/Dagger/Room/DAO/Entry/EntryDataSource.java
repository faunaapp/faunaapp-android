package com.example.faunaapp.Dagger.Room.DAO.Entry;

import androidx.lifecycle.LiveData;

import com.example.faunaapp.DTO.Entry;

import java.util.List;

import javax.inject.Inject;

public class EntryDataSource implements IEntryRepository {
    private EntryDao entryDao;

    @Inject
    public EntryDataSource(EntryDao entryDao) {
        this.entryDao = entryDao;
    }

    @Override
    public void insertEntry(Entry entry) {
        entryDao.insert(entry);
    }

    @Override
    public LiveData<List<Entry>> getAllEntries() {
        return entryDao.getAllEntries();
    }


}
