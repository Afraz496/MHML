package deloris.owusu14.imperial.ac.uk.sbmicrophone;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    //find a way to continuously send data 

    MediaRecorder mRecorder = new MediaRecorder();

    double amp;
    double dBvalue;
    double refamp = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        Button StartDB, StopDB;

        StartDB = (Button) findViewById(R.id.start_dB);
        StopDB = (Button) findViewById(R.id.stop_dB);

        TextView DecibelNumber, HowLoud;

        DecibelNumber = (TextView) findViewById(R.id.DecibelNumber);
        HowLoud = (TextView) findViewById(R.id.HowLoud);


        //Check if the device supports UNPROCESSED sound input, if the device does not
        //then it will return null

        if(audioManager.getProperty(AudioManager.PROPERTY_SUPPORT_AUDIO_SOURCE_UNPROCESSED) != null){
            mRecorder.setAudioSource(MediaRecorder.AudioSource.UNPROCESSED);
        }
        ///Check if it returns null, if it does then just use VOICE_RECOGNITION
        else{
            mRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
        }
        ///*

        //learn what the differences between these output formats are.
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        //I assume this line just sets the output file as null considering we don't actually need it
        mRecorder.setOutputFile("/dev/null");

        StartDB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try{
                    mRecorder.prepare();
                    mRecorder.start();
                    amp = mRecorder.getMaxAmplitude();
                    dBvalue = 20*Math.log10(amp/refamp);
                }catch (IOException exception){
                    mRecorder.reset();
                    mRecorder.release();
                    mRecorder = null;
                }
            }
        });

        DecibelNumber.setText(String.valueOf(dBvalue));

        String loud = "Busy Street";
        String moderate = "Conversation Level";
        String quiet = "Quiet Library";

        if(dBvalue>80){
            HowLoud.setText(loud);
        }
        else{
            if(dBvalue>55){
                HowLoud.setText(moderate);
            }
            else{
                HowLoud.setText(quiet);
            }
        }

        StopDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecorder.stop();
                mRecorder.reset();
                mRecorder = null;
            }
        });

    }



    //mRecorder = new MediaRecorder();


    //mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    //mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    //mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    //mRecorder.setOutputFile("/dev/null");
    //mRecorder.prepare();
    //mRecorder.start();

//    public double getAmplitude() {
  //      if (mRecorder != null)
    //        return  (mRecorder.getMaxAmplitude());
      //  else
        //    return 0;

    //}
}


