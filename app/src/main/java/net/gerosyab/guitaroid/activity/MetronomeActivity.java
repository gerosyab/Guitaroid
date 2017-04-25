package net.gerosyab.guitaroid.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.gerosyab.guitaroid.Constants;
import net.gerosyab.guitaroid.service.MetronomeService;
import net.gerosyab.guitaroid.R;
import net.gerosyab.guitaroid.view.AutoRepeatImageView;

/**
 * Created by donghe on 2017-04-03.
 */

public class MetronomeActivity extends AppCompatActivity {
    boolean isAlive = false;
    MetronomeService mService;
    boolean mBound = false;
    BroadcastReceiver broadcastReceiver;
    AutoRepeatImageView bpmMinusImg, bpmPlusImg, accentPrevImg, accentNextImg, soundPrevImg, soundNextImg;
    TextView bpmText, accentText, soundText;
    long bpm = 120;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_metronome);

        initViews();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = intent.getStringExtra(Constants.METRONOME.BROADCAST_MESSAGE);
                // do something here.
            }
        };


    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MetronomeService.LocalBinder binder = (MetronomeService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public static boolean isServiceRunningInForeground(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                if (service.foreground) {
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((broadcastReceiver), new IntentFilter(Constants.METRONOME.BROADCAST_MESSAGE));

    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void initViews(){
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        bpmMinusImg = (AutoRepeatImageView) findViewById(R.id.activity_metronome_bpm_minus_img);
        bpmPlusImg = (AutoRepeatImageView) findViewById(R.id.activity_metronome_bpm_plus_img);
        accentPrevImg = (AutoRepeatImageView) findViewById(R.id.activity_metronome_accent_prev_img);
        accentNextImg = (AutoRepeatImageView) findViewById(R.id.activity_metronome_accent_prev_img);
        soundPrevImg = (AutoRepeatImageView) findViewById(R.id.activity_metronome_sound_prev_img);
        soundNextImg = (AutoRepeatImageView) findViewById(R.id.activity_metronome_sound_next_img);
        bpmText = (TextView) findViewById(R.id.activity_metronome_bpm_text);
        accentText = (TextView) findViewById(R.id.activity_metronome_accent_text);
        soundText = (TextView) findViewById(R.id.activity_metronome_sound_text);



        bpmMinusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bpm > Constants.METRONOME.MIN_BPM){
                    bpm--;
                    bpmText.setText(String.format("%d", bpm));
                }
            }
        });

        bpmPlusImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(bpm < Constants.METRONOME.MAX_BPM){
                    bpm++;
                    bpmText.setText(String.format("%d", bpm));
                }
            }
        });

        bpmMinusImg.setAutoRepeatImageViewListener(new AutoRepeatImageView.AutoRepeatImageViewListener() {
            @Override
            public void onTouchActionEnd() {
                Toast.makeText(context, "bpmMinusImg touch action end", Toast.LENGTH_SHORT).show();
            }
        });

        bpmPlusImg.setAutoRepeatImageViewListener(new AutoRepeatImageView.AutoRepeatImageViewListener() {
            @Override
            public void onTouchActionEnd() {
                Toast.makeText(context, "bpmPlusImg touch action end", Toast.LENGTH_SHORT).show();
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAlive = isServiceRunningInForeground(getApplicationContext(), MetronomeService.class);
                Intent intent = new Intent(MetronomeActivity.this, MetronomeService.class);
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

                if (!isAlive) {
                    Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_SHORT).show();
                    intent.setAction(Constants.ACTION.METRONOME_STARTFOREGROUND_ACTION);

                } else {
                    Toast.makeText(getApplicationContext(), "Service Terminated", Toast.LENGTH_SHORT).show();
                    intent.setAction(Constants.ACTION.METRONOME_STOPFOREGROUND_ACTION);
                }
            }
        });
    }
}
