package net.gerosyab.guitaroid;

/**
 * Created by donghe on 2017-04-04.
 */

public class Constants {
    public interface ACTION {
        public static String METRONOME_MAIN_ACTION = "net.gerosyab.guitaroid.MetronomeService.action.main";
        public static String METRONOME_PLAY_PAUSE_ACTION = "net.gerosyab.guitaroid.MetronomeService.action.playPause";
        public static String METRONOME_CLOSE_ACTION = "net.gerosyab.guitaroid.MetronomeService.action.close";
        public static String METRONOME_PLUS_BPM_ACTION = "net.gerosyab.guitaroid.MetronomeService.action.plusBpm";
        public static String METRONOME_MINUS_BPM_ACTION = "net.gerosyab.guitaroid.MetronomeService.action.minusBpm";
        public static String METRONOME_BROADCAST_BPM_ACTION = "net.gerosyab.guitaroid.MetronomeService.action.broadcastBpm";
        public static String METRONOME_STARTFOREGROUND_ACTION = "net.gerosyab.guitaroid.MetronomeService.action.startforeground";
        public static String METRONOME_STOPFOREGROUND_ACTION = "net.gerosyab.guitaroid.MetronomeService.action.stopforeground";
        public static String RHYTHM_GUIDE_MAIN_ACTION = "net.gerosyab.guitaroid.RhythmGuideService.action.main";
        public static String RHYTHM_GUIDE_PLAY_PAUSE_ACTION = "net.gerosyab.guitaroid.RhythmGuideService.action.playPause";
        public static String RHYTHM_GUIDE_CLOSE_ACTION = "net.gerosyab.guitaroid.RhythmGuideService.action.close";
        public static String RHYTHM_GUIDE_PREV_ACTION = "net.gerosyab.guitaroid.RhythmGuideService.action.prev";
        public static String RHYTHM_GUIDE_NEXT_ACTION = "net.gerosyab.guitaroid.RhythmGuideService.action.next";
        public static String RHYTHM_GUIDE_STARTFOREGROUND_ACTION = "net.gerosyab.guitaroid.RhythmGuideService.action.startforeground";
        public static String RHYTHM_GUIDE_STOPFOREGROUND_ACTION = "net.gerosyab.guitaroid.RhythmGuideService.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int METRONOME_FOREGROUND_SERVICE = 101;
        public static int RHYTHM_GUIDE_FOREGROUND_SERVICE = 102;
    }

    public interface METRONOME {
        public long MIN_BPM = 10;
        public long MAX_BPM = 300;
        public int[][] SOUND_RESOURCE_ID_LIST = {{R.raw.beep1, R.raw.beep2}, {R.raw.cowbell1, R.raw.cowbell2},
                                                        {R.raw.hihat1, R.raw.hihat2}, {R.raw.ridebell1, R.raw.ridebell2},
                                                        {R.raw.rim1, R.raw.rim2}, {R.raw.stick1, R.raw.stick2},
                                                        {R.raw.tick, R.raw.tock}};
        public String[] SOUND_RESOURCE_NAME_LIST = {"BEEP", "COW BELL", "HI-HAT", "RIDE BELL", "RIM SHOT", "STICK", "TICK"};
        public int MAX_ACCENT = 16;
        public static String BROADCAST_MESSAGE = "net.gerosyab.guitaroid.service.MetronomeService.BROADCAST_MESSAGE";
        public static final int MSG_BPM = 0;
        public static final int MSG_SOUND = 1;
        public static final int MSG_ACCENT = 2;
        public static final int MSG_PLAY = 3;
        public static final String EXTRA_BPM_KEY = "net.gerosyab.guitaroid.service.MetronomeService.EXTRA_BPM_KEY";
        public static final String EXTRA_SOUND_KEY = "net.gerosyab.guitaroid.service.MetronomeService.EXTRA_SOUND_KEY";
        public static final String EXTRA_ACCENT_KEY = "net.gerosyab.guitaroid.service.MetronomeService.EXTRA_ACCENT_KEY";
    }

}
