package be.kejcs.sadg.Classes;

/**
 * Created by Josi on 6/04/2016.
 */
public class RoundParameters {
    public int song;
    public boolean ready;
    public long start;
    public RoundParameters(){
        ready = false;
        song = 0;
        start = 0;
    }

    public void setSong(String song){
        this.song =  Integer.valueOf(song);

    }

    public void setStart(String start){
        this.start =  Long.valueOf(start);

    }

    public void setReady(){
        this.ready= true;
    }

    public void setNotReady(){
        this.ready = false;
    }

    public boolean isReady(){
        return  this.ready;
    }


}
