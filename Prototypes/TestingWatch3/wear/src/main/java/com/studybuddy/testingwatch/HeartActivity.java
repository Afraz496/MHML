package com.studybuddy.testingwatch;

import android.content.ComponentName;
import android.content.Intent;
import android.app.Service;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

public class HeartActivity extends WearableActivity implements HeartBeatService.OnChangeListener {

    private static final String TAG = "HeartActivity";
    private TextView mTextViewHeart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewHeart = (TextView) findViewById(R.id.text);
        Log.i(TAG, "LISTENER REGISTERED.");
        mTextViewHeart.setText("Please Wait...");
        bindService(new Intent(HeartActivity.this, HeartBeatService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "connected to service.");
                // set our change listener to get change events
                ((HeartBeatService.HeartBeatServiceBinder)service).setChangeListener(HeartActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);

    }

    /**
     * Continuous sensor monitoring with onResume()
     */
    protected void onResume() {
        super.onResume();
    }

    /**
     * Updates the TextView on the Watch with a numeric value representing HR
     */
    public void onValueChanged(int newValue) {
        // will be called by the service whenever the heartbeat value changes.
        mTextViewHeart.setText(Integer.toString(newValue));
    }
}
