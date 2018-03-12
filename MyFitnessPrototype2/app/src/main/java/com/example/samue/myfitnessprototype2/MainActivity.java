package com.example.samue.myfitnessprototype2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int dayofItem, month, year;
    String foodName;
    public void pullInformation(View view){
        // Button submitButton = (Button)findViewById(R.id.submit);
        TextView foodQuerie = (TextView)findViewById(R.id.searchFoodName);
        TextView foodAmount = (TextView)findViewById(R.id.foodAmount);

        TextView day = (TextView)findViewById(R.id.daySlot);
        TextView monthSlot= (TextView)findViewById(R.id.monthSlot);
        TextView yearSlot= (TextView)findViewById(R.id.yearSlot);

        dayofItem = Integer.parseInt(day.getText().toString());
        month = Integer.parseInt(monthSlot.getText().toString());
        year = Integer.parseInt(yearSlot.getText().toString());

        if(dayofItem > 31 ||  1 > dayofItem){
            Toast.makeText(this, "Day must be between 1 - 31", Toast.LENGTH_LONG).show();
            dayofItem = 0;
        }
        if(month > 12 ||  1 > month){
            Toast.makeText(this, "Day must be between 1 - 31", Toast.LENGTH_LONG).show();
            dayofItem = 0;
        }
        if(month > 12 ||  1 > month){
            Toast.makeText(this, "Day must be between 1 - 31", Toast.LENGTH_LONG).show();
            dayofItem = 0;
        }
       // TextView foodQuerie = (TextView)findViewById(R.id.searchFoodName);

        foodName = foodQuerie.getText().toString();

      //  Toast.makeText(this, "search for " + foodName, Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_page);
    }
}
