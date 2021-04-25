package com.example.faunaapp.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.client.Client;
import com.example.faunaapp.client.ClientComponent;
import com.example.faunaapp.client.DaggerClientComponent;
import com.example.faunaapp.model.AllCalendarEntriesModel;
import com.example.faunaapp.model.IAllCalendarEntriesModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AllCalendarEntriesFragmentViewModel extends ViewModel {
    private IAllCalendarEntriesModel allCalendarEntriesModell;
    private MutableLiveData<ArrayList<Entry>> entries = new MutableLiveData<>();
    private Client client;
    private ExecutorService executorService;
    private ArrayList<Entry> entr;

    public AllCalendarEntriesFragmentViewModel() {
        ClientComponent component = DaggerClientComponent.create();
        client = component.getClient();
       allCalendarEntriesModell = new AllCalendarEntriesModel(client);
        executorService = Executors.newFixedThreadPool(2);
        entr = new ArrayList<>();
    }

    public void addToEntryList(Entry entry)
    {
        entr.add(entry);
        entries.postValue(entr);
    }

    public MutableLiveData<ArrayList<Entry>> getEntry() {
        return entries;
    }

    public void getAllTasks(){
        executorService.execute(() -> {
            entr.addAll(allCalendarEntriesModell.getAllEntries());

            while (true) {
                if(entr != null && entr.size() != 0) {
                    entries.postValue(entr);
                }
            }
        });
    }
}
