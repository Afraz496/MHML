package com.beraldo.hpe;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.Toast;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.view.KeyEvent;

import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;


import java.util.List;
import java.util.ArrayList;


public class CameraActivity extends AppCompatActivity implements DataClient.OnDataChangedListener, MessageClient.OnMessageReceivedListener {

    final static private String HR_PATH = "/heartrate";
    private static int OVERLAY_PERMISSION_REQ_CODE = 1;

    ArrayList<Integer> heartArray;


    public ArrayList getHeartArray()
    {
        return  this.heartArray;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.heartArray = new ArrayList<Integer>();
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_camera);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this.getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }

        if (null == savedInstanceState) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, CameraConnectionFragment.newInstance())
                    .commit();
        }



        Wearable.getDataClient(this).addListener(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("Testing Watch","Success");
            }
        });

    }



    private void updateTextFieldString(String text){
       // ((TextView)findViewById(R.id.heartbeat)).setText(text);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
        Wearable.getDataClient(this).addListener(this );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.getDataClient(this).removeListener(this);

    }

    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer){
        final List<DataEvent> events = FreezableUtils.freezeIterable(dataEventBuffer);
        for(DataEvent event:events){
            final Uri uri = event.getDataItem().getUri();
            final String path = uri!=null ? uri.getPath() : null;
            if(HR_PATH.equals(path)){
                final DataMap map = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                int HeartBeat = map.getInt("Heart Rate");

                heartArray.add(HeartBeat);

                Log.v("HR Vals", Integer.toString(HeartBeat));
                updateTextFieldString(Integer.toString(HeartBeat));
            }
        }
    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {

    }

    // Camera code...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this.getApplicationContext())) {
                    Toast.makeText(CameraActivity.this, "CameraActivity\", \"SYSTEM_ALERT_WINDOW, permission not granted...", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        }
    }
}