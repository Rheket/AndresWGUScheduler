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
    private LiveData<List<Course>> allCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);

        repository = new AppRepository(application);
        allCourses = repository.getAllCourses();

    }

    public void insert(Course course) {

        repository.insert(course);

    }

    public void update(Course course) {
        repository.update(course);
    }

    public void delete(Course course) {
        repository.delete(course);
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

}
