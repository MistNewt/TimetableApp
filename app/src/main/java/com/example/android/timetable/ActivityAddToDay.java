package com.example.android.timetable;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.timetable.data.TimetableContract.*;

import static com.example.android.timetable.SubjectsActivity.SUBJECT_LOADER;

/**
 * Created by Sudhanshu on 25-08-2017.
 */

public class ActivityAddToDay extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int DAY_LOADER = 100;

    SubjectCursorAdapter mCursorAdapter;
    Uri mCurrentUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_day);

        //Get the current URI associated with the intent
        mCurrentUri = getIntent().getData();

        // Referenc to the day's list view
        ListView dayListView = (ListView) findViewById(R.id.day_list);

        // Set the adapter
        mCursorAdapter = new SubjectCursorAdapter(this,null);
        dayListView.setAdapter(mCursorAdapter);

        // Initializing the loader
        getLoaderManager().initLoader(DAY_LOADER,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Perform the query to get the subject details
        String[] projection = {
                SubjectEntry.COLUMN_SUBJECT_NAME,
                SubjectEntry.COLUMN_CLASSES_PRESENT,
                SubjectEntry.COLUMN_TOTAL_CLASSES,
                SubjectEntry._ID
        };

        return new CursorLoader(this,
                mCurrentUri,
                projection, null, null, null);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Callback called when data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Callback called when data finishes loading
        mCursorAdapter.swapCursor(cursor);
    }
}
