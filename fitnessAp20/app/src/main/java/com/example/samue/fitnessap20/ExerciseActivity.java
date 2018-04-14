package com.example.samue.fitnessap20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class ExerciseActivity extends AppCompatActivity {

    ScrollView scExercies;
    LinearLayout ScrollLinear;
    Spinner cbLevelMuscle, cbFindBy;
    Button btn1;

    //Attribute to track how many buttons are on the screen at a time
    int numOfButtons;

    LinkedList<Button> buttons = new LinkedList<Button>();

    DatabaseManager dm = new DatabaseManager(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        scExercies = (ScrollView) findViewById(R.id.scExercies);

        ScrollLinear = (LinearLayout) findViewById(R.id.ScrollLinear);

        cbFindBy = (Spinner) findViewById(R.id.cbFindBy);
        cbLevelMuscle = (Spinner) findViewById(R.id.cbLevelMuscle);
        cbLevelMuscle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (cbLevelMuscle.getSelectedItem().toString().equals("Primary Muscle Targeted"))
                {
                    ArrayAdapter<String> Adapter = new ArrayAdapter<String>(ExerciseActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.MuscleTargets));
                    cbFindBy.setAdapter(Adapter);
                }
                else if (cbLevelMuscle.getSelectedItem().toString().equals("Fitness Level Required"))
                {
                    ArrayAdapter<String> Adapter = new ArrayAdapter<String>(ExerciseActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.FitnessLevel));
                    cbFindBy.setAdapter(Adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("");
            }



        });

        dm.loadExercises(this);
        InitialLoadUp();
    }

    //Loads all of the users exercies based on their fitness level
    public void InitialLoadUp()
    {
        User CurUser = new User();
        CurUser = dm.GetUsersDetails();
        ArrayList<Exercise> Exercises = dm.GetExercisesOnFitnessLevel(CurUser.FitnessLevel);

        numOfButtons = Exercises.size();

        for (int i = 0; i < Exercises.size(); i++) {

            TextView lblexerciseName = new TextView(this);

            // set some properties of rowTextView or something
            lblexerciseName.setText(Exercises.get(i).ExerciseName);

            lblexerciseName.setId(i+16);

            // add the textview to the linearlayout
            ScrollLinear.addView(lblexerciseName);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(900,900);
            Button btn = new Button(this);
            btn.setId(i);
            final int id_ = btn.getId();

            switch (Exercises.get(i).ExerciseName)
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

            ScrollLinear.addView(btn, params);
            btn1 = ((Button) findViewById(id_));
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                            "Button clicked index = " + id_, Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }

    }

    //Loads all exercies based upon selection in the filter boxes
    public void CreateTextAndButtons(View view)
    {
        ArrayList<Exercise> Exercises = new ArrayList();

        if(numOfButtons != 0 ){
            for (int i = 0; i < numOfButtons; i++)
            {
                Button btn = (Button) findViewById(i);
                ScrollLinear.removeView(btn);

                TextView lbl = (TextView) findViewById(i+16);
                ScrollLinear.removeView(lbl);

            }
        }

        if (cbLevelMuscle.getSelectedItem().toString().equals("Fitness Level Required"))
        {
            Exercises = dm.GetExercisesOnFitnessLevel(cbFindBy.getSelectedItem().toString());
        }
        else if (cbLevelMuscle.getSelectedItem().toString().equals("Primary Muscle Targeted"))
        {
            Exercises = dm.GetExercisesOnMuscle(cbFindBy.getSelectedItem().toString());
        }

        numOfButtons = Exercises.size();

        //Loop through amount of exercies
        for (int i = 0; i < Exercises.size(); i++) {

            //Dynamically create a text box to show exercise name
            TextView lblexerciseName = new TextView(this);
            lblexerciseName.setId(i+16);
            lblexerciseName.setText(Exercises.get(i).ExerciseName);
            // add the textview to the linearlayout
            ScrollLinear.addView(lblexerciseName);

            //Set size of new button
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(900,900);
            Button btn = new Button(this);
            //set id of new button
            btn.setId(i);
            final int id_ = btn.getId();

            //Load correct image dependant on which exercise is in the ArrayList
            switch (Exercises.get(i).ExerciseName)
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

            ScrollLinear.addView(btn, params);
            btn1 = ((Button) findViewById(id_));
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                            "Button clicked index = " + id_, Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }

    }


}
