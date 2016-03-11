package be.kejcs.sadg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Josi on 13/02/2016.
 */
public class StartTimeActivity extends AppCompatActivity {

    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_duration);

        button = (Button) findViewById(R.id.buttonNext);


        setOnButtonClickListeners();
    }

    private void genericListenerBinder(final Button b,final Class c){

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                b.setEnabled(false);
                finish();
            }
        });
    }

    private void setOnButtonClickListeners() {

        genericListenerBinder(button,ExampleActivity.class);


    }

    @Override
    protected void onResume() {
        super.onResume();
        button.setEnabled(true);
    }
}
