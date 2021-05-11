package com.example.faunaapp.MVVM.RoomModel.Category;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.faunaapp.DTO_and_Room_tables.Category;

import java.util.List;

@Dao
public interface CategoryDAO {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAll(List<Category> categoryList);

        @Query("Select * from category_table")
        List<Category> getAllCategories();
}
