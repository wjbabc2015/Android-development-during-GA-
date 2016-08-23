package ecu.edu.edema.edemadetectapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import ioio.lib.api.AnalogInput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;
/**
 * Created by wangj15 on 1/22/2016.
 */
public class weightMeasure extends IOIOActivity implements AdapterView.OnClickListener {

    private TextView setWeight;
    private Button getWeight;
    private Button back;
    private Button saveData;

    private float[] volts = new float[100];
    private int i = 0;
    String weightDis;
    String weighCapt;
    String currentTime;
    private final String TAG = "weight";
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measure_weight);
        mydb = new DBHelper(this);

        setWeight = (TextView) findViewById(R.id.weight_display);
        getWeight = (Button) findViewById(R.id.get_weight);
        back = (Button) findViewById(R.id.weight_back);
        saveData = (Button) findViewById(R.id.save_data);

        getWeight.setOnClickListener(this);
        back.setOnClickListener(this);
        saveData.setOnClickListener(this);

    }

    private void setScales(float f) {
        volts[i] = f;
        i ++;
        if (i > 99) {
            // float[] t = new float[100];
            float average = 0;
            float sum = 0;
            i = 0;
            // t = lowPass(volts, t);
            for (int j = 30; j < volts.length; j++) {
                sum = sum + volts[j];
            }

            average = sum / (volts.length - 30);

            float weightOutput = 121.48f * average - 2.4618f;
            final String str = String.format("%.2f", weightOutput);
            weightDis = str;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_weight:
                weighCapt = weightDis;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setWeight.setText(weightDis + " lb");
                    }
                });
            break;

            case R.id.save_data:
                currentTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                if (mydb.getCurrentTime(currentTime, TAG) != null && mydb.getCurrentTime(currentTime, TAG).getCount()>0){
                    Toast.makeText(getApplicationContext(), "You have already measured today!", Toast.LENGTH_SHORT).show();
                } else if (mydb.insertEweight(currentTime, weighCapt)){
                Toast.makeText(getApplicationContext(), "Saved Successfully!", Toast.LENGTH_SHORT).show();
            }

                break;

            case R.id.weight_back:
                Intent back = new Intent(this, healthActivity.class);
                //Intent back = new Intent(this, infoDisplay.class);
                startActivity(back);
                finish();
                break;
        }
    }

    class Looper extends BaseIOIOLooper{

        private AnalogInput ScaleInput;

        @Override
        public void setup() throws ConnectionLostException {
            ScaleInput = ioio_.openAnalogInput(44);
        }

        @Override
        public void loop() throws ConnectionLostException, InterruptedException {
            setScales(ScaleInput.getVoltage());
            Thread.sleep(30);
        }


        @Override
        public void disconnected() {
            ScaleInput.close();
        }
    }

    @Override
    protected IOIOLooper createIOIOLooper() {
        return new Looper();
    }
}
