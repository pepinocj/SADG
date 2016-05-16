package be.kejcs.sadg.Classes;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.kejcs.sadg.MainActivity;
import be.kejcs.sadg.R;

/**
 * Created by Josi on 4/04/2016.
 */
public class JukeBox implements android.media.MediaPlayer.OnErrorListener{

    public static int DELAY = 5000;
    Map<Integer,Music> songs;
    private static MediaPlayer mediaPlayer;
    private Activity activity;

    public JukeBox(MainActivity activity){
        this.songs = new HashMap<>();
        mediaPlayer  =    MediaPlayer.create(activity, R.raw.drake);
        mediaPlayer.setOnErrorListener(this);
        this.activity = activity;
    }

    public void addMusic(Music song){
        this.songs.put(song.getId(),song);
    }

    public void stopPlaying() {
//        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
        mediaPlayer.stop();
    }

    public void playMusicValue(int song){
        stopPlaying();
        mediaPlayer = MediaPlayer.create(activity, song);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(DELAY);
        mediaPlayer.start();

    }

    public void playMusicID(int songID){
//        stopPlaying();
//        Music m =  songs.get(Integer.valueOf(songID));
//        mediaPlayer = MediaPlayer.create(activity, m.getMusicValue());
//        mediaPlayer.setLooping(true);
//        mediaPlayer.seekTo(m.getAmountOfSeconds()*1000);
//        mediaPlayer.start();



        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }


        try{
            mediaPlayer.reset();
            Music m =  songs.get(Integer.valueOf(songID));
            AssetFileDescriptor afd = activity.getResources().openRawResourceFd(m.getMusicValue());
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.seekTo(m.getAmountOfSeconds()*1000);
            mediaPlayer.start();
        }catch (IOException e)
        {
            Log.e("MusicError", "Unable to play audio queue due to exception: " + e.getMessage(), e);
        }


    }


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return true;
    }
}
