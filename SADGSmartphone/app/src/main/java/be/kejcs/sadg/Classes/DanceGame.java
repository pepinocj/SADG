package be.kejcs.sadg.Classes;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import be.kejcs.sadg.MainActivity;
import be.kejcs.sadg.R;

/**
 * Created by Josi on 5/04/2016.
 */
public class DanceGame extends Handler {

    private JukeBox jukeBox;

    private MainActivity  activity;

    private Calendar systemTime;
    private long delay;

    public DanceGame(MainActivity activity){

        this.activity = activity;
        this.jukeBox = new JukeBox(activity);

        this.jukeBox.addMusic(new Music(R.raw.nyancat,0,0));
        this.jukeBox.addMusic(new Music(R.raw.harlemshake,1,0));
        this.jukeBox.addMusic(new Music(R.raw.steps,2,0));
        this.jukeBox.addMusic(new Music(R.raw.sugarhillapacha,3,10));
        this.jukeBox.addMusic(new Music(R.raw.chachaslide,4,30));
        this.jukeBox.addMusic(new Music(R.raw.souljaboy,5));
        this.jukeBox.addMusic(new Music(R.raw.gangnam,6,54));
        this.jukeBox.addMusic(new Music(R.raw.inzaire,7,0));
        this.jukeBox.addMusic(new Music(R.raw.putring,8,0));
        this.jukeBox.addMusic(new Music(R.raw.ketchup,9,26));
        this.jukeBox.addMusic(new Music(R.raw.thriller,10,32));
        this.jukeBox.addMusic(new Music(R.raw.silentowatchme,11,15));
        this.jukeBox.addMusic(new Music(R.raw.bubblebutt,12,74));
        this.jukeBox.addMusic(new Music(R.raw.siacheapthrills,13,20));
        this.jukeBox.addMusic(new Music(R.raw.drankdrugs,14,8));
        this.jukeBox.addMusic(new Music(R.raw.schmurda,15,10));
        this.jukeBox.addMusic(new Music(R.raw.drake,16,20));
        this.jukeBox.addMusic(new Music(R.raw.jumparound,17,0));
        this.jukeBox.addMusic(new Music(R.raw.kungfu,18,0));
        this.jukeBox.addMusic(new Music(R.raw.roboto,19,40));
        this.jukeBox.addMusic(new Music(R.raw.mundianpanjabi,20,24));
        this.jukeBox.addMusic(new Music(R.raw.partyrock,21,44));
        this.jukeBox.addMusic(new Music(R.raw.shakeitoff,22,29));
        this.jukeBox.addMusic(new Music(R.raw.uptown,23,14));
        this.jukeBox.addMusic(new Music(R.raw.onelove,24,0));
        this.jukeBox.addMusic(new Music(R.raw.skrillexbangarang,25,14));
        this.jukeBox.addMusic(new Music(R.raw.earthquake,26,0));
        this.jukeBox.addMusic(new Music(R.raw.freestyler,27,53));
        this.jukeBox.addMusic(new Music(R.raw.tnhgt,28,24));
        this.jukeBox.addMusic(new Music(R.raw.freekydiedieantwoord,29,10));
        this.jukeBox.addMusic(new Music(R.raw.louder,30,21));
        this.jukeBox.addMusic(new Music(R.raw.saltnpepapushit,31,112));
        this.jukeBox.addMusic(new Music(R.raw.talkdirty,32,30));
        this.jukeBox.addMusic(new Music(R.raw.temperature,33,0));
        this.jukeBox.addMusic(new Music(R.raw.turndown,34,0));
        this.jukeBox.addMusic(new Music(R.raw.work,35,14));
        this.jukeBox.addMusic(new Music(R.raw.jayz,36,0));
        this.jukeBox.addMusic(new Music(R.raw.danceofsugar,37,9));






        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(0);

        this.systemTime = c;
        this.delay = 0;
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

    public void setSystemTime(String message){
//        this.systemTime.setTimeInMillis(Long.valueOf(message));
        if(!(this.systemTime.getTimeInMillis() >0)) {
            this.systemTime.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
        } //SET TIME OF FIRST ROUND START
        delay = systemTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
    }

    public void playSongAt(int id, Calendar calendar,boolean hasScanned, long scanTime){



        Calendar now = Calendar.getInstance();


        long diff = calendar.getTimeInMillis() - (now.getTimeInMillis()-systemTime.getTimeInMillis());
        if(diff<0){
            diff = 0;
        }



//Poging 1
//        //TEMP: play in 5 seconds
//        long systemTimeDiffInvocationTime = calendar.getTimeInMillis() - now.getTimeInMillis();
//        long systemTimeDiffSmartphoneTime = systemTime.getTimeInMillis() - now.getTimeInMillis();
//        diff = systemTimeDiffInvocationTime + systemTimeDiffSmartphoneTime;

        //Poging 2
//        diff = 5000;
//        if(hasScanned){
//            diff = diff-(now.getTimeInMillis()-scanTime);
//        }

        // Poging3

//        long playAtMS = calendar.getTimeInMillis();
//        diff = 10000 + delay;
//
//        final long toastDiff = diff;
//        activity.runOnUiThread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(activity, "play in "+toastDiff+" millisec", Toast.LENGTH_LONG).show();
//                    }
//                }
//        );

        //poging 4
        //diff = 0;

        if(diff > 0) {
            Message m = new Message();
            m.arg1 = id;
            this.sendMessageDelayed(m, diff);
        }else{
            this.playSong(id);
        }


        Log.d("Server relative time", String.valueOf(calendar.getTimeInMillis()));
        Log.d("Client relative time", String.valueOf((now.getTimeInMillis()-systemTime.getTimeInMillis())));
        Log.d("Client absolute time", String.valueOf(now.getTimeInMillis()));
    }

}
