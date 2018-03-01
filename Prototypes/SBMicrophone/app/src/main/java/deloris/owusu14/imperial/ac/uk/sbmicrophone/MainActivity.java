package deloris.owusu14.imperial.ac.uk.sbmicrophone;

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
 */

//do the microphone part here
//then make a new project with a better than that combines the headphones part of the app and the
// not headphones part

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button StartSession = (Button) findViewById(R.id.StartSession);
        Button Record = (Button) findViewById(R.id.Record);

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

    }
}
