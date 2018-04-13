package com.example.samue.fitnessap20;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.String;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    EditText txtName, txtHeight, txtWeight ,txtDOB;
    RadioButton rbMale, rbFemale, rbLevel1, rbLevel2, rbLevel3, rbLevel4, rbLevel5;

    Spinner cbTarget;
    Button dateButton;

    DatePickerDialog.OnDateSetListener dateListener; // Created a datePick Dialog with listener
    int day, month, year;
    DatabaseManager dm = new DatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        //initializing all the TextViews
        txtName = (EditText)findViewById(R.id.name);
        txtHeight = (EditText)findViewById(R.id.height);
        txtDOB = (EditText)findViewById(R.id.DOB);
        txtWeight = (EditText)findViewById(R.id.weight);
        cbTarget = (Spinner)findViewById(R.id.cbTarget);


        //initializing the radio buttons by order
        rbMale = (RadioButton)findViewById(R.id.male);
        rbFemale = (RadioButton)findViewById(R.id.female);




        //Initializing the radio buttons that track your current workout routine
        rbLevel1 = (RadioButton)findViewById(R.id.rblevel1);
        rbLevel2 = (RadioButton)findViewById(R.id.rblevel2);
        rbLevel3 = (RadioButton)findViewById(R.id.rblevel3);
        rbLevel4 = (RadioButton)findViewById(R.id.rblevel4);
        rbLevel5 = (RadioButton)findViewById(R.id.rblevel5);

        //NewUser.CalculateRecommendedCalories();
        //dm.SetUserDetails(NewUser);
        dateButton = (Button)findViewById(R.id.dateButton);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(); // creates an instance of Calendar with the current date.
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);


                DatePickerDialog dialog = new DatePickerDialog(SignUpActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateListener,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();





            }
        });

        dateListener = new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
              String sDay ="";
               String sMonth= "";
               month = month + 1;
                if(day < 10){
                    sDay = "0" + day;
                }
                else sDay = "" + day;
                if( month < 10){
                    sMonth= "0" + month;
                }else sMonth = "" + month;


               txtDOB.setText("" + year + "/"+sMonth+"/"+sDay);

            }
        };



    }







    public void submitForm(View view) {

        if(verifyData()) {
            if (view == findViewById(R.id.submitButton)) {
                //Create new User object to hold data
                User NewUser = new User();
                //FullName
                NewUser.Name = txtName.getText().toString();
                //Height In CM
                NewUser.Height = txtHeight.getText().toString();
                //Weight in KG
                //Initial weight and current weight are the same on sign up
                NewUser.CurrentWeight = txtWeight.getText().toString();
                //User DOB in DD/MM/YYYY
                NewUser.InitialWeight = txtWeight.getText().toString();
                NewUser.DOB = txtDOB.getText().toString();

                if (rbMale.isChecked())
                {
                    NewUser.Sex = "Male";
                } else
                {
                    NewUser.Sex = "Female";
                }


                String TempTarget = cbTarget.getSelectedItem().toString();
                if (TempTarget.equals("Maintain Weight"))
                {
                    NewUser.Target = "M";
                }
                else if (TempTarget.equals("Lose 1 lb a week"))
                {
                    NewUser.Target = "-1LbW";
                }
                else if (TempTarget.equals("Lose 2 lb a week"))
                {
                    NewUser.Target = "-2LbW";
                }


                if (rbLevel1.isChecked() == true)
                {
                    NewUser.FitnessLevel = "1";
                }
                else if (rbLevel2.isChecked() == true)
                {
                    NewUser.FitnessLevel = "2";
                }
                else if (rbLevel3.isChecked() == true)
                {
                    NewUser.FitnessLevel = "3";
                }
                else if (rbLevel4.isChecked() == true)
                {
                    NewUser.FitnessLevel = "4";
                }
                else if (rbLevel5.isChecked() == true)
                {
                    NewUser.FitnessLevel = "5";
                }
                NewUser.CalculateRecommendedCalories();
                dm.refreshDatabase();
                dm.SetUserDetails(NewUser);
                dm.ResultWeight(NewUser.CurrentWeight);
                dm.ResultBMI(Double.toString(NewUser.CalculateBMI()));


                Intent activity = new Intent(getApplication(), MainActivity.class);


                startActivity(activity);

            }
        }



    }


    public void cancelForm(View view){


            Intent activity = new Intent(getApplication(), MainActivity.class);

            startActivity(activity);
    }


    public boolean verifyData(){
        if(txtName.getText().length() < 2) {
            Toast.makeText(this, "Enter a name at least 2 letters long", Toast.LENGTH_LONG).show();
            return false;
        }

        if(txtDOB.getText().length() < 10) {
            Toast.makeText(this, "Please select your DOB", Toast.LENGTH_LONG).show();
            return false;
        }

        if(txtHeight.getText().length() > 0) {
            if (Integer.valueOf(txtHeight.getText().toString()) > 250) {
                Toast.makeText(this, "Enter your height in cm, below 250", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(txtHeight.getText().length() == 0){
          Toast.makeText(this, "Please enter your height in inches", Toast.LENGTH_LONG).show();
          return false;
        }



        if(txtWeight.getText().length() > 0) {
            if (Integer.valueOf(txtWeight.getText().toString()) <= 20 || Integer.valueOf(txtWeight.getText().toString()) > 250) {
                Toast.makeText(this, "Enter your weight in Kg between 20-250", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(txtWeight.getText().length() == 0){
            Toast.makeText(this, "Please enter your weight in Kg", Toast.LENGTH_LONG).show();
            return false;
        }


        if(!rbMale.isChecked() && !rbFemale.isChecked()){
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_LONG).show();
            return false;
        }



//        if(target.getText().length() > 0) {
//            if (Integer.valueOf(target.getText().toString()) <= 0 || Integer.valueOf(target.getText().toString()) > 5) {
//                Toast.makeText(this, "please enter your target ( We recommend 1-2lbs per week but the max target is 5", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        }
//        if(target.getText().length() == 0){
//            Toast.makeText(this, "please enter your target ( We recommend 1-2lbs per week but the max target is 5", Toast.LENGTH_LONG).show();
//            return false;
//        }


        if(!rbLevel1.isChecked() && !rbLevel2.isChecked() && !rbLevel3.isChecked() && !rbLevel4.isChecked() && !rbLevel5.isChecked()){
            Toast.makeText(this, "Please select your fitness level", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


}
