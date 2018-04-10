package com.example.samue.fitnessap20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    TextView lblCalsRemaining, lblCalsRec;
    DatabaseManager dm = new DatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lblCalsRemaining = (TextView) findViewById(R.id.lblCalsRemaining);
        lblCalsRec = (TextView) findViewById(R.id.lblCalsRec);

        LoadData();

    }


    public void LoadData()
    {
        User CurUser = new User();
        CurUser = dm.GetUsersDetails();
        lblCalsRec.setText("Rec Cals: "+CurUser.CaloriesToConsume);

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
        int ConsumedCals = (int) TempConsumedCals;
        lblCalsRemaining.setText("Cur Cals: "+ConsumedCals);
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
