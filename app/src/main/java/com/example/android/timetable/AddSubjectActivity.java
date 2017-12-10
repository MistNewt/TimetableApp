package com.example.android.timetable;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.timetable.data.TimetableContract.*;


/**
 * Created by Sudhanshu on 22-08-2017.
 */

public class AddSubjectActivity extends AppCompatActivity {

    // Log tag to be used
    private final String LOG_TAG = AddSubjectActivity.class.getSimpleName();

    // Subject has changed flag
    private boolean mSubjectHasChanged = false;

    // Subject name EditText
    EditText mSubjectNameEditText;
    // Classes present EditText
    EditText mClassesPresentEditText;
    // All classes EditText
    EditText mTotalClassesEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        setTitle(getString(R.string.title_add_subject));

        // Get reference to EditTexts
        mSubjectNameEditText = (EditText)findViewById(R.id.edit_subject_name);
        mClassesPresentEditText = (EditText)findViewById(R.id.edit_classes_present);
        mTotalClassesEditText = (EditText)findViewById(R.id.edit_total_classes);

        // Setting up onTouchListeners
        mSubjectNameEditText.setOnTouchListener(mTouchListner);
        mClassesPresentEditText.setOnTouchListener(mTouchListner);
        mTotalClassesEditText.setOnTouchListener(mTouchListner);

    }

    // Setting up on touch listener
    // for warning the user when pet has been changed and not saved

    private View.OnTouchListener mTouchListner = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mSubjectHasChanged = true;
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_add_subjects, menu);
        return true;
    }


    /**
     * What happens on selecting the option
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Insert new subject
                saveSubject();
                // Return to parent activity
                finish();
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mSubjectHasChanged) {
                    NavUtils.navigateUpFromSameTask(AddSubjectActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(AddSubjectActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_alert);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveSubject() {

        String subjectName = mSubjectNameEditText.getText().toString().trim();
        String classesPresentString = mClassesPresentEditText.getText().toString().trim();
        String totalClassesString = mTotalClassesEditText.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(SubjectEntry.COLUMN_SUBJECT_NAME,subjectName);
        int classesPresent = 0,totalClasses= 0;
        if(!TextUtils.isEmpty(classesPresentString)) {
            classesPresent = Integer.parseInt(classesPresentString);
            values.put(SubjectEntry.COLUMN_CLASSES_PRESENT,classesPresent);
        }
        if(!TextUtils.isEmpty(totalClassesString)) {
            totalClasses = Integer.parseInt(totalClassesString);
            values.put(SubjectEntry.COLUMN_TOTAL_CLASSES,totalClasses);
        }

        Uri uri = getContentResolver().insert(SubjectEntry.CONTENT_URI,values);
        //Display appropriate toast message
        if (uri == null)
            Toast.makeText(this, getString(R.string.toast_subject_save_failed),
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, getString(R.string.toast_subject_save_success),
                    Toast.LENGTH_SHORT).show();
    }
}
