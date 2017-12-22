package com.davey.davescogsworth.data;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Davey on 12/17/2017.
 */
import com.davey.davescogsworth.data.AlarmContract.AlarmEntry;

public class AlarmDBHelper extends SQLiteOpenHelper {

    /** Logger tag for the Alarm DB class */
    public static final String LOG_TAG = AlarmDBHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "alarm.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 6;

    public long getDBSize() {
        SQLiteDatabase db = this.getReadableDatabase();
        long size  = DatabaseUtils.queryNumEntries(db, AlarmEntry.TABLE_NAME);
        db.close();
        return size;
    }

    /**
     * Constructs a new instance of {@link AlarmDBHelper}.
     * @param context of the app
     */
    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     *
     * @param db of type SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the Alarms table
        String SQL_CREATE_ALARM_TABLE =  "CREATE TABLE " +
                AlarmEntry.TABLE_NAME + " ("
                + AlarmEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AlarmEntry.COLUMN_ALARM_FACE + " TEXT , "
                + AlarmEntry.COLUMN_ALARM_TIME + " TEXT NOT NULL, "
                + AlarmEntry.COLUMN_ALARM_NAME + " TEXT , "
                + AlarmEntry.COLUMN_ALARM_OPTION + " TEXT , "
                + AlarmEntry.COLUMN_ALARM_REPEAT + " TEXT , "
                + AlarmEntry.COLUMN_ALARM_VIBRATE + " INTEGER , "
                + AlarmEntry.COLUMN_ALARM_REQ_CODE + " INTEGER ) ; ";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ALARM_TABLE);
    }

    /**
     * Stub for onUpgrade. To be used when upgrading the db.
     *
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AlarmEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}