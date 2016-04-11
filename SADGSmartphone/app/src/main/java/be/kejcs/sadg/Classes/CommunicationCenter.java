package be.kejcs.sadg.Classes;

import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Josi on 5/04/2016.
 */
public class CommunicationCenter {


    private RoundParameters roundParameters;
    private DanceGame danceGame;
    private Sender sender;
    private Receiver receiver;

    public void setSong(String song){
        roundParameters.setSong(song);
    }

    public void setStart(String start){
        roundParameters.setStart(start);

    }

    public void startRound(){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(roundParameters.start);
        danceGame.playSongAt(roundParameters.song, c);
    }



    public void stopRound(){
        danceGame.stopPlayingMusic();
    }

    public CommunicationCenter(DanceGame danceGame, Player player){
        this.roundParameters = new RoundParameters();
        this.danceGame = danceGame;
        ConnectionFactory factory = new ConnectionFactory();
        // Voor testen ipaddress = "localhost"
        factory.setHost(player.ip);
        factory.setUsername("player");
        factory.setPassword("player");
        // Initialisatie van connection
        this.sender = new Sender(factory,player.name);
        this.receiver = new Receiver(factory,player.name,this);



    }

    public void changeUsername(String message){
        //TODO implement;
    }

    public void resetPlayer(Player p){
        this.sender.resetUserID(p.name);
        this.receiver.resetUserID(p.name);

    }

    public void startConnectionThreads(){


            this.receiver.run();
            this.sender.run();



    }

    public void closeCommunication(){
        this.receiver.closeCommunication();
        this.sender.closeCommunication();
    }

    public void verifyPlayers(String s1, String s2){
        try{
            sender.sendVerify(s1,s2);
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    public void register(String name){
        try{
        sender.beginAsPlayer(name);
    } catch(IOException e){
        e.printStackTrace();
    }
    }
}
