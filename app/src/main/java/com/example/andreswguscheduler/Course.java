package com.example.andreswguscheduler;

import java.sql.Date;

public class Course {

    private String title;
    private Date startDate;

    public Course(String title, Date startDate, Date anticipatedEndDate, String status) {
        this.title = title;
        this.startDate = startDate;
        this.anticipatedEndDate = anticipatedEndDate;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getAnticipatedEndDate() {
        return anticipatedEndDate;
    }

    public String getStatus() {
        return status;
    }

    private Date anticipatedEndDate;
    private String status;

}
