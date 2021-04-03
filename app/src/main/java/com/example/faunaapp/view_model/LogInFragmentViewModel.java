package com.example.faunaapp.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogInFragmentViewModel extends ViewModel {
    private MutableLiveData<String> errorMessage;
    private String errorConstructor;

    public LogInFragmentViewModel() {
        errorMessage = new MutableLiveData<>();
    }

    public LiveData<String> getErrorMessage(){
        return errorMessage;
    }

    public void logIn(String email, String password)
    {
        errorConstructor = "";
        if(email.equals(""))
        {
            errorConstructor += "Please, provide your email\n";
        }
        if(password.equals(""))
        {
            errorConstructor += "Please, provide your password";
        }
        errorMessage.postValue(errorConstructor);
    }
}
