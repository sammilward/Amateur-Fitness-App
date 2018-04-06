package com.example.samue.fitnessap20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
     EditText name, height, weight ,age, calories, target;
    RadioButton male, female, buttonOne, buttonTwo, buttonThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initializing all the TextViews
        name = (EditText)findViewById(R.id.name);
        height = (EditText)findViewById(R.id.height);
        age = (EditText)findViewById(R.id.age);
        calories = (EditText)findViewById(R.id.calories);
        weight = (EditText)findViewById(R.id.weight);
        target = (EditText)findViewById(R.id.target);

        //initializing the radio buttons by order
        male = (RadioButton)findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);

        //Initializing the radio buttons that track your current workout routine
        buttonOne = (RadioButton)findViewById(R.id.button1);
        buttonTwo = (RadioButton)findViewById(R.id.button2);
        buttonThree = (RadioButton)findViewById(R.id.button3);




    }


    protected void submitForm(View view) {

        if(verifyData()) {
            if (view == findViewById(R.id.submitButton)) {

                Intent activity = new Intent(getApplication(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name.toString());

               activity.putExtras(bundle);

                startActivity(activity);

            }
        }



    }


    protected void cancelForm(View view){


            Intent activity = new Intent(getApplication(), MainActivity.class);

            startActivity(activity);
    }


    protected boolean verifyData(){
        if(name.getText().length() <= 0 || name.getText().length() < 4) {
            Toast.makeText(this, "The name must be 4 or more letters", Toast.LENGTH_LONG).show();
            return false;
        }

        if(age.getText().length() < 10) {
            Toast.makeText(this, "please enter your DOB  (day/month/year) eg 03/12/1995", Toast.LENGTH_LONG).show();
            return false;
        }

        if(height.getText().length() > 0) {
            if (Integer.valueOf(height.getText().toString()) <= 50 || Integer.valueOf(height.getText().toString()) > 99) {
                Toast.makeText(this, "please enter your height in inches", Toast.LENGTH_LONG).show();
                return false;
            }
        }

      if(height.getText().length() == 0){
          Toast.makeText(this, "please enter your height in inches", Toast.LENGTH_LONG).show();
          return false;
      }



        if(weight.getText().length() > 0) {
            if (Integer.valueOf(weight.getText().toString()) <= 50 || Integer.valueOf(weight.getText().toString()) > 200) {
                Toast.makeText(this, "please enter your weight in Kg", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(weight.getText().length() == 0){
            Toast.makeText(this, "please enter your weight in Kg", Toast.LENGTH_LONG).show();
            return false;
        }


        if(!male.isChecked() && !female.isChecked()){
            Toast.makeText(this, "please select your gender", Toast.LENGTH_LONG).show();
            return false;
        }



        if(target.getText().length() > 0) {
            if (Integer.valueOf(target.getText().toString()) <= 0 || Integer.valueOf(target.getText().toString()) > 5) {
                Toast.makeText(this, "please enter your target ( We recommend 1-2lbs per week but the max target is 5", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if(target.getText().length() == 0){
            Toast.makeText(this, "please enter your target ( We recommend 1-2lbs per week but the max target is 5", Toast.LENGTH_LONG).show();
            return false;
        }


        if(!buttonOne.isChecked() && !buttonTwo.isChecked() && !buttonThree.isChecked()){
            Toast.makeText(this, "please select how often you workout", Toast.LENGTH_LONG).show();
            return false;
        }


///This will need to be changed Later so that the type is converted to int
        if(calories.getText().length() <= 0 || calories.getText().length() < 3) {
            Toast.makeText(this, "Please enter how many calories you eat a day on average", Toast.LENGTH_LONG).show();
            return false;
        }






        return true;
    }


}
