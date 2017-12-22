package com.davey.davescogsworth;

import java.util.Calendar;

/**
 * Created by rob on 11/15/2017.
 */

public class Weather {

    private String mLocation;
    private String mDescription;
    private String mCurrentTemp;
    private String mLowTemp;
    private String mHighTemp;

    public Weather(){
        mLocation = "";
        mDescription = "";
        mCurrentTemp = "";
        mLowTemp = "";
        mHighTemp = "";
    }

    public Weather(String location, String description, String temp, String low, String high){
        mLocation = location;
        mDescription = description;
        mCurrentTemp = temp;
        mLowTemp = low;
        mHighTemp = high;
    }

    /**
     * These are the accessor functions
     * */
    public String getLocation(){return mLocation;}
    public String getDescription(){return mDescription;}
    public String getCurrentTemp(){return mCurrentTemp;}
    public String getLowTemp(){return mLowTemp;}
    public String getHighTemp(){return mHighTemp;}

    /**
     * These are the mutator functions
     * */
    public void setLocation(String loc){
        mLocation = loc;
        return;
    }

    public void setDescription(String desc){
        mDescription = desc;
        return;
    }

    public void setCurrentTemp(String temp){
        mCurrentTemp = removeDecimals(temp);
        return;
    }

    public void setLowTemp(String temp){
        mLowTemp = removeDecimals(temp);
        return;
    }

    public void setHighTemp(String temp){
        mHighTemp = removeDecimals(temp);
        return;
    }

    public String reportWeather(){

        String weatherReport;

        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY); // get the current time of the day

        if(currentHour < 12 && currentHour > 0){
            weatherReport = "Good morning, ";
        } else if(currentHour >= 12 && currentHour < 18) {
            weatherReport = "Good afternoon, ";
        } else {
            weatherReport = "Good evening, ";
        }

        weatherReport += "Today in " + mLocation + " the forecast is " + mDescription + " with a high of " + mHighTemp
                + " and a low of " + mLowTemp + ", it is currently " + mCurrentTemp + " degrees";

        return weatherReport;
    }

    private String removeDecimals(String s){
        String string = "";

        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) != '.'){
                string += s.charAt(i);
            } else {
                break;
            }
        }
        return string;
    }
}
