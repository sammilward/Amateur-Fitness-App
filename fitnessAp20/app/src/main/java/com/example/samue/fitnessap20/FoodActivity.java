package com.example.samue.fitnessap20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
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

