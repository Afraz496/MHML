package com.studybuddy.testingwatch;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class MainActivity extends AppCompatActivity implements DataClient.OnDataChangedListener, MessageClient.OnMessageReceivedListener {

    final static private String HR_PATH = "/heartrate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Wearable.getDataClient(this).addListener(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("Testing Watch","Success");
            }
        });

    }

    private void updateTextFieldString(String text){
        ((TextView)findViewById(R.id.heartbeat)).setText(text);
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
                Log.v("HR Vals", Integer.toString(HeartBeat));
                updateTextFieldString(Integer.toString(HeartBeat));
            }
        }
    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {

    }

}