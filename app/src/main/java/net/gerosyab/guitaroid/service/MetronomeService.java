package net.gerosyab.guitaroid.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;
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
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private static Notification notification;
    private static RemoteViews views;
    private LocalBroadcastManager localBroadCastManager;
    private boolean isNotificationExist = false;
    private long bpm = 120;
    private int sound = 0;
    private int accent = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "In onCreate");
        localBroadCastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "In onStartCommand");
        if (intent.getAction().equals(Constants.ACTION.METRONOME_STARTFOREGROUND_ACTION)) {
            bpm = intent.getLongExtra(Constants.METRONOME.EXTRA_BPM_KEY, 120);
            sound = intent.getIntExtra(Constants.METRONOME.EXTRA_SOUND_KEY, 0);
            accent = intent.getIntExtra(Constants.METRONOME.EXTRA_ACCENT_KEY, 0);
            if(!isNotificationExist) showNotification();
            Log.i(LOG_TAG, "Received Start Foreground Intent ");

        } else if (intent.getAction().equals(Constants.ACTION.METRONOME_PLAY_PAUSE_ACTION)) {
            Log.i(LOG_TAG, "Clicked Play Pause");
            Toast.makeText(this, "Clicked Play Pause", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.METRONOME_CLOSE_ACTION)) {
            Log.i(LOG_TAG, "Clicked Close");
            Toast.makeText(this, "Clicked Close", Toast.LENGTH_SHORT).show();
            isNotificationExist = false;
            stopForeground(true);
            stopSelf();
        } else if (intent.getAction().equals(Constants.ACTION.METRONOME_PLUS_BPM_ACTION)) {
            Log.i(LOG_TAG, "Clicked PlusBpm");
            Toast.makeText(this, "Clicked PlusBpm", Toast.LENGTH_SHORT).show();
            if (bpm < Constants.METRONOME.MAX_BPM) {
                bpm++;
                setBpm(bpm);
                Intent broadcastBpmintent = new Intent(Constants.METRONOME.BROADCAST_MESSAGE);
                broadcastBpmintent.putExtra(Constants.METRONOME.EXTRA_BPM_KEY, bpm);
                localBroadCastManager.sendBroadcast(broadcastBpmintent);
            }
        } else if (intent.getAction().equals(Constants.ACTION.METRONOME_MINUS_BPM_ACTION)) {
            Log.i(LOG_TAG, "Clicked MinusBpm");
            Toast.makeText(this, "Clicked MinusBpm", Toast.LENGTH_SHORT).show();
            if (bpm > Constants.METRONOME.MIN_BPM) {
                bpm--;
                setBpm(bpm);
                Intent broadcastBpmintent = new Intent(Constants.METRONOME.BROADCAST_MESSAGE);
                broadcastBpmintent.putExtra(Constants.METRONOME.EXTRA_BPM_KEY, bpm);
                localBroadCastManager.sendBroadcast(broadcastBpmintent);
            }
        } else if (intent.getAction().equals(
                Constants.ACTION.METRONOME_STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            isNotificationExist = false;
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    public void sendResult(String message) {
        Log.i(LOG_TAG, "In sendResult");
        Intent intent = new Intent(Constants.METRONOME.BROADCAST_MESSAGE);
        if(message != null)
            intent.putExtra(Constants.METRONOME.BROADCAST_MESSAGE, message);
        localBroadCastManager.sendBroadcast(intent);
    }

    public void showNotification(){
        Log.i(LOG_TAG, "In showNotification");

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        views = new RemoteViews(getPackageName(), R.layout.notification_metronome);
        views.setTextViewText(R.id.notification_metronome_bpm_text, "BPM : " + bpm);

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
        mBuilder = new NotificationCompat.Builder(this);

        int apiVersion = Build.VERSION.SDK_INT;

        if (apiVersion < Build.VERSION_CODES.HONEYCOMB) {
            notification = new Notification(R.drawable.icon5, "Guitaroid Metronome", System.currentTimeMillis());
            notification.contentView = views;
            notification.contentIntent = pendingIntent;

            notification.flags |= Notification.FLAG_NO_CLEAR; //Do not clear the notification
            notification.defaults |= Notification.DEFAULT_LIGHTS;
        }else if (apiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            notification = mBuilder
                    .setContentIntent(pendingIntent)
                    .setCustomContentView(views)
                    .setSmallIcon(R.drawable.icon5)
                    .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                    .setOngoing(true)
                    .build();
        }
        startForeground(Constants.NOTIFICATION_ID.METRONOME_FOREGROUND_SERVICE, notification);
        isNotificationExist = true;
    }

    @Override
    public void onDestroy() {
        Log.i(LOG_TAG, "In onDestroy");
        super.onDestroy();

    }

    public boolean isNotificationExist(){
        return isNotificationExist;
    }

    public void destroy(){
        Log.i(LOG_TAG, "In destroy");
        isNotificationExist = false;
        stopForeground(true);
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "In onBind");
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public MetronomeService getService() {
            Log.i(LOG_TAG, "In LocalBinder get Service");
            return MetronomeService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(LOG_TAG, "In onUnbind");
        // allow Rebind even if all clients have unbound with unbindService()
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(LOG_TAG, "In onRebind");
        super.onRebind(intent);
    }

    public void play(){
        Log.i(LOG_TAG, "In play");
    }

    public void setBpm(long bpm){
        Log.i(LOG_TAG, "In setBpm, bpm : " + bpm);
        this.bpm = bpm;
        views.setTextViewText(R.id.notification_metronome_bpm_text, "BPM : " + bpm);
        updateNotification();
    }

    public long getBpm(){
        Log.i(LOG_TAG, "In getBpm, bpm : " + bpm);
        return bpm;
    }

    public void setSound(int sound){
        Log.i(LOG_TAG, "In setSound, sound : " + sound);
        this.sound = sound;
    }

    public int getSound(){
        Log.i(LOG_TAG, "In getSound, sound : " + sound);
        return sound;
    }

    public void setAccent(int accent){
        Log.i(LOG_TAG, "In setAccent, accent : " + accent);
        this.accent = accent;
    }

    public int getAccent(){
        Log.i(LOG_TAG, "In getAccent, accent : " + accent);
        return accent;
    }

    // use this method to update the Notification's UI
    private void updateNotification(){

        int api = Build.VERSION.SDK_INT;
        // update the notification
        if (api < Build.VERSION_CODES.HONEYCOMB) {
            mNotificationManager.notify(Constants.NOTIFICATION_ID.METRONOME_FOREGROUND_SERVICE, notification);
        }else if (api >= Build.VERSION_CODES.HONEYCOMB) {
            mNotificationManager.notify(Constants.NOTIFICATION_ID.METRONOME_FOREGROUND_SERVICE, mBuilder.build());
        }
    }
}
