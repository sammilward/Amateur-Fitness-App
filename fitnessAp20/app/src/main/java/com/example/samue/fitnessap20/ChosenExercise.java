package com.example.samue.fitnessap20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChosenExercise extends AppCompatActivity {

    DatabaseManager dm = new DatabaseManager(this);
    Button btn, cmdAddExerciseDaily;
    TextView lblExerciseName, lblExercisePrimaryMuscle, lblFitLevelReq, lblDescription;
    EditText txtDuration, txtCaloriesBurnt;
    Exercise CurExercise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_exercise);

        lblExerciseName = (TextView) findViewById(R.id.lblExerciseName);
        lblExercisePrimaryMuscle = (TextView) findViewById(R.id.lblExercisePrimaryMuscle);
        lblFitLevelReq = (TextView) findViewById(R.id.lblFitLevelReq);
        lblDescription = (TextView) findViewById(R.id.lblDescription);

        txtDuration = (EditText) findViewById(R.id.txtDuration);
        txtCaloriesBurnt = (EditText) findViewById(R.id.txtCaloriesBurnt);

        btn = (Button) findViewById(R.id.cmdImage);
        cmdAddExerciseDaily = (Button) findViewById(R.id.cmdAddExerciseDaily);


        Intent intent = getIntent();
        String ExName = intent.getStringExtra(ExerciseActivity.EXTRA_MESSAGE);
        LoadChosenExercise(ExName);
    }

    public void LoadChosenExercise(String Name)
    {
        CurExercise = dm.GetExerciseData(Name);
        lblExerciseName.setText(CurExercise.ExerciseName);
        lblExercisePrimaryMuscle.setText("Primary Muscle: " + CurExercise.PrimaryMuscle);
        lblFitLevelReq.setText("Fitness Level Required: " + CurExercise.FitnessLevelReq);
        lblDescription.setText(CurExercise.Description);

        switch (CurExercise.ExerciseName)
        {
            case "Doorway Row":
                btn.setBackgroundResource(R.drawable.doorrow);
                break;
            case "Bench Dips":
                btn.setBackgroundResource(R.drawable.benchdips);
                break;
            case "Inverted Row":
                btn.setBackgroundResource(R.drawable.invertedrow);
                break;
            case "Diamond Push Up":
                btn.setBackgroundResource(R.drawable.diamondpushup);
                break;
            case "Pull-Up":
                btn.setBackgroundResource(R.drawable.pullup);
                break;
            case "Feet-Elevated Inverted Row":
                btn.setBackgroundResource(R.drawable.feetelevatedinvertedrow);
                break;
            case "Hindu Push Up":
                btn.setBackgroundResource(R.drawable.hindupushup);
                break;
            case "Wall Sit":
                btn.setBackgroundResource(R.drawable.wallsit);
                break;
            case "Sumo Squat":
                btn.setBackgroundResource(R.drawable.sumosquat);
                break;
            case "Air Squat":
                btn.setBackgroundResource(R.drawable.airsquat);
                break;
            case "Step-up":
                btn.setBackgroundResource(R.drawable.stepup);
                break;
            case "Static Lunge":
                btn.setBackgroundResource(R.drawable.staticlunge);
                break;
            case "Reverse Lunge":
                btn.setBackgroundResource(R.drawable.reverselunge);
                break;
            case "Bulgarian Split Squat":
                btn.setBackgroundResource(R.drawable.bulgariansplitsquat);
                break;
            case "Single-Leg Box Squat":
                btn.setBackgroundResource(R.drawable.singlelegboxsquat);
                break;
            case "Skater Squat":
                btn.setBackgroundResource(R.drawable.skatersquat);
                break;
            case "Shrimp Squat":
                btn.setBackgroundResource(R.drawable.shrimpsqaut);
                break;
            case "Glute Bridge":
                btn.setBackgroundResource(R.drawable.glutebridge);
                break;
            case "Side Lying Clam":
                btn.setBackgroundResource(R.drawable.sidelyingclam);
                break;
            case "Donkey Kick":
                btn.setBackgroundResource(R.drawable.donkeykick);
                break;
            case "Single Leg Romanian Deadlift":
                btn.setBackgroundResource(R.drawable.singlelegromaniandeadlift);
                break;
            case "Single-Leg Glute Bridge":
                btn.setBackgroundResource(R.drawable.singlelegglutebridge);
                break;
            case "Shoulders-and-Feet-Elevated Hip Raise":
                btn.setBackgroundResource(R.drawable.shouldersandfeetelevatedhipraise);
                break;
            case "Sit Up":
                btn.setBackgroundResource(R.drawable.situp);
                break;
            case "Twisting Sit Up":
                btn.setBackgroundResource(R.drawable.twistingsitup);
                break;
            case "Crunch":
                btn.setBackgroundResource(R.drawable.crunch);
                break;
            case "Reverse Crunch":
                btn.setBackgroundResource(R.drawable.reversecrunch);
                break;
            case "Flutter Kick":
                btn.setBackgroundResource(R.drawable.flutterkick);
                break;
            case "Bicycle Kick":
                btn.setBackgroundResource(R.drawable.bicyclekick);
                break;
            case "Superman":
                btn.setBackgroundResource(R.drawable.superman);
                break;
            case "Bird Dog":
                btn.setBackgroundResource(R.drawable.birddog);
                break;
            case "Plank":
                btn.setBackgroundResource(R.drawable.plank);
                break;
            case "Side Plank":
                btn.setBackgroundResource(R.drawable.sideplank);
                break;
            case "Side-To-Side Push-Up":
                btn.setBackgroundResource(R.drawable.sidetosidepushup);
                break;
            case "Push Up":
                btn.setBackgroundResource(R.drawable.pushup);
                break;

        }

    }


    public void AddExercise(View view)
    {
        if (Validated())
        {
            ExerciseDaily NewEx = new ExerciseDaily();
            NewEx.ExerciseName = CurExercise.ExerciseName;
            NewEx.CaloriesBurnt = txtCaloriesBurnt.getText().toString();
            NewEx.Duration = txtDuration.getText().toString();
            NewEx.DateExercised = "";
            dm.AddExerciseDaily(NewEx);
            ClearForm();
        }
    }

    public void ClearForm()
    {
        txtCaloriesBurnt.setText("");
        txtDuration.setText("");
    }

    public boolean Validated()
    {

        if (txtCaloriesBurnt.getText().length() == 0 || txtCaloriesBurnt.getText().length() > 3)
        {
            txtCaloriesBurnt.setText("");
            return false;
        }
        if (txtDuration.getText().length() == 0 || txtDuration.getText().length() > 3)
        {
            txtDuration.setText("");
            return false;
        }
        return true;
    }


}
