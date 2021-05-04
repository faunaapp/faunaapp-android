package com.example.faunaapp.MVVM.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faunaapp.Dagger.ApolloClient.ClientApollo;
import com.example.faunaapp.Dagger.ApolloClient.ClientApolloComponent;
import com.example.faunaapp.Dagger.ApolloClient.DaggerClientApolloComponent;
import com.example.faunaapp.EventBusObjects.TokenEvent;
import com.example.faunaapp.MVVM.Repository.ILogInModel;
import com.example.faunaapp.MVVM.Repository.LogInModel;

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
        token.postValue(tokenEvent.getToken());
    }

    public void logIn(String email, String password) {
             errorConstructor = "";
            boolean isLoggedIn = logInModel.logIn(email, password);
            if(!isLoggedIn)
            {
                errorConstructor += "Email or password are not correct";
                errorMessage.postValue(errorConstructor);
            }
    }
}
