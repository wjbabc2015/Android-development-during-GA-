package ecu.edu.edema.edemadetectapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by wangj15 on 11/24/2015.
 */
public class infoDisplay extends Activity{

    //private List<String> data = null;

    private ListView listView;

    DBHelper mydb;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_display);

        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getAllInfo("survey");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);

        //data = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(arrayAdapter);


       /* for (int i = 1; i<= mydb.numberOfRows(); i ++){
            Cursor rs = mydb.getData(i);
            String dateDisplay = rs.getString(rs.getColumnIndex(DBHelper.MEASURE_DATE));
            String scoreDisplay = rs.getString(rs.getColumnIndex(DBHelper.PATIENT_SCORE));
            String weightDisplay = rs.getString(rs.getColumnIndex(DBHelper.PATIENT_WEIGHT));
            data.add(i +dateDisplay + scoreDisplay + weightDisplay);
        }


        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }


   private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount(){
            System.out.println("getCout()--->" + data.size());
            return data.size();
        }

        @Override
        public Object getItem(int position){
            return null;
        }

        @Override
        public long getItemId(int position){
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("getView---->position: " + position);
            TextView tv = new TextView(getApplicationContext());
            tv.setTextSize(20);
            String s = data.get(position);
            tv.setText(s);
            return tv;
        }*/

    }

}
