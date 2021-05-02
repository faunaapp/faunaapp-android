package com.example.faunaapp.MVVM.model;

import com.example.faunaapp.DTO.Entry;

public interface IAddEntryModel {
    void submit(Entry entry, String token);
}
