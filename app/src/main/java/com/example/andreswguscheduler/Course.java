package com.example.andreswguscheduler;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String startDate;
    private String anticipatedEndDate;
    private String status;

    public Course(String title, String startDate, String anticipatedEndDate, String status) {
        this.title = title;
        this.startDate = startDate;
        this.anticipatedEndDate = anticipatedEndDate;
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getAnticipatedEndDate() {
        return anticipatedEndDate;
    }

    public String getStatus() {
        return status;
    }


}
