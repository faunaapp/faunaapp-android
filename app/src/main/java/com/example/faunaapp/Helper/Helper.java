package com.example.faunaapp.Helper;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Helper {
    public static void setSnackbar(View view, String errorMessage) {
        if (!errorMessage.equals("")) {
            Snackbar snackbar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).setAction("Retry", v -> {
            });
            snackbar.setTextColor(Color.RED);
            snackbar.show();
        }
    }
}
