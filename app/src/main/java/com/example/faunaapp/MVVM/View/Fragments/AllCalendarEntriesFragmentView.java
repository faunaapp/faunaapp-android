package com.example.faunaapp.MVVM.View.Fragments;

import android.content.SharedPreferences;
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

import com.example.faunaapp.DTO.TaskEntry;
import com.example.faunaapp.MVVM.RecyclerView.Adapters.EntriesAdapter;
import com.example.faunaapp.R;
import com.example.faunaapp.MVVM.View.Activities.MainActivity;
import com.example.faunaapp.MVVM.View_model.AllCalendarEntriesFragmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AllCalendarEntriesFragmentView extends Fragment {
    private View allCalendarEntriesView;
    private AllCalendarEntriesFragmentViewModel allCalendarEntriesFragmentViewModel;
    private FloatingActionButton addButton;
    private RecyclerView futureAppointemtsRecyclerView, pastAppointmentsRecyclerView;
    private static ArrayList<TaskEntry> futureAppointments,pastAppointments ;

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
        addButton =  allCalendarEntriesView.findViewById(R.id.fragment_all_calendar_entries_button_add);
        futureAppointemtsRecyclerView = allCalendarEntriesView.findViewById(R.id.fragment_all_calendar_entries_futureAppointments_recycler_view_id);
        pastAppointmentsRecyclerView = allCalendarEntriesView.findViewById(R.id.fragment_all_calendar_entries_pastAppointments_recycler_view_id);
        setUpObserver();
        if(futureAppointments == null && pastAppointments == null) {
            SharedPreferences prefs = ((MainActivity) getActivity()).getTokenStorage();
            String token = prefs.getString("token", "No token provided");
            allCalendarEntriesFragmentViewModel.getAllTasks(token);
        }

    }

    private void setUpRecyclerView(RecyclerView recyclerView, List<TaskEntry> entries) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.hasFixedSize();

        EntriesAdapter entriesAdapter = new EntriesAdapter(entries);
        recyclerView.setAdapter(entriesAdapter);
    }

    private void setUpObserver(){
        allCalendarEntriesFragmentViewModel.getEntries().observe(getViewLifecycleOwner(), list ->{
            configureAppointments(list);
            setUpRecycleViews();
        });
        allCalendarEntriesFragmentViewModel.getNewEntry().observe(getViewLifecycleOwner(), entry-> {
            futureAppointments.add(entry);
            setUpRecycleViews();
        });
    }

    private void setUpRecycleViews() {
        setUpRecyclerView(futureAppointemtsRecyclerView, futureAppointments);
        setUpRecyclerView(pastAppointmentsRecyclerView, pastAppointments);
    }

    private void configureAppointments(List<TaskEntry> list) {

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

    private boolean hasToGoToThePastAppointment(TaskEntry taskEntry) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
        int currentDay = Calendar.getInstance().get(Calendar.DATE);
        int currentHour = Calendar.getInstance().get(Calendar.HOUR);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        String [] date = taskEntry.getDate().split("/");
        int year  = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[0]);
        String hours, minutes;
        String [] times = taskEntry.getTime().split(":");
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
