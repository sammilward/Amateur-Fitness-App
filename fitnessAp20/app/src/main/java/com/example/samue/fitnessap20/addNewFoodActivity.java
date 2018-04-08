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
        //NEED VALIDATION FUNCTION TO BE CALLED HERE. ONLY NAME AND CALORIES ARE MANDITORY.
        //HOWEVER ALL FIELDS WILL NEED TO BE VALIDATED FOR BAD INPUT
        Food NewFood = new Food();
        NewFood.FoodName = txtFoodName.getText().toString();
        NewFood.CaloriesPer100 = txtCals.getText().toString();
        if (txtFat.getText().length() != 0)
        {
            NewFood.FatPer100 = txtFat.getText().toString();
        }
        if (txtFatSat.getText().length() != 0)
        {
            NewFood.FatSaturatedPer100 = txtFatSat.getText().toString();
        }
        if (txtCarbs.getText().length() != 0)
        {
            NewFood.CarbsPer100 = txtCarbs.getText().toString();
        }
        if (txtCarbSugars.getText().length()!=0)
        {
            NewFood.CarbSugarPer100 = txtCarbSugars.getText().toString();
        }
        if (txtProtein.getText().length()!=0)
        {
            NewFood.ProteinPer100 = txtProtein.getText().toString();
        }
        if (txtFibre.getText().length()!=0)
        {
            NewFood.FibrePer100 = txtFibre.getText().toString();
        }
        if (txtSalt.getText().length()!=0)
        {
            NewFood.SaltPer100 = txtSalt.getText().toString();
        }




        Toast.makeText(this, "New food added to database", Toast.LENGTH_LONG).show();

        dm.AddFood(NewFood);

        ClearForm();

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

    //SAM WRITE A VALIDATION FUNCTION HERE AND CALL IT AT THE START OF ADD FOOD, JUST LIKE ON THE REGISTRATION FORM
}
