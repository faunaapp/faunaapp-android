package com.example.faunaapp.model;

import androidx.lifecycle.MutableLiveData;

import com.example.faunaapp.DTO.Entry;

import java.util.ArrayList;

public interface IAllCalendarEntriesModel {

    ArrayList<Entry> getAllEntries();
}
