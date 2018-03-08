package deloris.owusu14.imperial.ac.uk.sbmicrophone;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import static java.lang.Math.abs;

public class SecondActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";

    //find a way to continuously send data
    MediaRecorder mRecorder = new MediaRecorder();

    double amp;
    double dBvalue;
    double refamp = 1;
    public double total = 0;
    public double sum = 0;
    public int maint = 1;

    Button StopDB;
    TextView DecibelNumber, HowLoud;
    boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        StopDB = (Button) findViewById(R.id.stop_dB);
        StopDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRecording = false;
                mRecorder.stop();
                mRecorder.reset();
                mRecorder = null;
            }
        });

        /*
        String loud = "Busy Street";
        String moderate = "Conversation Level";
        String quiet = "Quiet Library";

        if(dBvalue>80){
            HowLoud.setText(loud);
        }
        else {
            if (dBvalue > 55) {
                HowLoud.setText(moderate);
            } else {
                HowLoud.setText(quiet);
            }
        }
        */
    }

    Random RanNum = new Random();
    Handler postDecibels = new Handler();

    public void Record(View view) {
        isRecording = true;

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if(Build.VERSION.SDK_INT >= 24){
            if (audioManager.getProperty(AudioManager.PROPERTY_SUPPORT_AUDIO_SOURCE_UNPROCESSED) != null) {
                mRecorder.setAudioSource(MediaRecorder.AudioSource.UNPROCESSED);
                //mRecorder.setAudioSamplingRate(44100);
            }
            ///Check if it returns null, if it does then just use VOICE_RECOGNITION
            else {
                mRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
            }
        }
        else{
            mRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
        }

        //learn what the differences between these output formats are.
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        //I assume this line just sets the output file as null considering we don't actually need it
        mRecorder.setOutputFile("/dev/null");

        try{
            mRecorder.prepare();
        }catch (IOException exception){
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();

        Runnable decibelRunnable = new Runnable() {
            @Override
            public void run() {
                getDecibels();
            }
        };
        new Thread(decibelRunnable).start();

    }

    private void getDecibels() {
        while(isRecording) {
            //amp = RanNum.nextDouble();
            amp = mRecorder.getMaxAmplitude();
            dBvalue = 20*Math.log10(amp/refamp);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            postDecibels.post(new Runnable() {
                @Override
                public void run() {
                    DecibelNumber = (TextView) findViewById(R.id.DecibelNumber);
                    DecibelNumber.setText(String.valueOf(amp));
                }
            });
        }
    }

    private String abonormalevents(double dBnow, double dBprev){
        double difference;
        difference = abs(dBnow-dBprev);
        if(difference>10){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(c.getTime());
        }
        return null;
    }

    private double movingaverage(double value){
        sum = sum + value;
        total = sum/maint;
        maint++;
        return total;
    }

}


