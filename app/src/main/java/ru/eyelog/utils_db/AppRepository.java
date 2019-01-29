package ru.eyelog.utils_db;

import android.app.Application;
import android.arch.persistence.room.Room;

public class AppRepository extends Application {

    //DB part
    public static AppRepository instance;
    private AppDatabase database;

    public static AppRepository getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
