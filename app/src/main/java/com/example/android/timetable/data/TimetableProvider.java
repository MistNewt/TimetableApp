package com.example.android.timetable.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Sudhanshu on 21-08-2017.
 */

public class TimetableProvider extends ContentProvider {

    // Tag for log messages
    public static final String LOG_TAG = TimetableProvider.class.getSimpleName();

    // Uri Matcher code for the content URL for TimetableEntry
    private static final int T_TABLE = 100;

    // Uri matcher for the content of table
    private static final int T_TABLE_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // MAPS T_TABLE to respective URI
        sUriMatcher.addURI(TimetableContract.CONTENT_AUTHORITY,
                TimetableContract.PATH_TIMETABLE, T_TABLE);

        // Maps T_TABLE_ID to respective URI
        sUriMatcher.addURI(TimetableContract.CONTENT_AUTHORITY,
                TimetableContract.PATH_TIMETABLE + "/#", T_TABLE_ID);

    }

    //TimetableDbHelper object
    private TimetableDbHelper mDbHelper;

    //Initialize the provider and the database helper object
    @Override
    public boolean onCreate() {
        mDbHelper = new TimetableDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri,String [] projection, String selection,
                        String [] SelectionArgs, String sortOrder) {
        //TODO: This needs to be implemented
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
       // TODO: This needs to be implemented
        return null;
    }

    @Override
    public int update(Uri uri,ContentValues contentValues,String selection,
                      String[] selectionArgs) {
        //TODO: This needs to be implemented
        return 0;
    }

    @Override
    public int delete(Uri uri,String selection,String [] selectionArgs) {
        //TODO: This needs to be implemented
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
