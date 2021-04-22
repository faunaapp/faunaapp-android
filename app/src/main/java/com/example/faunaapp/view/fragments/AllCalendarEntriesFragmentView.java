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
import java.util.Calendar;
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
        pastAppointmentsRecyclerView = allCalendarEntriesView.findViewById(R.id.pastAppointments);
        allCalendarEntriesFragmentViewModel.getAllTasks();
        setUpObserver();
    }

    private void setUpRecyclerView(RecyclerView recyclerView, List<Entry> entries) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.hasFixedSize();

        EntriesAdapter entriesAdapter = new EntriesAdapter(entries);
        recyclerView.setAdapter(entriesAdapter);
    }

    private void setUpObserver(){
        allCalendarEntriesFragmentViewModel.getEntry().observe(getViewLifecycleOwner(), list ->{
            ArrayList<Entry> futureAppointments = new ArrayList<>();
            ArrayList<Entry> pastAppointments = new ArrayList<>();
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
            int currentDay = Calendar.getInstance().get(Calendar.DATE);
            int currentHour = Calendar.getInstance().get(Calendar.HOUR);
            int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
            for (int i = 0; i < list.size(); i++) {
                String [] date = list.get(i).getDate().split("/");
                int year  = Integer.parseInt(date[2]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[0]);

                String [] time = list.get(i).getTime().split(":");
                int hours = Integer.parseInt(time[0]);
                int minutes = Integer.parseInt(time[1]);


                if(year < currentYear || (year == currentYear && month < currentMonth) || (year == currentYear && month == currentMonth && day < currentDay) ||
                        (year == currentYear && month == currentMonth && day == currentDay && hours < currentHour) ||
                        (year == currentYear && month == currentMonth && day == currentDay && hours == currentHour && minutes < currentMinute)  )
                {
                    pastAppointments.add(list.get(i));
                }
                else
                {
                    futureAppointments.add(list.get(i));
                }
            }
            setUpRecyclerView(futureAppointemtsRecyclerView, futureAppointments);
            setUpRecyclerView(pastAppointmentsRecyclerView, pastAppointments);
        });
    }
}
