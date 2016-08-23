package ecu.edu.edema.edemadetectapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;

public class greetingActivity extends Activity implements AdapterView.OnClickListener{

    private Button checkHealth;
    private Button viewHistory;
    private Button myInfo;
    private Button dailyMessage;
    private Button done;
    private TextView greetingWord;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        greetingWord = (TextView) findViewById(R.id.greeting);
        imageView = (ImageView) findViewById(R.id.greeting_image);

        checkHealth = (Button) findViewById(R.id.check_health);

        viewHistory = (Button) findViewById(R.id.view_history);

        myInfo = (Button) findViewById(R.id.my_info);

        dailyMessage = (Button) findViewById(R.id.daily_message);
        done = (Button) findViewById(R.id.exit1);

        checkHealth.setOnClickListener(this);
        viewHistory.setOnClickListener(this);
        myInfo.setOnClickListener(this);
        dailyMessage.setOnClickListener(this);
        done.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);

        if (hours < 12){
            greetingWord.setText("Good Morning!");
            imageView.setImageResource(R.drawable.img18);
        }else if (hours < 18) {
            greetingWord.setText("Good Afternoon!");
            imageView.setImageResource(R.drawable.img18);
        }else {
            greetingWord.setText("Good Evening!");
            imageView.setImageResource(R.drawable.moon);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit1:
               finish();
                break;

            case R.id.check_health:
                Intent toMyhealth = new Intent(this, healthActivity.class);
                startActivity(toMyhealth);
                break;

            case R.id.view_history:
                Intent history = new Intent(this, displayHistory.class);
                startActivity(history);
                break;

            case R.id.my_info:
                Intent toMyinfo = new Intent(this, patientInfo.class);
                startActivity(toMyinfo);
                break;

            case R.id.daily_message:
                double roll = Math.random() * 5;
                if (roll > 2.5){
                    Toast.makeText(getApplicationContext(), "EAT HEALTHY", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "DO MORE EXERCISE", Toast.LENGTH_LONG).show();
                }

        }
    }
}
