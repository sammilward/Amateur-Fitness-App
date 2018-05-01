package com.example.samue.fitnessap20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    DatabaseManager dm = new DatabaseManager(this);
    TextView lblDate, lblWeight, lblBmi, lblBodyFat, lblWeightDif;
    BarChart ResultBarChar;
    Spinner GraphBy;

    TableLayout ResultsTable;
    TableRow ResultsRow;

    ArrayList<Results> AllResults = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ResultsTable = (TableLayout) findViewById(R.id.ResultsTable);

        lblWeightDif = (TextView) findViewById(R.id.lblWeightDif);

        ResultBarChar = (BarChart) findViewById(R.id.ResultBarChart);

        GraphBy = (Spinner) findViewById(R.id.SpinnerGraphFor);
        GraphBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (GraphBy.getSelectedItemPosition() == 0)
                {
                    CreateBarChartWeight();
                }
                else if (GraphBy.getSelectedItemPosition() == 1)
                {
                    CreateBarChartBMI();
                }
                else if (GraphBy.getSelectedItemPosition() == 2)
                {
                    CreateBarChartBF();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("");
            }



        });

        //Y axis data


        LoadData();
    }

    public void LoadData()
    {
        AllResults = dm.GetResults();

        for (int i = 0; i < AllResults.size(); i++)
        {
            //Make a new table row
            TableRow curRow = new TableRow(this);
            TableRow.LayoutParams Params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            curRow.setLayoutParams(Params);

            lblDate = new TextView(this);
            lblDate.setText(AllResults.get(i).DateResults);

            curRow.addView(lblDate);

            lblWeight = new TextView(this);
            lblWeight.setText(AllResults.get(i).Weight);

            curRow.addView(lblWeight);

            lblBmi = new TextView(this);
            lblBmi.setText(AllResults.get(i).BMI);

            curRow.addView(lblBmi);

            lblBodyFat = new TextView(this);
            lblBodyFat.setText(AllResults.get(i).BodyFat);
            curRow.addView(lblBodyFat);

            ResultsTable.addView(curRow);


        }

        User CurUsr = new User();
        CurUsr = dm.GetUsersDetails();

        Double Difference = round(Double.parseDouble(CurUsr.CurrentWeight) - Double.parseDouble(CurUsr.InitialWeight),1);

        if (Difference < 0)
        {
            lblWeightDif.setText("You have lost: " + Difference + "KG!");
        }
        else if (Difference > 0)
        {
            lblWeightDif.setText("You have gained: " + Difference + "KG!");
        }
        else {
            lblWeightDif.setText("Your weight has not changed since joining. Make sure you update your weght in the Profile page");
        }

        CreateBarChartWeight();
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public void CreateBarChartWeight()
    {
        //Y axis data

        ArrayList<BarEntry> Entries = new ArrayList<>();


        ArrayList<String> Dates = new ArrayList<>();


        int NumOfResults = AllResults.size();


        for (int i = 0; i < NumOfResults; i++)
        {
            Entries.add(new BarEntry(Float.parseFloat(AllResults.get(i).Weight),i));
            Dates.add(AllResults.get(i).DateResults);
        }

        BarDataSet barDataSet = new BarDataSet(Entries,"Weight");
        BarData data = new BarData(Dates,barDataSet);

        //barDataSet.setColors(new int[] {R.color.BackgroundCol});

        ResultBarChar.setData(data);
        ResultBarChar.setScaleEnabled(true);
        ResultBarChar.setTouchEnabled(true);
        ResultBarChar.setDragEnabled(true);
        ResultBarChar.setPinchZoom(true);
        ResultBarChar.setDescription("Graph of your weight since registration");
        ResultBarChar.notifyDataSetChanged();
        ResultBarChar.invalidate();




    }

    public void CreateBarChartBMI()
    {
        //Y axis data
        ArrayList<BarEntry> Entries = new ArrayList<>();


        ArrayList<String> Dates = new ArrayList<>();


        int NumOfResults = AllResults.size();


        for (int i = 0; i < NumOfResults; i++)
        {
            Entries.add(new BarEntry(Float.parseFloat(AllResults.get(i).BMI),i));
            Dates.add(AllResults.get(i).DateResults);
        }

        BarDataSet barDataSet = new BarDataSet(Entries,"BMI");
        //barDataSet.setColors(new int[] {R.color.BackgroundCol});
        BarData data = new BarData(Dates,barDataSet);


        ResultBarChar.setData(data);
        ResultBarChar.setScaleEnabled(true);
        ResultBarChar.setTouchEnabled(true);
        ResultBarChar.setDragEnabled(true);
        ResultBarChar.setPinchZoom(true);
        ResultBarChar.setDescription("Graph of your BMI since registration");
        ResultBarChar.notifyDataSetChanged();
        ResultBarChar.invalidate();


    }

    public void CreateBarChartBF()
    {
        //Y axis data
        ArrayList<BarEntry> Entries = new ArrayList<>();


        ArrayList<String> Dates = new ArrayList<>();


        int NumOfResults = AllResults.size();


        for (int i = 0; i < NumOfResults; i++)
        {
            Entries.add(new BarEntry(Float.parseFloat(AllResults.get(i).BodyFat),i));
            Dates.add(AllResults.get(i).DateResults);
        }

        BarDataSet barDataSet = new BarDataSet(Entries,"Body Fat");

        BarData data = new BarData(Dates,barDataSet);


        ResultBarChar.setData(data);
        ResultBarChar.setScaleEnabled(true);
        ResultBarChar.setTouchEnabled(true);
        ResultBarChar.setDragEnabled(true);
        ResultBarChar.setPinchZoom(true);
        ResultBarChar.setDescription("Graph of your body fat since registration");
        ResultBarChar.notifyDataSetChanged();
        ResultBarChar.invalidate();




    }

}
