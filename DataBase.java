/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnessapp;

/**
 *
 * @author samal
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author samal
 */
public class DataBase {
 
    private Connection connect() {
        Connection con = null;
        try {
            String url = "jdbc:sqlite:C:\\Users\\samal\\Documents\\2nd Year\\Practical Project Mng and Professional Dev\\Practical Project\\Practical-Project-Managment\\AmatureFitness.db";
            con = DriverManager.getConnection(url);
            System.out.println("Connection to Database is established");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    
        return con;
    }  
    
    public static void main(String[] args) throws SQLException {
        DataBase db = new DataBase();

        //Add new food item to Food Table
//        ArrayList<String> Data = new ArrayList();
//        Data.add("Pineapple");
//        Data.add("152");
//        Data.add("3");
//        Data.add("1.42");
//        Data.add("25.36");
//        Data.add("23.85");
//        Data.add("3.5");
//        Data.add("12");
//        Data.add("1.23");
//        db.InsertFoodItem(Data);

//Select a food item that has been ate today
//          ArrayList<String> Data2 = new ArrayList();
//          Data2.add("Apple");
//          Data2.add("Lunch");
//          Data2.add("100");
//          db.SelectedFoodItem(Data2);
          
//Remove a food item that has been eaten today
//            ArrayList<String> Data3 = new ArrayList();
//            Data3.add("2018-03-11");
//            Data3.add("Apple");
//            Data3.add("Lunch");
//            db.RemoveSelectedFoodItem(Data3);
         
//Add new exercise to the ExerciseDaily Table
//          ArrayList<String> Data4 = new ArrayList();
//          Data4.add("Bicep Curls");
//          Data4.add("30");
//          Data4.add("48");
//          db.InsertExerciseDone(Data4);


//Add new user to DB
//            ArrayList<String> Data5 = new ArrayList();
//            Data5.add("Sam Milward");
//            Data5.add("80.45");
//            Data5.add("80.45");
//            Data5.add("1.78");
//            Data5.add("27-06-1998");
//            Data5.add("Male");
//            Data5.add("-1LbW");
//            Data5.add("1.5");
//            Data5.add("1498");
//            db.InsertUsersDetails(Data5);

//Update users info functions
//        db.UpdateUsersCurrentWeight("79.45");
//        db.UpdateUsersFitnessLevel("1.75");
//        db.UpdateUsersHeight("1.81");
//        db.UpdateUsersTarget("-2LbW");

//Insert results to database
//        db.InsertBodyFatResults("22.56", "2018-03-11");
//        db.InsertBMIResults("19.74", "2018-03-11");
//        db.InsertWeightResults("74.54", "2018-03-11");
            
    }
             
    public void InsertUsersDetails (ArrayList<String> Details) throws SQLException
    {
        String Name = Details.get(0);
        String InitialWeight = Details.get(1);
        String CurrentWeight = Details.get(2);
        String Height = Details.get(3);
        String DOB = Details.get(4);
        String Sex = Details.get(5);
        String Target = Details.get(6);
        String FitnessLevel = Details.get(7);
        String CaloriesToConsume = Details.get(8);
        
        String SQL = "INSERT INTO User VALUES ('" + Name + "','" + InitialWeight + "','" + CurrentWeight + "','" + Height + "','" + DOB + "','" + Sex + "','" + Target + "','" + FitnessLevel + "','" + CaloriesToConsume + "');";
    
        Connection con = this.connect();
        PreparedStatement pstmt  = con.prepareStatement(SQL);
        pstmt.execute();
        con.close();
    }
    
    public void UpdateUsersCurrentWeight(String CurrentWeight) throws SQLException
    {
        String SQL = "UPDATE User SET CurrentWeight = '" + CurrentWeight + "';";
        Connection con = this.connect();
        PreparedStatement pstmt  = con.prepareStatement(SQL);
        pstmt.execute();
        con.close();
    }
    
    public void UpdateUsersHeight(String Height) throws SQLException
    {
        String SQL = "UPDATE User SET Height = '" + Height + "';";
        Connection con = this.connect();
        PreparedStatement pstmt  = con.prepareStatement(SQL);
        pstmt.execute();
        con.close();
    }
    
    public void UpdateUsersTarget(String Target) throws SQLException
    {
        String SQL = "UPDATE User SET Target = '" + Target + "';";
        Connection con = this.connect();
        PreparedStatement pstmt  = con.prepareStatement(SQL);
        pstmt.execute();
        con.close();
    }
    
    public void UpdateUsersFitnessLevel(String FitLevel) throws SQLException
    {
        String SQL = "UPDATE User SET FitnessLevel = '" + FitLevel + "';";
        Connection con = this.connect();
        PreparedStatement pstmt  = con.prepareStatement(SQL);
        pstmt.execute();
        con.close();
    }
    
    public ArrayList<String> GetFoodItemInfo (String FoodName) throws SQLException
    {
        String sql = "SELECT * FROM Food WHERE FoodName = '" + FoodName + "';";
        ArrayList<String> FoodInfo = new ArrayList();
        
        Connection con = this.connect();
        Statement stmt  = con.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
            
        FoodInfo.add(rs.getString("FoodName"));
        FoodInfo.add(rs.getString("CaloriesPer100"));
        FoodInfo.add(rs.getString("FatPer100"));
        FoodInfo.add(rs.getString("FatSaturatedPer100"));
        FoodInfo.add(rs.getString("CarbsPer100"));
        FoodInfo.add(rs.getString("CarbSugarPer100"));
        FoodInfo.add(rs.getString("ProteinPer100"));
        FoodInfo.add(rs.getString("FibrePer100"));
        FoodInfo.add(rs.getString("SaltPer100"));
        con.close();
       
        return FoodInfo;       
    }
    
    public void InsertFoodItem (ArrayList<String> FoodData) throws SQLException
    {
        String FoodName = FoodData.get(0);
        String CaloriesPer100 = FoodData.get(1);
        String FatPer100 = FoodData.get(2);
        String FatSaturatedPer100 = FoodData.get(3);
        String CarbsPer100 = FoodData.get(4);
        String CarbSugarPer100 = FoodData.get(5);
        String ProteinPer100 = FoodData.get(6);
        String FibrePer100 = FoodData.get(7);
        String SaltPer100 = FoodData.get(8);
        
        String SQL = "INSERT INTO Food VALUES ('" + FoodName +"','"+CaloriesPer100+"','"+FatPer100+"','"+FatSaturatedPer100+"','"+CarbsPer100+"','"+CarbSugarPer100+"','"+ProteinPer100+"','"+FibrePer100+"','"+SaltPer100+"');";
        Connection con = this.connect();
        PreparedStatement pstmt  = con.prepareStatement(SQL);
        pstmt.execute();
        con.close();
    }

    public void SelectedFoodItem (ArrayList<String> FoodData) throws SQLException
    {
        CreateTotalRow();
        String FoodName = FoodData.get(0);
        String Meal = FoodData.get(1);
        String AmountAte = FoodData.get(2);
        String SQL = "INSERT INTO DailyFood (FoodName,Meal,AmountAte) VALUES ('"+FoodName+"','"+Meal+"','"+AmountAte+"');";
        Connection con = this.connect();
        PreparedStatement pstmt  = con.prepareStatement(SQL);
        pstmt.execute();
        con.close();
    }
    
    public void RemoveSelectedFoodItem (ArrayList<String> SelectedData) throws SQLException
    {
        String DateAte = SelectedData.get(0);
        String FoodName = SelectedData.get(1);
        String Meal = SelectedData.get(2);
        
        String SQL = "DELETE FROM DailyFood WHERE DateAte = '" + DateAte + "' AND FoodName = '" + FoodName + "' AND MEAL = '" + Meal + "';";
        Connection con = this.connect();
        PreparedStatement pstmt  = con.prepareStatement(SQL);
        pstmt.execute();
        con.close();
    }

    public void InsertExerciseDone (ArrayList<String> ExerciseInfo) throws SQLException
    {
        CreateTotalRow();
        String ExerciseName = ExerciseInfo.get(0);
        String Duration = ExerciseInfo.get(1);
        String CaloriesBurnt = ExerciseInfo.get(2);
        
        String SQL = "INSERT INTO ExerciseDaily (ExerciseName,Duration,CaloriesBurnt) VALUES ('" + ExerciseName + "','" + Duration + "','" + CaloriesBurnt + "');";
        
        Connection con = this.connect();
        PreparedStatement pstmt  = con.prepareStatement(SQL);
        pstmt.execute();
        con.close();         
    }
    
    public void CreateTotalRow () 
    {
        String SQL = "INSERT INTO DailyTotals(TotalCalories) VALUES ('0');";
        try {
            Connection con = this.connect();
            PreparedStatement pstmt  = con.prepareStatement(SQL);
            pstmt.execute();
            con.close();  
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
    public void DeleteExerciseDone (ArrayList<String> ExerciseInfo) throws SQLException
    {
        String DateExercised = ExerciseInfo.get(0);
        String ExerciseName = ExerciseInfo.get(1);
        
        String SQL = "DELETE FROM ExerciseDaily WHERE DateExercised = '" + DateExercised + "' AND ExerciseName + '" + ExerciseName + "';";
        
        Connection con = this.connect();
        PreparedStatement pstmt  = con.prepareStatement(SQL);
        pstmt.execute();
        con.close();
    }
    
    public void CreateResultsRow () 
    {
        String SQL = "INSERT INTO Results(Weight) VALUES ('0');";
        try {
            Connection con = this.connect();
            PreparedStatement pstmt  = con.prepareStatement(SQL);
            pstmt.execute();
            con.close();  
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void InsertBMIResults (String Bmi, String Date) throws SQLException
    {
        CreateResultsRow();
        
        String SQL = "UPDATE Results SET BMI = '" + Bmi + "' WHERE DateResults = '" + Date + "';";
        
        Connection con = this.connect();
        PreparedStatement pstmt  = con.prepareStatement(SQL);
        pstmt.execute();
        con.close();         
    }
    
    public void InsertWeightResults (String Weight, String Date) throws SQLException
    {
        CreateResultsRow();
        
        String SQL = "UPDATE Results SET Weight = '" + Weight + "' WHERE DateResults = '" + Date + "';";
        
        Connection con = this.connect();
        PreparedStatement pstmt  = con.prepareStatement(SQL);
        pstmt.execute();
        con.close();         
    }
    
    public void InsertBodyFatResults (String BF, String Date) throws SQLException
    {
        CreateResultsRow();
        
        String SQL = "UPDATE Results SET BodyFat = '" + BF + "' WHERE DateResults = '" + Date + "';";
        
        Connection con = this.connect();
        PreparedStatement pstmt  = con.prepareStatement(SQL);
        pstmt.execute();
        con.close();         
    }
    
}
