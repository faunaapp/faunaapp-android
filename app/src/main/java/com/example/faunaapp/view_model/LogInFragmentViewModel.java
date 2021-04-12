package com.example.faunaapp.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faunaapp.client.Client;
import com.example.faunaapp.client.ClientComponent;
import com.example.faunaapp.client.DaggerClientComponent;
import com.example.faunaapp.model.ILogInModel;
import com.example.faunaapp.model.LogInModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogInFragmentViewModel extends ViewModel {
    private MutableLiveData<String> errorMessage;
    private String errorConstructor;
    private ILogInModel logInModel;
    private ExecutorService executorService;
    private MutableLiveData<String> token;
    private Client client;


    public LogInFragmentViewModel() {
        errorMessage = new MutableLiveData<>();
        token = new MediatorLiveData<>();
        ClientComponent component = DaggerClientComponent.create();
        client = component.getClient();
        executorService = Executors.newFixedThreadPool(2);
        logInModel = new LogInModel(client);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<String> getToken() {
        return token;
    }

    public void logIn(String email, String password) {
        if (checkIfEmailAndPasswordExists(email, password)) {
            executorService.execute( () -> {
                logInModel.logIn(email, password);
                token.postValue(logInModel.getToken());
            });
        }
    }

    private boolean checkIfEmailAndPasswordExists(String email, String password) {
        errorConstructor = "";
        if (email.equals("")) {
            errorConstructor += "Please, provide your email\n";
        }
        if (password.equals("")) {
            errorConstructor += "Please, provide your password";
        }
        if (errorConstructor.equals("")) {
            return true;
        }
        errorMessage.postValue(errorConstructor);
        return false;
    }
}
