package be.kejcs.sadg.Classes;

import android.os.Looper;
import android.util.Pair;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
    private Player  player;

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
        this.player = player;
        ConnectionFactory factory = new ConnectionFactory();
        // Voor testen ipaddress = "localhost"
        factory.setHost(player.ip);
        // Initialisatie van connection
        factory.setUsername("player");
        factory.setPassword("player");
        this.sender = new Sender(factory,player,this);
        this.receiver = new Receiver(factory,player,this);


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
//        this.sender.resetUserID(p.name);
//        this.receiver.resetUserID(p.name);
        //DEPRECATED

    }

    public void addPlayer(String p){
        try{
            this.sender.beginAsPlayer(p);
        }catch (IOException|NoConnectionMadeException e){
            e.printStackTrace();
            signalCommunicationException();
        }

    }

    public void startConnectionThreads(){
            this.receiver.run();
            this.sender.run();
    }

    public void verify(String s1,String s2){
        try{
            sender.sendVerify(s1, s2);
        }catch (IOException|NoConnectionMadeException e){
            e.printStackTrace();
            signalCommunicationException();
        }

    }

    public void removePlayer(String p){
        try{
            sender.removeAsPlayer(p);
        }catch (IOException|NoConnectionMadeException e){
            e.printStackTrace();
            signalCommunicationException();
        }
    }

    public void handleVerifyResults(final String message){

        mainActivity.runOnUiThread(new Runnable() {
                                       public void run() {
                                           mainActivity.showVerifyResultText(message);
                                       }
                                   }
        );
    }



    public void handleResults(final String message){
        mainActivity.runOnUiThread(new Runnable() {
                                       public void run() {
                                           mainActivity.showResultText(message);
                                       }
                                   }
            );
        }




            public void receiveScores(final Map<String, String> playersScores) {
                List<Pair<String,String>> scores = new ArrayList<>();

                for (String s: playersScores.keySet()){
                    scores.add(new Pair<>(s,playersScores.get(s)));
                }

                Collections.sort(scores, new Comparator<Pair<String, String>>() {
                    @Override
                    public int compare(Pair<String, String> lhs, Pair<String, String> rhs) {
                        return rhs.second.compareTo(lhs.second);
                    }
                });

                final List<Pair<String,String>> f_scores = new ArrayList<>(scores);

                mainActivity.runOnUiThread(new Runnable() {
                                               public void run() {
                                                   mainActivity.showLeaderBoard(f_scores);
                                               }
                                           }
                );

            }

            public void signalCommunicationException() {

                mainActivity.runOnUiThread(new Runnable() {
                                               public void run() {
                                                   mainActivity.popUpToastMessage("Something went wrong: reconnect or try changing the IP address");
                                               }
                                           }
                );

            }

            public void handleAcknowledgement(final boolean ok) {
                mainActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        String ack;
                        if (ok) {
                            ack = "You are registered!";
                        } else {
                            ack = "You're name is already in use! >:(  CHANGE IT";
                        }
                        Toast.makeText(mainActivity, ack, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
