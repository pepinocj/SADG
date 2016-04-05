package be.kejcs.sadg.Classes;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.util.Calendar;

import be.kejcs.sadg.MainActivity;
import be.kejcs.sadg.R;

/**
 * Created by Josi on 5/04/2016.
 */
public class DanceGame extends Handler {

    private JukeBox jukeBox;


    public DanceGame(MainActivity activity){
        this.jukeBox = new JukeBox(activity);
        this.jukeBox.addMusic(new Music(R.raw.dawin,0));
        this.jukeBox.addMusic(new Music(R.raw.drake,1));
        this.jukeBox.addMusic(new Music(R.raw.freestyler,2));
        this.jukeBox.addMusic(new Music(R.raw.sorry,3));
        this.jukeBox.addMusic(new Music(R.raw.work,4));

    }


    @Override
    public void handleMessage(Message msg) {

        this.playSong(msg.arg1);
        //do something here
    }

    public void playSong(int id){
        jukeBox.playMusicID(id);
    }


    public void stopPlayingMusic(){
        jukeBox.stopPlaying();
    }


    public void playSongAt(int id, Calendar calendar){

        Calendar now = Calendar.getInstance();

        long diff = calendar.getTimeInMillis() - now.getTimeInMillis();
        if(diff<0){
            diff = 0;
        }

        Message m = new Message();
        m.arg1 = id;
        this.sendMessageDelayed(m, diff);
    }

}
