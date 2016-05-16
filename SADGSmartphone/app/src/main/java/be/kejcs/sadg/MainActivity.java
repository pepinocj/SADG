package be.kejcs.sadg;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings.Secure;
import com.google.zxing.integration.android.IntentIntegrator;

import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import be.kejcs.sadg.Classes.CommunicationCenter;
import be.kejcs.sadg.Classes.DanceGame;
import be.kejcs.sadg.Classes.JukeBox;
import be.kejcs.sadg.Classes.Music;
import be.kejcs.sadg.Classes.Player;
import be.kejcs.sadg.Classes.QRModule;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mplayer;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    private Button reconnect;
    private Button buttonPlayDelay;
    private Button buttonStop;


    private Player player;

    private DanceGame danceGame;
    private CommunicationCenter communicationCenter;


    public TextView tvResults;
    public TextView tvVerify;
    public TextView tvExtraInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        reconnect = (Button) findViewById(R.id.reconnect);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        tvResults = (TextView) findViewById(R.id.textViewResults);
        tvVerify = (TextView) findViewById(R.id.textViewVerify);
        tvExtraInfo = (TextView) findViewById(R.id.textViewExtraInfo);

        setOnButtonClickListeners();


        this.danceGame = new DanceGame(this);
        this.player = new Player("DEFAULTPLAYER",Player.IP_ADRESS,Secure.getString(this.getContentResolver(),
                Secure.ANDROID_ID)
        );
        this.communicationCenter = new CommunicationCenter(danceGame,player,this);







    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    public void connect(View v){
        communicationCenter.startConnectionThreads(); // create new threads and connection
    }

    public void getNameOfPlayer(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("What is your name?");
        alertDialogBuilder.setMessage("Name must be unique");
        final EditText input = new EditText(this);
        alertDialogBuilder.setView(input);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        player.name = input.getText().toString();
                        if(player.name.equals("")){
                            player.name = "DEFAULTNAME";
                        }
                        Log.d("givenName", player.name);
                        ((ImageView) findViewById(R.id.imageViewQR)).setImageBitmap(QRModule.getQRBitmap(player.name));
                        ((TextView) findViewById(R.id.textViewName)).setText(player.name);
                        //communicationCenter.resetPlayer(player);
                        ((Button) findViewById(R.id.button2)).setVisibility(View.VISIBLE);
                        ((Button) findViewById(R.id.reconnect)).setVisibility(View.VISIBLE);
                        ((Button) findViewById(R.id.buttonStop)).setVisibility(View.VISIBLE);
                        //((Item) findViewById(R.id.first)).setVisibility(View.INVISIBLE);
                        communicationCenter.startConnectionThreads();


                    }
                });

//        alertDialog.setButton(0, "OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
//            }
//        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void register(View v){
        communicationCenter.addPlayer(player.name);
    }

    public void unregister(View v){

        communicationCenter.removePlayer(player.name);
    }


    private void setOnButtonClickListeners() {

        //genericListenerBinder(buttonExample,ExampleActivity.class);
        //genericListenerBinder(buttonEvents,EventActivity.class);

        reconnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //buttonExample.setEnabled(false);
                communicationCenter.startConnectionThreads();


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
                getNameOfPlayer(null);
                return true;
            case R.id.second:
                register(null);
                return true;
            case R.id.third:
                unregister(null);
                return true;
            case R.id.ip:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("What is the IP-adress of the server?");
                alertDialogBuilder.setMessage("Current: "+player.ip);
                final EditText input = new EditText(this);
                input.setText(player.ip);
                alertDialogBuilder.setView(input);

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String text = input.getText().toString();

                                player.ip = text;
                                communicationCenter.resetPlayer(player);

                                communicationCenter.startConnectionThreads();


                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
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
        if (scanResult != null ) {
            if (!(scanResult.getContents()+"").equals("null")){
                Toast.makeText(this, "Result : "+scanResult.getContents(), Toast.LENGTH_LONG).show();
                //tvExtraInfo.setText(scanResult.getContents());
                communicationCenter.lastScanTime =System.currentTimeMillis();
                communicationCenter.verify(player.name, scanResult.getContents());


                communicationCenter.hasScannedInThisRound = true;
            }

        }

    }

    public void showResultText(String message){
        tvResults.setText(message);
    }

    public void showVerifyResultText(String message){
        tvVerify.setText( message);
    }

    public void popUpToastMessage(String s){

        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void showLeaderBoard(List<Pair<String,String>> lb){
        Log.d("ShowLeaderBoard", lb.toString());
        String result = "";
        for(Pair s:lb){
            result = result + s.first + ": " + s.second+" \n";
        }

        tvExtraInfo.setText(result);
    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             ViewGroup container,
//                             Bundle savedInstanceState) {
//        View result=inflater.inflate(R.layout.pager, container, false);
//        ViewPager pager=(ViewPager)result.findViewById(R.id.pager);
//
//        setupViewPager(pager);
//
//        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(pager);
//        tabLayout.setVisibility(View.VISIBLE);
//
//        return(result);
//    }
//
//    private void setupViewPager(ViewPager viewPager) {
//        SleepDiaryPageAdapter adapter = new SleepDiaryPageAdapter(getChildFragmentManager());
//        adapter.addFragment(new EventsFragment(), "Events");
//        adapter.addFragment(new DiaryFragment(), "Diary");
//        viewPager.setAdapter(adapter);
//    }
//
//    private class SleepDiaryPageAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public SleepDiaryPageAdapter(FragmentManager mgr) {
//            super(mgr);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }
}
