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

    // Selected day
    private int mDay = TimetableEntry.DAY_NO_DAY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        setTitle(getString(R.string.title_add_subject));

    }

}
