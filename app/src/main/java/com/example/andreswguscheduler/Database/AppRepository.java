package com.example.andreswguscheduler.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.andreswguscheduler.DAO.AssessmentDao;
import com.example.andreswguscheduler.DAO.TermDao;
import com.example.andreswguscheduler.Entities.Assessment;
import com.example.andreswguscheduler.Entities.Course;
import com.example.andreswguscheduler.DAO.CourseDao;
import com.example.andreswguscheduler.Entities.Term;

import java.util.List;

public class AppRepository {

    private static int termId;
    private static int courseId;
    private LiveData<Integer> count;
    private CourseDao courseDao;
    private TermDao termDao;
    private AssessmentDao assessmentDao;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Term>> allTerms;
    private LiveData<List<Assessment>> allAssessments;

    public AppRepository(Application application) {

        AppDatabase database = AppDatabase.getInstance(application);

        courseDao = database.courseDao();
        allCourses = courseDao.getAllCourses(termId);

        termDao = database.termDao();
        allTerms = termDao.getAllTerms();

        assessmentDao = database.assessmentDao();
        allAssessments = assessmentDao.getAllAssessments(courseId);
    }

    public int getTermId() {
        return termId;
    }


    private static class MyTaskParams {
        String cNotes;
        int cId;
        MyTaskParams(String cNotes, int cId) {

            this.cNotes = cNotes;
            this.cId = cId;

        }
    }

    public void updateNotes (String cNotes, int cId) {
        MyTaskParams params = new MyTaskParams(cNotes, cId);
        new UpdateNotesAsyncTask(courseDao).execute(params);
    }

    private class UpdateNotesAsyncTask extends AsyncTask<MyTaskParams, Void, Void> {

        private CourseDao mCourseDao;

        public UpdateNotesAsyncTask(CourseDao courseDao) {
            mCourseDao = courseDao;
        }

        @Override
        protected Void doInBackground(MyTaskParams... myTaskParams) {

            String cNotes = myTaskParams[0].cNotes;
            int cId = myTaskParams[0].cId;

            mCourseDao.updateNotes(cNotes, cId);

            return null;
        }
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseId(){
        return courseId;
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

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    public void insertCourse(Course course) {

        new InsertCourseAsyncTask(courseDao).execute(course);

    }

    public void updateCourse(Course course) {

        new UpdateCourseAsyncTask(courseDao).execute(course);

    }

    public void deleteCourse(Course course) {

        new DeleteCourseAsyncTask(courseDao).execute(course);

    }

    public LiveData<List<Course>> getAllCourses(int termId) {

        allCourses = courseDao.getAllCourses(termId);

        return allCourses;
    }

    public LiveData<Integer> getCount(int tId) {

        count = courseDao.getCount(tId);

        return count;

    }


    public void insertAssessment(Assessment assessment) {
        new InsertAssessmentAsyncTask(assessmentDao).execute(assessment);
    }

    public void updateAssessment(Assessment assessment) {
        new UpdateAssessmentAsyncTask(assessmentDao).execute(assessment);
    }

    public void deleteAssessment(Assessment assessment) {
        new DeleteAssessmentAsyncTask(assessmentDao).execute(assessment);
    }

    public LiveData<List<Assessment>> getAllAssessments(int courseId) {

        allAssessments = assessmentDao.getAllAssessments(courseId);

        return allAssessments;
    }

    private static class InsertAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDao assessmentDao;

        private InsertAssessmentAsyncTask(AssessmentDao assessmentDao) {
            this.assessmentDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.insert(assessments[0]);
            return null;
        }
    }

    private static class UpdateAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDao assessmentDao;

        private UpdateAssessmentAsyncTask(AssessmentDao assessmentDao) {
            this.assessmentDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.update(assessments[0]);
            return null;
        }
    }

    private static class DeleteAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {
        private AssessmentDao assessmentDao;

        private DeleteAssessmentAsyncTask(AssessmentDao assessmentDao) {
            this.assessmentDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.delete(assessments[0]);
            return null;
        }
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


}
