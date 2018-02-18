package com.studybuddy.testingwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // message from API client! message from wear! The contents is the heartbeat.
            if (textView != null)
                textView.setText(String.format("%d",msg.what));
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.heartbeat);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register our handler with the DataLayerService. This ensures we get messages whenever the service receives something.
        DataLayerListenerService.setHandler(handler);
    }

    @Override
    protected void onPause() {
        // unregister our handler so the service does not need to send its messages anywhere.
        DataLayerListenerService.setHandler(null);
        super.onPause();
    }
}
