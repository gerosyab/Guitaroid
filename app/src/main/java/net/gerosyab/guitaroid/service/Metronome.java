package net.gerosyab.guitaroid.service;

import android.media.AudioTrack;

/**
 * Created by donghe on 2017-04-17.
 */

public class Metronome {
    private AudioTrack audioTrack;
    private static long curBpm = 120;
    private static long curAccent = 0;
    private static long curSoundIdx = 0;
    private static boolean isPlaying = false;

    public Metronome(){

    }

    public Metronome(long bpm, long accent, long sound){

    }

    public boolean isPlaying(){
        return isPlaying;
    }

    public void play(){

    }

    public void pause(){

    }

    public long getBpm(){
        return curBpm;
    }

    public void setBpm(long bpm){
        curBpm = bpm;
        refresh();
    }

    public void setSound(long idx){
        curSoundIdx = idx;
        refresh();
    }

    public long getSound(){
        return curSoundIdx;
    }

    public long getAccent(){
        return curAccent;
    }

    public void setAccent(long accent){
        curAccent = accent;
        refresh();
    }

    public void init(){

    }

    public void destroy(){

    }

    public void refresh(){

    }
}
