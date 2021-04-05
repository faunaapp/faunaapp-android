package com.example.faunaapp.view_model;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.faunaapp.repository.Repository;
import com.faunaapp.graphql.LogInMutation;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.transform.Result;

public class LogInFragmentViewModel extends ViewModel {
    private MutableLiveData<String> errorMessage;
    private String errorConstructor;
    private Repository repository;
    private ExecutorService executorService;
    private MutableLiveData<String> token;


    public LogInFragmentViewModel() {
        repository = Repository.getInstance();
        errorMessage = new MutableLiveData<>();
        token = new MediatorLiveData<>();
        executorService = Executors.newFixedThreadPool(2);
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
                repository.logIn(email, password);
                token.postValue(repository.getToken());
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
