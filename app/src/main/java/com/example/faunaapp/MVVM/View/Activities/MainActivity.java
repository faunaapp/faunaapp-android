package com.example.faunaapp.MVVM.View.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient.ClientApollo;
import com.example.faunaapp.MVVM.View.Fragments.AddTaskEntryFragmentView;
import com.example.faunaapp.MVVM.View.Fragments.AllCalendarEntriesFragmentView;
import com.example.faunaapp.MVVM.View.Fragments.LogInFragmentView;
import com.example.faunaapp.R;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs;

    public SharedPreferences getTokenStorage() {
        return prefs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClientApollo.updateActivity(this);
        prefs = getSharedPreferences("tokenStorage", MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        System.out.println("The button is clicked");
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().getPrimaryNavigationFragment();
        FragmentManager fragmentManager = navHostFragment.getChildFragmentManager();
        Fragment fragment = fragmentManager.getPrimaryNavigationFragment();
        if(fragment instanceof AllCalendarEntriesFragmentView)
        {
            Navigation.findNavController(fragment.requireView()).navigate(R.id.action_allCalendarEntriesFragment_to_log_in);
        }
        if(fragment instanceof AddTaskEntryFragmentView)
        {
            Navigation.findNavController(fragment.requireView()).navigate(R.id.action_addTaskEntryFragmentView_to_log_in);
        }
        return super.onOptionsItemSelected(item);
    }
}