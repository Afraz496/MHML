package deloris.owusu14.imperial.ac.uk.sbmicrophone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ServiceDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_demo);

        //LocalBroadcastManager.getInstance(this).registerReceiver(
        //        mMessageReceiver, new IntentFilter("intentKey"));
    }

    public void StartService(View view) {
        Intent serviceintent = new Intent(this, TheService.class);
        startService(serviceintent);
    }

    public void StopService(View view) {
        Intent serviceintent = new Intent(this, TheService.class);
        stopService(serviceintent);
    }



    /*
    double Decibels;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            double dB = intent.getDoubleExtra("Status", Decibels);
            TextView showDecibel = (TextView) findViewById(R.id.showDecibel);
            showDecibel.setText(String.valueOf(dB));
            // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    };
    */
}
