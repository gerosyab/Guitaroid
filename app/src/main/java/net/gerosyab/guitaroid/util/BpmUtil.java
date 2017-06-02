package net.gerosyab.guitaroid.util;

import net.gerosyab.guitaroid.Constants;

import java.util.ArrayList;

/**
 * Created by donghe on 2017-05-06.
 */

public class BpmUtil {
    public ArrayList<Long> taps;
    private boolean isTapping;

    public BpmUtil(){
        taps = new ArrayList<Long>();
        isTapping = false;
    }

    public void tap(){
        taps.add(System.nanoTime());
        isTapping = true;
    }

    public boolean isTapping(){
        return isTapping;
    }

    public void reset(){
//        final ArrayList<Long> oldTaps = taps;
        taps = null;
        taps = new ArrayList<Long>();
        isTapping = false;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                oldTaps.clear();
//            }
//        }).start();
    }

    public long getBpm(){
        ArrayList<Long> deltas = new ArrayList<Long>();
        int length = taps.size() - 1;
        for (int i = 0; i < length; i++) {
            long delta = taps.get(i + 1) - taps.get(i);
            deltas.add(delta);
        }
        long sum = 0L;
        for (Long delta : deltas) {
            sum = sum + delta;
        }
        long bpm = 60000000000L / (sum / deltas.size());
        if(bpm > Constants.METRONOME.MAX_BPM){
            bpm = Constants.METRONOME.MAX_BPM;
        }
        else if(bpm < Constants.METRONOME.MIN_BPM){
            bpm = Constants.METRONOME.MIN_BPM;
        }
        return bpm;
    }
}
