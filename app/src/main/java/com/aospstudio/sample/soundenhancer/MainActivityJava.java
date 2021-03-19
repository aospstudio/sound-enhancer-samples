package com.aospstudio.sample.soundenhancer;

import android.media.AudioManager;
import android.media.audiofx.BassBoost;
import android.media.audiofx.LoudnessEnhancer;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

public class MainActivityJava extends AppCompatActivity {

    private static BassBoost bassboosteffect;
    private static LoudnessEnhancer loudnessenhancer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        int audioSession = AudioManager.AUDIO_SESSION_ID_GENERATE;

        try {
            bassboosteffect = new BassBoost(0, audioSession);
            bassboosteffect.setStrength((short) 0);
            bassboosteffect.setEnabled(true);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show();
        }

        try {
            loudnessenhancer = new LoudnessEnhancer(audioSession);
            loudnessenhancer.setTargetGain(0);
            loudnessenhancer.setEnabled(true);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show();
        }

        AppCompatSeekBar deepbass_seek = findViewById(R.id.deepbass_seek);
        AppCompatSeekBar loudness_seek = findViewById(R.id.loudness_seek);

        deepbass_seek.setMax(1000);
        deepbass_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                try {
                    if (bassboosteffect.getStrengthSupported()) {
                        bassboosteffect.setStrength((short) i);
                    } else {
                        Toast.makeText(getApplicationContext(), "This feature is currently unavailable.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        loudness_seek.setMax(1000);
        loudness_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                try {
                    loudnessenhancer.setTargetGain(i);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
