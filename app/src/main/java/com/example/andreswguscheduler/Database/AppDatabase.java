package com.example.andreswguscheduler.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.andreswguscheduler.DAO.CourseDao;
import com.example.andreswguscheduler.DAO.TermDao;
import com.example.andreswguscheduler.Entities.Course;
import com.example.andreswguscheduler.Entities.Term;

@Database(entities = {Course.class, Term.class}, version = 4, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract CourseDao courseDao();
    public abstract TermDao termDao();

    public static synchronized AppDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "course_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();

        }

        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private CourseDao courseDao;
        private TermDao termDao;

        private PopulateDbAsyncTask(AppDatabase db) {

            termDao = db.termDao();
            courseDao = db.courseDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            //courseDao.insert(new Course("Course Title 1", "Start Date 1", "Anticipated End Date 1", "Status 1"));
            //courseDao.insert(new Course("Course Title 2", "Start Date 2", "Anticipated End Date 2", "Status 2"));
            //courseDao.insert(new Course("Course Title 3", "Start Date 3", "Anticipated End Date 3", "Status 3"));

            //termDao.insert(new Term("Term Title 1", "Term Start 1", "Term End 1"));

            return null;

        }
    }

}
