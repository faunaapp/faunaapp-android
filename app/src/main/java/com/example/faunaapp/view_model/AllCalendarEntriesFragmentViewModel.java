package com.example.faunaapp.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.Events.EntriesEvent;
import com.example.faunaapp.Events.EntryEvent;
import com.example.faunaapp.client.Client;
import com.example.faunaapp.client.ClientComponent;
import com.example.faunaapp.client.DaggerClientComponent;
import com.example.faunaapp.model.AllCalendarEntriesModel;
import com.example.faunaapp.model.IAllCalendarEntriesModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.List;

public class AllCalendarEntriesFragmentViewModel extends ViewModel {
    private IAllCalendarEntriesModel allCalendarEntriesModell;
    private MutableLiveData<List<Entry>> entries;
    private MutableLiveData<Entry> newEntry;
    private Client client;

    public AllCalendarEntriesFragmentViewModel() {
        ClientComponent component = DaggerClientComponent.create();
        client = component.getClient();
       allCalendarEntriesModell = new AllCalendarEntriesModel(client);
        entries = new MutableLiveData<>();
        newEntry = new MutableLiveData<>();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEntryEvent(EntriesEvent entriesEvent)
    {
        entries.setValue(entriesEvent.getEntries());
    }

    @Subscribe
    public void onNewEntryEvent(EntryEvent entryEvent)
    {
        newEntry.setValue(entryEvent.getEntry());
    }



    public LiveData<List<Entry>> getEntries() {
        return entries;
    }

    public void getAllTasks(String token){
            allCalendarEntriesModell.getAllEntries(token);
    }

    public LiveData<Entry> getNewEntry(){
        return newEntry;
    }
}
