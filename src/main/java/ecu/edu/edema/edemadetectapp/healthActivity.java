package ecu.edu.edema.edemadetectapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangj15 on 1/20/2016.
 */
public class healthActivity extends Activity implements OnClickListener {

    private Button measureWeight;
    private Button measureEdema;
    private Button survey;
    private Button back;

    private ImageView weightCheck;
    private ImageView edemaCheck;
    private ImageView feelingCheck;

    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_health);
        mydb = new DBHelper(this);

        measureWeight = (Button) findViewById(R.id.measure_weight);
        measureEdema = (Button) findViewById(R.id.measure_edema);
        survey = (Button) findViewById(R.id.feelling);
        back = (Button) findViewById(R.id.check_back);

        weightCheck = (ImageView) findViewById(R.id.weight_check);
        edemaCheck = (ImageView) findViewById(R.id.edema_check);
        feelingCheck = (ImageView) findViewById(R.id.felling_check);

        measureWeight.setOnClickListener(this);
        measureEdema.setOnClickListener(this);
        survey.setOnClickListener(this);
        back.setOnClickListener(this);

        String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if (mydb.getCurrentTime(currentTime, "weight") != null && mydb.getCurrentTime(currentTime, "weight").getCount()>0){
            weightCheck.setImageResource(R.drawable.check);
        }

        if (mydb.getCurrentTime(currentTime, "score") != null && mydb.getCurrentTime(currentTime, "score").getCount()>0){
            edemaCheck.setImageResource(R.drawable.check);
        }

        if (mydb.getCurrentTime(currentTime, "survey") != null && mydb.getCurrentTime(currentTime, "survey").getCount()>0){
            feelingCheck.setImageResource(R.drawable.check);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.measure_weight:
                Intent weightMeasure = new Intent(this, weightMeasure.class);
                startActivity(weightMeasure);
                break;

            case R.id.measure_edema:
                Intent edema = new Intent(this, emdemaMeasure.class);
                startActivity(edema);
                finish();
                break;

            case R.id.feelling:
                Intent survey = new Intent(this, survey.class);
                startActivity(survey);
                break;

            case R.id.check_back:
                Intent back = new Intent(this, greetingActivity.class);
                startActivity(back);
                finish();
        }
    }
}
