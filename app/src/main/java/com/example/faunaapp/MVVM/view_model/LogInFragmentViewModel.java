package com.example.faunaapp.MVVM.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.faunaapp.Dagger.ApolloClient.ClientApollo;
import com.example.faunaapp.Dagger.ApolloClient.ClientApolloComponent;
import com.example.faunaapp.Dagger.ApolloClient.DaggerClientApolloComponent;
import com.example.faunaapp.EventBusObjects.TokenEvent;
import com.example.faunaapp.MVVM.Repository.LogIn.ILogInRepository;
import com.example.faunaapp.MVVM.Repository.LogIn.LogInRepository;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LogInFragmentViewModel extends AndroidViewModel {
    private MutableLiveData<String> errorMessage;
    private String errorConstructor;
    private ILogInRepository logInRepository;

    private MutableLiveData<String> token;
    private ClientApollo clientApollo;


    public LogInFragmentViewModel(Application application) {
        super(application);
        EventBus.getDefault().register(this);
        errorMessage = new MutableLiveData<>();
        token = new MediatorLiveData<>();
        ClientApolloComponent component = DaggerClientApolloComponent.create();
        clientApollo = component.getClient();
        logInRepository = new LogInRepository(application);
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
            boolean isLoggedIn = logInRepository.logIn(email, password);
            if(!isLoggedIn)
            {
                errorConstructor += "Email or password are not correct";
                errorMessage.postValue(errorConstructor);
            }
    }
}
