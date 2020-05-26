package com.example.andreswguscheduler;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Course.class}, version = 1)
public abstract class CourseDatabase extends RoomDatabase {

    private static CourseDatabase instance;

    public abstract CourseDao courseDao();

    public static synchronized CourseDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(), CourseDatabase.class, "course_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();

        }

        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private CourseDao courseDao;

        private PopulateDbAsyncTask(CourseDatabase db) {

            courseDao = db.courseDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            courseDao.insert(new Course("Title 1", "Start Date 1", "Anticipated End Date 1", "Status 1"));
            courseDao.insert(new Course("Title 2", "Start Date 2", "Anticipated End Date 2", "Status 2"));
            courseDao.insert(new Course("Title 3", "Start Date 3", "Anticipated End Date 3", "Status 3"));

            return null;

        }
    }

}
