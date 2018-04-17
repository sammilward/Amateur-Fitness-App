package com.example.samue.fitnessap20;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

public class FoodActivity extends AppCompatActivity {

    Button dateButton;
    EditText date;
    EditText txtFoodAmount;
    Spinner cbFoodNames;
    RadioButton rbBreakfast, rbLunch, rbDinner, rbSnack;


    DatePickerDialog.OnDateSetListener dateListener; // Created a datePick Dialog with listener

    ArrayList<Food> AllFood;
    ArrayList<String> FoodNames = new ArrayList();

    int day, month, year;

    DatabaseManager dm = new DatabaseManager(this);


    public void onRestart() {
        super.onRestart();
        LoadUsersFood();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        date = (EditText)findViewById(R.id.date);
        dateButton = (Button)findViewById(R.id.dateButton);
        cbFoodNames = (Spinner) findViewById(R.id.cbFoodNames);
        txtFoodAmount = (EditText) findViewById(R.id.txtfoodAmount);

        rbBreakfast = (RadioButton) findViewById(R.id.rbBreakfast);
        rbLunch = (RadioButton) findViewById(R.id.rbLunch);
        rbDinner = (RadioButton) findViewById(R.id.rbDinner);
        rbSnack = (RadioButton) findViewById(R.id.rbSnack);

        Calendar calendar = Calendar.getInstance(); // creates an instance of Calendar with the current date.
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

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

        date.setText("" + year + "-"+ sMonth +"-" + sDay);

        LoadUsersFood();



        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(FoodActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateListener,
                        year,month-1,day);

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


                date.setText("" + year + "-"+ sMonth +"-" + sDay);
            }
        };





    }


    public void LoadUsersFood()
    {
        FoodNames.clear();
        FoodNames.add("Select a food");
        AllFood = dm.GetAllFood();
        for (int i = 0; i < AllFood.size(); i++)
        {
            FoodNames.add(AllFood.get(i).FoodName);
        }
        ResetSpinner();
    }

    public void ResetSpinner()
    {
        ArrayAdapter<String> Food_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, FoodNames);
        cbFoodNames.setAdapter(Food_adapter);
    }

    public void addFood(View view) {

        if (view == findViewById(R.id.addFood)) {

            Intent activity = new Intent(getApplication(), addNewFoodActivity.class);

            startActivity(activity);

        }

    }

    public boolean Validated()
    {
        if (txtFoodAmount.getText().toString().length() > 4 || txtFoodAmount.getText().toString().length() <= 0)
        {
            Toast.makeText(this, "Enter the amount of this food you have ate in Grams", Toast.LENGTH_LONG).show();
            return false;

        }
        if (!rbBreakfast.isChecked() && !rbLunch.isChecked() && !rbDinner.isChecked() && !rbSnack.isChecked())
        {
            Toast.makeText(this, "Select at which time of day you ate this food", Toast.LENGTH_LONG).show();
            return false;

        }

        if (cbFoodNames.getSelectedItemPosition() == 0)
        {
            Toast.makeText(this, "Please chose the food you ate", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }

    public void ClearForm()
    {
        rbBreakfast.setChecked(false);
        rbLunch.setChecked(false);
        rbDinner.setChecked(false);
        rbSnack.setChecked(false);

        cbFoodNames.setSelection(0);

        txtFoodAmount.setText("");

    }

    public void AddDailyFood(View view)
    {

        if(Validated())
        {
            DailyFood NewEntry = new DailyFood();
            NewEntry.FoodName = cbFoodNames.getSelectedItem().toString();
            NewEntry.AmountAte = txtFoodAmount.getText().toString();
            NewEntry.DateAte = date.getText().toString();

            if(rbBreakfast.isChecked()){
                NewEntry.Meal = "Breakfast";
            }
            else if (rbLunch.isChecked())
            {
                NewEntry.Meal = "Lunch";
            }
            else if (rbDinner.isChecked())
            {
                NewEntry.Meal = "Dinner";
            }
            else if (rbSnack.isChecked())
            {
                NewEntry.Meal = "Snack";
            }
            dm.AddDailyFood(NewEntry);
            ClearForm();

            Toast.makeText(this, "Food Added to your daily amount", Toast.LENGTH_LONG).show();


        }

    }

}

