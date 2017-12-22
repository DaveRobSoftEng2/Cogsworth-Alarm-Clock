package com.davey.davescogsworth;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.davey.davescogsworth.data.AlarmContract;

public class AlarmListActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor> {

    /** TextView that is displayed when the list is empty */
    private TextView emptyView;

    /** * Adapter for the list of Alarm  */
   // private AlarmAdapter mAdapter;

    /** * Adapter for the list of Alarm  */
    private AlarmCursoryAdapter mCursorAdapter;

    private static final int ALARM_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_list_activity);

        // Find a reference to the {@link ListView} in the layout
        // Find the ListView which will be populated with the item data
        final ListView alarmListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        alarmListView.setEmptyView(emptyView);

        // Create a new adapter that takes an empty list of alarms as input
        mCursorAdapter = new AlarmCursoryAdapter(this, null);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        alarmListView.setAdapter(mCursorAdapter);

        // Setup the item click listener to send to Alarm Details
        alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, final long id) {

                final String face = ((TextView) view.findViewById(R.id.item_time)).getText().toString();
                final String name = ((TextView) view.findViewById(R.id.alarm_name)).getText().toString();
                final String repeat = ((TextView) view.findViewById(R.id.alarm_repeat)).getText().toString();

                final Switch vibrate = ((Switch)
                        view.findViewById(R.id.alarm_vibrate));

                final Boolean vibrateVal = vibrate.isChecked();

                // Create new intent to go to {@link EditorActivity}
                //Intent intent = new Intent(String.valueOf(AlarmDetailsActivity.class));
                Intent intent = new Intent(
                        AlarmListActivity.this,
                        AlarmDetailsActivity.class);

                // Form the content URI that represents the specific AlarmAlarm that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link VisitedAlarmEntry#CONTENT_URI}.
                Uri currentAlarmUri =
                        ContentUris.withAppendedId(
                                AlarmContract.AlarmEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentAlarmUri);

                intent.putExtra("NameOfCallingClass", AlarmListActivity.this.toString());

                // Launch the {@link EditorActivity} to display the data for the current Alarm.
                startActivity(intent);
            }
        });
        // Kick off the loader
        getLoaderManager().initLoader(ALARM_LOADER, null, this);
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
                AlarmContract.AlarmEntry.COLUMN_ALARM_VIBRATE };

        return new CursorLoader(this,
                AlarmContract.AlarmEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    /*
     * Stubs for methods to be implemented later. Will need for views.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        // Update {@link AlarmCursorAdapter} with this new cursor containing updated Alarm data
        mCursorAdapter.swapCursor(data);
    }

    /*
     * Stubs for methods to be implemented later. Will need for views.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }

    /** This method is called when the back button is pressed */
    @Override
    public void onBackPressed() {
        finish();
    }
}
