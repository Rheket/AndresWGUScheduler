package com.example.andreswguscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.andreswguscheduler.Entities.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessment_table WHERE assessmentsCourseId = :courseId ORDER BY assessmentDueDate ASC")
    LiveData<List<Assessment>> getAllAssessments(int courseId);

}
