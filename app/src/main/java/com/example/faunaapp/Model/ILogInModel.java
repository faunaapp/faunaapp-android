package com.example.faunaapp.Model;

public interface ILogInModel {
    void logIn(String email, String password);
    String getToken();
}
