package com.assignment2.nikolaijucutan.martianlander;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *@author Nikko Jucutan
 *@version 1.0
 */
public class start_screen extends AppCompatActivity {

    /**
     * firs method called when this intent start
     * @param savedInstanceState object that is passed into the onCreate method.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    /**
     *
     * @param v called in activity_start_screen.xml
     */
    public void startMainActivity(View v)
    {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
