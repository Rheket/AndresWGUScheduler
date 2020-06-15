package com.example.andreswguscheduler.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.andreswguscheduler.DAO.TermDao;
import com.example.andreswguscheduler.Entities.Course;
import com.example.andreswguscheduler.DAO.CourseDao;
import com.example.andreswguscheduler.Entities.Term;

import java.util.List;

//FIXME only 1 repository necessary?
public class AppRepository {

    private CourseDao courseDao;
    private TermDao termDao;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Term>> allTerms;

    public AppRepository(Application application) {

        AppDatabase database = AppDatabase.getInstance(application);
        courseDao = database.courseDao();
        allCourses = courseDao.getAllCourses();

        //FIXME Not sure if term should be initialized here or another function
        termDao = database.termDao();
        allTerms = termDao.getAllTerms();
    }

    public void insert(Course course) {

        new InsertCourseAsyncTask(courseDao).execute(course);

    }

    public void insertTerm(Term term) {

        new InsertTermAsyncTask(termDao).execute(term);

    }

    public void updateTerm(Term term) {

        new UpdateTermAsyncTask(termDao).execute(term);
    }

    public void deleteTerm(Term term) {

        new DeleteTermAsyncTask(termDao).execute(term);
    }

    public void update(Course course) {

        new UpdateCourseAsyncTask(courseDao).execute(course);

    }

    public void delete(Course course) {

        new DeleteCourseAsyncTask(courseDao).execute(course);

    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    private static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao courseDao;

        private InsertCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {

            courseDao.insert(courses[0]);
            return null;

        }

    }

    private static class InsertTermAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDao termDao;

        private InsertTermAsyncTask(TermDao termDao) {

            this.termDao = termDao;

        }

        @Override
        protected Void doInBackground(Term... terms) {

            termDao.insert(terms[0]);
            return null;
        }
    }

    private static class UpdateTermAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDao termDao;

        private UpdateTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Term... terms) {

            termDao.update(terms[0]);
            return null;
        }
    }

    private static class DeleteTermAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDao termDao;

        private DeleteTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Term... terms) {

            termDao.delete(terms[0]);
            return null;
        }
    }

    private static class UpdateCourseAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao courseDao;

        private UpdateCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {

            courseDao.update(courses[0]);
            return null;

        }

    }

    private static class DeleteCourseAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao courseDao;

        private DeleteCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {

            courseDao.delete(courses[0]);
            return null;

        }

    }

}
