package com.dk.mycontacts;

import android.app.Application;

import androidx.room.Room;

import com.dk.mycontacts.database.AppDatabase;

public class App extends Application {
    private static App instance;
    private AppDatabase appDatabase;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, getString(R.string.database_name))
                .build();
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
