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
    private static ArrayList<Entry> futureAppointments,pastAppointments ;
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
        if(futureAppointments == null && pastAppointments == null) {
            allCalendarEntriesFragmentViewModel.getAllTasks();
        }
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
            configureAppointments(list);
            setUpRecycleViews();
        });
    }

    private void setUpRecycleViews() {
        setUpRecyclerView(futureAppointemtsRecyclerView, futureAppointments);
        setUpRecyclerView(pastAppointmentsRecyclerView, pastAppointments);
    }

    private void configureAppointments(ArrayList<Entry> list) {

        if(futureAppointments == null && pastAppointments == null) {
            futureAppointments = new ArrayList<>();
            pastAppointments = new ArrayList<>();
        }

        for (int i = 0; i < list.size(); i++) {
           if(hasToGoToThePastAppointment(list.get(i))){
               pastAppointments.add(list.get(i));
           }
           else{
               futureAppointments.add(list.get(i));
           }
        }
    }

    private boolean hasToGoToThePastAppointment(Entry entry) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDay = Calendar.getInstance().get(Calendar.DATE);
        int currentHour = Calendar.getInstance().get(Calendar.HOUR);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        String [] date = entry.getDate().split("/");
        int year  = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[0]);
        String hours, minutes;
        String [] times = entry.getTime().split(":");
        if(times[0].trim().length() == 1)
        {
            hours = "0"+times[0].trim();
        }
        else{
            hours = times[0].trim();
        }
        if(times[1].trim().length() == 1)
        {
            minutes = "0"+times[1].trim();
        }
        else
        {
            minutes = times[1].trim();
        }

        int hr = Integer.parseInt(hours);
        int min = Integer.parseInt(minutes);


        if(year < currentYear || (year == currentYear && month < currentMonth) || (year == currentYear && month == currentMonth && day < currentDay) ||
                (year == currentYear && month == currentMonth && day == currentDay && hr < currentHour) ||
                (year == currentYear && month == currentMonth && day == currentDay && hr == currentHour && min < currentMinute)  )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
