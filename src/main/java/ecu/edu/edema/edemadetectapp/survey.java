package ecu.edu.edema.edemadetectapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangj15 on 1/25/2016.
 */
public class survey extends Activity implements AdapterView.OnItemSelectedListener, AdapterView.OnClickListener{

    private Spinner flLevel;
    private Spinner brLevel;

    private Button submit;
    private Button back;

    private ArrayAdapter<CharSequence> flAdapter;
    private ArrayAdapter<CharSequence> brAdapter;

    String flItem, brItem;

    private final String TAG = "survey";

    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey);

        mydb = new DBHelper(this);

        flLevel = (Spinner) findViewById(R.id.feeling_level);
        brLevel = (Spinner) findViewById(R.id.breath_level);

        submit = (Button) findViewById(R.id.survey_submit);
        back = (Button) findViewById(R.id.survey_back);

        flLevel.setOnItemSelectedListener(this);
        brLevel.setOnItemSelectedListener(this);

        flAdapter = ArrayAdapter.createFromResource(this, R.array.Feeling_level, R.layout.spinner_item);
        flAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        brAdapter = ArrayAdapter.createFromResource(this, R.array.Breath_level, R.layout.spinner_item);
        brAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        flLevel.setAdapter(flAdapter);
        brLevel.setAdapter(brAdapter);

        submit.setOnClickListener(this);
        back.setOnClickListener(this);



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.feeling_level:
                flItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + flItem, Toast.LENGTH_SHORT).show();
                break;

            case R.id.breath_level:
                brItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + brItem, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.survey_submit:
                String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                if (mydb.getCurrentTime(currentTime, TAG) != null && mydb.getCurrentTime(currentTime, TAG).getCount()>0){
                    Toast.makeText(getApplicationContext(), "You have already done survey today!", Toast.LENGTH_SHORT).show();
                } else if (mydb.insertSurvey(currentTime, flItem, brItem)) {
                    Toast.makeText(getApplicationContext(), "submitted Successfully!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.survey_back:
                Intent back = new Intent(this, healthActivity.class);
                //Intent back = new Intent(this, infoDisplay.class);
                startActivity(back);
                finish();
                break;
        }
    }
}
