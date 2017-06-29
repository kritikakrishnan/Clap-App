package com.example.kritikagopalakrishnan.clapmusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.AudioManager;



public class Proximity_Sensor extends AppCompatActivity {
    private MediaPlayer clapmusic = new MediaPlayer();
    private int length = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity__sensor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        System.out.println("in onCreate \n");
        Context context = this;
        SensorManager mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor ProximitySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (ProximitySensor != null) {
            System.out.println("Sensor.TYPE_PROXIMITY Available");
            mySensorManager.registerListener(
                    ProximitySensorListener,
                    ProximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        } else {
            System.out.println("Sensor.TYPE_PROXIMITY NOT Available");
        }
        clapmusic.setAudioStreamType(AudioManager.STREAM_MUSIC);
        clapmusic =  MediaPlayer.create(this, R.raw.song);
        clapmusic.setLooping(true);
        clapmusic.setVolume(100, 100);
        clapmusic.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
            }

    });
    }



    private final SensorEventListener ProximitySensorListener
            = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                System.out.println("******* Proximity Event" + event.values[0]);
                if (event.values[0] == 0) {
                    //near
                    System.out.println("in near \n");
                    if (clapmusic != null) {
                        clapmusic.setLooping(true);
                        clapmusic.setVolume(100, 100);
                        clapmusic.start();
                        clapmusic.seekTo(length);
                    }

                    Toast.makeText(getApplicationContext(), "near", Toast.LENGTH_SHORT).show();
                } else {
                    //far
                    if (clapmusic.isPlaying()) {
                        System.out.println("in far \n");
                        clapmusic.pause();
                        length = clapmusic.getCurrentPosition();
                        Toast.makeText(getApplicationContext(), "far", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }

    };
}
