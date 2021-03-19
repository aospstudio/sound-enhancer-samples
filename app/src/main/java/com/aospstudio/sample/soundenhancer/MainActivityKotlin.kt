package com.aospstudio.sample.soundenhancer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.AudioManager
import android.media.audiofx.BassBoost
import java.lang.Exception
import android.widget.Toast
import android.media.audiofx.LoudnessEnhancer
import androidx.appcompat.widget.AppCompatSeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.SeekBar

class MainActivityKotlin : AppCompatActivity() {

    private var bassboosteffect: BassBoost? = null
    private var loudnessenhancer: LoudnessEnhancer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {
        val audioSession = AudioManager.AUDIO_SESSION_ID_GENERATE

        try {
            bassboosteffect = BassBoost(0, audioSession)
            bassboosteffect!!.setStrength(0.toShort())
            bassboosteffect!!.enabled = true
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Error: $e", Toast.LENGTH_LONG).show()
        }
        try {
            loudnessenhancer = LoudnessEnhancer(audioSession)
            loudnessenhancer!!.setTargetGain(0)
            loudnessenhancer!!.enabled = true
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Error: $e", Toast.LENGTH_LONG).show()
        }

        val deepbass_seek = findViewById<AppCompatSeekBar>(R.id.deepbass_seek)
        val loudness_seek = findViewById<AppCompatSeekBar>(R.id.loudness_seek)

        deepbass_seek.max = 1000
        deepbass_seek.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                try {
                    if (bassboosteffect!!.strengthSupported) {
                        bassboosteffect!!.setStrength(i.toShort())
                    } else {
                        Toast.makeText(applicationContext, "This feature is currently unavailable.", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Error: $e", Toast.LENGTH_LONG).show()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        loudness_seek.max = 1000
        loudness_seek.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                try {
                    loudnessenhancer!!.setTargetGain(i)
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Error: $e", Toast.LENGTH_LONG).show()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}
