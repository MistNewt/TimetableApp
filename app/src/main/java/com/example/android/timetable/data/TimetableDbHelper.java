package com.example.android.timetable.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.timetable.data.TimetableContract.*;

/**
 * Created by Sudhanshu on 21-08-2017.
 */

public class TimetableDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_TIMETABLE_ENTRIES =
            "CREATE TABLE " + TimetableEntry.TABLE_NAME + "(" +
                    TimetableEntry._ID + "INTEGER PRIMARY KEY," +
                    TimetableEntry.COLUMN_SUBJECT_CODE + " INTEGER," +
                    TimetableEntry.COLUMN_ON_MONDAY + " INTEGER," +
                    TimetableEntry.COLUMN_ON_TUESDAY + " INTEGER," +
                    TimetableEntry.COLUMN_ON_WEDNESDAY + " INTEGER," +
                    TimetableEntry.COLUMN_ON_THURSDAY + " INTEGER," +
                    TimetableEntry.COLUMN_ON_FRIDAY + " INTEGER," +
                    TimetableEntry.COLUMN_ON_SATURDAY + " INTEGER," +
                    TimetableEntry.COLUMN_ON_SUNDAY + " INTEGER," +
                    " FOREIGN KEY (" + TimetableEntry.COLUMN_SUBJECT_CODE +
                    ") REFERENCES " + SubjectEntry.TABLE_NAME + "(" +
                    SubjectEntry._ID + "));";

    private static final String SQL_DELETE_TIMETABLE_ENTRIES =
            "DROP TABLE IF EXISTS " + TimetableEntry.TABLE_NAME;

    private static final String SQL_CREATE_SUBJECT_ENTRIES =
            "CREATE TABLE " + SubjectEntry.TABLE_NAME + "(" +
                    SubjectEntry._ID + "INTEGER PRIMARY KEY," +
                    SubjectEntry.COLUMN_ALL_CLASSES + " INTEGER," +
                    SubjectEntry.COLUMN_CLASSES_PRESENT + " INTEGER)";

    private static final String SQL_DELETE_SUBJECT_ENTRIES =
            "DROP TABLE IF EXISTS " + SubjectEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "attendance.db";

    public TimetableDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TIMETABLE_ENTRIES);
        db.execSQL(SQL_CREATE_SUBJECT_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Newer version means a change in schema
        // so discard table and start over
        db.execSQL(SQL_DELETE_TIMETABLE_ENTRIES);
        db.execSQL(SQL_DELETE_SUBJECT_ENTRIES);
        onCreate(db);
    }
}

