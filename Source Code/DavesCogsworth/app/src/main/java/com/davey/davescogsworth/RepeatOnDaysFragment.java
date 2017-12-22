package com.davey.davescogsworth;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

/**
 * Created by Davey on 11/26/2017.
 */


public class RepeatOnDaysFragment
        extends ListFragment{

    Button returnButton;

    public RepeatOnDaysFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        LayoutInflater inflater = getActivity().getLayoutInflater();

        getListView().addFooterView(inflater.inflate(
                R.layout.repeat_list_footer, null));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.repeat_days_list,
                container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CustomRepeatDaysFragAdapter customRepeatDaysFragAdapter = new CustomRepeatDaysFragAdapter(
                 getActivity(), getResources().getStringArray(R.array.days));

        setListAdapter(customRepeatDaysFragAdapter);
    }

    @Override
    public void onListItemClick(ListView listview, View view, int position, long id) {
        super.onListItemClick(listview, view, position, id);

        AlarmDetailsActivity alarmDetails =
                (AlarmDetailsActivity) getActivity();

        final CheckedTextView day_item =
                 view.findViewById(R.id.day_check_input);

        String temp = "";

        if (day_item.isChecked()) {
            temp += String.valueOf(0);
            (alarmDetails.alarmRepeat) =
                    new StringBuilder((alarmDetails.alarmRepeat))
                            .replace(position,position + 1, temp)
                            .toString();
            day_item.setChecked(false);
        }
        else {
            day_item.setChecked(true);
            temp += String.valueOf(1);
            (alarmDetails.alarmRepeat) =
                    new StringBuilder((alarmDetails.alarmRepeat))
                            .replace(position,position + 1, temp)
                            .toString();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}