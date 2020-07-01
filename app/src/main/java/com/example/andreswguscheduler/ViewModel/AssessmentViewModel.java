package com.example.andreswguscheduler.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.andreswguscheduler.Database.AppRepository;
import com.example.andreswguscheduler.Entities.Assessment;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<Assessment>> allAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);

        repository = new AppRepository(application);
    }

    public void insertAssessment(Assessment assessment) {
        repository.insertAssessment(assessment);
    }

    public void updateAssessment(Assessment assessment) {
        repository.updateAssessment(assessment);
    }

    public void deleteAssessment(Assessment assessment) {
        repository.deleteAssessment(assessment);
    }

    public LiveData<List<Assessment>> getAllAssessments(int courseId) {
        allAssessments = repository.getAllAssessments(courseId);
        return allAssessments;
    }

    public void setCourseId(int courseId) {
        repository.setCourseId(courseId);
    }

    public int getCourseId() {
        return repository.getCourseId();
    }

}
