package com.example.faunaapp.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.faunaapp.R;
import com.example.faunaapp.view_model.LogInFragmentViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LogInFragment extends Fragment {


    private Button logInButton;
    private TextInputLayout email, password;
    private LogInFragmentViewModel logInViewModel;
    private View logInView;
    private SharedPreferences localStorage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localStorage = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        logInView = inflater.inflate(R.layout.log_in_fragment, container, false);
        initializeFragmentsValues();
        logInButton.setOnClickListener(view -> {
            logInViewModel.logIn(Objects.requireNonNull(email.getEditText()).getText().toString(), Objects.requireNonNull(password.getEditText()).getText().toString());
        });
        return logInView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        localStorage.getString("token", "no token");
    }

    private void initializeFragmentsValues() {
        logInViewModel = new ViewModelProvider(this).get(LogInFragmentViewModel.class);
        logInButton = logInView.findViewById(R.id.log_in_button);
        email = logInView.findViewById(R.id.emailInput);
        password = logInView.findViewById(R.id.passwordInput);
        setUpObserver();
    }

    private void setUpObserver() {
        logInViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (!errorMessage.equals("")) {
             Snackbar snackbar = Snackbar.make(logInView, errorMessage, Snackbar.LENGTH_LONG).setAction("Retry", view -> {
                });
             snackbar.setTextColor(Color.RED);
             snackbar.show();
            }
        });

        logInViewModel.getToken().observe(getViewLifecycleOwner(), token-> {
            System.out.println("Token is here: " + token);
            SharedPreferences.Editor editor = localStorage.edit();
            editor.putString("token", token);
            editor.apply();

        });
    }


}
