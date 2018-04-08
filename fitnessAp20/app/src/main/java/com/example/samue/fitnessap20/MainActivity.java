package com.example.samue.fitnessap20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DatabaseManager dm = new DatabaseManager(this);
    TextView lblCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lblCurrentUser = (TextView)findViewById(R.id.lblCurrentUser);
        loadData();
    }

    protected void loadData()
    {
        lblCurrentUser.setText(dm.GetUsersDetails().Name);
    }


    protected void butPressed(View view){

            if(view == findViewById(R.id.cmdContiinue)){
                Intent activity = new Intent(getApplication(), HomeActivity.class);

                startActivity(activity);


            }
            else {
                Intent activity = new Intent(getApplication(), SignUpActivity.class);

                startActivity(activity);
            }



        }


}
