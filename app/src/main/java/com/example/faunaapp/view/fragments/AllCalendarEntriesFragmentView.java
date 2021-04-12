package com.example.faunaapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.faunaapp.R;
import com.example.faunaapp.view_model.AllCalendarEntriesFragmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AllCalendarEntriesFragmentView extends Fragment {
    private View allCalendarEntriesView;
    private AllCalendarEntriesFragmentViewModel allCalendarEntriesFragmentViewModel;
    private FloatingActionButton addButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        allCalendarEntriesView = inflater.inflate(R.layout.all_calendar_entries_fragment, container, false);
        initializeFragmentsValues();
        addButton.setOnClickListener( view -> {
            Navigation.findNavController(allCalendarEntriesView).navigate(R.id.action_allCalendarEntriesFragment_to_addEntryFragmentView);
        });
        return allCalendarEntriesView;
    }

    private void initializeFragmentsValues() {
        allCalendarEntriesFragmentViewModel = new ViewModelProvider(this).get(AllCalendarEntriesFragmentViewModel.class);
        addButton =  allCalendarEntriesView.findViewById(R.id.add_button);
    }
}
