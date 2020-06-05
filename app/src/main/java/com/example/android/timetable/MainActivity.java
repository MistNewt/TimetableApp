package com.example.android.timetable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private TextView mDayView;
    private TextView mSubjectsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDayView = (TextView) findViewById(R.id.dayview_activity);
        mSubjectsView = (TextView) findViewById(R.id.subjects_activity);

        // Setting ClickListeners to open respective activities
        mDayView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DayViewActivity.class);
                startActivity(intent);
            }
        });

        mSubjectsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SubjectsActivity.class);
                startActivity(intent);
            }
        });
    }
}
