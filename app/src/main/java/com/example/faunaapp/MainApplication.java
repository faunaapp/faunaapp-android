package com.example.faunaapp;

import android.app.Application;
import android.content.Context;

import com.example.faunaapp.Dagger.Room.Component.AppComponent;
import com.example.faunaapp.Dagger.Room.Component.DaggerAppComponent;
import com.example.faunaapp.Dagger.Room.Moldule.AppModule;
import com.example.faunaapp.Dagger.Room.Moldule.RoomModule;

public class MainApplication extends Application {
    private AppComponent appComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .roomModule(new RoomModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
