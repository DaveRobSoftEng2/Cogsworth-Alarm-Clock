package com.davey.davescogsworth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Updated by Davey on 12/9/2017.
 */

public class AlarmAdapter extends ArrayAdapter<Alarm> {
    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param alarm         A List of Location objects to display in a list
     */
    public AlarmAdapter(Context context, ArrayList<Alarm> alarm) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, alarm);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.alarm_list_item, parent, false);
        }

        // Get the {@link location} object located at this position in the list
        Alarm currentAlarm = getItem(position);

        //Name
        // Find the TextView in the alarm_list_item.xmlem.xml layout with the ID version_name
        TextView alarmView = listItemView.findViewById(R.id.item_time);
        alarmView.setText(currentAlarm.getAlarmTime());

        //Alarm Label / Name
        // Find the TextView in the alarm_list_item.xmlem.xml layout with the ID version_number
        TextView alarmLabelTextView = (TextView) listItemView.findViewById(R.id.alarm_name);
        // Get the version number from the current location object and
        // set this text on the number TextView
        alarmLabelTextView.setText(currentAlarm.getLabel());

        //Repeat
        // Find the TextView in the alarm_list_itemt_item.xml layout with the ID version_number
        TextView phoneTextView = (TextView) listItemView.findViewById(R.id.alarm_repeat);
        // Get the version number from the current location object and
        // set this text on the number TextView
        phoneTextView.setText(currentAlarm.getRepeat());

        return listItemView;
    }
}
