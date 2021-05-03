package com.example.faunaapp.MVVM.view_model;


import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faunaapp.Dagger.ApolloClient.DaggerClientApolloComponent;
import com.example.faunaapp.data.DTO.TaskEntry;
import com.example.faunaapp.Dagger.ApolloClient.ClientApollo;
import com.example.faunaapp.Dagger.ApolloClient.ClientApolloComponent;


import com.example.faunaapp.data.Entry.ITaskEntryRepository;
import com.example.faunaapp.MVVM.model.AddEntryModel;
import com.example.faunaapp.MVVM.model.IAddEntryModel;
import com.example.faunaapp.MVVM.view.activities.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;


public class AddEntryFragmentViewModel extends ViewModel {
    private String constantDateText, constantTimeText;
    private ExecutorService executorService;
    private MutableLiveData<String> date, time, error;
    private ClientApollo clientApollo;
    private IAddEntryModel entryModel;

    private ITaskEntryRepository entryRepository;

    @Inject
    public AddEntryFragmentViewModel(ITaskEntryRepository entryRepository) {
        this.entryRepository = entryRepository;


        constantDateText = "Vælg dato";
        constantTimeText = "Tidspunkt";
        date = new MutableLiveData<>();
        time = new MutableLiveData<>();
        error = new MutableLiveData<>();
        executorService = Executors.newFixedThreadPool(2);
        ClientApolloComponent component = DaggerClientApolloComponent.create();
        clientApollo = component.getClient();
        entryModel = new AddEntryModel(clientApollo);
    }

    public void chooseDate(String date) {
        this.date.postValue(constantDateText + "\n" + date);
    }

    public void chooseTime(String time) {
        this.time.postValue(constantTimeText + "\n" + time);
    }

    public void submitTheEntry(TaskEntry taskEntry, String token) {
        String errorMessage = getErrorIfExists(taskEntry);
        entryRepository.insertTaskEntry(taskEntry);
        if(!errorMessage.equals(""))
        {
            error.postValue(errorMessage);
            return;
        }
        error.postValue("");
        executorService.execute(() -> {
            entryModel.submit(taskEntry, token);
        });
    }

    private String getErrorIfExists(TaskEntry taskEntry) {
        String errorMessage = "";
        if (taskEntry.getHeading().trim().equals("")) {
            errorMessage += "The heading field is empty\n";
        }
        if (taskEntry.getTitle().trim().equals("")) {
            errorMessage += "The title field is empty\n";
        }
        if (!errorMessage.equals("")) {
            return errorMessage;
        } else {
            String date = taskEntry.getDate().substring(taskEntry.getDate().lastIndexOf("\n") + 1);
            String time = taskEntry.getTime().substring(taskEntry.getTime().lastIndexOf("\n") + 1);
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
