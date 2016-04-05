package be.kejcs.sadg;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.Random;

import be.kejcs.sadg.Classes.DanceGame;
import be.kejcs.sadg.Classes.JukeBox;
import be.kejcs.sadg.Classes.Music;
import be.kejcs.sadg.Classes.Player;
import be.kejcs.sadg.Classes.QRModule;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mplayer;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    private Button buttonPlayNow;
    private Button buttonPlayDelay;
    private Button buttonStop;


    private Player player;

    private DanceGame danceGame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPlayNow = (Button) findViewById(R.id.buttonPlayNow);
        buttonPlayDelay = (Button) findViewById(R.id.buttonPlayDelay);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        setOnButtonClickListeners();

        ((ImageView) findViewById(R.id.imageViewQR)).setImageBitmap(QRModule.getQRBitmap("TEST"));
        this.danceGame = new DanceGame(this);




    }



    private void setOnButtonClickListeners() {

        //genericListenerBinder(buttonExample,ExampleActivity.class);
        //genericListenerBinder(buttonEvents,EventActivity.class);

        buttonPlayNow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //buttonExample.setEnabled(false);
                Random gen = new Random();

                int song = gen.nextInt(4);
                danceGame.playSong(song);


            }
        });

        buttonPlayDelay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //buttonTimeLine.setEnabled(false);

                Calendar t = Calendar.getInstance();
                t.setTimeInMillis(t.getTimeInMillis()+30000);
                danceGame.playSongAt(0, t);
            }
        });



        buttonStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //buttonTimeLine.setEnabled(false);

                danceGame.stopPlayingMusic();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //This function is called on Startup and loads the menu.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //This function is called when an option in the menu is clicked
        switch (item.getItemId()) {
            case R.id.first:
                Toast.makeText(this, "You clicked the first one!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.second:
                Toast.makeText(this, "You clicked the second one!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.third:
                Toast.makeText(this, "You clicked the third one!", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void scanQR(View v) {

            IntentIntegrator intentIntegrator = new IntentIntegrator(this); // where this is activity
            intentIntegrator.initiateScan(IntentIntegrator.QR_CODE_TYPES); // or QR_CODE_TYPES if you need to scan QR

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            Toast.makeText(this, "Result : "+scanResult.getContents(), Toast.LENGTH_LONG).show();

        }
    }
}
