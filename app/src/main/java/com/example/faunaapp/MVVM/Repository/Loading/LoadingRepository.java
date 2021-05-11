package com.example.faunaapp.MVVM.Repository.Loading;

import android.app.Application;

import com.example.faunaapp.MVVM.RemoteDataSource.RemouteSource.AddTaskEntry.AddTaskEntryRemoteDataSource;
import com.example.faunaapp.MVVM.RoomModel.Category.CategoryModel;
import com.example.faunaapp.MVVM.RoomModel.Category.ICategoryModel;
import com.example.faunaapp.MVVM.RoomModel.TaskEntry.TaskEntryModel;

public class LoadingRepository implements ILoadingRepository {
    private ICategoryModel categoryModel;

    public LoadingRepository(Application application) {
        categoryModel = CategoryModel.getInstance(application);
    }
    @Override
    public void seedCategories() {
        categoryModel.seedCategories();
    }
}
