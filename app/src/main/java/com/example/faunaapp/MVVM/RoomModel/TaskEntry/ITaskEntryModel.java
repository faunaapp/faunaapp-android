package com.example.faunaapp.MVVM.RoomModel.TaskEntry;

import androidx.lifecycle.LiveData;

import com.example.faunaapp.DTO.TaskEntry;

import java.util.List;

public interface ITaskEntryModel {
     void insertTaskEntry(TaskEntry taskEntry);
     void getAllEntries();
}
