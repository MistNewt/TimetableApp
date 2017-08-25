package com.example.android.timetable;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.timetable.data.TimetableContract.SubjectEntry;

/**
 * Created by Sudhanshu on 25-08-2017.
 */
public class SubjectsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int SUBJECT_LOADER = 0;

    SubjectCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        // Setting the title of the activity
        setTitle("Subjects");

        // Reference to the subject listview
        ListView subjectListView = (ListView)findViewById(R.id.subject_list);

        // Attach the loader to the list view
        mCursorAdapter = new SubjectCursorAdapter(this,null);
        subjectListView.setAdapter(mCursorAdapter);

        // Initializing the loader
        getLoaderManager().initLoader(SUBJECT_LOADER,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Perform the query to get the subject details

        String [] projection = {
                SubjectEntry.COLUMN_SUBJECT_NAME,
                SubjectEntry.COLUMN_CLASSES_PRESENT,
                SubjectEntry.COLUMN_TOTAL_CLASSES,
                SubjectEntry._ID
        };

        return new CursorLoader(this,
                SubjectEntry.CONTENT_URI,
                projection, null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Callback called when data finishes loading
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}
