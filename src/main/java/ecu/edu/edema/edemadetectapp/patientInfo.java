package ecu.edu.edema.edemadetectapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wangj15 on 1/15/2016.
 */
public class patientInfo extends Activity implements AdapterView.OnItemSelectedListener, AdapterView.OnClickListener{

    private EditText name;
    private EditText physname;
    private EditText weight;
    private EditText height;
    private Spinner gender;
    private TextView bmiDisplay;

    private Button submit;
    private Button back;

    String item, bmiS;
    //String weightS, heightS, bmiS;

    DBHelper mydb;


    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        mydb = new DBHelper(this);

        name = (EditText) findViewById(R.id.name_input);
        physname = (EditText) findViewById(R.id.physname_input);
        weight = (EditText) findViewById(R.id.weight_input);
        height = (EditText) findViewById(R.id.height_input);

        gender = (Spinner) findViewById(R.id.gender);

        bmiDisplay = (TextView) findViewById(R.id.bmi_output);

        submit = (Button) findViewById(R.id.info_submit);
        back = (Button) findViewById(R.id.info_back);

        gender.setOnItemSelectedListener(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.Gender, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        gender.setAdapter(adapter);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_submit:
                String nameS = name.getText().toString();
                String pnameS = physname.getText().toString();
                String weightS = weight.getText().toString();
                String heightS = height.getText().toString();

                double weightD = Double.parseDouble(weightS);
                double heightD = Double.parseDouble(heightS);

                setBMI(weightD,heightD);



                if (mydb.getMyinfoData(nameS) != null && mydb.getMyinfoData(nameS).getCount()>0) {
                    if (mydb.updateMyinfo(nameS, pnameS, item, weightS, heightS, bmiS)) {
                        Toast.makeText(getApplicationContext(), "Updated successfully!", Toast.LENGTH_SHORT).show();
                    }
                }else if (mydb.insertMyinfo(nameS, pnameS, item, weightS, heightS, bmiS)) {
                        Toast.makeText(getApplicationContext(), "Submitted successfully!", Toast.LENGTH_SHORT).show();}


                  /*  if (mydb.updateMyinfo(nameS, pnameS, item, weightS, heightS, bmiS)) {
                        Toast.makeText(getApplicationContext(), "Updated successfully!", Toast.LENGTH_SHORT).show();
                    } else if (mydb.insertMyinfo(nameS, pnameS, item, weightS, heightS, bmiS)) {
                        Toast.makeText(getApplicationContext(), "Submitted successfully!", Toast.LENGTH_SHORT).show();
                    }
*/
                break;

            case R.id.info_back:
                Intent back = new Intent(this, greetingActivity.class);
                //Intent back = new Intent(this, infoDisplay.class);
                startActivity(back);
                finish();
                break;


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        item = parent.getItemAtPosition(pos).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setBMI (double weight, double height){

        double bmiD = (weight * 703)/(height * height);

        if (bmiD < 18.5){
            bmiS = "Underweight";
            bmiDisplay.setTextColor(Color.parseColor("#FF21B7ED"));
        }else if (bmiD < 25){
            bmiS = "Healthy";
            bmiDisplay.setTextColor(Color.parseColor("#FF11EF0D"));
        }else if (bmiD < 30){
            bmiS = "Overweight";
            bmiDisplay.setTextColor(Color.parseColor("#FFF3DF7A"));
        }else if (bmiD < 40){
            bmiS = "Obese";
            bmiDisplay.setTextColor(Color.parseColor("#FFF06B05"));
        }else {
            bmiS = "Severe!!";
            bmiDisplay.setTextColor(Color.parseColor("#FFF90505"));
        }

        bmiDisplay.setText(bmiS);

    }
}
