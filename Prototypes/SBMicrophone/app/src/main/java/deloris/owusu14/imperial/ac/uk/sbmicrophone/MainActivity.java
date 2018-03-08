package deloris.owusu14.imperial.ac.uk.sbmicrophone;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.media.MediaRecorder;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import static android.Manifest.permission.RECORD_AUDIO;

/* Steps:
1. Modify the main activity java code to add the audio capture
2. Modify the XML file to add an GUI(graphical user interface) component
3. Modify the Android Manifest file to add an permissions
4. Run on an actual device because it will NOT work on an emulator, as you need a microphone.

Things I need to be constantly checking in the overall thread,
1. If Headset is on
2. If music is on
3. If 1 is not true need constantly checking the decibel level
 */

//do the microphone part here
//then make a new project with a better than that combines the headphones part of the app and the
// not headphones part

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        Button StartSession = (Button) findViewById(R.id.StartSession);
        Button Record = (Button) findViewById(R.id.Record);
        Button StartServicePage = (Button) findViewById(R.id.service_start);

        StartSession.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(startintent);
            }
        });

        Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recordintent = new Intent(getApplicationContext(), Recorder.class);
                startActivity(recordintent);
            }
        });

        StartServicePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent servicepageintent = new Intent(getApplicationContext(), ServiceDemo.class);
                startActivity(servicepageintent);
            }
        });
    }
}
