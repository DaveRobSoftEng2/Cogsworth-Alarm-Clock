package com.davey.davescogsworth;

/**
 * Updated by Davey on 12/8/2017.
 * API contract for the Alarm object.
 */


public class Alarm {
    /** Time of Alarm */
    private String mFace;

    /** Time of Alarm */
    private String mTime;

    /** Name of Alarm */
    private String mLabel;

    /** Alarm Option of Alarm */
    private String mAlarmOption;

    /** repeat days of Alarm */
    private String mRepeat;

    /** vibrate Bool of Alarm */
    private String mVibrate;

    /** vibrate Bool of Alarm */
    private String mReqCode;

    public Alarm(String face,
                 String time,
                 String label,
                 String option,
                 String repeat,
                 String vibrate,
                 String requestCode){
        mFace = face;
        mTime = time;
        mLabel = label;
        mAlarmOption = option;
        mRepeat = repeat;
        mVibrate = vibrate;
        mReqCode = requestCode;
    }

    public String getAlarmFace(){ return mFace; }

    public String getAlarmTime(){ return mTime; }

    public String getLabel(){ return mLabel; }

    public String getOption(){ return mAlarmOption; }

    public String getRepeat(){ return mRepeat; }

    public String getVibrate(){ return mVibrate; }

    public String getRequestCode(){ return mReqCode; }
}
