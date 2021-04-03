package com.example.faunaapp.view.fragments;

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

import com.example.faunaapp.R;
import com.example.faunaapp.view_model.LogInFragmentViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LogInFragment extends Fragment {

    private Button logInButton;
    private TextInputLayout email, password;
    private LogInFragmentViewModel logInViewModel;
    private View logInView;
    private String errorMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        logInView = inflater.inflate(R.layout.log_in_fragment, container, false);
        initializeFragmentsValues(logInView);
//        logInViewModel.getErrorMessage(email.getEditText().getText().toString(), password.getEditText().getText().toString()).observe(getViewLifecycleOwner(), s -> {
//
//        });
      logInButton.setOnClickListener(view -> {
          logInViewModel.logIn(Objects.requireNonNull(email.getEditText()).getText().toString(), Objects.requireNonNull(password.getEditText()).getText().toString());
        });
        return logInView;
    }


    private void initializeFragmentsValues(View view) {
        logInViewModel = new ViewModelProvider(this).get(LogInFragmentViewModel.class);
        logInButton = view.findViewById(R.id.log_in_button);
        email = view.findViewById(R.id.emailInput);
        password = view.findViewById(R.id.passwordInput);
    }


}
