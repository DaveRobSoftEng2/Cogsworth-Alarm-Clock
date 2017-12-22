package com.davey.davescogsworth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

/**
 * Created by Davey on 11/30/2017.
 */

public class CustomRepeatDaysFragAdapter extends BaseAdapter {

        String[] names;
        Context context;
        LayoutInflater inflater;
        String value;

        public CustomRepeatDaysFragAdapter(Context context, String[] names) {
            this.context = context;
            this.names = names;
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            inflater = (LayoutInflater.from(context));

            View item = inflater.inflate(R.layout.repeat_days_list_item, parent, false);

            final CheckedTextView day_item =
                     item.findViewById(R.id.day_check_input);

            day_item.setText(names[position]);

            AlarmDetailsActivity alarmDetails =
                    (AlarmDetailsActivity) context;

            if ( Character.toString(alarmDetails.alarmRepeat.charAt(position)).equals("0")) {
                day_item.setChecked(false);
            }
            else {
                day_item.setChecked(true);
            }

            return item;
        }
}
