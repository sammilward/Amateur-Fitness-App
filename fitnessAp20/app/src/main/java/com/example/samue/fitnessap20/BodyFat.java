package com.example.samue.fitnessap20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

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


        if (Validated())
        {
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
            double RoundedBF = round(BodyFat,1);
            lblBodyFat.setText("Body Fat Percentage: " + Double.toString(RoundedBF));
            lblBodyFat.setVisibility(View.VISIBLE);
            dm.ResultBodyFat(Double.toString(RoundedBF));
        }



    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public boolean Validated()
    {
        if ("Female".equals(curuser.Sex))
        {
            if(txtWristCir.getText().length() <= 0 || txtWristCir.getText().length() > 3)
            {
                Toast.makeText(this, "Enter a rounded inch measurement of Wrist", Toast.LENGTH_LONG).show();
                return false;
            }
            if (txtForarmCir.getText().length() <= 0 || txtForarmCir.getText().length() > 3)
            {
                Toast.makeText(this, "Enter a rounded inch measurement of Forarm", Toast.LENGTH_LONG).show();
                return false;
            }

            if (txtHipCir.getText().length() <= 0 || txtHipCir.getText().length() > 3)
            {
                Toast.makeText(this, "Enter a rounded inch measurement of Hip", Toast.LENGTH_LONG).show();
                return false;
            }

            if (txtWaistGirth.getText().length() <= 0 || txtWaistGirth.getText().length() > 3)
            {
                Toast.makeText(this, "Enter a rounded inch measurement of Waist", Toast.LENGTH_LONG).show();
                return false;
            }

        }
        else if ("Male".equals(curuser.Sex))
        {
            if (txtWaistGirth.getText().length() <= 0 || txtWaistGirth.getText().length() > 3)
            {
                Toast.makeText(this, "Enter a rounded inch measurement of Waist", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

    //http://www.bmi-calculator.net/body-fat-calculator/#result


}
