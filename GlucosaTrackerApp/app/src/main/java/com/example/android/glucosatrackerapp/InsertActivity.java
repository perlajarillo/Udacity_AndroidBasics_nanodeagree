package com.example.android.glucosatrackerapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.glucosatrackerapp.data.GlucoseMeasurementContract.GlucoseMeasurementEntry;
import com.example.android.glucosatrackerapp.data.GlucoseMeasurementDbHelper;

public class InsertActivity extends AppCompatActivity {
    SQLiteDatabase db;
    GlucoseMeasurementDbHelper mDbHelper;
    private EditText mLevelEditText;
    private EditText mDateEditText;
    private EditText mTimeEditText;
    private Spinner mMomentSpinner;
    private Button btnInsert;
    private int mMoment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        // Find all relevant views that we will need to read user input from
        mLevelEditText = (EditText) findViewById(R.id.edit_measurement_level);
        mMomentSpinner = (Spinner) findViewById(R.id.spinner_moment);
        mDateEditText = (EditText) findViewById(R.id.edit_measurement_date);
        mTimeEditText = (EditText) findViewById(R.id.edit_measurement_time);
        btnInsert = (Button) findViewById(R.id.btn_insert_measurement);
        mDbHelper = new GlucoseMeasurementDbHelper(this);
        setupSpinner();

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertGlucoseMeasurement();
                finish();
            }
        });

    }

    public void insertGlucoseMeasurement() {
        db = mDbHelper.getWritableDatabase();
        String message = "";
        String measurement_level = mLevelEditText.getText().toString().trim();
        String measurement_date = mDateEditText.getText().toString().trim();
        String measurement_time = mTimeEditText.getText().toString().trim();
        if (!measurement_level.isEmpty() && !measurement_date.isEmpty() && !measurement_time.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(GlucoseMeasurementEntry.COLUMN_MEASUREMENT_GLUCOSE, measurement_level);
            values.put(GlucoseMeasurementEntry.COLUMN_MEASUREMENT_DATE, measurement_date);
            values.put(GlucoseMeasurementEntry.COLUMN_MEASUREMENT_TIME, measurement_time);
            values.put(GlucoseMeasurementEntry.COLUMN_MEASUREMENT_MOMENT, mMoment);
            long newRowId = db.insert(GlucoseMeasurementEntry.TABLE_NAME, null, values);
            if (newRowId == -1) {
                message = "Error with saving measurement";
            } else {
                message = "New Row ID:" + newRowId;
            }
        } else {
            message = "No row inserted:";
        }
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    private void setupSpinner() {
        ArrayAdapter momentSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_moment_options, android.R.layout.simple_spinner_item);
        momentSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mMomentSpinner.setAdapter(momentSpinnerAdapter);
        // Set the integer mSelected to the constant values
        mMomentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.fasting_blood_glucose))) {
                        mMoment = GlucoseMeasurementEntry.MEASUREMENT_MOMENT_FBG; // FBG
                    } else {
                        mMoment = GlucoseMeasurementEntry.MEASUREMENT_MOMENT_PPG; // PPG
                    }
                }
            }
            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mMoment = 0; // Unselected
            }
        });
    }
}
