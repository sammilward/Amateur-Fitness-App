package com.example.samue.fitnessap20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addNewFoodActivity extends AppCompatActivity {

    EditText txtFoodName,txtCals,txtFat,txtFatSat,txtCarbs,txtCarbSugars,txtProtein,txtFibre,txtSalt;
    DatabaseManager dm = new DatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_food);

        txtFoodName = (EditText) findViewById(R.id.editText29);
        txtCals = (EditText) findViewById(R.id.editText28);
        txtFat = (EditText) findViewById(R.id.editText27);
        txtFatSat = (EditText) findViewById(R.id.editText25);
        txtCarbs = (EditText) findViewById(R.id.editText26);
        txtCarbSugars = (EditText) findViewById(R.id.editText24);
        txtProtein  = (EditText) findViewById(R.id.editText20);
        txtFibre = (EditText) findViewById(R.id.editText21);
        txtSalt = (EditText) findViewById(R.id.editText22);


    }

    public void AddFood(View view)
    {
        if (Validated())
        {
            Food NewFood = new Food();
            NewFood.FoodName = txtFoodName.getText().toString();
            NewFood.CaloriesPer100 = RoundStringToString(txtCals.getText().toString(),1);
            if (txtFat.getText().length() != 0)
            {
                NewFood.FatPer100 = RoundStringToString(txtFat.getText().toString(),1);
            }
            if (txtFatSat.getText().length() != 0)
            {
                NewFood.FatSaturatedPer100 = RoundStringToString(txtFatSat.getText().toString(),1);
            }
            if (txtCarbs.getText().length() != 0)
            {
                NewFood.CarbsPer100 = RoundStringToString(txtCarbs.getText().toString(),1);
            }
            if (txtCarbSugars.getText().length()!=0)
            {
                NewFood.CarbSugarPer100 = RoundStringToString(txtCarbSugars.getText().toString(),1);
            }
            if (txtProtein.getText().length()!=0)
            {
                NewFood.ProteinPer100 = RoundStringToString(txtProtein.getText().toString(),1);
            }
            if (txtFibre.getText().length()!=0)
            {
                NewFood.FibrePer100 = RoundStringToString(txtFibre.getText().toString(),1);
            }
            if (txtSalt.getText().length()!=0)
            {
                NewFood.SaltPer100 = RoundStringToString(txtSalt.getText().toString(),1);
            }




            Toast.makeText(this, "New food added to database", Toast.LENGTH_LONG).show();

            dm.AddFood(NewFood);

            ClearForm();
        }



    }

    public boolean Validated()
    {
        if (txtFoodName.getText().toString().length() == 0 || txtFoodName.getText().toString().length() > 30)
        {
            Toast.makeText(this, "Please enter a food name between 1 and 30 characters in length", Toast.LENGTH_LONG).show();
            return false;
        }

        if (txtCals.getText().toString().length() == 0 || txtCals.getText().toString().length() > 6)
        {
            Toast.makeText(this, "Enter a calorie amount between 1 and 4 values long", Toast.LENGTH_LONG).show();
            return false;
        }

        if (txtFat.getText().toString().length() != 0)
        {
            if (txtFat.getText().toString().length() > 6)
            {
                Toast.makeText(this, "Fat per 100g must be lower than 4 values long", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (txtFatSat.getText().toString().length() != 0)
        {
            if (txtFatSat.getText().toString().length() > 6)
            {
                Toast.makeText(this, "Fat sat per 100g must be lower than 4 values long", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (txtCarbs.getText().toString().length() != 0)
        {
            if (txtCarbs.getText().toString().length() > 6)
            {
                Toast.makeText(this, "Carbs per 100g must be lower than 4 values long", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (txtCarbSugars.getText().toString().length() != 0)
        {
            if (txtCarbSugars.getText().toString().length() > 6)
            {
                Toast.makeText(this, "Carb sugars per 100g must be lower than 4 values long", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (txtProtein.getText().toString().length() != 0)
        {
            if (txtProtein.getText().toString().length() > 6)
            {
                Toast.makeText(this, "Protein per 100g must be lower than 4 values long", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (txtFibre.getText().toString().length() != 0)
        {
            if (txtFibre.getText().toString().length() > 6)
            {
                Toast.makeText(this, "Fibre per 100g must be lower than 4 values long", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (txtSalt.getText().toString().length() != 0)
        {
            if (txtSalt.getText().toString().length() > 6)
            {
                Toast.makeText(this, "Salt per 100g must be lower than 4 values long", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

    protected void ClearForm(){
        txtFoodName.setText("");
        txtCals.setText("");
        txtFat.setText("");
        txtFatSat.setText("");
        txtCarbs.setText("");
        txtCarbSugars.setText("");
        txtProtein.setText("");
        txtFibre.setText("");
        txtSalt.setText("");
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public String RoundStringToString (String Convert, int precision)
    {
        Double Converted = Double.parseDouble(Convert);
        Double Rounded = round(Converted, precision);
        String returnedString = Double.toString(Rounded);
        return returnedString;

    }

    //SAM WRITE A VALIDATION FUNCTION HERE AND CALL IT AT THE START OF ADD FOOD, JUST LIKE ON THE REGISTRATION FORM
}
