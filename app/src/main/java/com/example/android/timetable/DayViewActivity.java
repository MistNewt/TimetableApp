package com.example.android.timetable;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.android.timetable.data.TimetableContract.*;

public class DayViewActivity extends AppCompatActivity {

    public static final String LOG_TAG = DayViewActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ArrayList<CardItem> mCardList;
    private MyCardAdapter mMyCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);

        // Get reference to the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyler_view);

        // initialize mCardList
        mCardList = new ArrayList<>();
        //initialize adapter
        mMyCardAdapter = new MyCardAdapter(this, mCardList);

        // setting the layout manager to display 1 column (card) per row
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Item Decoration for the view
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMyCardAdapter);

        generateFakeData(); // Fake data for filling the initial cards
        // Setup FAB to open AddSubjectActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DayViewActivity.this, AddSubjectActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Generating fake data
     */
    private void generateFakeData() {
        mCardList.add(new CardItem("Monday", 0, 0));
        mCardList.add(new CardItem("Tuesday", 0, 0));
        mCardList.add(new CardItem("Wednesday", 0, 0));
        mCardList.add(new CardItem("Thursday", 0, 0));
        mCardList.add(new CardItem("Friday", 0, 0));
        mCardList.add(new CardItem("Saturday", 0, 0));

        mMyCardAdapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                // spacing - column * ((1f / spanCount) * spacing)
                outRect.left = spacing - column * spacing / spanCount;
                // (column + 1) * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                // column * ((1f / spanCount) * spacing)
                outRect.left = column * spacing / spanCount;
                // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
