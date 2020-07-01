package com.example.andreswguscheduler.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "course_table", foreignKeys = {
        @ForeignKey(onDelete = CASCADE, entity = Term.class,
                parentColumns = "id", childColumns = "termId")},
        indices = {@Index("termId")})
public class Course implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private int termId;

    private String courseTitle;
    private String courseStartDate;
    private String courseAnticipatedEndDate;
    private String courseStatus;
    private String courseMentorName;
    private String courseMentorEmail;
    private String courseMentorPhoneNumber;
    private String courseNotes;

    public Course(int termId, String courseTitle, String courseStartDate, String courseAnticipatedEndDate, String courseStatus, String courseMentorName, String courseMentorEmail, String courseMentorPhoneNumber) {

        this.termId = termId;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseAnticipatedEndDate = courseAnticipatedEndDate;
        this.courseStatus = courseStatus;
        this.courseMentorName = courseMentorName;
        this.courseMentorEmail = courseMentorEmail;
        this.courseMentorPhoneNumber = courseMentorPhoneNumber;

    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTermId() {
        return termId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public String getCourseAnticipatedEndDate() {
        return courseAnticipatedEndDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public String getCourseMentorName() {
        return courseMentorName;
    }

    public String getCourseMentorEmail() {
        return courseMentorEmail;
    }

    public String getCourseMentorPhoneNumber() {
        return courseMentorPhoneNumber;
    }

    public String getCourseNotes() {
        return courseNotes;
    }


}
