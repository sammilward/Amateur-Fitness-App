package com.example.samue.myfitnessprototype2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class food extends AppCompatActivity {
    String foodName;
    public void pullInformation(View view){
       // Button submitButton = (Button)findViewById(R.id.submit);
        TextView foodQuerie = (TextView)findViewById(R.id.searchFoodName);

        foodName = foodQuerie.getText().toString();

        Toast.makeText(this, "search for " + foodName, Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_page);
    }
}
