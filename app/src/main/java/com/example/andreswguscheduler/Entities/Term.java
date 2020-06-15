package com.example.andreswguscheduler.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "term_table")
public class Term implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String termTitle;
    private String startDate;
    private String endDate;


    public Term(String termTitle, String startDate, String endDate) {
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
