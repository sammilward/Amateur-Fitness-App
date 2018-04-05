package com.example.samue.fitnessap20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    protected void butPressed(View view){

            if(view == findViewById(R.id.button)){
                Intent activity = new Intent(getApplication(), HomeActivity.class);

                startActivity(activity);


            }
            else {
                Intent activity = new Intent(getApplication(), SignUpActivity.class);

                startActivity(activity);
            }



        }


}
