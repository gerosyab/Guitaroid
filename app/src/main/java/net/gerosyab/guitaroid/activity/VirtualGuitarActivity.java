package net.gerosyab.guitaroid.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import net.gerosyab.guitaroid.R;

public class VirtualGuitarActivity extends AppCompatActivity {
//    private native void FrequencyDomain(int samplerate, int buffersize);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the device's sample rate and buffer size to enable low-latency Android audio output, if available.
        String samplerateString = null, buffersizeString = null;
        if (Build.VERSION.SDK_INT >= 17) {
            AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
            samplerateString = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE);
            buffersizeString = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER);
        }
        if (samplerateString == null) samplerateString = "44100";
        if (buffersizeString == null) buffersizeString = "512";

        System.loadLibrary("SuperpoweredExample");

        ImageButton imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        ImageButton imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        ImageButton imageButton6 = (ImageButton) findViewById(R.id.imageButton6);

        imageButton1.setVisibility(View.GONE);
        imageButton2.setVisibility(View.GONE);
        imageButton3.setVisibility(View.GONE);
        imageButton4.setVisibility(View.GONE);
        imageButton5.setVisibility(View.GONE);

        final String finalSamplerateString = samplerateString;
        final String finalBuffersizeString = buffersizeString;

        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FrequencyDomain(Integer.parseInt(finalSamplerateString), Integer.parseInt(finalBuffersizeString));
            }
        });
    }
}
