package be.kejcs.sadg.Classes;

import android.app.Activity;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.kejcs.sadg.MainActivity;
import be.kejcs.sadg.R;

/**
 * Created by Josi on 4/04/2016.
 */
public class JukeBox {

    public static int DELAY = 5000;
    Map<Integer,Music> songs;
    private MediaPlayer mediaPlayer;
    private Activity activity;

    public JukeBox(MainActivity activity){
        this.songs = new HashMap<>();
        this.mediaPlayer  =    MediaPlayer.create(activity, R.raw.drake);
        this.activity = activity;
    }

    public void addMusic(Music song){
        this.songs.put(song.getId(),song);
    }

    public void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void playMusicValue(int song){
        stopPlaying();
        mediaPlayer = MediaPlayer.create(activity, song);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(DELAY);
        mediaPlayer.start();

    }

    public void playMusicID(int songID){
        stopPlaying();
        mediaPlayer = MediaPlayer.create(activity, songs.get(Integer.valueOf( songID)).getMusicValue());
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(DELAY);
        mediaPlayer.start();

    }


}
