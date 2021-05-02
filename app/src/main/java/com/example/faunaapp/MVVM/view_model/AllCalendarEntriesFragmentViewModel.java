package com.example.faunaapp.MVVM.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.Dagger.ApolloClient.ClientApollo;
import com.example.faunaapp.Dagger.ApolloClient.DaggerClientApolloComponent;
import com.example.faunaapp.EventBusObjects.EntriesEvent;
import com.example.faunaapp.EventBusObjects.EntryEvent;
import com.example.faunaapp.Dagger.ApolloClient.ClientApolloComponent;

import com.example.faunaapp.MVVM.model.AllCalendarEntriesModel;
import com.example.faunaapp.MVVM.model.IAllCalendarEntriesModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.List;

public class AllCalendarEntriesFragmentViewModel extends ViewModel {
    private IAllCalendarEntriesModel allCalendarEntriesModell;
    private MutableLiveData<List<Entry>> entries;
    private MutableLiveData<Entry> newEntry;
    private ClientApollo clientApollo;

    public AllCalendarEntriesFragmentViewModel() {
        ClientApolloComponent component = DaggerClientApolloComponent.create();
        clientApollo = component.getClient();
       allCalendarEntriesModell = new AllCalendarEntriesModel(clientApollo);
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
