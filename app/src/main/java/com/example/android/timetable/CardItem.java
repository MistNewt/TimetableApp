package com.example.android.timetable;

/**
 * Created by Sudhanshu on 23-08-2017.
 */

public class CardItem {
    private String mDayTitle;
    private int mTotalSubjects;

    public CardItem(String title,int totalSubjects) {
        mDayTitle = title;
        mTotalSubjects= totalSubjects;
    }

    public String getTitle() {
        return mDayTitle;
    }

    public int getTotalSubjects() {
        return mTotalSubjects;
    }
}
