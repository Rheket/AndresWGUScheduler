package com.example.andreswguscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.andreswguscheduler.Entities.Course;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT COUNT(courseId) FROM course_table WHERE termId = :tId")
    LiveData<Integer> getCount(int tId);

    @Query("SELECT * FROM course_table WHERE termId = :termId ORDER BY courseStartDate ASC")
    LiveData<List<Course>> getAllCourses(int termId);

    @Query("UPDATE course_table SET courseNotes = :cNotes WHERE courseId = :cId")
    void updateNotes(String cNotes, int cId);

}
