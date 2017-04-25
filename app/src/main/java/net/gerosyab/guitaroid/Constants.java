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
        public int[][] SOUND_LIST = {{R.raw.beep1, R.raw.beep2}};
        public int MAX_ACCENT = 16;
        public static String BROADCAST_MESSAGE = "net.gerosyab.guitaroid.service.MetronomeService.BROADCAST_MESSAGE";
    }

}
