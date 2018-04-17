package com.example.samue.fitnessap20;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    TextView lblCalsAte, lblCalsRec, lblRemainingCals, lbldate, lblMeal, lblFoodName, lblCalories, lblExName, lblDuration, lblCalBurnt;
    TextView lblTotalCal, lblTotalCarbs, lblTotalCarbSugar, lblTotalProtein, lblTotalFat, lblTotalFatSat, lblTotalSalt, lblTotalFibre, lblTotalDuration, lblTotalCalBurnt;
    DatabaseManager dm = new DatabaseManager(this);
    ProgressBar CalProgress;
    Button ChangeDate, cmdRemoveFood, cmdRemoveExercise;
    TableLayout FoodTable, ExerciseTable, TotalTable;
    TableRow CurRow, CurRow2, CurRow3;

    DatePickerDialog.OnDateSetListener dateListener;

    int day, month, year;

    private int ProgressVal;

    public void onRestart() {
        super.onRestart();
        LoadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lblCalsAte = (TextView) findViewById(R.id.lblCalsAte);
        lblCalsRec = (TextView) findViewById(R.id.lblCalsRec);
        lblRemainingCals = (TextView) findViewById(R.id.lblRemaingCalories);
        lbldate = (TextView) findViewById(R.id.lbldate);

        CalProgress = (ProgressBar) findViewById(R.id.progressBar);

        ChangeDate = (Button)findViewById(R.id.cmdChangeDate);

        FoodTable = (TableLayout) findViewById(R.id.FoodTable);
        ExerciseTable = (TableLayout) findViewById(R.id.ExerciseTable);
        TotalTable = (TableLayout) findViewById(R.id.TotalTable);

        Calendar calendar = Calendar.getInstance(); // creates an instance of Calendar with the current date.
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);


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

        lbldate.setText("" + year + "-"+ sMonth +"-" + day);

        ChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(HomeActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateListener,
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


                lbldate.setText("" + year + "-"+ sMonth +"-" + sDay);
                LoadData();
            }
        };


        LoadData();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void LoadData()
    {
        User CurUser = new User();
        CurUser = dm.GetUsersDetails();
        lblCalsRec.setText("Target Cals: "+CurUser.CaloriesToConsume);

        ArrayList<DailyFood> AllFoodData = new ArrayList();
        ArrayList<ExerciseDaily> AllExerciseData = new ArrayList();
        DailyFood CurDay = new DailyFood();
        AllFoodData = dm.GetDailyFood(lbldate.getText().toString());
        AllExerciseData = dm.GetExerciseDaily(lbldate.getText().toString());

        ResetFoodTable();
        ResetExerciseTable();
        //This value needs to be changed for date selected via a calander


        double TempConsumedCals = 0;

        //Loop to calculate calories ate on given date
        //And to populate the food table
        for (int i = 0; i < AllFoodData.size(); i++)
        {
            Food CurrentFood = new Food();
            CurrentFood = dm.GetFoodData(AllFoodData.get(i).FoodName);
            double CalsAte = Double.parseDouble(CurrentFood.CaloriesPer100) * (Double.parseDouble(AllFoodData.get(i).AmountAte) / 100);
            int CalsAte2 = (int) CalsAte;
            TempConsumedCals = TempConsumedCals + CalsAte;

            CurRow = new TableRow(this);

            lblMeal = new TextView(this);
            lblMeal.setText(AllFoodData.get(i).Meal);
            CurRow.addView(lblMeal);

            lblFoodName = new TextView(this);
            lblFoodName.setText(CurrentFood.FoodName);
            lblFoodName.setMaxWidth(100);
            CurRow.addView(lblFoodName);

            lblCalories = new TextView(this);
            lblCalories.setText(""+CalsAte2);
            CurRow.addView(lblCalories);

            cmdRemoveFood = new Button(this);
            cmdRemoveFood.setId(i);

            final int id_ = cmdRemoveFood.getId();

            final DailyFood food = new DailyFood();
            food.DateAte = lbldate.getText().toString();
            food.Meal = AllFoodData.get(i).Meal;
            food.FoodName = CurrentFood.FoodName;

            cmdRemoveFood.setText("x");
            cmdRemoveFood.setLayoutParams(new TableRow.LayoutParams(100,100));
            cmdRemoveFood.setBackgroundColor(Color.rgb(230,0,0));
            cmdRemoveFood.setPadding(0,0,10,10);


            cmdRemoveFood.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dm.RemoveDailyFood(food);
                    LoadData();
                }
            });

            CurRow.addView(cmdRemoveFood);

            FoodTable.addView(CurRow);


        }

        //Loop here to calculate all calories burnt
        //And populate the exercise table
        for (int i = 0; i < AllExerciseData.size(); i++)
        {
            TempConsumedCals = TempConsumedCals - Double.parseDouble(AllExerciseData.get(i).CaloriesBurnt);

            CurRow2 = new TableRow(this);

            lblExName = new TextView(this);
            lblExName.setText(AllExerciseData.get(i).ExerciseName);
            lblExName.setMaxWidth(100);

            CurRow2.addView(lblExName);

            lblDuration = new TextView(this);
            lblDuration.setText(AllExerciseData.get(i).Duration);
            CurRow2.addView(lblDuration);

            lblCalBurnt = new TextView(this);
            lblCalBurnt.setText(AllExerciseData.get(i).CaloriesBurnt);
            CurRow2.addView(lblCalBurnt);

            cmdRemoveExercise = new Button(this);
            cmdRemoveExercise.setId(i);

            final int id_ = cmdRemoveExercise.getId();

            final ExerciseDaily Ex = new ExerciseDaily();
            Ex.DateExercised = lbldate.getText().toString();
            Ex.ExerciseName = AllExerciseData.get(i).ExerciseName;

            cmdRemoveExercise.setText("x");
            cmdRemoveExercise.setLayoutParams(new TableRow.LayoutParams(100,100));
            cmdRemoveExercise.setBackgroundColor(Color.rgb(230,0,0));
            cmdRemoveExercise.setPadding(0,0,10,10);


            cmdRemoveExercise.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dm.RemoveExerciseDaily(Ex);
                    LoadData();
                }
            });

            CurRow2.addView(cmdRemoveExercise);

            ExerciseTable.addView(CurRow2);

        }


        //Populate totals table
        DailyTotals CurTotals = new DailyTotals();
        CurTotals = dm.GetDailyTotals(lbldate.getText().toString());
        ResetTotalTable();
        CurRow3 = new TableRow(this);
        Double PrintedVal;

        //
        lblTotalCal = new TextView(this);
        PrintedVal = round(CurTotals.TotalCalories,1);
        lblTotalCal.setText(""+PrintedVal);
        CurRow3.addView(lblTotalCal);

        lblTotalCalBurnt = new TextView(this);
        PrintedVal = round(CurTotals.TotalCaloriesBurnt,1);
        lblTotalCalBurnt.setText(""+PrintedVal);
        CurRow3.addView(lblTotalCalBurnt);

        lblTotalDuration = new TextView(this);
        PrintedVal = round(CurTotals.TotalDuration,1);
        lblTotalDuration.setText(""+PrintedVal);
        CurRow3.addView(lblTotalDuration);

        lblTotalCarbs = new TextView(this);
        PrintedVal = round(CurTotals.TotalCarbs,1);
        lblTotalCarbs.setText(""+PrintedVal);
        CurRow3.addView(lblTotalCarbs);

        lblTotalProtein = new TextView(this);
        PrintedVal = round(CurTotals.TotalProtein,1);
        lblTotalProtein.setText(""+PrintedVal);
        CurRow3.addView(lblTotalProtein);

        lblTotalFat = new TextView(this);
        PrintedVal = round(CurTotals.TotalFat,1);
        lblTotalFat.setText(""+PrintedVal);
        CurRow3.addView(lblTotalFat);

        lblTotalCarbSugar = new TextView(this);
        PrintedVal = round(CurTotals.TotalCarbSugars,1);
        lblTotalCarbSugar.setText(""+PrintedVal);
        CurRow3.addView(lblTotalCarbSugar);

        lblTotalFatSat = new TextView(this);
        PrintedVal = round(CurTotals.TotalFatSaturates,1);
        lblTotalFatSat.setText(""+PrintedVal);
        CurRow3.addView(lblTotalFatSat);

        lblTotalSalt = new TextView(this);
        PrintedVal = round(CurTotals.TotalSalt,1);
        lblTotalSalt.setText(""+PrintedVal);
        CurRow3.addView(lblTotalSalt);

        lblTotalFibre = new TextView(this);
        PrintedVal = round(CurTotals.TotalFibre,1);
        lblTotalFibre.setText(""+PrintedVal);
        CurRow3.addView(lblTotalFibre);

        TotalTable.addView(CurRow3);






        int ConsumedCals = (int) TempConsumedCals;
        lblCalsAte.setText("Current Cals: "+ConsumedCals);

        Double tempProgress = (ConsumedCals/Double.parseDouble(CurUser.CaloriesToConsume)) * 100;
        ProgressVal = tempProgress.intValue();
        CalProgress.setProgress(ProgressVal);
        System.out.println("");

        //Remaing calories to consume
        Double Remaining = Double.parseDouble(CurUser.CaloriesToConsume) - TempConsumedCals;
        int CalsRemaining = Remaining.intValue();


        //If user calories intake below 50 % of rec then turn RED
        if (ProgressVal < 50)
        {
            lblRemainingCals.setTextColor(Color.RED);
            CalProgress.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
        //If user calories intake between 50-75 % of rec then turn ORANGE
        else if (ProgressVal < 75)
        {
            lblRemainingCals.setTextColor(Color.rgb(255,165,0));
            CalProgress.setProgressTintList(ColorStateList.valueOf(Color.rgb(255,165,0)));
        }
        //If user calories intake between 75 - 100 % of rec then turn GREEN
        else if(ProgressVal < 100)
        {
            lblRemainingCals.setTextColor(Color.GREEN);
            CalProgress.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }
        //if user cals over rec by less than 5% then turn ORANGE
        else if(ProgressVal < 105)
        {
            lblRemainingCals.setTextColor(Color.rgb(255,165,0));
            CalProgress.setProgressTintList(ColorStateList.valueOf(Color.rgb(255,165,0)));
        }
        //If user over rec calories more than 5% then turn RED;
        else
        {
            lblRemainingCals.setTextColor(Color.RED);
            CalProgress.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
        lblRemainingCals.setText("Remaining Cals: "+CalsRemaining);

    }



    public void ResetFoodTable()
    {
        int count = FoodTable.getChildCount();
        for (int i = 1; i < count; i++) {
            View child = FoodTable.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

    }


    public void ResetExerciseTable()
    {
        int count = ExerciseTable.getChildCount();
        for (int i = 1; i < count; i++) {
            View child = ExerciseTable.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

    }

    public void ResetTotalTable()
    {
        int count = TotalTable.getChildCount();
        for (int i = 1; i < count; i++) {
            View child = TotalTable.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
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

        if(view == findViewById(R.id.cmdResults) ){
            Intent activity = new Intent(getApplication(), ResultsActivity.class);

            startActivity(activity);
        }




    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

}
