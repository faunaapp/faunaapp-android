package com.example.faunaapp.DTO_and_Room_tables;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithTaskEntries {
    @Embedded public Category category;
    @Relation(
            parentColumn = "id",
            entityColumn = "categoryId"
    )
    public List<TaskEntry> taskEntries;
}
