package deloris.owusu14.imperial.ac.uk.headphones;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.AudioManager;
import android.widget.TextView;
import android.widget.Toast;

import static android.media.AudioManager.STREAM_MUSIC;

public class VolumeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_display);

        AudioManager getVolume = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        TextView VolumeNumber = (TextView) findViewById(R.id.VolumeNumber);
        boolean isHeadsetIn = getVolume.isWiredHeadsetOn();
        boolean isMusicOn = getVolume.isMusicActive();
        int Volume = 0;

        //int a = getVolume.GET_DEVICES_INPUTS;
        //think this is way to get the bluetooth devices however not working
        //        getVolume.getDevices();


        if (isHeadsetIn) {
            if(isMusicOn){
                Volume = getVolume.getStreamVolume(STREAM_MUSIC);
                VolumeNumber.setText(String.valueOf(Volume));
            }
            else{
                Volume = getVolume.getStreamVolume(STREAM_MUSIC);
                VolumeNumber.setText(String.valueOf(Volume));
                Toast.makeText(VolumeDisplay.this, "No music on; below 85dB", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(VolumeDisplay.this, "No headset detected", Toast.LENGTH_LONG).show();
        }

        //place inside a runnable to check when volume is going up or down
        // does not work with bluetooth yet... which is a problem
    }
}