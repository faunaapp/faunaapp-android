package com.example.faunaapp.MVVM.View.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.faunaapp.MVVM.View_model.LoadingViewModel;
import com.example.faunaapp.R;

public class LoadingFragmentView extends Fragment {
    private View loadingView;
    private LoadingViewModel loadingViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadingView = inflater.inflate(R.layout.loading_fragment, container, false);
        loadingViewModel = new ViewModelProvider(this).get(LoadingViewModel.class);
        seedCategories();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(loadingView).navigate(R.id.action_loading_to_log_in);
            }
        }, 3000);
        return loadingView;
    }

    private void seedCategories(){
        loadingViewModel.seedCategories();
    }
}
