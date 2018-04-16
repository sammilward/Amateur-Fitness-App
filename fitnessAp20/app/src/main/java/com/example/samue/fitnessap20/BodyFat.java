package com.example.samue.fitnessap20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BodyFat extends AppCompatActivity {

    TextView lblWaistGirth, lblWristCir, lblHipCir, lblForearmCir, lblBodyFat;
    EditText txtWaistGirth, txtWristCir, txtHipCir , txtForarmCir;
    Button CalculateBF;
    DatabaseManager dm = new DatabaseManager(this);

    User curuser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_fat);

        txtWaistGirth = (EditText) findViewById(R.id.txtWaistGirth);
        txtWristCir = (EditText) findViewById(R.id.txtWristCir);
        txtHipCir = (EditText) findViewById(R.id.txtHipCir);
        txtForarmCir = (EditText) findViewById(R.id.txtForarmCir);

        lblWaistGirth = (TextView) findViewById(R.id.lblWaistGirth);
        lblWristCir = (TextView) findViewById(R.id.lblWristCir);
        lblHipCir = (TextView) findViewById(R.id.lblHipCir);
        lblForearmCir = (TextView) findViewById(R.id.lblForarmCir);
        lblBodyFat = (TextView) findViewById(R.id.lblBodyFat);

        CalculateBF = (Button) findViewById(R.id.cmdCalculateBF);

        LoadData();
    }

    void LoadData()
    {
        curuser = dm.GetUsersDetails();
        if ("Female".equals(curuser.Sex))
        {
            ifFemale();
        }

    }

    void ifFemale()
    {
        lblWristCir.setVisibility(View.VISIBLE);
        txtWristCir.setVisibility(View.VISIBLE);
        lblHipCir.setVisibility(View.VISIBLE);
        txtHipCir.setVisibility(View.VISIBLE);
        lblForearmCir.setVisibility(View.VISIBLE);
        txtForarmCir.setVisibility(View.VISIBLE);
    }

    void Calculate(View view)
    {
        Double BodyFat = 0.0;




        if ("Female".equals(curuser.Sex))
        {
            Double WaistGir = Double.parseDouble(txtWaistGirth.getText().toString());
            Double WristCir = Double.parseDouble(txtWristCir.getText().toString());
            Double HipCir = Double.parseDouble(txtHipCir.getText().toString());
            Double ForearmCir = Double.parseDouble(txtForarmCir.getText().toString());
            BodyFat = curuser.CalculateBodyFat(WaistGir,WristCir,HipCir,ForearmCir);
        }
        else
        {
            Double WaistGir = Double.parseDouble(txtWaistGirth.getText().toString());
            BodyFat = curuser.CalculateBodyFat(WaistGir,0.0,0.0,0.0);
        }
        String BodyFatShort = Double.toString(BodyFat).substring(0,4);
        lblBodyFat.setText("Body Fat Percentage: " + BodyFatShort);
        lblBodyFat.setVisibility(View.VISIBLE);
        dm.ResultBodyFat(BodyFatShort);

    }
}
