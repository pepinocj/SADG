package be.jhoobergs.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import be.jhoobergs.mathgame.Classes.ItemCollection;

public class MainActivity extends AppCompatActivity {

    private Button buttonExample;
    private Button buttonTimeLine;
    private Button buttonEvents;
    private Button buttonQuestionnaire;
    private Button buttonDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonExample = (Button) findViewById(R.id.buttonExample);
        buttonTimeLine = (Button) findViewById(R.id.buttonTimeLine);
        buttonEvents = (Button) findViewById(R.id.buttonEvents);
        buttonDiary = (Button) findViewById(R.id.buttonDiary);
        buttonQuestionnaire = (Button) findViewById(R.id.buttonQuestionnaire);

        setOnButtonClickListeners();
    }

    private void genericListenerBinder(final Button b,final Class c){

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                b.setEnabled(false);
                Intent intent = new Intent(getApplicationContext(), c);
                startActivity(intent);
            }
        });
    }

    private void setOnButtonClickListeners() {

        genericListenerBinder(buttonExample,ExampleActivity.class);
        genericListenerBinder(buttonEvents,EventActivity.class);

    }

    @Override
    protected void onResume() {
        super.onResume();
        buttonExample.setEnabled(true);
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
}
