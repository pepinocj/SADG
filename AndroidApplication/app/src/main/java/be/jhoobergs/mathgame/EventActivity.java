package be.jhoobergs.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Josi on 13/02/2016.
 */
public class EventActivity extends AppCompatActivity {

    private Button buttonNightmare;
    private Button buttonAlcohol;
    private Button buttonToilet;
    private Button buttonDinner;
    private Button buttonSport;
    private Button buttonCoffee;
    private Button buttonSnack;
    private Button buttonOther;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        buttonNightmare = (Button) findViewById(R.id.buttonNightmare);
        buttonAlcohol = (Button) findViewById(R.id.buttonAlcohol);
        buttonToilet = (Button) findViewById(R.id.buttonToilet);
        buttonDinner = (Button) findViewById(R.id.buttonDinner);
        buttonSport = (Button) findViewById(R.id.buttonSport);
        buttonCoffee = (Button) findViewById(R.id.buttonCoffee);
        buttonSnack = (Button) findViewById(R.id.buttonSnack);
        buttonOther = (Button) findViewById(R.id.buttonOther);

        setOnButtonClickListeners();
    }

    private void genericListenerBinder(final Button b,final String label){

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SleepJournalApp app = (SleepJournalApp) getApplication();
                app.eventBuilder.label = label;

                b.setEnabled(false);
                Intent intent = new Intent(getApplicationContext(), StartTimeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setOnButtonClickListeners() {

        genericListenerBinder(buttonNightmare,"nightmare");
        genericListenerBinder(buttonAlcohol,"alcohol");
        genericListenerBinder(buttonToilet,"toilet");
        genericListenerBinder(buttonDinner,"dinner");
        genericListenerBinder(buttonSport,"sport");
        genericListenerBinder(buttonCoffee,"coffee");
        genericListenerBinder(buttonSnack,"snack");
        genericListenerBinder(buttonOther,"other");



    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Button> bs = Arrays.asList(buttonNightmare,buttonAlcohol,buttonCoffee,buttonDinner,buttonOther,buttonSnack,buttonSport,buttonToilet);
        for(Button b:bs){
            b.setEnabled(true);
        }


    }
}
