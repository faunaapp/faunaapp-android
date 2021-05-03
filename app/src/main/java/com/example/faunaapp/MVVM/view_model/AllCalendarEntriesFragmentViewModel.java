package com.example.faunaapp.MVVM.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faunaapp.data.DTO.TaskEntry;
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
    private MutableLiveData<List<TaskEntry>> entries;
    private MutableLiveData<TaskEntry> newEntry;
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
            allCalendarEntriesModell.getAllEntries(token);
    }

    public LiveData<TaskEntry> getNewEntry(){
        return newEntry;
    }
}
