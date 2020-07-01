package com.example.andreswguscheduler.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.andreswguscheduler.Entities.Course;
import com.example.andreswguscheduler.Database.AppRepository;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<Integer> count;
    private LiveData<List<Course>> allCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);

        repository = new AppRepository(application);
        //allCourses = repository.getAllCourses();

    }

    public void insertCourse(Course course) {

        repository.insertCourse(course);

    }

    public void updateCourse(Course course) {
        repository.updateCourse(course);
    }

    public void deleteCourse(Course course) {
        repository.deleteCourse(course);
    }

    public LiveData<List<Course>> getAllCourses(int termId) {
        allCourses = repository.getAllCourses(termId);
        return allCourses;
    }



    public LiveData<Integer> getCount(int tId) {
        count = repository.getCount(tId);
        return count;
    }

    public void setTermId(int termId) {

        repository.setTermId(termId);

    }

    public void updateNotes(String cNotes, int cId) {
        repository.updateNotes(cNotes, cId);
    }

    public int getTermId() {
        return repository.getTermId();
    }
}
