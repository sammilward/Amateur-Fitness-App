package com.samal.fitnessamateur;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {
    TextView txtSelect;
    Button cmdSelect;
    DatabaseManager dbMan = new DatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //Wipes all data from tables
        dbMan.refreshDatabase();


        dbMan.loadExercises(this);

//        Food NewFood = new Food();
//        NewFood.FoodName = "Chips";
//        NewFood.CaloriesPer100 = "347";
//        NewFood.CarbsPer100 = "32";
//        NewFood.CarbSugarPer100 = "1.2";
//        NewFood.FatPer100 = "23.87";
//        NewFood.FatSaturatedPer100 = "34.78";
//        NewFood.SaltPer100 = "2.4";
//        NewFood.FibrePer100 = "1.1";
//
//        dbMan.AddFood(NewFood);

//        DailyFood NewDaily = new DailyFood();
//        NewDaily.AmountAte = "200";
//        NewDaily.FoodName = "Chips";
//        NewDaily.Meal = "Tea";
//
//        dbMan.AddDailyFood(NewDaily);

//        ExerciseDaily NewDailyEx = new ExerciseDaily();
//        NewDailyEx.ExerciseName = "Bicep Curls";
//        NewDailyEx.CaloriesBurnt = "15";
//        NewDailyEx.Duration = "5";
//
//        dbMan.AddExerciseDaily(NewDailyEx);

        cmdSelect = (Button) findViewById(R.id.cmdSelect);
        txtSelect = (TextView) findViewById(R.id.txtSelect);


        User TestUser = new User();
        TestUser.Name = "Sam";
        TestUser.Height = "185";
        TestUser.InitialWeight = "85";
        TestUser.CurrentWeight = "85";
        TestUser.DOB = "27/06/1998";
        TestUser.Sex = "Male";
        TestUser.Target = "-1LbW";
        TestUser.FitnessLevel = "3";
        dbMan.SetUserDetails(TestUser);

        User GetInfo = new User();
        GetInfo = dbMan.GetUsersDetails();
        txtSelect.setText(Double.toString(GetInfo.CalculateBMI()));


//        DailyTotals Test = new DailyTotals();
//        Test = dbMan.GetDailyTotals("2018-03-24");
//        txtSelect.setText( Double.toString(Test.TotalCalories));
    }
}
