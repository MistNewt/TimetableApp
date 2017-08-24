package com.example.android.timetable;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.timetable.data.TimetableContract.*;

/**
 * Created by Sudhanshu on 22-08-2017.
 */

public class SubjectCursorAdapter extends CursorAdapter {

    public SubjectCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView attendanceTextView = (TextView) view.findViewById(R.id.attendance);

        int nameColIndex = cursor.getColumnIndex(SubjectEntry.COLUMN_SUBJECT_NAME);
        int presentColIndex = cursor.getColumnIndex(SubjectEntry.COLUMN_CLASSES_PRESENT);
        int totalColIndex = cursor.getColumnIndex(SubjectEntry.COLUMN_TOTAL_CLASSES);

        int presentClasses = cursor.getInt(presentColIndex);
        int totalClasses = cursor.getInt(totalColIndex);

        double attendance = (double) presentClasses / totalClasses;

        nameTextView.setText(cursor.getString(nameColIndex));
        attendanceTextView.setText(context.getString(R.string.attendance) + ": " +
                Double.toString(attendance));

    }
}
