package com.example.faunaapp.MVVM.View_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.faunaapp.MVVM.Repository.AddTaskEntry.IAddTaskEntryRepository;
import com.example.faunaapp.MVVM.Repository.Loading.ILoadingRepository;
import com.example.faunaapp.MVVM.Repository.Loading.LoadingRepository;

import org.jetbrains.annotations.NotNull;

public class LoadingViewModel extends AndroidViewModel {
    private ILoadingRepository loadingRepository;
    public LoadingViewModel(@NonNull @NotNull Application application) {
        super(application);
        loadingRepository = new LoadingRepository(application);
    }

    public void seedCategories(){
        loadingRepository.seedCategories();
    }

}
