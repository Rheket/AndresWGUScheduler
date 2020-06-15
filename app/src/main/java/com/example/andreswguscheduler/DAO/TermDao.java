package com.example.andreswguscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.andreswguscheduler.Entities.Course;
import com.example.andreswguscheduler.Entities.Term;

import java.util.List;

@Dao
public interface TermDao {

    @Insert
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM term_table ORDER BY startDate DESC")
    LiveData<List<Term>> getAllTerms();

}
