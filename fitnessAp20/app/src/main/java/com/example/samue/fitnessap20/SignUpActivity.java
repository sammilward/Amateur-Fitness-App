package com.example.samue.fitnessap20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }


    protected void pressedButton(View view) {

        if (view == findViewById(R.id.submitButton)) {

            Intent activity = new Intent(getApplication(), MainActivity.class);

            startActivity(activity);

        }
        if (view == findViewById(R.id.cancelButton) ){

            Intent activity = new Intent(getApplication(), MainActivity.class);

            startActivity(activity);

        }

    }


}
