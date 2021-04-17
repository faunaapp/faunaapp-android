package com.example.faunaapp.view.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static android.content.DialogInterface.BUTTON_POSITIVE;

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
