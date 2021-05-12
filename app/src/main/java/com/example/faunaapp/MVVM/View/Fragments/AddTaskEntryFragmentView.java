package com.example.faunaapp.MVVM.View.Fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.faunaapp.DTO_and_Room_tables.TaskEntry;
import com.example.faunaapp.Helpers.Helper;
import com.example.faunaapp.R;
import com.example.faunaapp.MVVM.View.Activities.MainActivity;
import com.example.faunaapp.MVVM.View_model.AddTaskEntryFragmentViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddTaskEntryFragmentView extends Fragment implements AdapterView.OnItemSelectedListener {
    private String textFromSpinner;
    private int categoryId;
    private View addEntryView;
    private Button dateButton, timeButton, saveButton;
    private MaterialDatePicker<Long> datePicker;
    private FragmentManager fragmentManager;
    private TextInputLayout headingTextInput, titleTextInput, noteTextInput;
    private TimpePickerFragment timePickerFragment;
    private TaskEntry taskEntryToSubmit;
    private Spinner spinner;


    private AddTaskEntryFragmentViewModel addTaskEntryFragmentViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addEntryView = inflater.inflate(R.layout.add_task_entry_fragment, container, false);
        initializeFragmentValues();
        timePickerFragment.setFragment(this);
        dateButton.setOnClickListener(view -> {
            datePicker.show(fragmentManager, "tag");
            datePicker.addOnPositiveButtonClickListener(selection -> {
                addTaskEntryFragmentViewModel.chooseDate(getFormatDate(selection, "dd/MM/yyyy"));
            });
        });
        timeButton.setOnClickListener(view -> {
            timePickerFragment.show(requireActivity().getSupportFragmentManager(), "timePicker");
        });
        saveButton.setOnClickListener(view -> {
            SharedPreferences prefs = ((MainActivity) getActivity()).getTokenStorage();
            String token = prefs.getString("token", "No token provided");
            taskEntryToSubmit = new TaskEntry(headingTextInput.getEditText().getText().toString(), titleTextInput.getEditText().getText().toString(), noteTextInput.getEditText().getText().toString(), getClearDateOrTime(dateButton.getText().toString()), getClearDateOrTime(timeButton.getText().toString()), categoryId);
             addTaskEntryFragmentViewModel.submitTheEntry(taskEntryToSubmit, token);
        });

        return addEntryView;
    }

    private String getClearDateOrTime(String text)
    {
       String clearData =  text.substring(text.lastIndexOf("\n") + 1);
       return clearData;
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
        addTaskEntryFragmentViewModel.chooseTime(time);
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
        dateButton = addEntryView.findViewById(R.id.fragment_add_task_entry_text_date_button_id);
        timeButton = addEntryView.findViewById(R.id.fragment_add_task_entry_text_time_button_id);
        headingTextInput = addEntryView.findViewById(R.id.fragment_add_task_entry_text_input_email_input_id);
        titleTextInput = addEntryView.findViewById(R.id.fragment_add_task_entry_text_topic_input_id);
        noteTextInput = addEntryView.findViewById(R.id.fragment_add_task_entry_textInput_note_input_id);
        saveButton = addEntryView.findViewById(R.id.fragment_add_task_entry_save_button_id);
        datePicker = getDatePickers();
        spinner = (Spinner) addEntryView.findViewById(R.id.spinner_add_task_entry_fragment);
        spinner.setOnItemSelectedListener(this);
        addTaskEntryFragmentViewModel = new ViewModelProvider(this).get(AddTaskEntryFragmentViewModel.class);
        timePickerFragment = new TimpePickerFragment();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        setUpObserver();
    }

    private void setUpObserver() {
        addTaskEntryFragmentViewModel.getDate().observe(getViewLifecycleOwner(), date -> {
            dateButton.setText(date);
        });
        addTaskEntryFragmentViewModel.getTime().observe(getViewLifecycleOwner(), time -> {
            timeButton.setText(time);
        });
        addTaskEntryFragmentViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error.equals("")) {
                Navigation.findNavController(addEntryView).navigate(R.id.action_addEntryFragmentView_to_allCalendarEntriesFragment);

            } else {
                Helper.setSnackbar(addEntryView, error);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        textFromSpinner = parent.getItemAtPosition(position).toString();
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(textFromSpinner);
        if(m.find())
        {
            categoryId = Integer.parseInt(Objects.requireNonNull(m.group(0)));
            categoryId--;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
