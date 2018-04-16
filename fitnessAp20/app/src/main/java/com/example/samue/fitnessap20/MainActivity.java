package com.example.samue.fitnessap20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseManager dm = new DatabaseManager(this);
    TextView lblCurrentUser;

    public void onRestart() {
        super.onRestart();
        loadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lblCurrentUser = (TextView)findViewById(R.id.lblCurrentUser);
        loadData();

    }

    public void loadData()
    {
        lblCurrentUser.setText(dm.GetUsersDetails().Name);
    }


 public void butPressed(View view){


        if(view == findViewById(R.id.cmdContinue)){
            if (dm.GetUsersDetails().Name == "NO USER")
            {
                Toast.makeText(this, "You need to create a profile", Toast.LENGTH_LONG).show();

            }
            else{
                Intent activity = new Intent(getApplication(), HomeActivity.class);

                startActivity(activity);
            }

        }
        else {

            Intent activity = new Intent(getApplication(), SignUpActivity.class);

            startActivity(activity);
        }


        }


}
