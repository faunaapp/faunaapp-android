package com.example.faunaapp.MVVM.view.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TimpePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private AddEntryFragmentView addEntryFragmentView;
    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        addEntryFragmentView.chooseTime(hourOfDay+ " : " + minute);
    }
    @NotNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.requireContext(), this, 0, 0, true);
        return timePickerDialog;
    }

    public void setFragment(AddEntryFragmentView addEntryFragmentView)
    {
        this.addEntryFragmentView = addEntryFragmentView;
    }


}
