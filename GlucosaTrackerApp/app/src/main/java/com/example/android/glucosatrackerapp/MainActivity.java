package com.example.android.glucosatrackerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.glucosatrackerapp.data.GlucoseMeasurementContract.GlucoseMeasurementEntry;
import com.example.android.glucosatrackerapp.data.GlucoseMeasurementDbHelper;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    GlucoseMeasurementDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new GlucoseMeasurementDbHelper(this);
        displayDatabaseInfo();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert:
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
                return true;
            //other cases can be writen if we add more options to the menu
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayDatabaseInfo() {

        // Create and/or open a database to read from it
        db = mDbHelper.getReadableDatabase();
        String[] projection = null;
        String selection = null;
        String selectionArgs = null;

        Cursor c = db.query(GlucoseMeasurementEntry.TABLE_NAME, projection,
                selection, null,
                null, null, null);

        try {

            TextView displayView = (TextView) findViewById(R.id.display_text);
            displayView.setText("Number of measurements in the database: " + c.getCount());
            displayView.append("\n \n" + GlucoseMeasurementEntry._ID + " - \t \t" +
                    GlucoseMeasurementEntry.COLUMN_MEASUREMENT_GLUCOSE + " - \t \t" +
                    GlucoseMeasurementEntry.COLUMN_MEASUREMENT_TIME + " - \t \t" +
                    GlucoseMeasurementEntry.COLUMN_MEASUREMENT_DATE + " - \t \t" +
                    GlucoseMeasurementEntry.COLUMN_MEASUREMENT_MOMENT);
            while (c.moveToNext()) {
                int itemId = c.getInt(
                        c.getColumnIndexOrThrow(GlucoseMeasurementEntry._ID)
                );
                int itemGlucosaLevel = c.getInt(c.getColumnIndexOrThrow(GlucoseMeasurementEntry.COLUMN_MEASUREMENT_GLUCOSE));
                String itemTime = c.getString(c.getColumnIndexOrThrow(GlucoseMeasurementEntry.COLUMN_MEASUREMENT_TIME));
                String itemDate = c.getString(c.getColumnIndexOrThrow(GlucoseMeasurementEntry.COLUMN_MEASUREMENT_DATE));
                int itemMoment = c.getInt(c.getColumnIndexOrThrow(GlucoseMeasurementEntry.COLUMN_MEASUREMENT_MOMENT));
                displayView.append("\n" + String.valueOf(itemId) + " - \t \t" + itemGlucosaLevel +
                        " - \t \t" + itemTime + " - \t \t" + String.valueOf(itemDate) + " - \t \t" + String.valueOf(itemMoment));
            }
        } finally {
            // Closing the cursor
            c.close();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

}
