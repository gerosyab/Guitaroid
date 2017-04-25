package net.gerosyab.guitaroid.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import net.gerosyab.guitaroid.Constants;
import net.gerosyab.guitaroid.R;
import net.gerosyab.guitaroid.activity.MetronomeActivity;


/**
 * Created by donghe on 2017-04-03.
 */

public class MetronomeService extends Service {
    private static final String LOG_TAG = "MetronomeService";
    private final IBinder mBinder = new LocalBinder();
    private static Notification notification;
    private static RemoteViews views;
    private LocalBroadcastManager localBroadCastManager;

    @Override
    public void onCreate() {
        super.onCreate();
        localBroadCastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION.METRONOME_STARTFOREGROUND_ACTION)) {
            showNotification();
            Log.i(LOG_TAG, "Received Start Foreground Intent ");

        } else if (intent.getAction().equals(Constants.ACTION.METRONOME_PLAY_PAUSE_ACTION)) {
            Log.i(LOG_TAG, "Clicked Play Pause");
            Toast.makeText(this, "Clicked Play Pause", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.METRONOME_CLOSE_ACTION)) {
            Log.i(LOG_TAG, "Clicked Close");
            Toast.makeText(this, "Clicked Close", Toast.LENGTH_SHORT).show();
            stopForeground(true);
            stopSelf();
        } else if (intent.getAction().equals(Constants.ACTION.METRONOME_PLUS_BPM_ACTION)) {
            Log.i(LOG_TAG, "Clicked PlusBpm");
            Toast.makeText(this, "Clicked PlusBpm", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.METRONOME_MINUS_BPM_ACTION)) {
            Log.i(LOG_TAG, "Clicked MinusBpm");
            Toast.makeText(this, "Clicked MinusBpm", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(
                Constants.ACTION.METRONOME_STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    public void sendResult(String message) {
        Intent intent = new Intent(Constants.METRONOME.BROADCAST_MESSAGE);
        if(message != null)
            intent.putExtra(Constants.METRONOME.BROADCAST_MESSAGE, message);
        localBroadCastManager.sendBroadcast(intent);
    }

    private void showNotification(){
        views = new RemoteViews(getPackageName(), R.layout.notification_metronome);

        Intent notificationIntent = new Intent(this, MetronomeActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack
        stackBuilder.addParentStack(MetronomeActivity.class);
        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(notificationIntent);
        // Gets a PendingIntent containing the entire back stack

//            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
//            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Intent playPauseIntent = new Intent(this, MetronomeService.class);
        playPauseIntent.setAction(Constants.ACTION.METRONOME_PLAY_PAUSE_ACTION);
        PendingIntent pendingPlayStopIntent = PendingIntent.getService(this, 0, playPauseIntent, 0);

        Intent closeIntent = new Intent(this, MetronomeService.class);
        closeIntent.setAction(Constants.ACTION.METRONOME_CLOSE_ACTION);
        PendingIntent pendingCloseIntent = PendingIntent.getService(this, 0, closeIntent, 0);

        Intent plusBpmIntent = new Intent(this, MetronomeService.class);
        plusBpmIntent.setAction(Constants.ACTION.METRONOME_PLUS_BPM_ACTION);
        PendingIntent pendingPlusBpmIntent = PendingIntent.getService(this, 0, plusBpmIntent, 0);

        Intent minusBpmIntent = new Intent(this, MetronomeService.class);
        minusBpmIntent.setAction(Constants.ACTION.METRONOME_MINUS_BPM_ACTION);
        PendingIntent pendingMinusBpmIntent = PendingIntent.getService(this, 0, minusBpmIntent, 0);

        views.setOnClickPendingIntent(R.id.notification_metronome_close_btn_img, pendingCloseIntent);
        views.setOnClickPendingIntent(R.id.notification_metronome_minus_btn_img, pendingMinusBpmIntent);
        views.setOnClickPendingIntent(R.id.notification_metronome_plus_btn_img, pendingPlusBpmIntent);
        views.setOnClickPendingIntent(R.id.notification_metronome_play_pause_btn_img, pendingPlayStopIntent);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon123);

        notification = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setCustomContentView(views)
                .setSmallIcon(R.drawable.icon5)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setOngoing(true)
                .build();
        startForeground(Constants.NOTIFICATION_ID.METRONOME_FOREGROUND_SERVICE, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "In onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public MetronomeService getService() {
            return MetronomeService.this;
        }
    }

}
