package com.example.andreswguscheduler.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "assessment_table")
public class Assessment implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

    @ForeignKey(onDelete = CASCADE, entity = Course.class, parentColumns = "courseId", childColumns = "assessmentId")
    @ColumnInfo(name = "assessmentsCourseId")
    private int assessmentsCourseId;

    private String assessmentTitle;
    private String assessmentType;
    private String assessmentDueDate;

    public Assessment(int assessmentsCourseId, String assessmentTitle, String assessmentType, String assessmentDueDate) {
        this.assessmentsCourseId = assessmentsCourseId;
        this.assessmentTitle = assessmentTitle;
        this.assessmentType = assessmentType;
        this.assessmentDueDate = assessmentDueDate;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public int getAssessmentsCourseId() {
        return assessmentsCourseId;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public String getAssessmentDueDate() {
        return assessmentDueDate;
    }


}
