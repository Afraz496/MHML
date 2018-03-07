package com.beraldo.hpe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent in = getIntent();
        float present_avg = in.getFloatExtra("present_avg", -1);
        float heart_avg = in.getFloatExtra("heart_avg", -1);

        Log.d("mhml",  "value=" + String.valueOf(present_avg));
        Log.d("mhml",  "value=" + String.valueOf(heart_avg));
    }


}
