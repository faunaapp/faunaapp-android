package com.example.faunaapp.MVVM.RoomModel.TaskEntry;

import com.example.faunaapp.DTO_and_Room_tables.TaskEntry;

public interface ITaskEntryModel {
     void insertTaskEntry(TaskEntry taskEntry);
     void getAllEntries();
}
