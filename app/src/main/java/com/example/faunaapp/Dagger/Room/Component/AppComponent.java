package com.example.faunaapp.Dagger.Room.Component;

import android.content.Context;

import com.example.faunaapp.Dagger.Room.Moldule.AppModule;
import com.example.faunaapp.Dagger.Room.Moldule.RoomModule;
import com.example.faunaapp.MVVM.view.activities.MainActivity;
import com.example.faunaapp.MVVM.view.fragments.AddEntryFragmentView;
import com.example.faunaapp.data.Database.FaunaappDatabase;
import com.example.faunaapp.data.Entry.ITaskEntryRepository;
import com.example.faunaapp.data.Entry.TaskEntryDao;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(dependencies = {}, modules = {AppModule.class, RoomModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(AddEntryFragmentView addEntryFragmentView);
    //void inject(Activity activity);

    TaskEntryDao entryDao();

    FaunaappDatabase faunaDatabase();

    ITaskEntryRepository entryRepository();

    Context context();
}
