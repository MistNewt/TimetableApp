package com.example.android.timetable;

/**
 * Created by Sudhanshu on 23-08-2017.
 */

public class CardItem {
    private String mDayTitle;
    private int mTotalSubjects;
    private double mOverallPercentage;

    public CardItem(String title,int totalSubjects,double overallPercentage) {
        mDayTitle = title;
        mTotalSubjects= totalSubjects;
        mOverallPercentage = overallPercentage;
    }

    public String getTitle() {
        return mDayTitle;
    }

    public int getTotalSubjects() {
        return mTotalSubjects;
    }

    public double getOverallPercentage() {
        return mOverallPercentage;
    }
}
