package com.example.faunaapp.MVVM.View_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.faunaapp.DTO.TaskEntry;

import com.example.faunaapp.EventBusObjects.EntriesEvent;
import com.example.faunaapp.EventBusObjects.EntryEvent;

import com.example.faunaapp.MVVM.Repository.AllCalendarsEntry.AllCalendarEntriesRepository;
import com.example.faunaapp.MVVM.Repository.AllCalendarsEntry.IAllCalendarEntriesRepository;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class AllCalendarEntriesFragmentViewModel extends AndroidViewModel {
    private IAllCalendarEntriesRepository allCalendarEntriesRepository;
    private MutableLiveData<List<TaskEntry>> entries;
    private MutableLiveData<TaskEntry> newEntry;


    public AllCalendarEntriesFragmentViewModel(Application application) {
        super(application);
        allCalendarEntriesRepository = new AllCalendarEntriesRepository(application);
        entries = new MutableLiveData<>();
        newEntry = new MutableLiveData<>();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEntryEvent(EntriesEvent entriesEvent)
    {
        entries.postValue(entriesEvent.getEntries());
    }

    @Subscribe
    public void onNewEntryEvent(EntryEvent entryEvent)
    {
        newEntry.postValue(entryEvent.getTaskEntry());
    }



    public LiveData<List<TaskEntry>> getEntries() {
        return entries;
    }

    public void getAllTasks(String token){
        allCalendarEntriesRepository.getAllEntries(token);
    }

    public LiveData<TaskEntry> getNewEntry(){
        return newEntry;
    }
}
