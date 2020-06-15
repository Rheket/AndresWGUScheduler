package com.example.andreswguscheduler.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseId;

    @ForeignKey(entity = Term.class, parentColumns = "id", childColumns = "courseId")
    @ColumnInfo(name = "termId")
    private int termId;

    private String courseTitle;
    private String courseStartDate;
    private String courseAnticipatedEndDate;
    private String courseStatus;
    private String courseMentorName;
    private String courseMentorEmail;
    private String courseMentorPhoneNumber;
    private String courseNotes;

    public Course(String courseTitle, String courseStartDate, String courseAnticipatedEndDate, String courseStatus, String courseMentorName, String courseMentorEmail, String courseMentorPhoneNumber, String courseNotes) {
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseAnticipatedEndDate = courseAnticipatedEndDate;
        this.courseStatus = courseStatus;
        this.courseMentorName = courseMentorName;
        this.courseMentorEmail = courseMentorEmail;
        this.courseMentorPhoneNumber = courseMentorPhoneNumber;
        this.courseNotes = courseNotes;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
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
