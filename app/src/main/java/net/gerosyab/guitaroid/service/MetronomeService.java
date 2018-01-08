package net.gerosyab.guitaroid.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import net.gerosyab.guitaroid.Constants;
import net.gerosyab.guitaroid.R;
import net.gerosyab.guitaroid.activity.MetronomeActivity;

import java.util.Vector;

import nl.igorski.lib.audio.MWEngine;
import nl.igorski.lib.audio.helpers.DevicePropertyCalculator;
import nl.igorski.lib.audio.mwengine.Delay;
import nl.igorski.lib.audio.mwengine.Filter;
import nl.igorski.lib.audio.mwengine.Finalizer;
import nl.igorski.lib.audio.mwengine.JavaUtilities;
import nl.igorski.lib.audio.mwengine.LPFHPFilter;
import nl.igorski.lib.audio.mwengine.Notifications;
import nl.igorski.lib.audio.mwengine.Phaser;
import nl.igorski.lib.audio.mwengine.ProcessingChain;
import nl.igorski.lib.audio.mwengine.SampleEvent;
import nl.igorski.lib.audio.mwengine.SampleManager;
import nl.igorski.lib.audio.mwengine.SampledInstrument;
import nl.igorski.lib.audio.mwengine.SequencerController;
import nl.igorski.lib.audio.mwengine.SynthEvent;
import nl.igorski.lib.audio.mwengine.SynthInstrument;


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

    private Finalizer           _finalizer;
    private LPFHPFilter         _lpfhpf;
    private SynthInstrument     _synth1;
    private SynthInstrument     _synth2;
    private SampledInstrument   _sampler;
    private Filter              _filter;
    private Phaser              _phaser;
    private Delay               _delay;
    private MWEngine            _engine;
    private SequencerController _sequencerController;
    private Vector<SynthEvent>  _synth1Events;
    private Vector<SynthEvent>  _synth2Events;
    private Vector<SampleEvent> _drumEvents;

    private boolean _sequencerPlaying = false;
    private boolean _inited           = false;

    private float minFilterCutoff = 50.0f;
    private float maxFilterCutoff;

    private int SAMPLE_RATE;
    private int BUFFER_SIZE;
    private int OUTPUT_CHANNELS = 1; // 1 = mono, 2 = stereo

    private static int STEPS_PER_MEASURE = 16; // amount of subdivisions within a single measure

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "In onCreate");
        localBroadCastManager = LocalBroadcastManager.getInstance(this);

        init();
    }

    private void init()
    {
        if ( _inited)
            return;
        else if(_engine == null){
            Log.d(LOG_TAG, "initing MWEngineActivity");

            // STEP 1 : preparing the native audio engine

            _engine = new MWEngine(getApplicationContext(), new StateObserver());

            // get the recommended buffer size for this device (NOTE : lower buffer sizes may
            // provide lower latency, but make sure all buffer sizes are powers of two of
            // the recommended buffer size (overcomes glitching in buffer callbacks )
            // getting the correct sample rate upfront will omit having audio going past the system
            // resampler reducing overall latency

            BUFFER_SIZE = DevicePropertyCalculator.getRecommendedBufferSize(getApplicationContext());
            SAMPLE_RATE = DevicePropertyCalculator.getRecommendedSampleRate(getApplicationContext());

            _engine.createOutput(SAMPLE_RATE, BUFFER_SIZE, OUTPUT_CHANNELS);
            _sequencerController = _engine.getSequencerController();

            // cache some of the engines properties

            final ProcessingChain masterBus = _engine.getMasterBusProcessors();
            final SequencerController sequencer = _engine.getSequencerController();

            sequencer.updateMeasures(1, STEPS_PER_MEASURE); // we'll loop just a single measure with given subdivisions
            _engine.start(); // starts the engines render thread (NOTE : sequencer is still paused!)

            // create a lowpass filter to catch all low rumbling and a Finalizer (limiter) to prevent clipping of output :)
            _lpfhpf = new LPFHPFilter((float) MWEngine.SAMPLE_RATE, 55, OUTPUT_CHANNELS);
            _finalizer = new Finalizer(2f, 500f, MWEngine.SAMPLE_RATE, OUTPUT_CHANNELS);

            masterBus.addProcessor(_finalizer);
            masterBus.addProcessor(_lpfhpf);

            // STEP 2 : let's create some instruments =D

//            _synth1 = new SynthInstrument();
//            _synth2 = new SynthInstrument();
            _sampler = new SampledInstrument();

//            _synth1.getOscillatorProperties(0).setWaveform(2); // sawtooth (see global.h for enumerations)
//            _synth2.getOscillatorProperties(0).setWaveform(5); // pulse width modulation

            // a high decay for synth 1 (bubblier effect)
//            _synth1.getAdsr().setDecay(.9f);

            // add a filter to synth 1
            maxFilterCutoff = (float) SAMPLE_RATE / 8;

            _filter = new Filter(maxFilterCutoff / 2, (float) (Math.sqrt(1) / 2), minFilterCutoff, maxFilterCutoff, 0f, 1);
//            _synth1.getAudioChannel().getProcessingChain().addProcessor(_filter);

            // add a phaser to synth 1
            _phaser = new Phaser(.5f, .7f, .5f, 440.f, 1600.f);
//            _synth1.getAudioChannel().getProcessingChain().addProcessor(_phaser);

            // add some funky delay to synth 2
            _delay = new Delay(250, 2000, .35f, .5f, OUTPUT_CHANNELS);
//            _synth2.getAudioChannel().getProcessingChain().addProcessor(_delay);

            // prepare synthesizer volumes
//            _synth2.getAudioChannel().setVolume(.7f);

            // STEP 3 : load some samples from the packaged assets folder into the SampleManager

            loadWAVAsset("beep1.wav", "beep1");
            loadWAVAsset("beep2.wav", "beep2");

            // STEP 4 : let's create some music !

//            _synth1Events = new Vector<SynthEvent>();
//            _synth2Events = new Vector<SynthEvent>();
            _drumEvents = new Vector<SampleEvent>();

            sequencer.setTempoNow(120.0f, 4, 4); // 120 BPM in 4/4 time

            // STEP 4.1 : Sample events to play back a drum beat

                    createDrumEvent( "beep1",  2 );  // hi-hat on the second 8th note after the first beat of the bar
                    createDrumEvent( "beep1",  6 );  // hi-hat on the second 8th note after the second beat
                    createDrumEvent( "beep1",  10 ); // hi-hat on the second 8th note after the third beat
                    createDrumEvent( "beep1",  14 ); // hi-hat on the second 8th note after the fourth beat
                    createDrumEvent( "beep2", 12 ); // clap sound on the third beat of the bar

//            final Button playPauseButton = (Button) findViewById(R.id.activity_rhythmguide_button);
//            playPauseButton.setOnClickListener(new RhythmGuideActivity.PlayClickHandler());

            //        final SeekBar filterSlider = ( SeekBar ) findViewById( R.id.FilterCutoffSlider );
            //        filterSlider.setOnSeekBarChangeListener( new FilterCutOffChangeHandler() );
            //
            //        final SeekBar decaySlider = ( SeekBar ) findViewById( R.id.SynthDecaySlider );
            //        decaySlider.setOnSeekBarChangeListener( new SynthDecayChangeHandler() );
            //
            //        final SeekBar feedbackSlider = ( SeekBar ) findViewById( R.id.MixSlider );
            //        feedbackSlider.setOnSeekBarChangeListener( new DelayMixChangeHandler() );
            //
            //        final SeekBar tempoSlider = ( SeekBar ) findViewById( R.id.TempoSlider );
            //        tempoSlider.setOnSeekBarChangeListener( new TempoChangeHandler() );

            _inited = true;
        }
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
            play();
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

        if ( _engine != null )
        {
            _engine.pause();
            _engine.dispose();
        }

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
        _sequencerPlaying = !_sequencerPlaying;
        _engine.getSequencerController().setPlaying( _sequencerPlaying );
    }

    public void setBpm(long bpm){
        Log.i(LOG_TAG, "In setBpm, bpm : " + bpm);
        this.bpm = bpm;
        views.setTextViewText(R.id.notification_metronome_bpm_text, "BPM : " + bpm);
        _engine.getSequencerController().setTempo( bpm, 4, 4 );
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

    public boolean isPlaying(){
        return _sequencerPlaying;
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

    /**
     * convenience method to load WAV files packaged in the APK
     * and read their audio content into MWEngine's SampleManager
     *
     * @param assetName {String} assetName filename for the resource in the /assets folder
     * @param sampleName {String} identifier for the files WAV content inside the SampleManager
     */
    private void loadWAVAsset( String assetName, String sampleName )
    {
        final Context ctx = getApplicationContext();

        boolean result = JavaUtilities.createSampleFromAsset(
                sampleName, ctx.getAssets(), ctx.getCacheDir().getAbsolutePath(), assetName
        );
    }

    /**
     * convenience method for creating a new SampleEvent
     *
     * @param sampleName {String} identifier (inside the SampleManager) of the sample to use
     * @param position {int} position within the composition to place the event at
     */
    private void createDrumEvent( String sampleName, int position )
    {
        final SampleEvent drumEvent = new SampleEvent( _sampler );
        drumEvent.setSample( SampleManager.getSample( sampleName ));
        drumEvent.positionEvent( 0, STEPS_PER_MEASURE, position );
        drumEvent.addToSequencer(); // samples have to be explicitly added for playback

        _drumEvents.add( drumEvent );
    }

    /**
     *  invoked when user presses the play / pause button
     */
    private class PlayClickHandler implements View.OnClickListener
    {
        public void onClick( View v )
        {
            // start/stop the sequencer so we can toggle hearing actual output! ;)
            play();
            if(_sequencerPlaying){
                views.setImageViewResource(R.id.notification_metronome_play_pause_btn_img, R.drawable.play_icon);
            }
            else {
                views.setImageViewResource(R.id.notification_metronome_play_pause_btn_img, R.drawable.pause_icon);
            }

        }
    }

    /* state change message listener */

    private class StateObserver implements MWEngine.IObserver
    {
        // cache the enumerations (from native layer) as integer Array

        private final Notifications.ids[] _notificationEnums = Notifications.ids.values();

        public void handleNotification( int aNotificationId )
        {
            switch ( _notificationEnums[ aNotificationId ])
            {
                case ERROR_HARDWARE_UNAVAILABLE:

                    Log.d( LOG_TAG, "ERROR : received Open SL error callback from native layer" );

                    // re-initialize thread
                    if ( _engine.canRestartEngine() )
                    {
                        _engine.dispose();
                        _engine.createOutput( SAMPLE_RATE, BUFFER_SIZE, OUTPUT_CHANNELS );
                        _engine.start();
                    }
                    else {
                        Log.d( LOG_TAG, "exceeded maximum amount of retries. Cannot continue using audio engine" );
                    }
                    break;

                case MARKER_POSITION_REACHED:

                    Log.d( LOG_TAG, "Marker position has been reached" );
                    break;
            }
        }

        public void handleNotification( int aNotificationId, int aNotificationValue )
        {
            switch ( _notificationEnums[ aNotificationId ])
            {
                case SEQUENCER_POSITION_UPDATED:

                    // for this notification id, the notification value describes the precise buffer offset of the
                    // engine when the notification fired (as a value in the range of 0 - BUFFER_SIZE). using this value
                    // we can calculate the amount of samples pending until the next step position is reached
                    // which in turn allows us to calculate the engine latency

                    int sequencerPosition = _sequencerController.getStepPosition();
                    int elapsedSamples    = _sequencerController.getBufferPosition();

                    Log.d( LOG_TAG, "seq. position: " + sequencerPosition + ", buffer offset: " + aNotificationValue +
                            ", elapsed samples: " + elapsedSamples );
                    break;
            }
        }
    }
}
