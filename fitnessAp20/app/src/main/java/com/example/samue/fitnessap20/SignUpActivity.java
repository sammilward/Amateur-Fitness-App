package com.example.samue.fitnessap20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.String;

public class SignUpActivity extends AppCompatActivity {
    EditText txtName, txtHeight, txtWeight ,txtDOB;
    RadioButton rbMale, rbFemale, rbLevel1, rbLevel2, rbLevel3, rbLevel4, rbLevel5;
    Spinner cbTarget;
    DatabaseManager dm = new DatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        //initializing all the TextViews
        txtName = (EditText)findViewById(R.id.name);
        txtHeight = (EditText)findViewById(R.id.height);
        txtDOB = (EditText)findViewById(R.id.age);
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


    }




    protected void submitForm(View view) {

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






                Intent activity = new Intent(getApplication(), MainActivity.class);
                //Bundle bundle = new Bundle();
                //bundle.putString("name", txtName.toString());

                //activity.putExtras(bundle);

                startActivity(activity);

            }
        }



    }


    protected void cancelForm(View view){


            Intent activity = new Intent(getApplication(), MainActivity.class);

            startActivity(activity);
    }


    protected boolean verifyData(){
        if(txtName.getText().length() <= 0 || txtName.getText().length() < 2) {
            Toast.makeText(this, "The name must be 2 or more letters", Toast.LENGTH_LONG).show();
            return false;
        }

        if(txtDOB.getText().length() < 10) {
            Toast.makeText(this, "Please enter your DOB  (day/month/year) eg 03/12/1995", Toast.LENGTH_LONG).show();
            return false;
        }

        if(txtHeight.getText().length() > 0) {
            if (Integer.valueOf(txtHeight.getText().toString()) > 250) {
                Toast.makeText(this, "Please enter your height in cm", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(txtHeight.getText().length() == 0){
          Toast.makeText(this, "Please enter your height in inches", Toast.LENGTH_LONG).show();
          return false;
        }



        if(txtWeight.getText().length() > 0) {
            if (Integer.valueOf(txtWeight.getText().toString()) <= 20 || Integer.valueOf(txtWeight.getText().toString()) > 250) {
                Toast.makeText(this, "Please enter your weight in Kg", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(txtWeight.getText().length() == 0){
            Toast.makeText(this, "Please enter your weight in Kg", Toast.LENGTH_LONG).show();
            return false;
        }


        if(!rbMale.isChecked() && !rbMale.isChecked()){
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
