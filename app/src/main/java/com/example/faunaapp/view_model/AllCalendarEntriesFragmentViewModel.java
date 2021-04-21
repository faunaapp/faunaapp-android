package com.example.faunaapp.view_model;

import androidx.lifecycle.ViewModel;

import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.client.Client;
import com.example.faunaapp.client.ClientComponent;
import com.example.faunaapp.client.DaggerClientComponent;
import com.example.faunaapp.model.AllCalendarEntriesModel;
import com.example.faunaapp.model.IAllCalendarEntriesModel;

import java.util.ArrayList;

public class AllCalendarEntriesFragmentViewModel extends ViewModel {
    private IAllCalendarEntriesModel allCalendarEntriesModell;
    private Client client;
    public AllCalendarEntriesFragmentViewModel() {
        ClientComponent component = DaggerClientComponent.create();
        client = component.getClient();
       allCalendarEntriesModell = new AllCalendarEntriesModel(client);
    }

    public ArrayList<Entry> getAllTasks(){
       return allCalendarEntriesModell.getAllEntries();
    }
}
