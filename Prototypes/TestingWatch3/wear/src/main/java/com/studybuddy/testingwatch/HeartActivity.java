package com.studybuddy.testingwatch;

import android.content.ComponentName;
import android.content.Intent;
import android.app.Service;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import com.google.android.gms.wearable.DataEvent;

import java.util.List;


public class HeartActivity extends WearableActivity implements HeartBeatService.OnChangeListener {

    private static final String TAG = "HeartActivity";
    private static final String DB_TAG = "Start Condition";
    private TextView mTextViewHeart;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference StartConditionRef = database.getReference("StartCondition");
    DatabaseReference HRRef = database.getReference("HR");


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
    public void onValueChanged(final int newValue) {
        // will be called by the service whenever the heartbeat value changes
        StartConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(DB_TAG,"Value is" + value);
                if(value.equals("1")){
                    mTextViewHeart.setText("You are in a study session! Focus on your notes!");
                    HRRef.setValue(newValue);
                }
                else{
                    mTextViewHeart.setText("Start the StudyBuddy application to begin your seesion");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(DB_TAG,"Failed to read value", databaseError.toException());
            }
        });

    }

}

