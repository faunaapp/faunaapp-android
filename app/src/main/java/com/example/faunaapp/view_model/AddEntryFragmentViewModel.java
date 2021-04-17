package com.example.faunaapp.view_model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faunaapp.DTO.Entry;

import java.util.List;

import kotlin.text.Regex;

public class AddEntryFragmentViewModel extends ViewModel {
    private String constantDateText, constantTimeText;
    private MutableLiveData<String> date, time, error;


    public AddEntryFragmentViewModel() {
        constantDateText = "Vælg dato";
        constantTimeText = "Tidspunkt";
        date = new MutableLiveData<>();
        time = new MutableLiveData<>();
        error = new MutableLiveData<>();
    }

    public void chooseDate(String date){
        this.date.postValue(constantDateText + "\n" + date);
    }
    public void chooseTime(String time){
        this.time.postValue(constantTimeText +"\n" + time);
    }

    public void submitTheEntry(Entry entry){

        String errorMessage = "";
        if(entry.getHeading().trim().equals(""))
        {
            errorMessage += "The heading field is empty\n";
        }
        if(entry.getTitle().trim().equals(""))
        {
            errorMessage += "The title field is empty\n";
        }
        if(!errorMessage.equals(""))
        {
            error.postValue(errorMessage);
            return;
        }
        String date =  entry.getDate().substring(entry.getDate().lastIndexOf("\n") +1);
        String time = entry.getTime().substring(entry.getTime().lastIndexOf("\n")+1);
        if(date !=null ){

        }
    }

    public MutableLiveData<String> getDate() {
        return date;
    }

    public MutableLiveData<String> getTime() {
        return time;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}
