package ecu.edu.edema.edemadetectapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
 * Created by wangj15 on 1/28/2016.
 */
public class emdemaMeasure extends Activity implements AdapterView.OnClickListener {

    private TextView edemaDisplay;
    private Button getScore;
    private Button back;
    private Button saveData;

    DBHelper mydb;

    String scoreS;

    private final String TAG = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measure_edema);
        mydb = new DBHelper(this);

        edemaDisplay = (TextView) findViewById(R.id.edema_display);
        getScore = (Button) findViewById(R.id.get_score);
        back = (Button) findViewById(R.id.edema_back);
        saveData = (Button) findViewById(R.id.save_edemadata);

        getScore.setOnClickListener(this);
        back.setOnClickListener(this);
        saveData.setOnClickListener(this);

    }

    private void setEdemaScore(float d, float f) {
        //final String strd = String.format("%.2f", d);
        //final String strf = String.format("%.2f", f);
        final String edemaDisplayS = String.format("%.2f", d * f);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                edemaDisplay.setText(edemaDisplayS);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_score:
                double score = Math.random() * 5;
                scoreS = String.format("%.1f", score);
                edemaDisplay.setText(scoreS);
                break;
            case R.id.edema_back:
                Intent back = new Intent(this, healthActivity.class);
                startActivity(back);
                finish();
                break;
            case R.id.save_edemadata:
                String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                if (mydb.getCurrentTime(currentTime, TAG) != null && mydb.getCurrentTime(currentTime, TAG).getCount()>0){
                    Toast.makeText(getApplicationContext(), "You have already measured today!", Toast.LENGTH_SHORT).show();
                } else if (mydb.insertEscore(currentTime, scoreS)){
                    Toast.makeText(getApplicationContext(), "Saved Successfully!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

   /* class Looper extends BaseIOIOLooper {
        private AnalogInput displacementInput;
        private AnalogInput forceInput;
        private int count = 0;

        @Override
        public void setup() throws ConnectionLostException {
            displacementInput = ioio_.openAnalogInput(40);
            forceInput = ioio_.openAnalogInput(42);
        }

        @Override
        public void loop() throws ConnectionLostException, InterruptedException {
            setEdemaScore(displacementInput.getVoltage(), forceInput.getVoltage());
            Thread.sleep(30);
        }


        @Override
        public void disconnected() {
            displacementInput.close();
            forceInput.close();
        }
    }

    @Override
    protected IOIOLooper createIOIOLooper() {
        return new Looper();
    }*/
}
