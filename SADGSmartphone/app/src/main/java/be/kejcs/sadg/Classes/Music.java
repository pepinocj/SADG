package be.kejcs.sadg.Classes;

import android.media.MediaPlayer;

/**
 * Created by Josi on 4/04/2016.
 */
public class Music {


    private int song;
    private int id;
    private int amountOfSeconds;
    public static int DELAY = 5000;
    public Music(int song,int id, int amountOfSecondsBeforePlaying){
        this.song = song;
        this.id = id;
        this.amountOfSeconds = amountOfSecondsBeforePlaying;

    }

    public void play(){

    }


    public int getId(){
        return this.id;
    }

    public int getMusicValue(){
        return this.song;
    }

    public int getAmountOfSeconds(){return this.amountOfSeconds}



}
