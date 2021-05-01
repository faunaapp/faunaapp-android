package com.example.faunaapp.Helper;

import android.graphics.Color;
import android.view.View;

import androidx.core.util.Pair;

import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    public static void setSnackbar(View view, String errorMessage) {
        if (!errorMessage.equals("")) {
            Snackbar snackbar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).setAction("Retry", v -> {
            });
            snackbar.setTextColor(Color.RED);
            snackbar.show();
        }
    }
    public static Pair<String, String> getDateAndTimeFromISO8601(String dateAndTime) {
        Pattern patternDate = Pattern.compile("(.*?)T");
        Pattern patternTime = Pattern.compile("([0-9]+:[0-9]+)");
        String[] dates;
        String date = "", time = "";
        Matcher matcherDate = patternDate.matcher(dateAndTime);
        if (matcherDate.find()) {
            dates = matcherDate.group(1).split("-");
            date = dates[2] + "/" + dates[1] + "/" + dates[0];
        }
        Matcher matcherTime = patternTime.matcher(dateAndTime);
        if (matcherTime.find()) {
            time = matcherTime.group(1);
        }
        Pair<String, String> dateTimePair = new Pair<>(date, time);

        return dateTimePair;
    }
}
