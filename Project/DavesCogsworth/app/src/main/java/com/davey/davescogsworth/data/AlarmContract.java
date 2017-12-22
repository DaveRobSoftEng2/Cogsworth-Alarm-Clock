package com.davey.davescogsworth.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Davey on 12/17/2017.
 */

public class AlarmContract {

    /** Empty constructor */
    private AlarmContract(){}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.davey.davescogsworth";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.Alarmdiary/Alarm/ is a valid path for
     * looking at item data. content://com.example.android.AlarmAlarmdiary/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    /** Prospective Alarm URL */
    public static final String PATH_ALARM = "alarm";

    /**
     * Inner class that defines constant values for the items database table.
     * Each entry in the table represents a single Alarm.
     */
    public static final class AlarmEntry implements BaseColumns {

        /** The content URI to access the Alarm data in the provider */
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ALARM);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of AlarmAlarms.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/"
                        + CONTENT_AUTHORITY
                        + "/"
                        + PATH_ALARM;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single Alarm.
         */
        public static final String CONTENT_RESTAURANT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/"
                        + CONTENT_AUTHORITY
                        + "/"
                        + PATH_ALARM;

        /** Name of database table for Alarms */
        public final static String TABLE_NAME = "alarm";

        /**
         * Unique ID number for the alarm (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the Alarm.
         *
         * Type: TEXT
         */
        public final static String COLUMN_ALARM_FACE ="face";
        /**
         * Name of the Alarm.
         *
         * Type: TEXT
         */
        public final static String COLUMN_ALARM_TIME ="time";

        /**
         * Address of the Alarm.
         *
         * Type: TEXT
         */
        public final static String COLUMN_ALARM_NAME = "name";

        /**
         * Phone number for the Alarm.
         *
         * Type: TEXT
         */
        public final static String COLUMN_ALARM_OPTION = "option";

        /**
         * Note for the Alarm.
         *
         * Type: TEXT
         */
        public final static String COLUMN_ALARM_REPEAT = "repeat";

        /**
         * Main image for the Alarm.
         *
         * Type: Image
         */
        public final static String COLUMN_ALARM_VIBRATE = "vibrate";
        /**
         * Main image for the Alarm.
         *
         * Type: Image
         */
        public final static String COLUMN_ALARM_REQ_CODE = "requestCode";

    }
}
