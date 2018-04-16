package com.example.samue.fitnessap20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DecimalFormat;

public class ProfileActivity extends AppCompatActivity {

    EditText txtName, txtInitialWeight,txtCurrentWeight,txtHeight, txtDOB, txtRecCals, txtBMI;
    Spinner cbTarget, cbFitnessLevel;
    Button cmdEditSave, cmdChangeDate;
    boolean EditClicked = false;
    DatabaseManager dm = new DatabaseManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = (EditText) findViewById(R.id.txtName);
        txtInitialWeight = (EditText) findViewById(R.id.txtInitalWeight);
        txtCurrentWeight = (EditText) findViewById(R.id.txtCurrentWeight);
        txtHeight = (EditText) findViewById(R.id.txtHeight);
        txtDOB = (EditText) findViewById(R.id.txtDOB);
        txtRecCals = (EditText) findViewById(R.id.txtRecCals);
        txtBMI = (EditText) findViewById(R.id.txtBMI);
        cbTarget = (Spinner) findViewById(R.id.cbTarget);
        cbFitnessLevel = (Spinner) findViewById(R.id.cbFitnessLevel);
        cmdEditSave = (Button) findViewById(R.id.cmdEditSave);
        cmdChangeDate = (Button) findViewById(R.id.cmdChangeDOB);


        txtName.setEnabled(false);
        txtInitialWeight.setEnabled(false);
        txtCurrentWeight.setEnabled(false);
        txtHeight.setEnabled(false);
        txtDOB.setEnabled(false);
        txtRecCals.setEnabled(false);
        txtBMI.setEnabled(false);
        cbTarget.setEnabled(false);
        cbFitnessLevel.setEnabled(false);
        LoadData();

    }

    public void SetEditable(View view)
    {

        if (!EditClicked)
        {
            txtName.setEnabled(true);
            txtCurrentWeight.setEnabled(true);
            txtHeight.setEnabled(true);
            txtRecCals.setEnabled(true);
            cbTarget.setEnabled(true);
            cbFitnessLevel.setEnabled(true);
            cmdChangeDate.setVisibility(view.VISIBLE);

            EditClicked = true;
            cmdEditSave.setText("Save Changes");
        }
        else
        {
            if (Validated())
            {
                SaveData();
                EditClicked = false;
                cmdEditSave.setText("Edit Profile");
                txtName.setEnabled(false);
                txtCurrentWeight.setEnabled(false);
                txtHeight.setEnabled(false);
                txtRecCals.setEnabled(false);
                cbTarget.setEnabled(false);
                cbFitnessLevel.setEnabled(false);
                cmdChangeDate.setVisibility(view.INVISIBLE);
            }

        }
    }

    public void LoadData()
    {
        User CurUser = new User();
        CurUser = dm.GetUsersDetails();
        txtName.setText(CurUser.Name);
        txtInitialWeight.setText(CurUser.InitialWeight);
        txtCurrentWeight.setText(CurUser.CurrentWeight);
        txtHeight.setText(CurUser.Height);
        txtDOB.setText(CurUser.DOB);
        txtRecCals.setText(CurUser.CaloriesToConsume);

        switch (CurUser.Target)
        {
            case "M":
                cbTarget.setSelection(0);
                break;
            case "-1LbW":
                cbTarget.setSelection(1);
                break;
            case "-2LbW":
                cbTarget.setSelection(2);
        }

        switch (CurUser.FitnessLevel)
        {
            case "1":
                cbFitnessLevel.setSelection(0);
                break;
            case "2":
                cbFitnessLevel.setSelection(1);
                break;
            case "3":
                cbFitnessLevel.setSelection(2);
                break;
            case "4":
                cbFitnessLevel.setSelection(3);
                break;
            case "5":
                cbFitnessLevel.setSelection(4);
                break;
        }

        Double TempBmi = Double.parseDouble(CurUser.CalculateBMI()), BMI;
        DecimalFormat df = new DecimalFormat("#.#");
        BMI = Double.valueOf(df.format(TempBmi));

        String BMIMeaning = "";

        if (BMI < 18.5) BMIMeaning = "Underweight";
        else if (18.5 <= BMI && BMI < 24.9) BMIMeaning = "Healthy";
        else if (25 <= BMI && BMI < 29.9) BMIMeaning = "Overweight";
        else BMIMeaning = "Obese";

        txtBMI.setText(Double.toString(BMI) + " - " + BMIMeaning);


    }

    //Check if entered information is valid
    public boolean Validated()
    {
        return true;
    }

    //Save users new data to the database
    public void SaveData()
    {
        User UpdatedUser = new User();
        UpdatedUser.Name = txtName.getText().toString();
        UpdatedUser.InitialWeight = txtInitialWeight.getText().toString();
        UpdatedUser.CurrentWeight = txtCurrentWeight.getText().toString();
        UpdatedUser.Height = txtHeight.getText().toString();
        UpdatedUser.DOB = txtDOB.getText().toString();
        String TempTarget = cbTarget.getSelectedItem().toString();
        if (TempTarget.equals("Maintain Weight"))
        {
            UpdatedUser.Target = "M";
        }
        else if (TempTarget.equals("Lose 1 lb a week"))
        {
            UpdatedUser.Target = "-1LbW";
        }
        else if (TempTarget.equals("Lose 2 lb a week"))
        {
            UpdatedUser.Target = "-2LbW";
        }
        switch (cbFitnessLevel.getSelectedItem().toString())
        {
            case "1":
                UpdatedUser.FitnessLevel = "1";
                break;
            case "2":
                UpdatedUser.FitnessLevel = "2";
                break;
            case "3":
                UpdatedUser.FitnessLevel = "3";
                break;
            case "4":
                UpdatedUser.FitnessLevel = "4";
                break;
            case "5":
                UpdatedUser.FitnessLevel = "5";
                break;
        }
        UpdatedUser.CalculateRecommendedCalories();
        dm.SetUserDetails(UpdatedUser);
        dm.ResultWeight(UpdatedUser.CurrentWeight);
        dm.ResultBMI(UpdatedUser.CalculateBMI());

        LoadData();
    }

    //Write function to use calander to input date method
    public void SetDate()
    {

    }

    //New activity that opens that allows users to input info to add body fat results
    public void LoadBodyFat(View view){


        Intent intent = new Intent(this, BodyFat.class);
        startActivity(intent);

    }
}
