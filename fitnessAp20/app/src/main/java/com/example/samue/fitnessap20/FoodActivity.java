package com.example.samue.fitnessap20;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class FoodActivity extends AppCompatActivity {
    Button dateButton;
    EditText date;
    DatePickerDialog.OnDateSetListener dateListener; // Created a datePick Dialog with listener
    int day, month, year;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        date = (EditText)findViewById(R.id.date);
        dateButton = (Button)findViewById(R.id.dateButton);

        Calendar calendar = Calendar.getInstance(); // creates an instance of Calendar with the current date.
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);



        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DatePickerDialog dialog = new DatePickerDialog(FoodActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateListener,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();




            }
        });


        dateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){

                String sDay;
                String sMonth;
                month = month + 1;
                if(day < 10){
                    sDay = "0" + day;
                }
                else sDay = "" + day;
                if( month < 10){
                    sMonth= "0" + month;
                }else sMonth = "" + month;


                date.setText("" + year + "/"+ sMonth +"/" + sDay);
            }
        };





    }


    public void setDate(View view){

    }





    public void addFood(View view) {

        if (view == findViewById(R.id.addFood)) {

            Intent activity = new Intent(getApplication(), addNewFoodActivity.class);

            startActivity(activity);

        }

    }

}

