package com.example.android.timetable;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.timetable.data.TimetableContract;
import com.example.android.timetable.data.TimetableContract.*;

import static android.R.attr.key;
import static com.example.android.timetable.data.TimetableContract.TimetableEntry.CLASS_YES;

/**
 * Created by Sudhanshu on 25-08-2017.
 */
public class SubjectsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = SubjectsActivity.class.getSimpleName();

    public static final int SUBJECT_LOADER = 200;

    SubjectCursorAdapter mCursorAdapter;

    // For storing the uri from intent
    Uri mCurrentUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        // Setting the title of the activity
        setTitle("Subjects");

        // Getting Intent data
        // This will be null if intent is from the MainActivity
        mCurrentUri = getIntent().getData();

        // Reference to the subject listview
        ListView subjectListView = (ListView) findViewById(R.id.subject_list);

        // Attach the loader to the list view
        mCursorAdapter = new SubjectCursorAdapter(this, null);
        subjectListView.setAdapter(mCursorAdapter);

        if (mCurrentUri != null) { // Intent is from ActivityAddToDay
            // Set up onItemClickListener
            subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view,
                                        int position, long id) {
                    Toast.makeText(getApplicationContext(), "Id of item: " + id,
                            Toast.LENGTH_SHORT).show();

                    // Initialising values for columns
                    // and setting all days to false
                    ContentValues values = new ContentValues();
                    values.put(TimetableEntry.COLUMN_SUBJECT_CODE,id);
                    values.put(TimetableEntry.COLUMN_ON_MONDAY,TimetableEntry.CLASS_NO);
                    values.put(TimetableEntry.COLUMN_ON_TUESDAY,TimetableEntry.CLASS_NO);
                    values.put(TimetableEntry.COLUMN_ON_WEDNESDAY,TimetableEntry.CLASS_NO);
                    values.put(TimetableEntry.COLUMN_ON_THURSDAY,TimetableEntry.CLASS_NO);
                    values.put(TimetableEntry.COLUMN_ON_FRIDAY,TimetableEntry.CLASS_NO);
                    values.put(TimetableEntry.COLUMN_ON_SATURDAY,TimetableEntry.CLASS_NO);
                    values.put(TimetableEntry.COLUMN_ON_SUNDAY,TimetableEntry.CLASS_NO);

                    int currentDayCode = (int)ContentUris.parseId(mCurrentUri);
                    String currentDay = matchDay(currentDayCode);

                    // Updating the class column
                    if(values.containsKey(currentDay)) {
                        values.remove(currentDay);
                        values.put(currentDay,TimetableEntry.CLASS_YES);
                    }

                    // Inserting into timetable table
                    Uri newUri = getContentResolver().insert(TimetableEntry.CONTENT_URI,values);
                    //Display appropriate toast message
                    if (newUri != null) {
                        Toast.makeText(getApplicationContext(),
                                getString(R.string.toast_subject_add_day_success),
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                getString(R.string.toast_subject_add_day_failed),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // Initializing the loader
        getLoaderManager().initLoader(SUBJECT_LOADER, null, this);
    }

    private String matchDay(int dayCode) {
        String day;
        switch(dayCode) {
            case TimetableEntry.DAY_MONDAY:
                day = TimetableEntry.COLUMN_ON_MONDAY;
                break;
            case TimetableEntry.DAY_TUESDAY:
                day = TimetableEntry.COLUMN_ON_TUESDAY;
                break;
            case TimetableEntry.DAY_WEDNESDAY:
                day = TimetableEntry.COLUMN_ON_WEDNESDAY;
                break;
            case TimetableEntry.DAY_THURSDAY:
                day = TimetableEntry.COLUMN_ON_THURSDAY;
                break;
            case TimetableEntry.DAY_FRIDAY:
                day = TimetableEntry.COLUMN_ON_FRIDAY;
                break;
            case TimetableEntry.DAY_SATURDAY:
                day = TimetableEntry.COLUMN_ON_SATURDAY;
                break;
            case TimetableEntry.DAY_SUNDAY:
                day = TimetableEntry.COLUMN_ON_SUNDAY;
                break;
            default:
                Log.v(LOG_TAG,"Invalid day, unknown uri "+mCurrentUri);
                throw new IllegalArgumentException("Invalid day, unknown uri "+mCurrentUri);
        }
        return day;
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
                    SubjectEntry.CONTENT_URI,
                    projection, null, null, null);
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
