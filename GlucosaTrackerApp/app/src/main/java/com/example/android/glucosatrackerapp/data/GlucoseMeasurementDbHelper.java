package com.example.android.glucosatrackerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.glucosatrackerapp.data.GlucoseMeasurementContract.GlucoseMeasurementEntry;

/**
 * Created by PerlaIvet on 05/01/2018.
 */

public class GlucoseMeasurementDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GlucoseTracker.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_GLUCOSE_MEASUREMENT_TABLE =
            "CREATE TABLE " + GlucoseMeasurementEntry.TABLE_NAME + " (" +
                    GlucoseMeasurementEntry._ID + " INTEGER PRIMARY KEY," +
                    GlucoseMeasurementEntry.COLUMN_MEASUREMENT_GLUCOSE + INT_TYPE + COMMA_SEP +
                    GlucoseMeasurementEntry.COLUMN_MEASUREMENT_MOMENT + INT_TYPE + COMMA_SEP +
                    GlucoseMeasurementEntry.COLUMN_MEASUREMENT_DATE + TEXT_TYPE + COMMA_SEP +
                    GlucoseMeasurementEntry.COLUMN_MEASUREMENT_TIME + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GlucoseMeasurementEntry.TABLE_NAME;

    public GlucoseMeasurementDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating the database
        db.execSQL(SQL_CREATE_GLUCOSE_MEASUREMENT_TABLE);
    }

    //The next two methods are no used in this moment, but they are necessary if we need to make
    // changes in the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
