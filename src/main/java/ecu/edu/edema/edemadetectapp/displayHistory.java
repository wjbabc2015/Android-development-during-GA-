package ecu.edu.edema.edemadetectapp;

/**
 * Created by wangj15 on 1/27/2016.
 */

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class displayHistory extends Activity implements AdapterView.OnClickListener {

    private View weightChart, edemaChart;
    private String[] days = new String[] {
            "1st", "2nd" , "3rd", "4th", "5th", "6th","7th", "8th" , "9th", "10th",
            "11th", "12th", "13th", "14th", "15th", "16th","17th", "18th" , "19th", "20th",
            "21st", "22nd" , "23rd", "24th", "25th", "26th","27th", "28th" , "29th", "30th"
    };

    DBHelper mydb;
    int numberW = 30;
    int numberS = 30;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_trend);
        mydb = new DBHelper(this);

        Button back = (Button) findViewById(R.id.chart_back);

        back.setOnClickListener(this);


        openWeightChart();
        openEdemaChart();
    }

    private void openWeightChart(){

        double[] weight = new double[numberW];
        //double[] weight = mydb.getWeightData();
        //numberW = mydb.numberOfRows("weight");
        for (int w = 0; w<numberW ; w++){
            double weightTemp = Math.random() *2 + 155;
            weight[w] = weightTemp;
        }

        double max = 0;
        double min = 500;
        for (int i=0; i < numberW; i ++){
            if (weight[i] > max){
                max = weight[i];
            }
        }

        for (int i=0; i < numberW; i ++){
            if (weight[i] < min){
                min = weight[i];
            }
        }
        double average = (max + min)/2;
        double [] upperLine = new double [numberW];
        double [] lowerLine = new double [numberW];

        for (int u = 0; u<numberW; u++){
            upperLine[u] = average + 0.5;
        }

        for (int l = 0; l<numberW; l++){
            lowerLine[l] = average - 0.5;
        }

// Creating an XYSeries for Income
        XYSeries weightSeries = new XYSeries("Weight");
        XYSeries upperLineSeries = new XYSeries("Upper Line");
        XYSeries lowerLineSeries = new XYSeries("Lower Line");

// Adding data to Weight, Upper Line, and Lower Line Series
        for(int i=0;i<numberW ;i++){
            weightSeries.add(i,weight[i]);
            upperLineSeries.add(i, upperLine[i]);
            lowerLineSeries.add(i, lowerLine[i]);
        }

// Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
// Adding Weight Series to the dataset
        dataset.addSeries(weightSeries);
        dataset.addSeries(upperLineSeries);
        dataset.addSeries(lowerLineSeries);


// Creating XYSeriesRenderer to customize weightRenderer
        XYSeriesRenderer weightRenderer = new XYSeriesRenderer();
        weightRenderer.setColor(Color.CYAN); //color of the graph set to cyan
        weightRenderer.setFillPoints(true);
        weightRenderer.setLineWidth(2f);
        weightRenderer.setDisplayChartValues(true);
//setting chart value distance
        weightRenderer.setDisplayChartValuesDistance(10);
//setting line graph point style to circle
        weightRenderer.setPointStyle(PointStyle.CIRCLE);
//setting stroke of the line chart to solid
        weightRenderer.setStroke(BasicStroke.SOLID);

        // Creating XYSeriesRenderer to customize upperLineRenderer
        XYSeriesRenderer upperLineRenderer = new XYSeriesRenderer();
        upperLineRenderer.setColor(Color.GREEN); //color of the graph set to cyan
        upperLineRenderer.setFillPoints(true);
        upperLineRenderer.setLineWidth(4f);
        upperLineRenderer.setDisplayChartValues(false);
//setting chart value distance
        upperLineRenderer.setDisplayChartValuesDistance(10);
//setting line graph point style to circle
        upperLineRenderer.setPointStyle(PointStyle.CIRCLE);
//setting stroke of the line chart to solid
        upperLineRenderer.setStroke(BasicStroke.SOLID);

        // Creating XYSeriesRenderer to customize lowerLineRenderer
        XYSeriesRenderer lowerLineRenderer = new XYSeriesRenderer();
        lowerLineRenderer.setColor(Color.GREEN); //color of the graph set to cyan
        lowerLineRenderer.setFillPoints(true);
        lowerLineRenderer.setLineWidth(4f);
        lowerLineRenderer.setDisplayChartValues(false);
//setting chart value distance
        lowerLineRenderer.setDisplayChartValuesDistance(10);
//setting line graph point style to circle
        lowerLineRenderer.setPointStyle(PointStyle.CIRCLE);
//setting stroke of the line chart to solid
        lowerLineRenderer.setStroke(BasicStroke.SOLID);

// Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Weight Trend");
        //String currentTime = new SimpleDateFormat("yyyy-MM").format(new Date());
        multiRenderer.setXTitle("The recent 30 days");
        multiRenderer.setYTitle("Weight (lb)");

/***
 * Customizing graphs
 */
//setting text size of the title
        multiRenderer.setChartTitleTextSize(28);
//setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(24);
//setting text size of the graph lable
        multiRenderer.setLabelsTextSize(24);
//setting zoom buttons visiblity
        multiRenderer.setZoomButtonsVisible(false);
//setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(true, false);
//setting click false on graph
        multiRenderer.setClickEnabled(false);
//setting zoom to false on both axis
        multiRenderer.setZoomEnabled(false, true);
//setting lines to display on y axis
        multiRenderer.setShowGridY(true);
//setting lines to display on x axis
        multiRenderer.setShowGridX(true);
//setting legend to fit the screen size
        multiRenderer.setFitLegend(true);
//setting displaying line on grid
        multiRenderer.setShowGrid(true);
//setting zoom to false
        multiRenderer.setZoomEnabled(false);
//setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(false);
//setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(true);
//setting to in scroll to false
        multiRenderer.setInScroll(false);
//setting to set legend height of the graph
        multiRenderer.setLegendHeight(30);
//setting x axis label align
        multiRenderer.setXLabelsAlign(Align.CENTER);
//setting y axis label to align
        multiRenderer.setYLabelsAlign(Align.LEFT);
//setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
//setting no of values to display in y axis
        multiRenderer.setYLabels(10);
// setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
// if you use dynamic values then get the max y value and set here

        multiRenderer.setYAxisMax(max + 3);
        multiRenderer.setYAxisMin(min - 3);
//setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-0.5);
//setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMax(11);
//setting bar size or space between two bars
//multiRenderer.setBarSpacing(0.5);
//Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
//Setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setScale(2f);
//setting x axis point size
        multiRenderer.setPointSize(4f);
//setting the margin size for the graph in the order top, left, bottom, right
        multiRenderer.setMargins(new int[]{30, 30, 30, 30});

        for(int i=0; i< numberW;i++){
            multiRenderer.addXTextLabel(i, days[i]);
        }

// Adding incomeRenderer and expenseRenderer to multipleRenderer
// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
// should be same
        multiRenderer.addSeriesRenderer(weightRenderer);
        multiRenderer.addSeriesRenderer(upperLineRenderer);
        multiRenderer.addSeriesRenderer(lowerLineRenderer);

//this part is used to display graph on the xml
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.weight_chart);
//remove any views before u paint the chart
        chartContainer.removeAllViews();
//drawing bar chart
        weightChart = ChartFactory.getLineChartView(displayHistory.this, dataset, multiRenderer);
//adding the view to the linearlayout
        chartContainer.addView(weightChart);

    }

    private void openEdemaChart(){

        double[] edemaScore = new double[numberS];
        //double[] edemaScore = mydb.getScoreData();
        //numberS = mydb.numberOfRows("score");

        for (int w = 0; w<numberS ; w++){
            double edemaTemp = Math.random() * 5;
            edemaScore[w] = edemaTemp;
        }

// Creating an XYSeries for Income
        XYSeries edemaSeries = new XYSeries("Edema Score");

// Adding data to Income and Expense Series
        for(int i=0;i< numberS;i++){
            edemaSeries.add(i,edemaScore[i]);
        }

// Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
// Adding Income Series to the dataset
        dataset.addSeries(edemaSeries);

// Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer edemaRenderer = new XYSeriesRenderer();
        edemaRenderer.setColor(Color.CYAN); //color of the graph set to cyan
        edemaRenderer.setFillPoints(true);
        edemaRenderer.setLineWidth(2f);
        edemaRenderer.setDisplayChartValues(true);
//setting chart value distance
        edemaRenderer.setDisplayChartValuesDistance(10);
//setting line graph point style to circle
        edemaRenderer.setPointStyle(PointStyle.CIRCLE);
//setting stroke of the line chart to solid
        edemaRenderer.setStroke(BasicStroke.SOLID);


// Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Edema Score");
        //String currentTime = new SimpleDateFormat("yyyy-MM").format(new Date());
        multiRenderer.setXTitle("The recent 30 days");
        multiRenderer.setYTitle("Score");

/***
 * Customizing graphs
 */
//setting text size of the title
        multiRenderer.setChartTitleTextSize(28);
//setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(24);
//setting text size of the graph lable
        multiRenderer.setLabelsTextSize(24);
//setting zoom buttons visiblity
        multiRenderer.setZoomButtonsVisible(false);
//setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(true, false);
//setting click false on graph
        multiRenderer.setClickEnabled(false);
//setting zoom to false on both axis
        multiRenderer.setZoomEnabled(false, false);
//setting lines to display on y axis
        multiRenderer.setShowGridY(true);
//setting lines to display on x axis
        multiRenderer.setShowGridX(true);
//setting legend to fit the screen size
        multiRenderer.setFitLegend(true);
//setting displaying line on grid
        multiRenderer.setShowGrid(true);
//setting zoom to false
        multiRenderer.setZoomEnabled(false);
//setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(false);
//setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(true);
//setting to in scroll to false
        multiRenderer.setInScroll(false);
//setting to set legend height of the graph
        multiRenderer.setLegendHeight(30);
//setting x axis label align
        multiRenderer.setXLabelsAlign(Align.CENTER);
//setting y axis label to align
        multiRenderer.setYLabelsAlign(Align.LEFT);
//setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
//setting no of values to display in y axis
        multiRenderer.setYLabels(10);
// setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
// if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMin(0);
        multiRenderer.setYAxisMax(5);
//setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-0.5);
//setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMax(11);
//setting bar size or space between two bars
//multiRenderer.setBarSpacing(0.5);
//Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
//Setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setScale(2f);
//setting x axis point size
        multiRenderer.setPointSize(4f);
//setting the margin size for the graph in the order top, left, bottom, right
        multiRenderer.setMargins(new int[]{30, 30, 30, 30});

        for(int i=0; i< numberS;i++){
            multiRenderer.addXTextLabel(i, days[i]);
        }

// Adding incomeRenderer and expenseRenderer to multipleRenderer
// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
// should be same
        multiRenderer.addSeriesRenderer(edemaRenderer);

//this part is used to display graph on the xml
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.edema_chart);
//remove any views before u paint the chart
        chartContainer.removeAllViews();
//drawing bar chart
        edemaChart = ChartFactory.getLineChartView(displayHistory.this, dataset, multiRenderer);
//adding the view to the linearlayout
        chartContainer.addView(edemaChart);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chart_back:
                Intent back = new Intent(this, greetingActivity.class);
                startActivity(back);
                finish();
                break;
        }
    }
}
