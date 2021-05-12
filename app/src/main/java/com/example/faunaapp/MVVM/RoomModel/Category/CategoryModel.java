package com.example.faunaapp.MVVM.RoomModel.Category;

import android.app.Application;

import com.example.faunaapp.DTO_and_Room_tables.Category;
import com.example.faunaapp.Enum.CategoryEnum;
import com.example.faunaapp.MVVM.RoomModel.Database.FaunaappDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryModel implements ICategoryModel {
    private static CategoryModel instance;
    private CategoryDAO categoryDAO;
    private final ExecutorService executorService;

    public CategoryModel(Application application) {
        FaunaappDatabase faunaappDatabase = FaunaappDatabase.getInstance(application);
        categoryDAO = faunaappDatabase.categoryDAO();
        executorService = Executors.newFixedThreadPool(2);
    }

    public static synchronized CategoryModel getInstance(Application application) {
        if (instance == null) {
            instance = new CategoryModel(application);
        }
        return instance;
    }

    @Override
    public void seedCategories() {
     executorService.execute(()-> {
         categoryDAO.insertAll(initializeCategories());
     });
    }

    private List<Category> initializeCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, CategoryEnum.MEDICINE.name()));
        categories.add(new Category(2, CategoryEnum.FOOD.name()));
        categories.add(new Category(3, CategoryEnum.ACTIVITY.name()));
        categories.add(new Category(4, CategoryEnum.APPOINTMENT.name()));
        categories.add(new Category(5, CategoryEnum.HEAT.name()));
        return categories;
    }
}
