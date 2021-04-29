package com.example.faunaapp.view.fragments;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.faunaapp.Helper.Helper;
import com.example.faunaapp.R;
import com.example.faunaapp.view.activities.MainActivity;
import com.example.faunaapp.view_model.LogInFragmentViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LogInFragmentView extends Fragment {


    private Button logInButton;
    private TextInputLayout email, password;
    private LogInFragmentViewModel logInViewModel;
    private View logInView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        logInView = inflater.inflate(R.layout.login_fragment, container, false);
        initializeFragmentsValues();
        logInButton.setOnClickListener(view -> {
            logInViewModel.logIn(Objects.requireNonNull(email.getEditText()).getText().toString(), Objects.requireNonNull(password.getEditText()).getText().toString());
        });
        return logInView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity)getActivity()).getTokenStorage().getString("token", "no token");
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getTokenStorage().getString("token", "no token");
    }

    private void initializeFragmentsValues() {
        logInViewModel = new ViewModelProvider(this).get(LogInFragmentViewModel.class);
        logInButton = logInView.findViewById(R.id.log_in_button);
        email = logInView.findViewById(R.id.headingInput);
        password = logInView.findViewById(R.id.titleInput);
        setUpObserver();
    }

    private void setUpObserver() {
        logInViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
          Helper.setSnackbar(logInView, errorMessage);
        });

        logInViewModel.getToken().observe(getViewLifecycleOwner(), token-> {
            if(token.equals("No token provided"))
            {
               Helper.setSnackbar(logInView,"Please, provide a valid email and password");
               return;
            }
            Log.i("Tag", token);
            SharedPreferences.Editor editor =  ((MainActivity)getActivity()).getTokenStorage().edit();
            editor.putString("token", token);
            editor.apply();
            Navigation.findNavController(logInView).navigate(R.id.action_log_in_to_allCalendarEntriesFragment);
        });
    }
}
