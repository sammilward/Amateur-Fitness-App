package com.example.samue.fitnessap20;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Edwin on 01/05/2018.
 */
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    public MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity  = mainActivityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }

    @Test
    public void onRestart() throws Exception {
    }

    @Test
    public void onCreate() throws Exception {
        View view = mainActivity.findViewById(R.id.dateButton);
        assertNotNull(view);
    }

    @Test
    public void loadData() throws Exception {
    }

    @Test
    public void butPressed() throws Exception {
    }

}