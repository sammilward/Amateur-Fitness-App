package com.example.samue.fitnessap20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    DatabaseManager dm = new DatabaseManager(this);
    TextView lblDate, lblWeight, lblBmi, lblBodyFat, lblWeightDif;

    TableLayout ResultsTable;
    TableRow ResultsRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ResultsTable = (TableLayout) findViewById(R.id.ResultsTable);

        lblWeightDif = (TextView) findViewById(R.id.lblWeightDif);

        LoadData();
    }

    public void LoadData()
    {
        ArrayList<Results> AllResults = dm.GetResults();

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
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

}
