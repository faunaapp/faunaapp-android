package com.example.faunaapp.MVVM.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faunaapp.Dagger.ApolloClient.ClientApollo;
import com.example.faunaapp.Dagger.ApolloClient.ClientApolloComponent;
import com.example.faunaapp.EventBusObjects.TokenEvent;
import com.example.faunaapp.client.DaggerClientApolloComponent;
import com.example.faunaapp.MVVM.model.ILogInModel;
import com.example.faunaapp.MVVM.model.LogInModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogInFragmentViewModel extends ViewModel {
    private MutableLiveData<String> errorMessage;
    private String errorConstructor;
    private ILogInModel logInModel;
    private ExecutorService executorService;
    private MutableLiveData<String> token;
    private ClientApollo clientApollo;


    public LogInFragmentViewModel() {

        EventBus.getDefault().register(this);
        errorMessage = new MutableLiveData<>();
        token = new MediatorLiveData<>();
        ClientApolloComponent component = DaggerClientApolloComponent.create();
        clientApollo = component.getClient();
        executorService = Executors.newFixedThreadPool(2);
        logInModel = new LogInModel(clientApollo);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<String> getToken() {
        return token;
    }

    @Subscribe
    public void onTokenEvent(TokenEvent tokenEvent)
    {
        token.setValue(tokenEvent.getToken());
    }



    public void logIn(String email, String password) {
        if (checkIfEmailAndPasswordExists(email, password)) {
            logInModel.logIn(email, password);
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