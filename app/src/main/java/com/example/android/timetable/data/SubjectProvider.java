package com.example.android.timetable.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.android.timetable.R;
import com.example.android.timetable.data.TimetableContract.*;

/**
 * Created by Sudhanshu on 24-08-2017.
 */

public class SubjectProvider extends ContentProvider {

    // Tag for log messages
    public static final String LOG_TAG = SubjectProvider.class.getSimpleName();

    // Uri Matcher code for the content URL
    private static final int SUBJECTS = 200;
    // Uri matcher for the content of tables
    private static final int SUBJECT_ID = 201;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // Maps SUBJECTS to respective URI
        sUriMatcher.addURI(TimetableContract.CONTENT_AUTHORITY,
                TimetableContract.PATH_SUBJECT, SUBJECTS);
        // Maps SUBJECT_ID to respective URI
        sUriMatcher.addURI(TimetableContract.CONTENT_AUTHORITY,
                TimetableContract.PATH_SUBJECT + "/#", SUBJECT_ID);
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
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] SelectionArgs, String sortOrder) {
        //TODO: This needs to be implemented
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SUBJECTS:
                return insertSubject(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion not supported" +
                        "for " + uri);
        }
    }

    private Uri insertSubject(Uri uri, ContentValues values) {

        // Sanity checking the data
        String subjectName = values.getAsString(SubjectEntry.COLUMN_SUBJECT_NAME);
        Integer classesPresent = values.getAsInteger(SubjectEntry.COLUMN_CLASSES_PRESENT);
        Integer allClasses = values.getAsInteger(SubjectEntry.COLUMN_ALL_CLASSES);

        if (subjectName == null || subjectName.equals(""))
            throw new IllegalArgumentException("Subject requires a name");
        if (classesPresent == null || classesPresent < 0)
            throw new IllegalArgumentException("Classes present not a valid number");
        if (allClasses == null || allClasses < 0)
            throw new IllegalArgumentException("All Classes not a valid number");

        // Get writable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id = db.insert(SubjectEntry.TABLE_NAME, null, values);

        // If insertion failed
        if (id == -1) {
            Log.e(LOG_TAG, "Insertion failed " + uri);
            return null;
        }

        // Notify change
        getContext().getContentResolver().notifyChange(uri, null);
        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        //TODO: This needs to be implemented
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //TODO: This needs to be implemented
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}