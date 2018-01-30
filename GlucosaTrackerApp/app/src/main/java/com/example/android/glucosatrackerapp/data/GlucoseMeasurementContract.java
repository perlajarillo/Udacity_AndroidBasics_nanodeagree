package com.example.android.glucosatrackerapp.data;

import android.provider.BaseColumns;

/**
 * Created by PerlaIvet on 05/01/2018.
 */

public class GlucoseMeasurementContract {
    public static abstract class GlucoseMeasurementEntry implements BaseColumns {
        //Name of the table
        public static final String TABLE_NAME = "GlucoseMeasurement";
        //Name of the columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_MEASUREMENT_GLUCOSE = "glucose_level";
        public static final String COLUMN_MEASUREMENT_MOMENT = "moment";
        public static final String COLUMN_MEASUREMENT_DATE = "date";
        public static final String COLUMN_MEASUREMENT_TIME = "time";
        /* Posible values for measurement moment */
        //FBG: fasting blood glucose
        public static final int MEASUREMENT_MOMENT_FBG = 0;
        //PPG: post prandial blood glucose, After a carbohydrate rich, full meal,
        // two hours are allowed to elapse before blood is taken again for estimation of glucose.
        public static final int MEASUREMENT_MOMENT_PPG = 1;
    }
}
