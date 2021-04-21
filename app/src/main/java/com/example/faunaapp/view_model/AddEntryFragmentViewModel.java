package com.example.faunaapp.view_model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.faunaapp.DTO.Entry;
import com.example.faunaapp.client.Client;
import com.example.faunaapp.client.ClientComponent;
import com.example.faunaapp.client.DaggerClientComponent;
import com.example.faunaapp.model.AddEntryModel;
import com.example.faunaapp.model.IAddEntryModel;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.text.Regex;

public class AddEntryFragmentViewModel extends ViewModel {
    private String constantDateText, constantTimeText;
    private ExecutorService executorService;
    private MutableLiveData<String> date, time, error;
    private Client client;
    private IAddEntryModel entryModel;


    public AddEntryFragmentViewModel() {
        constantDateText = "Vælg dato";
        constantTimeText = "Tidspunkt";
        date = new MutableLiveData<>();
        time = new MutableLiveData<>();
        error = new MutableLiveData<>();
        executorService = Executors.newFixedThreadPool(2);
        ClientComponent component = DaggerClientComponent.create();
        client = component.getClient();
        entryModel = new AddEntryModel(client);
    }

    public void chooseDate(String date) {
        this.date.postValue(constantDateText + "\n" + date);
    }

    public void chooseTime(String time) {
        this.time.postValue(constantTimeText + "\n" + time);
    }

    public void submitTheEntry(Entry entry) {
        String errorMessage = getErrorIfExists(entry);
        if(!errorMessage.equals(""))
        {
            error.postValue(errorMessage);
            return;
        }
        error.postValue("");
        entry.setDate(entry.getDate().substring(entry.getDate().lastIndexOf("\n") + 1));
        entry.setTime(entry.getTime().substring(entry.getTime().lastIndexOf("\n") + 1));
        executorService.execute(() -> {
            entryModel.submit(entry);
        });
    }

    private String getErrorIfExists(Entry entry) {
        String errorMessage = "";
        if (entry.getHeading().trim().equals("")) {
            errorMessage += "The heading field is empty\n";
        }
        if (entry.getTitle().trim().equals("")) {
            errorMessage += "The title field is empty\n";
        }
        if (!errorMessage.equals("")) {
            return errorMessage;
        } else {
            String date = entry.getDate().substring(entry.getDate().lastIndexOf("\n") + 1);
            String time = entry.getTime().substring(entry.getTime().lastIndexOf("\n") + 1);
            Pattern hasAnyNumbers = Pattern.compile("\\d", Pattern.CASE_INSENSITIVE);
            Matcher matchHasAnyNumbersDate = hasAnyNumbers.matcher(date);
            Matcher matchHasAnyNumbersTime = hasAnyNumbers.matcher(time);


            if (!matchHasAnyNumbersDate.find()) {
                errorMessage += "Date is not chosen\n";
            }
            if (!matchHasAnyNumbersTime.find()) {
                errorMessage += "Time is not chosen";
            }
        }
        return errorMessage;
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
