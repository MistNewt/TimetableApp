package com.example.android.timetable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android.timetable.data.TimetableContract;
import com.example.android.timetable.data.TimetableContract.*;

import static android.text.style.TtsSpan.GENDER_MALE;

/**
 * Created by Sudhanshu on 22-08-2017.
 */

public class AddSubjectActivity extends AppCompatActivity {

    // Log tag to be used
    private final String LOG_TAG = AddSubjectActivity.class.getSimpleName();

    // Day gender spinner object
    private Spinner mDaySpinner;

    // Selected day
    private int mDay = TimetableEntry.DAY_NO_DAY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        setTitle(getString(R.string.title_add_subject));

        // Getting reference to the spinner
        mDaySpinner = (Spinner)findViewById(R.id.day_spinner);

        // Setup the spinner
        setupSpinner();

    }

    void setupSpinner() {

        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter daySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_week_days, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mDaySpinner.setAdapter(daySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.day_monday))) {
                        mDay = TimetableEntry.DAY_MONDAY;
                    } else if (selection.equals(getString(R.string.day_tuesday))) {
                        mDay = TimetableEntry.DAY_TUESDAY;
                    } else if (selection.equals(getString(R.string.day_wednesday))) {
                        mDay = TimetableEntry.DAY_WEDNESDAY;
                    } else if (selection.equals(getString(R.string.day_thursday))) {
                        mDay = TimetableEntry.DAY_THURSDAY;
                    } else if (selection.equals(getString(R.string.day_friday))) {
                        mDay = TimetableEntry.DAY_FRIDAY;
                    } else if (selection.equals(getString(R.string.day_saturday))) {
                        mDay = TimetableEntry.DAY_SATURDAY;
                    } else {
                        mDay = TimetableEntry.DAY_SUNDAY;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDay = TimetableEntry.DAY_NO_DAY;
            }
        });

    }
}
