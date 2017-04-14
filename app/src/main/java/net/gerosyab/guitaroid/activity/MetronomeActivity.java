package net.gerosyab.guitaroid.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.gerosyab.guitaroid.Constants;
import net.gerosyab.guitaroid.service.MetronomeService;
import net.gerosyab.guitaroid.R;

/**
 * Created by donghe on 2017-04-03.
 */

public class MetronomeActivity extends AppCompatActivity {
    boolean isAlive = false;
    MetronomeService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAlive = isServiceRunningInForeground(getApplicationContext(), MetronomeService.class);
                Intent intent = new Intent(MetronomeActivity.this, MetronomeService.class);
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

                if (mService.isPlaying()) {
                    Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_SHORT).show();
                    intent.setAction(Constants.ACTION.METRONOME_STARTFOREGROUND_ACTION);

                } else {
                    Toast.makeText(getApplicationContext(), "Service Terminated", Toast.LENGTH_SHORT).show();
                    intent.setAction(Constants.ACTION.METRONOME_STOPFOREGROUND_ACTION);
                }
            }
        });

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
}
