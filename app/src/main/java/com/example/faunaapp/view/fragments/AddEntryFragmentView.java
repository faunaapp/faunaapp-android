package com.example.faunaapp.view.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.Helper.CustomMessageEvent;
import com.example.faunaapp.Helper.Helper;
import com.example.faunaapp.R;
import com.example.faunaapp.view.activities.MainActivity;
import com.example.faunaapp.view_model.AddEntryFragmentViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddEntryFragmentView extends Fragment {
    private View addEntryView;
    private Button dateButton, timeButton, saveButton;
    private MaterialDatePicker<Long> datePicker;
    private FragmentManager fragmentManager;
    private TextInputLayout headingTextInput, titleTextInput, noteTextInput;
    private AddEntryFragmentViewModel addEntryFragmentViewModel;
    private TimpePickerFragment timePickerFragment;
    private Entry entryToSubmit;
    private View allCalendarEntriesView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         allCalendarEntriesView = inflater.inflate(R.layout.all_calendar_entries_fragment, container, false);
        addEntryView = inflater.inflate(R.layout.add_entry_fragment, container, false);

        initializeFragmentValues();
        timePickerFragment.setFragment(this);
        dateButton.setOnClickListener(view -> {
            datePicker.show(fragmentManager, "tag");
            datePicker.addOnPositiveButtonClickListener(selection -> {
                addEntryFragmentViewModel.chooseDate(getFormatDate(selection, "dd/MM/yyyy"));
            });
        });
        timeButton.setOnClickListener(view -> {
            timePickerFragment.show(requireActivity().getSupportFragmentManager(), "timePicker");
        });
        saveButton.setOnClickListener(view -> {
            SharedPreferences prefs = ((MainActivity) getActivity()).getTokenStorage();
            String token = prefs.getString("token", "No token provided");
            Log.i("Tag", token);
            entryToSubmit = new Entry(headingTextInput.getEditText().getText().toString(), titleTextInput.getEditText().getText().toString(), noteTextInput.getEditText().getText().toString(), dateButton.getText().toString(), timeButton.getText().toString(), token);
            MainActivity.setNewEntry(entryToSubmit);
            CustomMessageEvent event = new CustomMessageEvent();
            event.setEntry(entryToSubmit);
            EventBus.getDefault().post(event);
            addEntryFragmentViewModel.submitTheEntry(entryToSubmit);
        });

        return addEntryView;
    }

    public String getFormatDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public void chooseTime(String time) {
        addEntryFragmentViewModel.chooseTime(time);
    }

    private MaterialDatePicker<Long> getDatePickers() {
        MaterialDatePicker.Builder<Long> dateBuilder = MaterialDatePicker.Builder.datePicker();
        setDateConstraint(dateBuilder);
        return dateBuilder.build();
    }


    private void setDateConstraint(MaterialDatePicker.Builder<Long> dateBuilder) {
        dateBuilder.setTitleText("Vælg dato").setSelection(MaterialDatePicker.todayInUtcMilliseconds());
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        CalendarConstraints.DateValidator dateValidatorMin = DateValidatorPointForward.from(MaterialDatePicker.todayInUtcMilliseconds());
        constraintsBuilder.setValidator(dateValidatorMin);
        dateBuilder.setCalendarConstraints(constraintsBuilder.build());
    }

    private void initializeFragmentValues() {
        fragmentManager = getActivity().getSupportFragmentManager();
        dateButton = addEntryView.findViewById(R.id.dateButton);
        timeButton = addEntryView.findViewById(R.id.timeButton);
        headingTextInput = addEntryView.findViewById(R.id.headingInput);
        titleTextInput = addEntryView.findViewById(R.id.titleInput);
        noteTextInput = addEntryView.findViewById(R.id.noteInput);
        saveButton = addEntryView.findViewById(R.id.saveButton);
        datePicker = getDatePickers();
        addEntryFragmentViewModel = new AddEntryFragmentViewModel();
        timePickerFragment = new TimpePickerFragment();
        setUpObserver();
    }

    private void setUpObserver() {
        addEntryFragmentViewModel.getDate().observe(getViewLifecycleOwner(), date -> {
            dateButton.setText(date);
        });
        addEntryFragmentViewModel.getTime().observe(getViewLifecycleOwner(), time -> {
            timeButton.setText(time);
        });
        addEntryFragmentViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error.equals("")) {
                Navigation.findNavController(addEntryView).navigate(R.id.action_addEntryFragmentView_to_allCalendarEntriesFragment);

            } else {
                Helper.setSnackbar(addEntryView, error);
            }
        });
    }
}
