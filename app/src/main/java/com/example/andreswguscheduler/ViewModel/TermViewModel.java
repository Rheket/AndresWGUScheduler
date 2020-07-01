package com.example.andreswguscheduler.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.andreswguscheduler.Database.AppRepository;
import com.example.andreswguscheduler.Entities.Term;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<Term>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);

        repository = new AppRepository(application);
        allTerms = repository.getAllTerms();
    }

    public void insertTerm(Term term) {
        repository.insertTerm(term);
    }

    public void updateTerm(Term term) {
        repository.updateTerm(term);
    }

    public void deleteTerm(Term term) {
        repository.deleteTerm(term);
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

}
