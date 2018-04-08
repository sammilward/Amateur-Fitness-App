package com.example.samue.fitnessap20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.lang.UCharacter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.transform.Result;

/**
 * Created by samal on 12/03/2018.
 */

public class DatabaseManager extends SQLiteOpenHelper{

    private SQLiteDatabase database;
    private static int DatabaseVer = 1;
    private static String DatabaseName = "AmatureFitness.db";

    private static String dailyFoodTable = "DailyFood";
    private static String dailyTotalTable = "DailyTotals";
    private static String ExerciseTable = "Exercise";
    private static String ExerciseDailyTable = "ExerciseDaily";
    private static String FoodTable = "Food";
    private static String ResultsTable = "Results";
    private static String UserTable = "User";


    public DatabaseManager(Context context) {
        super(context, DatabaseName, null, DatabaseVer);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + dailyFoodTable + " (DateAte DATETIME NOT NULL  DEFAULT CURRENT_DATE," +
                        "FoodName VARCHAR(20) NOT NULL , Meal VARCHAR(20) NOT NULL , AmountAte DOUBLE NOT NULL ," +
                        " PRIMARY KEY (DateAte, FoodName, Meal));"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + dailyTotalTable + " (DateTotal DATETIME PRIMARY KEY  NOT NULL  DEFAULT CURRENT_DATE, " +
                         "TotalCalories INTEGER DEFAULT 0, TotalCarbs DOUBLE DEFAULT 0.00, TotalCarbSugars DOUBLE DEFAULT 0.00, TotalProtein DOUBLE DEFAULT 0.00, " +
                        "TotalFat DOUBLE DEFAULT 0.00, TotalFatSaturates DOUBLE DEFAULT 0.00, TotalSalt DOUBLE DEFAULT 0.00, TotalDuration DOUBLE DEFAULT 0.00, " +
                        "TotalCaloriesBurnt DOUBLE DEFAULT 0.00, TotalFibre DOUBLE DEFAULT 0.00);"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + ExerciseTable + "(ExerciseName VARCHAR(30) PRIMARY KEY  NOT NULL , FitnessLevelReq DOUBLE NOT NULL , " +
                "PrimaryMuscle VARCHAR(20), PhotoPath VARCHAR(50), Description VARCHAR(512));"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + ExerciseDailyTable + "(DateExercised DATETIME NOT NULL DEFAULT CURRENT_DATE, ExerciseName VARCHAR(40) NOT NULL , " +
                "Duration DOUBLE DEFAULT 0, CaloriesBurnt DOUBLE DEFAULT 0, PRIMARY KEY (DateExercised, ExerciseName));"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + FoodTable + " (FoodName VARCHAR(20) PRIMARY KEY  NOT NULL ,CaloriesPer100 INTEGER NOT NULL ,FatPer100 DOUBLE DEFAULT (0.00) , " +
                "FatSaturatedPer100 DOUBLE DEFAULT (0.00) ,CarbsPer100 DOUBLE DEFAULT (0.00) ,CarbSugarPer100 DOUBLE DEFAULT (0.00) ,ProteinPer100 DOUBLE DEFAULT (0.00) , " +
                "FibrePer100 DOUBLE DEFAULT (0.00) ,SaltPer100 DOUBLE DEFAULT (0.00));"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + ResultsTable + " (DateResults DATETIME PRIMARY KEY  NOT NULL  DEFAULT (CURRENT_DATE) ,Weight DOUBLE DEFAULT (0) ,BMI DOUBLE DEFAULT (0) , "
                + "BodyFat DOUBLE DEFAULT (0));"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + UserTable + "(Name VARCHAR(20) PRIMARY KEY  NOT NULL ,InitialWeight DOUBLE NOT NULL ,CurrentWeight DOUBLE, Height INTEGER,DOB DATETIME,Sex VARCHAR(6)," +
                        "Target VARCHAR(10),FitnessLevel DOUBLE,CaloriesToConsume INTEGER DEFAULT (0));"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + dailyFoodTable);
        db.execSQL("DROP TABLE IF EXISTS" + dailyTotalTable);
        db.execSQL("DROP TABLE IF EXISTS" + ExerciseTable);
        db.execSQL("DROP TABLE IF EXISTS" + ExerciseDailyTable);
        db.execSQL("DROP TABLE IF EXISTS" + FoodTable);
        db.execSQL("DROP TABLE IF EXISTS" + ResultsTable);
        db.execSQL("DROP TABLE IF EXISTS" + UserTable);

    }

    public void refreshDatabase()
    {
        database = this.getWritableDatabase();

        onCreate(database);

        database.execSQL("DELETE FROM " + dailyFoodTable);
        database.execSQL("DELETE FROM " + dailyTotalTable);
        database.execSQL("DELETE FROM " + ExerciseTable);
        database.execSQL("DELETE FROM " + ExerciseDailyTable);
        database.execSQL("DELETE FROM " + FoodTable);
        database.execSQL("DELETE FROM " + ResultsTable);
        database.execSQL("DELETE FROM " + UserTable);
        database.close();
    }
    //EXERCISE FUNCTIONS
    //Loads exercises from textfile placed in the Assets file WORKS
    public void loadExercises(Context context)
    {
        //Use the textfile in the assets to populate the database.
        //Only do this once as table infromation is stored in the .db file
        database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + ExerciseTable);
        database.close();


        //Function to populate database from textfile
        try{
            final InputStream file = context.getAssets().open("Exercise.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){
                database = this.getWritableDatabase();
                String[] data = line.split("@");
                Exercise Temp = new Exercise();
                Temp.ExerciseName = data[0].toString();
                Temp.FitnessLevelReq = data[1].toString();
                Temp.PrimaryMuscle = data[2].toString();
                Temp.PhotoPath = data[3].toString();
                Temp.Description = data[4].toString();


                ContentValues Values = new ContentValues();
                Values.put("ExerciseName", Temp.ExerciseName);
                Values.put("FitnessLevelReq", Temp.FitnessLevelReq);
                Values.put("PrimaryMuscle", Temp.PrimaryMuscle);
                Values.put("PhotoPath", Temp.PhotoPath);
                Values.put("Description", Temp.Description);

                long input;
                input = database.insert(ExerciseTable, null, Values);
                if (input == -1)
                {
                    //System.out.println("Has not been entered to DB");
                }
                database.close();
                line = reader.readLine();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    //Gets all Exercise Data based on Exercise name WORKS
    public Exercise GetExerciseData(Exercise Ex)
    {
        Exercise Temp = new Exercise();
        //String SQLQuery = "SELECT * FROM " + dailyFoodTable + " WHERE DateAte = '" + Date + "';";
        try {
            database = this.getWritableDatabase();
            //Cursor Query (
            //String table,
            //String[] columns - Array of strings of columns to get
            //String Selection = Where statment
            //String [] selectionArgs -
            //String GroupBy
            //String having
            //String orderBy
            String[] Cols = {"ExerciseName", "FitnessLevelReq", "PrimaryMuscle", "PhotoPath", "Description"};
            Cursor cursor = database.query(ExerciseTable, Cols, "ExerciseName = '" + Ex.ExerciseName + "'" ,null,null,null,null);
            cursor.moveToFirst();
            Temp.ExerciseName = cursor.getString(cursor.getColumnIndex("ExerciseName"));
            Temp.FitnessLevelReq = cursor.getString(cursor.getColumnIndex("FitnessLevelReq"));
            Temp.PrimaryMuscle = cursor.getString(cursor.getColumnIndex("PrimaryMuscle"));
            Temp.PhotoPath = cursor.getString(cursor.getColumnIndex("PhotoPath"));
            Temp.Description = cursor.getString(cursor.getColumnIndex("Description"));

            cursor.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        database.close();
        return Temp;
    }
    //Gets ArrayList on Exercises based on Primary Muscle
    public ArrayList<Exercise> GetExercisesOnMuscle (Exercise Ex)
    {
        ArrayList<Exercise> Exercises = new ArrayList();

        //String SQLQuery = "SELECT * FROM " + dailyFoodTable + " WHERE DateAte = '" + Date + "';";
        try {
            database = this.getWritableDatabase();
            //Cursor Query (
            //String table,
            //String[] columns - Array of strings of columns to get
            //String Selection = Where statment
            //String [] selectionArgs -
            //String GroupBy
            //String having
            //String orderBy
            String[] Cols = {"ExerciseName", "FitnessLevelReq", "PrimaryMuscle", "PhotoPath", "Description"};
            Cursor cursor = database.query(ExerciseTable, Cols, "PrimaryMuscle = '" + Ex.PrimaryMuscle + "'" ,null,null,null, null);
            while (cursor.moveToNext())
            {
                Exercise Temp = new Exercise();
                Temp.ExerciseName = cursor.getString(cursor.getColumnIndex("ExerciseName"));
                Temp.FitnessLevelReq = cursor.getString(cursor.getColumnIndex("FitnessLevelReq"));
                Temp.PrimaryMuscle = cursor.getString(cursor.getColumnIndex("PrimaryMuscle"));
                Temp.PhotoPath = cursor.getString(cursor.getColumnIndex("PhotoPath"));
                Temp.Description = cursor.getString(cursor.getColumnIndex("Description"));
                Exercises.add(Temp);
            }


            cursor.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        database.close();
        return Exercises;
    }
    //Gets ArrayList on Exercises based on FitnessLevelReq
    public ArrayList<Exercise> GetExercisesOnFitnessLevel (Exercise Ex)
    {
        ArrayList<Exercise> Exercises = new ArrayList();

        //String SQLQuery = "SELECT * FROM " + dailyFoodTable + " WHERE DateAte = '" + Date + "';";
        try {
            database = this.getWritableDatabase();
            //Cursor Query (
            //String table,
            //String[] columns - Array of strings of columns to get
            //String Selection = Where statment
            //String [] selectionArgs -
            //String GroupBy
            //String having
            //String orderBy
            String[] Cols = {"ExerciseName", "FitnessLevelReq", "PrimaryMuscle", "PhotoPath", "Description"};
            Cursor cursor = database.query(ExerciseTable, Cols, "FitnessLevelReq = '" + Ex.FitnessLevelReq + "'" ,null,null,null, null);
            while (cursor.moveToNext())
            {
                Exercise Temp = new Exercise();
                Temp.ExerciseName = cursor.getString(cursor.getColumnIndex("ExerciseName"));
                Temp.FitnessLevelReq = cursor.getString(cursor.getColumnIndex("FitnessLevelReq"));
                Temp.PrimaryMuscle = cursor.getString(cursor.getColumnIndex("PrimaryMuscle"));
                Temp.PhotoPath = cursor.getString(cursor.getColumnIndex("PhotoPath"));
                Temp.Description = cursor.getString(cursor.getColumnIndex("Description"));
                Exercises.add(Temp);
            }


            cursor.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        database.close();
        return Exercises;
    }


    //EXERCISE DAILY FUNCTIONS
    //Adds a ExerciseDaily to the SQL Table WORKS
    public boolean AddExerciseDaily(ExerciseDaily Ex)
    {
        boolean Worked = false;
        try {

            database = this.getWritableDatabase();
            ContentValues Values = new ContentValues();

            String Date = "";
            if (Ex.DateExercised == "")
            {
                //If no date has been passed with the ExerciseDaily
                //Automatically insert the current data
                Values.put("ExerciseName", Ex.ExerciseName);
                Values.put("Duration", Ex.Duration);
                Values.put("CaloriesBurnt", Ex.CaloriesBurnt);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Date = dateFormat.format(date).toString();
            }
            else
            {
                //Insert the data the user has entered.
                //Needs to be in format YYYY-MM-DD
                Date = Ex.DateExercised;
                Values.put("DateExercised", Date);
                Values.put("ExerciseName", Ex.ExerciseName);
                Values.put("Duration", Ex.Duration);
                Values.put("CaloriesBurnt", Ex.CaloriesBurnt);
            }

            long Test = database.insert(ExerciseDailyTable, null, Values);
            if (Test == -1)
            {
                Worked = false;
            }
            else{
                Worked = true;
            }
            database.close();
            UpdateTotalExercise(Date);

        } catch (SQLException e)
        {
            System.out.println("Error -" + e.getMessage());
        }
        return Worked;
    }
    //Removes an ExerciseDaily from the SQL Table WORKS
    public void RemoveExerciseDaily(ExerciseDaily Ex)
    {
        try {
            //If date not specified (Today)
            if (Ex.DateExercised == "")
            {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Ex.DateExercised = dateFormat.format(date).toString();
            }

            database = this.getWritableDatabase();

            database.delete(ExerciseDailyTable,"DateExercised=? AND ExerciseName=?", new String[] {Ex.DateExercised, Ex.ExerciseName});

            database.close();
            UpdateTotalExercise(Ex.DateExercised);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }
    //Gets an ArrayList of Exercise Daily from the SQL Table based on Date passed via Ex WORKS
    public ArrayList<ExerciseDaily> GetExerciseDaily (String Date)
    {
        ArrayList<ExerciseDaily> List = new ArrayList();

        try {

            if (Date == "")
            {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Date = dateFormat.format(date).toString();
            }

            database = this.getWritableDatabase();
            String[] Cols = {"DateExercised", "ExerciseName", "Duration", "CaloriesBurnt"};
            Cursor cursor = database.query(ExerciseDailyTable, Cols, "DateExercised = '" + Date + "'" ,null,null,null,null);

            while(cursor.moveToNext())
            {
                ExerciseDaily Temp = new ExerciseDaily();
                Temp.DateExercised = cursor.getString(cursor.getColumnIndex("DateExercised"));
                Temp.ExerciseName = cursor.getString(cursor.getColumnIndex("ExerciseName"));
                Temp.Duration = cursor.getString(cursor.getColumnIndex("Duration"));
                Temp.CaloriesBurnt = cursor.getString(cursor.getColumnIndex("CaloriesBurnt"));
                List.add(Temp);
            }
            cursor.close();


        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }


        return List;
    }

    //DAILY FOOD FUNCTIONS
    //Adds a new DailyFood to the SQL Table WORKS
    public void AddDailyFood(DailyFood Food)
    {
        try {
            database = this.getWritableDatabase();
            ContentValues Values = new ContentValues();
            //Remove below line to automatically put date in
            //to allow both put if statement if Food.DateAte != ""
            //Values.put("DateAte", Food.DateAte);
            String Date = "";
            if (Food.DateAte == "") {
                Values.put("FoodName", Food.FoodName);
                Values.put("Meal", Food.Meal);
                Values.put("AmountAte", Food.AmountAte);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Date = dateFormat.format(date).toString();

            }
            else
            {
                Date = Food.DateAte;
                Values.put("DateAte", Date);
                Values.put("FoodName", Food.FoodName);
                Values.put("Meal", Food.Meal);
                Values.put("AmountAte", Food.AmountAte);
            }
            database.insert(dailyFoodTable, null, Values);
            database.close();
            UpdateTotalFood(Date);

        } catch (SQLException e)
        {
            System.out.println("Error -" + e.getMessage());
        }


    }
    //Removed Daily Food From SQL Table WORKS
    public void RemoveDailyFood(DailyFood Food)
    {
        String Date = "";
        if (Food.DateAte == "")
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Date = dateFormat.format(date).toString();
        }
        else {
            Date = Food.DateAte;
        }

        database = this.getWritableDatabase();

        database.delete(dailyFoodTable, "DateAte=? AND FoodName=? AND Meal=?", new String[] {Date, Food.FoodName, Food.Meal});

        database.close();
        UpdateTotalFood(Date);
    }
    //Gets an ArrayList of DailyFood based on Date WORKS
    public ArrayList<DailyFood> GetDailyFood (String Date)
    {
        ArrayList<DailyFood> DailyFoodForDay = new ArrayList();

        try {
            database = this.getWritableDatabase();

            if (Date == "")
            {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Date = dateFormat.format(date).toString();
            }
            //Cursor Query (
            //String table,
            //String[] columns - Array of strings of columns to get
            //String Selection = Where statment
            //String [] selectionArgs -
            //String GroupBy
            //String having
            //String orderBy
            String[] Cols = {"DateAte", "FoodName", "Meal", "AmountAte"};
            Cursor cursor = database.query(dailyFoodTable, Cols, "DateAte = '" + Date + "'" ,null,null,null,null);

            while(cursor.moveToNext())
            {
                DailyFood Temp = new DailyFood();
                Temp.DateAte = cursor.getString(cursor.getColumnIndex("DateAte"));
                Temp.FoodName = cursor.getString(cursor.getColumnIndex("FoodName"));
                Temp.Meal = cursor.getString(cursor.getColumnIndex("Meal"));
                Temp.AmountAte = cursor.getString(cursor.getColumnIndex("AmountAte"));
                DailyFoodForDay.add(Temp);
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        database.close();

        return DailyFoodForDay;
    }

    //FOOD FUNCTIONS
    //Adds food Item to SQL Table Food WORKS
    public void AddFood(Food FoodItem)
    {
        try {
            database = this.getWritableDatabase();
            ContentValues Values = new ContentValues();
            //Remove below line to automatically put date in
            //to allow both put if statement if Food.DateAte != ""
            //Values.put("DateAte", Food.DateAte);
            Values.put("FoodName", FoodItem.FoodName);
            Values.put("CaloriesPer100", FoodItem.CaloriesPer100);
            Values.put("FatPer100", FoodItem.FatPer100);
            Values.put("FatSaturatedPer100", FoodItem.FatSaturatedPer100);
            Values.put("CarbsPer100", FoodItem.CarbsPer100);
            Values.put("CarbSugarPer100", FoodItem.CarbSugarPer100);
            Values.put("ProteinPer100", FoodItem.ProteinPer100);
            Values.put("FibrePer100", FoodItem.FibrePer100);
            Values.put("SaltPer100", FoodItem.SaltPer100);


            database.insert(FoodTable, null, Values);
            database.close();
        } catch (SQLException e)
        {
            System.out.println("Error -" + e.getMessage());
        }

    }
    //Removes food Item from SQL Table Food WORKS
    public void RemoveFood(Food FoodItem)
    {
        database = this.getWritableDatabase();

        database.delete(FoodTable, "FoodName=?", new String [] {FoodItem.FoodName});

        database.close();
    }
    //Gets food data from SQL Table WORKS
    public Food GetFoodData(String FoodName)
    {
        Food Temp = new Food();
        //String SQLQuery = "SELECT * FROM " + dailyFoodTable + " WHERE DateAte = '" + Date + "';";
        try {
            database = this.getWritableDatabase();
            //Cursor Query (
            //String table,
            //String[] columns - Array of strings of columns to get
            //String Selection = Where statment
            //String [] selectionArgs -
            //String GroupBy
            //String having
            //String orderBy
            String[] Cols = {"FoodName", "CaloriesPer100", "FatPer100", "FatSaturatedPer100", "CarbsPer100", "CarbSugarPer100", "ProteinPer100", "FibrePer100", "SaltPer100"};
            Cursor cursor = database.query(FoodTable, Cols, "FoodName = '" + FoodName + "'" ,null,null,null,null);
            cursor.moveToFirst();
            Temp.FoodName = cursor.getString(cursor.getColumnIndex("FoodName"));
            Temp.CaloriesPer100 = cursor.getString(cursor.getColumnIndex("CaloriesPer100"));
            Temp.FatPer100 = cursor.getString(cursor.getColumnIndex("FatPer100"));
            Temp.FatSaturatedPer100 = cursor.getString(cursor.getColumnIndex("FatSaturatedPer100"));
            Temp.CarbsPer100 = cursor.getString(cursor.getColumnIndex("CarbsPer100"));
            Temp.CarbSugarPer100 = cursor.getString(cursor.getColumnIndex("CarbSugarPer100"));
            Temp.ProteinPer100 = cursor.getString(cursor.getColumnIndex("ProteinPer100"));
            Temp.FibrePer100 = cursor.getString(cursor.getColumnIndex("FibrePer100"));
            Temp.SaltPer100 = cursor.getString(cursor.getColumnIndex("SaltPer100"));

            cursor.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        database.close();
        return Temp;
    }


    //Users Functions
    //Write User Details into SQL Table WORKS
    public void SetUserDetails (User UserDetails)
    {
        try {
            database = this.getWritableDatabase();
            database.execSQL("DELETE FROM " + UserTable);
            ContentValues Values = new ContentValues();

            Values.put("Name", UserDetails.Name);
            Values.put("InitialWeight", UserDetails.InitialWeight);
            Values.put("CurrentWeight", UserDetails.CurrentWeight);
            Values.put("Height", UserDetails.Height);
            Values.put("DOB", UserDetails.DOB);
            Values.put("Sex", UserDetails.Sex);
            Values.put("Target", UserDetails.Target);
            Values.put("FitnessLevel", UserDetails.FitnessLevel);
            Values.put("CaloriesToConsume", UserDetails.CaloriesToConsume);
            database.insert(UserTable,null,Values);
            database.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    //Get user details from SQL User Table WORKS
    public User GetUsersDetails ()
    {
        User Temp = new User();
        try {
            database = this.getWritableDatabase();

            String[] Cols = {"Name", "InitialWeight", "CurrentWeight", "Height", "DOB", "Sex", "Target", "FitnessLevel", "CaloriesToConsume"};
            Cursor cursor = database.query(UserTable, Cols, null ,null,null,null,null);
            cursor.moveToFirst();
            Temp.Name = cursor.getString(cursor.getColumnIndex("Name"));
            Temp.InitialWeight = cursor.getString(cursor.getColumnIndex("InitialWeight"));
            Temp.CurrentWeight = cursor.getString(cursor.getColumnIndex("CurrentWeight"));
            Temp.Height = cursor.getString(cursor.getColumnIndex("Height"));
            Temp.DOB = cursor.getString(cursor.getColumnIndex("DOB"));
            Temp.Sex = cursor.getString(cursor.getColumnIndex("Sex"));
            Temp.Target = cursor.getString(cursor.getColumnIndex("Target"));
            Temp.FitnessLevel = cursor.getString(cursor.getColumnIndex("FitnessLevel"));
            Temp.CaloriesToConsume = cursor.getString(cursor.getColumnIndex("CaloriesToConsume"));

            cursor.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        database.close();
        return Temp;
    }


    //RESULTS FUNCTIONS
    //Adds new row into Results table with current date, ready to be updated with Results. Called when new result is to be added.
    public void AddResultRow()
    {
        try {
            database = this.getWritableDatabase();
            ContentValues Values = new ContentValues();
            Values.put("Weight", "0");
            Values.put("BMI", "0");
            Values.put("BodyFat", "0");
            database.insert(ResultsTable, null, Values);
            database.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    //Update the weight of the result for the current date
    public void ResultWeight(String Weight)
    {
        AddResultRow();
        try {
            //AddResultRow();
            database = this.getWritableDatabase();
            ContentValues Values = new ContentValues();
            Values.put("Weight", Weight);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String CurrentDate = dateFormat.format(date).toString();
            //Update Weight for Result
            database.update(ResultsTable, Values, "DateResults='"+CurrentDate+"'",null);
            database.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    //Update the BMI of the result for the current date
    public void ResultBMI(String BMI)
    {
        AddResultRow();
        try {
            database = this.getWritableDatabase();
            ContentValues Values = new ContentValues();
            Values.put("BMI", BMI);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String CurrentDate = dateFormat.format(date).toString();
            //Update Weight for Result
            database.update(ResultsTable, Values, "DateResults='"+CurrentDate+"'",null);
            database.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    //Update the BodyFat of the result table for the current date
    public void ResultBodyFat(String BodyFat)
    {
        AddResultRow();
        try {
            database = this.getWritableDatabase();
            ContentValues Values = new ContentValues();
            Values.put("BodyFat", BodyFat);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String CurrentDate = dateFormat.format(date).toString();
            //Update Weight for Result
            database.update(ResultsTable, Values, "DateResults='"+CurrentDate+"'",null);
            database.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    //Get the results of a certain date
    public Results GetResults (String date)
    {
        Results Temp = new Results();
        try {
            database = this.getWritableDatabase();

            String[] Cols = {"DateResults","Weight", "BMI", "BodyFat"};
            Cursor cursor = database.query(ResultsTable, Cols, "DateResults = '" + date + "'" ,null,null,null,null);
            cursor.moveToFirst();
            Temp.Weight = cursor.getString(cursor.getColumnIndex("Weight"));
            Temp.BMI = cursor.getString(cursor.getColumnIndex("BMI"));
            Temp.BodyFat = cursor.getString(cursor.getColumnIndex("BodyFat"));
            cursor.close();
            database.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }


        return Temp;
    }


    //TOTAL FUNCTIONS
    //Adds a new row for totals for the day specified
    public void AddTotalRow(String Date)
    {
        try {
            database = this.getWritableDatabase();
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            Date date = new Date();
//            String CurrentDate = dateFormat.format(date).toString();
            ContentValues Values = new ContentValues();
            Values.put("DateTotal", Date);
            database.insert(dailyTotalTable, null, Values);
            database.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    //Updates the total food parts of the row for the given date
    public void UpdateTotalFood(String Date) {
        AddTotalRow(Date);
        ArrayList<DailyFood> DaysFood = GetDailyFood(Date);
        DailyTotals TempTotal = new DailyTotals();
        for (int i = 0; i < DaysFood.size(); i++) {
            Food TempFood = new Food();
            TempFood = GetFoodData(DaysFood.get(i).FoodName);
            TempTotal.TotalCalories = TempTotal.TotalCalories + (Double.parseDouble(TempFood.CaloriesPer100) * (Double.parseDouble(DaysFood.get(i).AmountAte) / 100));
            TempTotal.TotalCarbs = TempTotal.TotalCarbs + (Double.parseDouble(TempFood.CarbsPer100) * (Double.parseDouble(DaysFood.get(i).AmountAte) / 100));
            TempTotal.TotalCarbSugars = TempTotal.TotalCarbSugars + (Double.parseDouble(TempFood.CarbSugarPer100) * (Double.parseDouble(DaysFood.get(i).AmountAte) / 100));
            TempTotal.TotalProtein = TempTotal.TotalProtein + (Double.parseDouble(TempFood.ProteinPer100) * (Double.parseDouble(DaysFood.get(i).AmountAte) / 100));
            TempTotal.TotalFat = TempTotal.TotalFat + (Double.parseDouble(TempFood.FatPer100) * (Double.parseDouble(DaysFood.get(i).AmountAte) / 100));
            TempTotal.TotalFatSaturates = TempTotal.TotalFatSaturates + (Double.parseDouble(TempFood.FatSaturatedPer100) * (Double.parseDouble(DaysFood.get(i).AmountAte) / 100));
            TempTotal.TotalSalt = TempTotal.TotalSalt + (Double.parseDouble(TempFood.SaltPer100) * (Double.parseDouble(DaysFood.get(i).AmountAte) / 100));
            TempTotal.TotalFibre = TempTotal.TotalFibre + (Double.parseDouble(TempFood.FibrePer100) * (Double.parseDouble(DaysFood.get(i).AmountAte) / 100));
        }
        try {
            database = this.getWritableDatabase();
            ContentValues Values = new ContentValues();
            Values.put("TotalCalories", TempTotal.TotalCalories);
            Values.put("TotalCarbs", TempTotal.TotalCarbs);
            Values.put("TotalCarbSugars", TempTotal.TotalCarbSugars);
            Values.put("TotalProtein", TempTotal.TotalProtein);
            Values.put("TotalFat", TempTotal.TotalFat);
            Values.put("TotalFatSaturates", TempTotal.TotalFatSaturates);
            Values.put("TotalSalt", TempTotal.TotalSalt);
            Values.put("TotalFibre", TempTotal.TotalFibre);

            database.update(dailyTotalTable, Values, "DateTotal='" + Date + "'", null);

            database.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Updates the total exercise parts of the row for the given date
    public void UpdateTotalExercise(String Date)
    {
        AddTotalRow(Date);
        ArrayList<ExerciseDaily> DaysExercise = GetExerciseDaily(Date);
        DailyTotals TempTotal = new DailyTotals();
        for (int i = 0; i < DaysExercise.size(); i++)
        {
            //Food TempFood = new Food();
            //TempFood = GetFoodData(DaysFood.get(i).FoodName);
            TempTotal.TotalCaloriesBurnt = TempTotal.TotalCaloriesBurnt + Double.parseDouble(DaysExercise.get(i).CaloriesBurnt);
            TempTotal.TotalDuration = TempTotal.TotalDuration + Double.parseDouble(DaysExercise.get(i).Duration);
        }
        try {
            database = this.getWritableDatabase();

            ContentValues Values = new ContentValues();
            Values.put("TotalCaloriesBurnt", TempTotal.TotalCaloriesBurnt);
            Values.put("TotalDuration", TempTotal.TotalDuration);

            database.update(dailyTotalTable, Values, "DateTotal='" + Date + "'", null);
            database.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public DailyTotals GetDailyTotals(String Date)
    {
        DailyTotals Temp = new DailyTotals();

        try {
            database = this.getWritableDatabase();

            String[] Cols = {"DateTotal","TotalCalories", "TotalCarbs", "TotalCarbSugars", "TotalProtein", "TotalFat", "TotalFatSaturates", "TotalSalt", "TotalFibre", "TotalDuration", "TotalCaloriesBurnt"};
            Cursor cursor = database.query(dailyTotalTable, Cols, "DateTotal = '" + Date + "'" ,null,null,null,null);
            cursor.moveToFirst();
            Temp.DateTotal = cursor.getString(cursor.getColumnIndex("DateTotal"));
            Temp.TotalCalories = cursor.getDouble(cursor.getColumnIndex("TotalCalories"));
            Temp.TotalCarbs = cursor.getDouble(cursor.getColumnIndex("TotalCarbs"));
            Temp.TotalCarbSugars = cursor.getDouble(cursor.getColumnIndex("TotalCarbSugars"));
            Temp.TotalProtein = cursor.getDouble(cursor.getColumnIndex("TotalProtein"));
            Temp.TotalFat = cursor.getDouble(cursor.getColumnIndex("TotalFat"));
            Temp.TotalFatSaturates = cursor.getDouble(cursor.getColumnIndex("TotalFatSaturates"));
            Temp.TotalSalt = cursor.getDouble(cursor.getColumnIndex("TotalSalt"));
            Temp.TotalFibre = cursor.getDouble(cursor.getColumnIndex("TotalFibre"));
            Temp.TotalDuration = cursor.getDouble(cursor.getColumnIndex("TotalDuration"));
            Temp.TotalCaloriesBurnt = cursor.getDouble(cursor.getColumnIndex("TotalCaloriesBurnt"));
            cursor.close();
            database.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return Temp;
    }
}


