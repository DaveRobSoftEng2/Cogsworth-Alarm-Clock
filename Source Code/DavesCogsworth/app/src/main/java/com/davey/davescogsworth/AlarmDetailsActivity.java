
package com.davey.davescogsworth;

import android.app.AlarmManager;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.davey.davescogsworth.data.AlarmContract;
import com.davey.davescogsworth.data.AlarmContract.AlarmEntry;
import com.davey.davescogsworth.data.AlarmDBHelper;

import java.util.Calendar;

public class AlarmDetailsActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    /** EditText field to enter the Alarm's name */
    private TextInputEditText editableAlarmLabel;

    String alarmLabelString = "";

    private long timeVal = 0;

    /** Textview field to enter the Alarm's name */
    private String alarmOption;

    /** Temporary Checkbox field to enter the Alarms's repeat Setting */
     String alarmRepeat = "0000000";

    /** Checkbox field to enter the Alarms's Vibration Setting */
    private boolean alarmVibrate = false;
    CheckBox vibrate_checkbox;

    /** Boolean flag that keeps track of whether the Alarm
     *  has been edited (true) or not (false) */
    private boolean mAlarmHasChanged = false;

    /** Content URI for the existing Alarm (null if it's a new Alarm) */
    private Uri mCurrentAlarmUri;

    Boolean fragMade = false;

    AlarmManager alarm_manager;

    TimePicker alarm_timepicker;

    PendingIntent pending_intent;

    RepeatOnDaysFragment frag;

    FragmentManager fragmentManager = getSupportFragmentManager();

    // Setup FAB to open AlarmDetailsActivity
    FloatingActionButton addAlarmFab;

    // Setup FAB to open Alarm List
    FloatingActionButton cancelFab;

    // Setup FAB to open Alarm List
    Button repeatButton;

    Calendar calendar;

    //Intent is for Alarm Receiver
    Intent alarmReceivingIntent;

    int requestCode = 0;

    Spinner alarmOptionspinner;

    String face = "";

    private static final int ALARM_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_details_activity);

        editableAlarmLabel = findViewById(R.id.alarmLabel);

        vibrate_checkbox = (CheckBox) findViewById(R.id.alarm_vibrate);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new Alarm or editing an existing one.
        Intent intent = getIntent();
        mCurrentAlarmUri = intent.getData();

        // NEW ALARM
        //if (mCurrentAlarmUri == null || intent.getStringExtra("NameOfCallingClass") != null) {
        if (mCurrentAlarmUri == null) {
                // This is a new Alarm, so change the app bar to say "Add a Alarm"
            setTitle("New Alarm");

            mCurrentAlarmUri = null;
            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a Alarm that hasn't been created yet.)
            invalidateOptionsMenu();

        } //old alarm
        else {
            // Otherwise this is an existing alarm, so change app bar to say "Edit Alarm"
            setTitle("Edit Alarm");

            // Kick off the loader
            getLoaderManager().initLoader(ALARM_LOADER, null, this);
        }

        //Intent is for Alarm Receiver
        alarmReceivingIntent = new Intent(AlarmDetailsActivity.this, AlarmReceiver.class);

        frag = new RepeatOnDaysFragment();

        //We're picking up Alarm Service
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //Linking TimePicker
        alarm_timepicker = findViewById(R.id.timePicker);

        //we need calender for dates
        calendar = Calendar.getInstance();

        // Setup FAB to open AlarmDetailsActivity
        addAlarmFab = findViewById(R.id.add_alarm_fab);

        // Setup FAB to open Alarm List
        cancelFab = findViewById(R.id.cancel_fab);

        // Setup FAB to open Alarm List
        repeatButton = findViewById(R.id.repeat_days_frag_fire);

        // Spinner element
        alarmOptionspinner = (Spinner) findViewById(R.id.alarmOptionSpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.alarm_spin_options, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        alarmOptionspinner.setAdapter(adapter);

        cancelFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fragMade) {
                    fragmentShowHide();
                }
                else {
                    fragMade = true;
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.frag_container, frag);
                    fragmentTransaction.addToBackStack("myFrag");
                    fragmentTransaction.show(frag);
                    fragmentTransaction.commit();
                }
            }
        });

        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fragMade) {
                    fragmentShowHide();
                }
                else {
                    fragMade = true;
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.frag_container, frag);
                    fragmentTransaction.addToBackStack("myFrag");
                    fragmentTransaction.show(frag);
                    fragmentTransaction.commit();
                }
            }
        });

    }/***********************************END onCreate************/

    //This is just for future functionality.
    public void optionSpinner(String option) {

        switch (option) {
            case "Alarm": //Alarm
                // Whatever you want to happen when the first item gets selected
                alarmOption = "Alarm";
            break;
            case "Soundcloud": // Soundcloud
                // Whatever you want to happen when the second item gets selected
                alarmOption = "Soundcloud";
                break;
            case "Spotify": //Spotify
                // Whatever you want to happen when the third item gets selected
                alarmOption = "Spotify";
                break;
            case "Pandora": //Pandora
                // Whatever you want to happen when the fourth item gets selected
                alarmOption = "Pandora";
                break;
            case "Weather": //Weather
                // Whatever you want to happen when the fifth item gets selected
                alarmOption = "Weather";
                break;
            case  "Reminder": //Reminder
                // Whatever you want to happen when the sixth item gets selected
                alarmOption = "Reminder";
                break;
        }
    }


    //This is just for future functionality.
    public int getOptionPositionSpinner(String option) {

        switch (option) {
            case "Alarm": //Alarm
                // Whatever you want to happen when the first item gets selected
                return 0;
            case "Soundcloud": // Soundcloud
                // Whatever you want to happen when the second item gets selected
                return 1;
            case "Spotify": //Spotify
                // Whatever you want to happen when the third item gets selected
                return 2;
            case "Pandora": //Pandora
                // Whatever you want to happen when the fourth item gets selected
                return 3;
            case "Weather": //Weather
                // Whatever you want to happen when the fifth item gets selected
                return 4;
            case  "Reminder": //Reminder
                // Whatever you want to happen when the sixth item gets selected
                return 5;
            default:
                return -1;
        }
    }

    /** Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     *
     * @param discardButtonClickListener is the click listener for what to do when
     * the user confirms they want to discard their changes**/
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing,
                new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {// User clicked the "Keep editing" button, so dismiss the dialog
                    // and continue editing the item.
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveAlarm() {
        alarmLabelString = editableAlarmLabel.getText().toString();

        optionSpinner(alarmOptionspinner.getSelectedItem().toString());

        calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
        calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

        int hour = alarm_timepicker.getHour();
        int minute = alarm_timepicker.getMinute();

        String hour_string = String.valueOf(hour);
        String minute_string = String.valueOf(minute);
        String am_pm = "";

        if (hour > 12) {
            hour_string = String.valueOf(hour - 12);
        }
        if (minute < 10) {
            minute_string = "0" + String.valueOf(minute);
        }
        if (hour > 12) {
            am_pm = "PM";
        } else {
            am_pm = "AM";
        }
         face += hour_string;
         face += ":";
         face += minute_string;
         face += ":";
         face += am_pm;

        alarmVibrate =  vibrate_checkbox.isChecked();

        // Appending necessary Information to alarmReceivingIntent
        timeVal =  calendar.getTimeInMillis();

        // Check if this is supposed to be a new alarm
        // and check if all the fields in the editor are blank
        if (TextUtils.isEmpty(alarmLabelString)) {
            // Since no fields were modified, we can return early without creating a new Alarm.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            Context context = getApplicationContext();
            Toast.makeText(
                    context,
                    getString(R.string.incomplete_form),
                    Toast.LENGTH_SHORT).show();
        }
        else if(mCurrentAlarmUri == null ) {
            // get request code from db size
            AlarmDBHelper db = new AlarmDBHelper(getApplicationContext());
            long size = db.getDBSize();
            requestCode = (int) size;
            databaseEntry();
        }
        else {
            databaseEntry();
        }
    }

    public void databaseEntry() {
        ContentValues values = new ContentValues();
        values.put(AlarmContract.AlarmEntry.COLUMN_ALARM_FACE, face);
        values.put(AlarmContract.AlarmEntry.COLUMN_ALARM_TIME, timeVal);
        values.put(AlarmContract.AlarmEntry.COLUMN_ALARM_NAME, alarmLabelString);
        values.put(AlarmContract.AlarmEntry.COLUMN_ALARM_OPTION, alarmOption);
        values.put(AlarmContract.AlarmEntry.COLUMN_ALARM_REPEAT, alarmRepeat);
        values.put(AlarmContract.AlarmEntry.COLUMN_ALARM_VIBRATE, alarmVibrate);
        values.put(AlarmContract.AlarmEntry.COLUMN_ALARM_REQ_CODE, requestCode);

        // integer value. Use 0 by default.
        // Determine if this is a new or existing Alarm by checking if mCurrentRestaAlarmurantUri is null or not
        if (mCurrentAlarmUri == null) {
            // This is a NEW Alarm, so insert a new Alarm into the provider,
            // returning the content URI for the new Alarm.
            Uri newUri = getContentResolver().insert(AlarmEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(AlarmDetailsActivity.this, getString(R.string.editor_insert_alarm_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(AlarmDetailsActivity.this, getString(R.string.editor_insert_alarm_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING Alarm, so update the Alarm with content URI: mCurrentAlarmUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentAlarmUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentAlarmUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(AlarmDetailsActivity.this, getString(R.string.editor_update_alarm_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(AlarmDetailsActivity.this, getString(R.string.editor_update_alarm_succesful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        createAlarm();
    }

    public void createAlarm() {

        alarmReceivingIntent.putExtra("face", face);
        alarmReceivingIntent.putExtra("time", timeVal);
        alarmReceivingIntent.putExtra("label", alarmLabelString);
        alarmReceivingIntent.putExtra("option", alarmOption);
        alarmReceivingIntent.putExtra("repeat", alarmRepeat);
        alarmReceivingIntent.putExtra("vibrate", alarmVibrate);

        pending_intent = PendingIntent.getBroadcast(
                AlarmDetailsActivity.this,
                requestCode,
                alarmReceivingIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

            /*alarm_manager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pending_intent);*/

        // Set the alarm to start at approximately 2:00 p.m.
            /*Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 14);*/

        alarm_manager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                timeVal,
                AlarmManager.INTERVAL_DAY,
                pending_intent);

        finish();
    }

    private void fragmentShowHide() {
        if (frag.isHidden()) {
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();

            fragmentTransaction
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .show(frag)
                    .commit();
        }
        else {
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();

            fragmentTransaction
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .hide(frag)
                    .commit();
        }
    }

    /**
     * Perform the deletion of the Alarm in the database.
     */
    private void deleteAlarm() {
        // Only perform the delete if this is an existing Alarm.
        if (mCurrentAlarmUri != null) {
            // Call the ContentResolver to delete the Alarm at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentAlarmUri
            // content URI already identifies the Alarm that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentAlarmUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_alarm_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_alarm_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        PendingIntent.getBroadcast(
                AlarmDetailsActivity.this,
                requestCode,
                alarmReceivingIntent,
                PendingIntent.FLAG_UPDATE_CURRENT).cancel();


        //PendingIntent.getBroadcast(context, 0, intent,
         //       PendingIntent.FLAG_UPDATE_CURRENT).cancel();
        // Close the activity
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }


    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new item, hide the "Delete" menu item.
        if (mCurrentAlarmUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    /**
     * Prompt the user to confirm that they want to delete this Alarm.
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the Alarm.
                deleteAlarm();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the Alarm.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save item to database
                saveAlarm();
                // Exit activity
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the item hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mAlarmHasChanged) {
                    NavUtils.navigateUpFromSameTask(AlarmDetailsActivity.this);
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
                                NavUtils.navigateUpFromSameTask(AlarmDetailsActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializes the cursor with the activity.
     *
     * @param i
     * @param bundle
     * @return cursor
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                AlarmContract.AlarmEntry._ID,
                AlarmContract.AlarmEntry.COLUMN_ALARM_FACE,
                AlarmContract.AlarmEntry.COLUMN_ALARM_TIME,
                AlarmContract.AlarmEntry.COLUMN_ALARM_NAME,
                AlarmContract.AlarmEntry.COLUMN_ALARM_OPTION,
                AlarmContract.AlarmEntry.COLUMN_ALARM_REPEAT,
                AlarmContract.AlarmEntry.COLUMN_ALARM_VIBRATE,
                AlarmContract.AlarmEntry.COLUMN_ALARM_REQ_CODE  };

        return new CursorLoader(this,
                mCurrentAlarmUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of Alarm attributes that we're interested in
            int timeColumnIndex = cursor.getColumnIndex(AlarmEntry.COLUMN_ALARM_TIME);
            int nameColumnIndex = cursor.getColumnIndex(AlarmEntry.COLUMN_ALARM_NAME);
            int optionColumnIndex = cursor.getColumnIndex(AlarmEntry.COLUMN_ALARM_OPTION);
            int repeatColumnIndex = cursor.getColumnIndex(AlarmEntry.COLUMN_ALARM_REPEAT);
            int vibrateColumnIndex = cursor.getColumnIndex(AlarmEntry.COLUMN_ALARM_VIBRATE);
            int reqCodeColumnIndex = cursor.getColumnIndex(AlarmEntry.COLUMN_ALARM_REQ_CODE);

            // Extract out the value from the Cursor for the given column index
            int time = cursor.getInt(timeColumnIndex);
            String label = cursor.getString(nameColumnIndex);
            String option = cursor.getString(optionColumnIndex);
            String repeat = cursor.getString(repeatColumnIndex);
            int itemVibrate = cursor.getInt(vibrateColumnIndex);
            int req = cursor.getInt(reqCodeColumnIndex);

            // Update the views on the screen with the values from the database
            editableAlarmLabel.setText(label);
            requestCode = req;
            alarmRepeat = repeat;
            alarmOptionspinner.setSelection( getOptionPositionSpinner(option) );
            if(itemVibrate > 0) {
                vibrate_checkbox.setChecked(true);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Update the views on the screen with the values from the database
        editableAlarmLabel.setText("");
    }

}

