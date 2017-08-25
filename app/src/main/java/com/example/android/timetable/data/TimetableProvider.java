package com.example.android.timetable.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.timetable.data.TimetableContract.*;

/**
 * Created by Sudhanshu on 21-08-2017.
 */

public class TimetableProvider extends ContentProvider {

    // Tag for log messages
    public static final String LOG_TAG = TimetableProvider.class.getSimpleName();

    // Uri Matcher code for the content URL for TimetableEntry
    // This will never be used though. Just maintaining the tradition
    private static final int T_TABLE = 100;

    // Uri matcher for the content of table
    private static final int T_TABLE_DAY = 101;

    // Current day-column
    private static String mCurrentCol;

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
                TimetableContract.PATH_TIMETABLE + "/#", T_TABLE_DAY);

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
                        String [] selectionArgs, String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result
        Cursor cursor;

        // Matching URI to a specific code
        int match = sUriMatcher.match(uri);
        switch(match) {
            case T_TABLE_DAY:
                /**
                 * Peform the join operation between two tables and give the result
                 */

                // Matching the day id with the day column
                int col_id = (int)ContentUris.parseId(uri);
                matchColumn(col_id);
                String myProjection = getProjectionString(projection);
                selection = mCurrentCol + "=?";
                // CLASS_YES = 1, CLASS_NO = 0
                selectionArgs = new String[]{String.valueOf(TimetableEntry.CLASS_YES)};
                final String MY_QUERY = "SELECT " + myProjection + " FROM " +
                        TimetableEntry.TABLE_NAME +
                        " a INNER JOIN " + SubjectEntry.TABLE_NAME + " b ON a." +
                        TimetableEntry.COLUMN_SUBJECT_CODE + " = b." + SubjectEntry._ID +
                        " WHERE a." + selection;
                cursor = database.rawQuery(MY_QUERY,selectionArgs);
                Log.v(LOG_TAG,"Query performed");
                break;
            default:
                Log.v(LOG_TAG,"Cannot query; unknown URI "+uri);
                throw new IllegalArgumentException("Cannot query; unknown URI "+uri);
        }
        // Set notification uri for change when
        //  getContext().getContentResolver().notifyChange(uri,null);
        // is called
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    // Converts projection string array into one string
    // used for rawQuery()
    private String getProjectionString(String [] projection) {
        String myProjection = projection[0];
        for(int i=1;i<projection.length;i++) {
            myProjection += "," + projection[i];
        }
        return myProjection;
    }

    // Maps the day id with the appropriate column name in timetable table
    private void matchColumn(int col_id) {
        switch(col_id) {
            case TimetableEntry.DAY_MONDAY:
                mCurrentCol = TimetableEntry.COLUMN_ON_MONDAY;
                break;
            case TimetableEntry.DAY_TUESDAY:
                mCurrentCol = TimetableEntry.COLUMN_ON_TUESDAY;
                break;
            case TimetableEntry.DAY_WEDNESDAY:
                mCurrentCol = TimetableEntry.COLUMN_ON_WEDNESDAY;
                break;
            case TimetableEntry.DAY_THURSDAY:
                mCurrentCol = TimetableEntry.COLUMN_ON_THURSDAY;
                break;
            case TimetableEntry.DAY_FRIDAY:
                mCurrentCol = TimetableEntry.COLUMN_ON_FRIDAY;
                break;
            case TimetableEntry.DAY_SATURDAY:
                mCurrentCol = TimetableEntry.COLUMN_ON_SATURDAY;
                break;
            case TimetableEntry.DAY_SUNDAY:
                mCurrentCol = TimetableEntry.COLUMN_ON_SUNDAY;
                break;
            default:
                Log.v(LOG_TAG,"The day can't be resolved");
                throw new IllegalArgumentException("The day can't be resolved");
        }
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
