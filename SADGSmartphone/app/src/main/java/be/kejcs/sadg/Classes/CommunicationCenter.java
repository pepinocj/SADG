package be.kejcs.sadg.Classes;

import android.widget.RemoteViews;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Josi on 5/04/2016.
 */
public class CommunicationCenter {

    private DanceGame danceGame;
    private Sender sender;
    private Receiver receiver;
    public CommunicationCenter(DanceGame danceGame, Player player){
        this.danceGame = danceGame;
        try{
            this.sender = new Sender(player.ip,String.valueOf(player.id));
            this.receiver = new Receiver(player.ip,String.valueOf(player.id),danceGame);
        }catch(IOException e){
            e.printStackTrace();
        }catch(TimeoutException e){
            e.printStackTrace();
        }


    }
}
