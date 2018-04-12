package com.example.samue.fitnessap20;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by samal on 12/03/2018.
 */

public class User {
    public String Name; //Sam Milward
    public String InitialWeight; //KG
    public String CurrentWeight; //KG
    public String Height; //CM
    public String DOB; //27/06/1998
    public String Sex; //Male Or Female
    public String Target; //-1LbW, -2LbW
    public String FitnessLevel; //1, 2, 3, 4, 5
    public String CaloriesToConsume; //1805



    public double CalculateBMI()
    {
        double UserMeterHeight = Double.parseDouble(Height)/100;
        double BMI = (Double.parseDouble(CurrentWeight)/UserMeterHeight) / UserMeterHeight;
        String BMIMeaning = "";

//        if (BMI < 18.5) BMIMeaning = "Underweight";
//        else if (18.5 <= BMI && BMI < 24.9) BMIMeaning = "Healthy";
//        else if (25 <= BMI && BMI < 29.9) BMIMeaning = "Overweight";
//        else BMIMeaning = "Obese";
//
//        System.out.println(BMIMeaning);



        return BMI;
    }

    public int CalculateRecommendedCalories()
    {
        int Age = CalculateAge(); // Should equal to function call to calculate age from birthday //Belive james is writting this
        int CalsRecommended = 0;
        double FitL = GetFitnessMultiplier();

        if ("Male".equals(Sex))
        {
            CalsRecommended = (int) (((10*Double.parseDouble(CurrentWeight)) + (6.25 * Double.parseDouble(Height)) - (5*Age) + 5) * FitL);
        }
        else
        {
            CalsRecommended = (int) (((10*Double.parseDouble(CurrentWeight)) + (6.25*Double.parseDouble(Height)) - (5*Age) -161) * FitL);
        }
        System.out.println(CalsRecommended);
        //M For Maintain
        if ("M".equals(Target))
        {
            CalsRecommended = CalsRecommended;
        }
        else if ("-1LbW".equals(Target))
        {
            CalsRecommended = CalsRecommended - 500;
        }
        else if ("-2LbW".equals(Target))
        {
            CalsRecommended = CalsRecommended - 1000;
        }
        CaloriesToConsume = Integer.toString(CalsRecommended);

        return CalsRecommended;
    }

    public double CalculateBodyFat(double WaistGirth, double WristCir, double HipCir, double ForearmCir)
    {
        double BodyFat = 0;
        double KgToLbConvertion = 2.204622618488;
        //Convert from KG to LBs
        double Weight = Double.parseDouble(CurrentWeight) * KgToLbConvertion;


        double LeanBodyWeight = 0.00;


        if ("Male".equals(Sex))
        {
            double TempVar1 = (Weight * 1.082) + 94.42;
            LeanBodyWeight = TempVar1 - (WaistGirth * 4.15);
        }
        else
        {
            double TempVar1 = (0.732 * Weight) + 8.987;
            double TempVar2 = (WristCir / 3.14);
            double TempVar3 = (WaistGirth * 0.157);
            double TempVar4 = (HipCir * 0.249);
            double TempVar5 = (ForearmCir * 0.434);

            double TempVar6 = TempVar1 + TempVar2;
            double TempVar7 = TempVar6 - TempVar3;
            double TempVar8 = TempVar7 - TempVar4;
            LeanBodyWeight = TempVar5 + TempVar8;
        }

        double FatAmount = Weight - LeanBodyWeight;
        BodyFat = (FatAmount * 100) / Weight;
        return BodyFat;
    }

    public double GetFitnessMultiplier()
    {
        double Level = 0;
        if ("1".equals(FitnessLevel))
        {
            Level = 1.2;
        }
        else if ("2".equals(FitnessLevel))
        {
            Level = 1.3;
        }
        else if ("3".equals(FitnessLevel) | "4".equals(FitnessLevel))
        {
            Level =  1.4;
        }
        else if ("5".equals(FitnessLevel))
        {
            Level = 1.5;
        }
        return  Level;
    }

    private int CalculateAge()
    {

        Date date = new Date();
        int Age = 0;
        //Get the values of the DOB that has been passed
        int Year = Integer.parseInt(DOB.substring(0, 4));
        int Month = Integer.parseInt(DOB.substring(5, 7));
        int Day = Integer.parseInt(DOB.substring(8, 10));

        //Get current date
        int CurYear, CurMonth, CurDay;
        DateFormat dateFormatYear = new SimpleDateFormat("yyyy");
        CurYear = Integer.parseInt(dateFormatYear.format(date));

        DateFormat dateFormatMonth = new SimpleDateFormat("MM");
        CurMonth = Integer.parseInt(dateFormatMonth.format(date));

        DateFormat dateFormatDay = new SimpleDateFormat("dd");
        CurDay = Integer.parseInt(dateFormatDay.format(date));

        //Age = difference in years
        Age = CurYear - Year;
        //If DOB month larger than current yeara remove a year from age
        if (Month>CurMonth)
        {
            Age = Age - 1;
        }
        //If they are equal check days
        else if (Month==CurMonth)
        {
            //If DOBDay bigger than current day, then remove year from age
            if (Day>CurDay)
            {
                Age = Age - 1;
            }
        }
        return Age;
    }

}
