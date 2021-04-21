package com.example.faunaapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.Helper.EntriesAdapter;
import com.example.faunaapp.R;
import com.example.faunaapp.view_model.AllCalendarEntriesFragmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AllCalendarEntriesFragmentView extends Fragment {
    private View allCalendarEntriesView;
    private AllCalendarEntriesFragmentViewModel allCalendarEntriesFragmentViewModel;
    private FloatingActionButton addButton;
    private RecyclerView futureAppointemtsRecyclerView, pastAppointmentsRecyclerView;

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
        futureAppointemtsRecyclerView = allCalendarEntriesView.findViewById(R.id.futureAppointments);
        allCalendarEntriesFragmentViewModel.getAllTasks();
        setUpRecyclerView(futureAppointemtsRecyclerView);
        pastAppointmentsRecyclerView = allCalendarEntriesView.findViewById(R.id.pastAppointments);
        setUpRecyclerView(pastAppointmentsRecyclerView);
    }

    private void setUpRecyclerView(RecyclerView recyclerView) {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry("Heading", "Important title", "Descriptive description", "23/02/2020", "13 : 00", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhbGljZUBmYXVuYWFwcC5kayIsImlhdCI6MTYxODkwODE1NSwiZXhwIjoxNjUwMDEyMTU1fQ.X-WvMpYd-PFBvX7qDNCgYBr2ZI1hJ68LydFUQWgw8Xk"));
        entries.add(new Entry("Heading", "Important title", "Descriptive description", "19/02/2020", "7 : 00", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhbGljZUBmYXVuYWFwcC5kayIsImlhdCI6MTYxODkwODE1NSwiZXhwIjoxNjUwMDEyMTU1fQ.X-WvMpYd-PFBvX7qDNCgYBr2ZI1hJ68LydFUQWgw8Xk"));
        entries.add(new Entry("Heading", "Important title", "Descriptive description", "19/02/2020", "8 : 00", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhbGljZUBmYXVuYWFwcC5kayIsImlhdCI6MTYxODkwODE1NSwiZXhwIjoxNjUwMDEyMTU1fQ.X-WvMpYd-PFBvX7qDNCgYBr2ZI1hJ68LydFUQWgw8Xk"));
        entries.add(new Entry("Heading", "Important title", "Descriptive description", "19/02/2020", "7 : 00", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhbGljZUBmYXVuYWFwcC5kayIsImlhdCI6MTYxODkwODE1NSwiZXhwIjoxNjUwMDEyMTU1fQ.X-WvMpYd-PFBvX7qDNCgYBr2ZI1hJ68LydFUQWgw8Xk"));
        entries.add(new Entry("Heading", "Important title", "Descriptive description", "19/02/2020", "7 : 00", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhbGljZUBmYXVuYWFwcC5kayIsImlhdCI6MTYxODkwODE1NSwiZXhwIjoxNjUwMDEyMTU1fQ.X-WvMpYd-PFBvX7qDNCgYBr2ZI1hJ68LydFUQWgw8Xk"));
        entries.add(new Entry("Heading", "Important title", "Descriptive description", "19/02/2020", "7 : 00", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhbGljZUBmYXVuYWFwcC5kayIsImlhdCI6MTYxODkwODE1NSwiZXhwIjoxNjUwMDEyMTU1fQ.X-WvMpYd-PFBvX7qDNCgYBr2ZI1hJ68LydFUQWgw8Xk"));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.hasFixedSize();

        EntriesAdapter entriesAdapter = new EntriesAdapter(entries);
        recyclerView.setAdapter(entriesAdapter);
    }
}
