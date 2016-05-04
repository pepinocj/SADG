package be.kejcs.sadg.Classes;

import android.widget.RemoteViews;
import android.widget.Toast;

import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import be.kejcs.sadg.MainActivity;

/**
 * Created by Josi on 5/04/2016.
 */
public class CommunicationCenter {


    private RoundParameters roundParameters;
    private DanceGame danceGame;
    private Sender sender;
    private Receiver receiver;
    private MainActivity mainActivity;


    public boolean hasScannedInThisRound;
    public long lastScanTime;


    public void setSong(String song){
        roundParameters.setSong(song);
    }

    public void setStart(String start){
        roundParameters.setStart(start);

    }



    public void startRound(){

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(roundParameters.start);



        danceGame.playSongAt(roundParameters.song, c,hasScannedInThisRound,lastScanTime);

        hasScannedInThisRound = false;
    }



    public void stopRound(){
        danceGame.stopPlayingMusic();
    }

    public CommunicationCenter(DanceGame danceGame, Player player, MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.roundParameters = new RoundParameters();
        this.danceGame = danceGame;
        ConnectionFactory factory = new ConnectionFactory();
        // Voor testen ipaddress = "localhost"
        factory.setHost(player.ip);
        // Initialisatie van connection
        factory.setUsername("player");
        factory.setPassword("player");
        this.sender = new Sender(factory,player.name);
        this.receiver = new Receiver(factory,player.name,this);


        this.hasScannedInThisRound = false;
        this.lastScanTime = 0;


    }

    public void setSystemTime(String message){
        danceGame.setSystemTime(message);
    }

    public void changeUsername(String message){
                //TODO implement;
    }

    public void resetPlayer(Player p){
        this.sender.resetUserID(p.name);
        this.receiver.resetUserID(p.name);

    }

    public void addPlayer(String p){
        try{
            this.sender.beginAsPlayer(p);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void startConnectionThreads(){
            this.receiver.run();
            this.sender.run();
    }

    public void verify(String s1,String s2){
        try{
            sender.sendVerify(s1, s2);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void removePlayer(String p){
        try{
            sender.removeAsPlayer(p);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleVerifyResults(final String message){
        mainActivity.showVerifyResultText(message);



    }

    public void handleResults(final String message){
  mainActivity.showResultText(message);



    }

    public void receiveScores(Map<String, String> playersScores) {
    }
}
