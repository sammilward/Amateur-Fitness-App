package com.example.samue.fitnessap20;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    TextView lblCalsAte, lblCalsRec, lblRemainingCals;
    DatabaseManager dm = new DatabaseManager(this);
    ProgressBar CalProgress;

    private int ProgressVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lblCalsAte = (TextView) findViewById(R.id.lblCalsAte);
        lblCalsRec = (TextView) findViewById(R.id.lblCalsRec);
        lblRemainingCals = (TextView) findViewById(R.id.lblRemaingCalories);

        CalProgress = (ProgressBar) findViewById(R.id.progressBar);


        LoadData();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void LoadData()
    {
        User CurUser = new User();
        CurUser = dm.GetUsersDetails();
        lblCalsRec.setText("Target Cals: "+CurUser.CaloriesToConsume);

        ArrayList<DailyFood> AllFoodData = new ArrayList();
        DailyFood CurDay = new DailyFood();
        AllFoodData = dm.GetDailyFood("2018-04-10");
        //This value needs to be changed for date selected via a calander



        double TempConsumedCals = 0;

        //Loop to calculate calories ate on given date
        for (int i = 0; i < AllFoodData.size(); i++)
        {
            Food CurrentFood = new Food();
            CurrentFood = dm.GetFoodData(AllFoodData.get(i).FoodName);
            TempConsumedCals = TempConsumedCals + (Double.parseDouble(CurrentFood.CaloriesPer100) * (Double.parseDouble(AllFoodData.get(i).AmountAte) / 100));
        }

        //Loop here to calculate all calories burnt
        //Remove figure from TempConsumedCals

        int ConsumedCals = (int) TempConsumedCals;
        lblCalsAte.setText("Current Cals: "+ConsumedCals);

        Double tempProgress = (ConsumedCals/Double.parseDouble(CurUser.CaloriesToConsume)) * 100;
        ProgressVal = tempProgress.intValue();
        CalProgress.setProgress(ProgressVal);
        System.out.println("");

        //Remaing calories to consume
        Double Remaining = Double.parseDouble(CurUser.CaloriesToConsume) - TempConsumedCals;
        int CalsRemaining = Remaining.intValue();
        if (ProgressVal < 50)
        {
            lblRemainingCals.setTextColor(Color.RED);
            CalProgress.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
        else if (ProgressVal < 75)
        {
            lblRemainingCals.setTextColor(Color.YELLOW);
            CalProgress.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        }
        else if(ProgressVal < 100)
        {
            lblRemainingCals.setTextColor(Color.GREEN);
            CalProgress.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }
        else if(ProgressVal < 105)
        {
            lblRemainingCals.setTextColor(Color.rgb(255,165,0));
            CalProgress.setProgressTintList(ColorStateList.valueOf(Color.rgb(255,165,0)));
        }
        else
        {
            lblRemainingCals.setTextColor(Color.RED);
            CalProgress.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
        lblRemainingCals.setText("Remaining Cals: "+CalsRemaining);

    }


    //Switch from one screen to another
    public void pressedButton(View view){

        if(view == findViewById(R.id.foodButton)){
            Intent activity = new Intent(getApplication(), FoodActivity.class);

            startActivity(activity);


        }

        if(view == findViewById(R.id.exerciseButton)){
            Intent activity = new Intent(getApplication(), ExerciseActivity.class);

            startActivity(activity);


        }
       if(view == findViewById(R.id.profileButton) ){
            Intent activity = new Intent(getApplication(), ProfileActivity.class);

            startActivity(activity);
        }



    }

}
