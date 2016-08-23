package ecu.edu.edema.edemadetectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by wangj15 on 1/19/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "edemaSystem.db";

    public static final String MYINFO_TABLE = "myinfo";
    public static final String WEIGHT_TABLE = "edemaweight";
    public static final String SCORE_TABLE = "edemascore";

    public static final String PATIENT_NAME = "name";
    public static final String PHYS_NAME = "pname";
    public static final String PATIENT_GENDER = "gender";
    public static final String PATIENT_WEIGHT = "pweight";
    public static final String PATIENT_HEIGHT = "pheight";
    public static final String PATIENT_BMI = "bmi";

    public static final String MEASURE_TIME = "time";

    public static final String MEASURE_WEIGHT = "weight";
    public static final String MEASURE_SCORE = "score";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table myinfo ("
                        + PATIENT_NAME + " text,"
                        + PHYS_NAME + " text,"
                        + PATIENT_GENDER + " text,"
                        + PATIENT_WEIGHT + " text,"
                        + PATIENT_HEIGHT + " text,"
                        + PATIENT_BMI + " text)"
        );

        db.execSQL("create table " + WEIGHT_TABLE + " ("
                        + MEASURE_TIME + " text,"
                        + MEASURE_WEIGHT + " text)"
        );

        db.execSQL("create table " + SCORE_TABLE + " ("
                        + MEASURE_TIME + " text,"
                        + MEASURE_SCORE + " text)"
        );

        db.execSQL("create table survey (time text, feeling text, breathing text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS myinfo");
        db.execSQL("DROP TABLE IF EXISTS edemaweight");
        db.execSQL("DROP TABLE IF EXISTS edemascore");
        db.execSQL("DROP TABLE IF EXISTS survey");
        onCreate(db);
    }

    public boolean insertMyinfo(String name, String pname, String gender, String weight, String height, String bmi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PATIENT_NAME, name);
        contentValues.put(PHYS_NAME, pname);
        contentValues.put(PATIENT_GENDER, gender);
        contentValues.put(PATIENT_WEIGHT, weight);
        contentValues.put(PATIENT_HEIGHT, height);
        contentValues.put(PATIENT_BMI, bmi);
        db.insert(MYINFO_TABLE, null, contentValues);
        return true;
    }

    public boolean updateMyinfo(String name, String pname, String gender, String weight, String height, String bmi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PHYS_NAME, pname);
        contentValues.put(PATIENT_GENDER, gender);
        contentValues.put(PATIENT_WEIGHT, weight);
        contentValues.put(PATIENT_HEIGHT, height);
        contentValues.put(PATIENT_BMI, bmi);
        db.update(MYINFO_TABLE, contentValues, "name=?", new String[]{name});
        return true;
    }

    public boolean insertEweight(String date, String weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEASURE_TIME, date);
        contentValues.put(MEASURE_WEIGHT, weight);
        db.insert(WEIGHT_TABLE, null, contentValues);
        return true;
    }

    public boolean insertSurvey(String date, String feeling, String breathing) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", date);
        contentValues.put("feeling", feeling);
        contentValues.put("breathing", breathing);
        db.insert("survey", null, contentValues);
        return true;
    }

    public boolean insertEscore(String date, String score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEASURE_TIME, date);
        contentValues.put(MEASURE_SCORE, score);
        db.insert(SCORE_TABLE, null, contentValues);
        return true;
    }

    public Cursor getCurrentTime (String currentTime, String tag){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;

        switch (tag){
            case "weight":
                res = db.rawQuery("select * from edemaweight where time='" + currentTime + "'", null);
                break;

            case "score":
                res = db.rawQuery("select * from edemascore where time='" + currentTime + "'", null);
                break;

            case "survey":
                res = db.rawQuery("select * from survey where time='" + currentTime + "'", null);
                break;
        }
        return res;
    }
    /*
          public boolean insertEscore(String date, String score) {
              SQLiteDatabase db = this.getWritableDatabase();
              ContentValues contentValues = new ContentValues();
              contentValues.put(MEASURE_TIME, date);
              contentValues.put(MEASURE_SCORE, score);
              db.insert(SCORE_TABLE, null, contentValues);
              return true;
          }
      */
    public Cursor getMyinfoData(String name) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + MYINFO_TABLE + " where " + PATIENT_NAME + "='" + name + "'", null);
        return res;
    }

    public ArrayList<String> getAllInfo(String tag)
    {
        ArrayList<String> array_list = new ArrayList<>();
        Cursor res;

        SQLiteDatabase db = this.getReadableDatabase();

        switch (tag){
            case "weight":
                res =  db.rawQuery( "select * from edemaweight", null );
                res.moveToFirst();

                while(res.isAfterLast() == false){

                    String weightMeased = res.getString(res.getColumnIndex(MEASURE_WEIGHT));
                    String currentTime = res.getString(res.getColumnIndex(MEASURE_TIME));
                    array_list.add(currentTime + ". " + weightMeased + "| ");
                    res.moveToNext();
                }
                break;

            case "info":
                res =  db.rawQuery( "select * from myinfo", null );
                res.moveToFirst();

                while(res.isAfterLast() == false){
                    String name = res.getString(res.getColumnIndex(PATIENT_NAME));
                    String genderDisplay = res.getString(res.getColumnIndex(PATIENT_GENDER));
                    String scoreDisplay = res.getString(res.getColumnIndex(PATIENT_BMI));
                    String weightDisplay = res.getString(res.getColumnIndex(PATIENT_WEIGHT));
                    array_list.add(name + ". " + genderDisplay + "| " + scoreDisplay + "| " + weightDisplay);
                    res.moveToNext();
                }
                break;

            case "survey":
                res =  db.rawQuery( "select * from survey", null );
                res.moveToFirst();

                while(res.isAfterLast() == false){
                    String surveyTime = res.getString(res.getColumnIndex("time"));
                    String flLevel = res.getString(res.getColumnIndex("feeling"));
                    String brlevel = res.getString(res.getColumnIndex("breathing"));
                    array_list.add(surveyTime + ". " + flLevel + "| " + brlevel);
                    res.moveToNext();
                }
                break;



        }

        return array_list;

    }

    public int numberOfRows(String tag){

        int numRows = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        /*Cursor cursor = db.rawQuery("select count(*) as numRows from myinfo", null);
        cursor.moveToFirst();
        int numRows = cursor.getInt(0);
        cursor.close();*/
        switch (tag){
            case "weight":
                numRows = (int) DatabaseUtils.queryNumEntries(db, "edemaweight");
                break;
            case "score":
                numRows = (int) DatabaseUtils.queryNumEntries(db, "edemascore");
        }

        return numRows;
    }

    public double [] getWeightData() {
        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs = db.rawQuery("select " + MEASURE_WEIGHT + " from edemaweight", null);
        rs.moveToFirst();

        double[] result = new double[numberOfRows(MEASURE_WEIGHT)];
        while (rs.isAfterLast() == false) {
            String columnS = rs.getString(rs.getColumnIndex(MEASURE_WEIGHT));
            double column = Double.parseDouble(columnS);
            result [i] = column;
            rs.moveToNext();
            i ++;
        }
        return result;
    }

    public double [] getScoreData() {
        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs = db.rawQuery("select " + MEASURE_SCORE + " from edemascore", null);
        rs.moveToFirst();

        double[] result = new double[numberOfRows(MEASURE_SCORE)];
        while (rs.isAfterLast() == false) {
            String columnS = rs.getString(rs.getColumnIndex(MEASURE_SCORE));
            double column = Double.parseDouble(columnS);
            result [i] = column;
            rs.moveToNext();
            i ++;
        }
        return result;
    }


}
