package com.example.faunaapp.view_model;

import androidx.lifecycle.ViewModel;

import com.example.faunaapp.client.Client;
import com.example.faunaapp.client.ClientComponent;
import com.example.faunaapp.client.DaggerClientComponent;

public class AllCalendarEntriesFragmentViewModel extends ViewModel {
    private Client client;
    public AllCalendarEntriesFragmentViewModel() {
        ClientComponent component = DaggerClientComponent.create();
        client = component.getClient();
    }
}
