package com.davey.davescogsworth;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.davey.davescogsworth.data.AlarmContract;

/**
 * Created by Davey on 12/17/2017.
 */

public class AlarmCursoryAdapter  extends CursorAdapter {

    String repeatDays = "";

    Boolean justWent = false;
    /**
     * Constructs a new {@link AlarmCursoryAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public AlarmCursoryAdapter(Context context, Cursor c){
        super(context,c, 0);
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
        // Inflate a list item view using the layout specified in Alarm_list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.alarm_list_item, parent, false);
    }

    /**
     * This method binds the Alarm data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current Alarm can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView alarmFace = (TextView) view.findViewById(R.id.item_time);
        TextView alarmLabel = (TextView) view.findViewById(R.id.alarm_name);
        TextView alarmRepeat = (TextView) view.findViewById(R.id.alarm_repeat);
        Switch alarmVibrateSwitch = (Switch) view.findViewById(R.id.alarm_vibrate);

        // Find the columns of item attributes that we're interested in
        int timeFaceColumnIndex = cursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_ALARM_FACE);
        int timeColumnIndex = cursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_ALARM_TIME);
        int labelColumnIndex = cursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_ALARM_NAME);
        int repeatColumnIndex = cursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_ALARM_REPEAT);
        int vibrateColumnIndex = cursor.getColumnIndex(AlarmContract.AlarmEntry.COLUMN_ALARM_REPEAT);

        // Read the item attributes from the Cursor for the current item
        String itemFace = cursor.getString(timeFaceColumnIndex);
        String itemLabel = cursor.getString(labelColumnIndex);
        //This is going to be a String of Bools (array of bools)
        String itemRepeats = cursor.getString(repeatColumnIndex);
        int itemVibrate = cursor.getInt(vibrateColumnIndex);

        //get the repeat days
        //getBlob

        //need a switch here
        for(int i = 0; i < itemRepeats.length() - 1; i++) {
            if(justWent) {
                repeatDays += ", ";
            }
            getDayRepeat(i, Character.getNumericValue(itemRepeats.charAt(i)));
        }

        interpretData();

        // Update the TextViews with the attributes for the current item
        //Log.d(" In Cursory Adapter ", "  time String is :"
        //        + String.toString(itemTime) );
        alarmFace.setText(itemFace);
        alarmLabel.setText(itemLabel);
        alarmRepeat.setText(repeatDays);
        if(itemVibrate > 0) {
            alarmVibrateSwitch.setChecked(true);
        }
        else {
            alarmVibrateSwitch.setChecked(false);
        }
        return;
    }

    public void getDayRepeat(int position , int bool) {

        switch (position) {
            case 0: //Monday
                 if(bool > 0){
                     repeatDays += "M";
                     justWent = true;
                 }
                 else {
                     justWent = false;
                 }
                break;
            case 1: // Tuesday
                if(bool > 0){
                    repeatDays += "Tu";
                    justWent = true;
                }
                else {
                    justWent = false;
                }
                break;
            case 2: //Wednesday
                if(bool > 0){
                    repeatDays += "We";
                    justWent = true;
                }
                else {
                    justWent = false;
                }
                break;
            case 3: //Thursday
                if(bool > 0){
                    repeatDays += "Th";
                    justWent = true;
                }
                else {
                    justWent = false;
                }
                break;
            case 4: //Friday
                if(bool > 0){
                    repeatDays += "Fr";
                    justWent = true;
                }
                else {
                    justWent = false;
                }
                break;
            case 5: //Saturday
                if(bool > 0){
                    repeatDays += "Sa";
                    justWent = true;
                }
                else {
                    justWent = false;
                }
                break;
            case 6: //Sunday
                if(bool > 0){
                    repeatDays += "Su";
                    justWent = true;
                }
                else {
                    justWent = false;
                }
                break;
        }
    }

    public void interpretData() {
        //All Week
        if(repeatDays.contains("M") &&
                repeatDays.contains("Tu") &&
                repeatDays.contains("We") &&
                repeatDays.contains("Th") &&
                repeatDays.contains("Fr") &&
                repeatDays.contains("Sa") &&
                repeatDays.contains("Su") ) {
            repeatDays = "Everyday";
        }
        //Weekdays
        else if(repeatDays.contains("M") &&
                repeatDays.contains("Tu") &&
                repeatDays.contains("We") &&
                repeatDays.contains("Th") &&
                repeatDays.contains("Fr")) {
            repeatDays = "Weekdays";
        }
        //Weekend
        else if(repeatDays.contains("Fr") &&
                repeatDays.contains("Sa") &&
                repeatDays.contains("Su") ) {
            repeatDays = "Weekends";
        }
        //Never
        if(!repeatDays.contains("M") &&
                !repeatDays.contains("Tu") &&
                !repeatDays.contains("We") &&
                !repeatDays.contains("Th") &&
                !repeatDays.contains("Fr") &&
                !repeatDays.contains("Sa") &&
                !repeatDays.contains("Su") ) {
            repeatDays = "Never";
        }
    }
}
