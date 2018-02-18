package com.assignment2.nikolaijucutan.martianlander;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 *@author Nikko Jucutan
 *@version 1.0
 */
public class MainActivity extends AppCompatActivity {
    private Button upBtn;
    private Button leftBtn;
    private Button rightBtn;
    private MediaPlayer mp;//sfx for booster
    static MediaPlayer colSfx;
    private MediaPlayer bgm;
    MartianLanderView mlv;


    /**
     * @param savedInstanceState object that is passed into the onCreate method.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //first method that is called when this intent is called from start_screen
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.content_main);
        init();

    }

    //when app is minimized this method is called
    //it will pause the thread, means stop from running.
    //and calls onResume() when app is called again.
    @Override
    protected void onPause() {
        super.onPause();
        mlv.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mlv.onResume();
    }

    private void init()
    {
        //initialize variables
        upBtn = (Button) findViewById(R.id.upBtn);
        leftBtn = (Button) findViewById(R.id.leftBtn);
        rightBtn = (Button) findViewById(R.id.rightBtn);
        mlv = (MartianLanderView) findViewById(R.id.surface_view);

        //initialize music file
        //this was .wav originally and had to convert it to mp3 with lower bitrate for less memory usage.
        mp = MediaPlayer.create(this.getApplicationContext(), R.raw.main_rocket);
        colSfx = MediaPlayer.create(this.getApplicationContext(), R.raw.col);
        bgm = MediaPlayer.create(this.getApplicationContext(), R.raw.bgm);
        bgm.start();
        //loops the music when its finished

        //spaceship controls
        ssControls();
    }

    /**
     * @return boolean the result of colissionDetected and zeroFuel
     * if both true then controls will stop working.
     */
    private static boolean gameOver()
    {
        //check if there's fuel
        //if not this method will disable the button
        return MartianLanderSpaceship.colissionDetected || MartianLanderGauge.zeroFuel;
    }

    private void ssControls()
    {
        //UP button listener
        upBtn.setOnTouchListener(new View.OnTouchListener() {
            //this object helps in multi tasking
            //because we need to perform repeated actions.
            private Handler upHandler;
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:


                        if (upHandler != null) return true;
                        upHandler = new Handler();
                        upHandler.postDelayed(mAction, 0);
                        break;

                    case MotionEvent.ACTION_UP:
                        //pause sfx
                        mp.pause();
                        MartianLanderSpaceship.mainBooster = false;
                        Log.i("GG", "onTouch: gameover");
                        if (upHandler == null) return true;
                        //remove pending posts (queued actions)
                        upHandler.removeCallbacks(mAction);
                        upHandler = null;
                        break;
                }
                return false;
            }

            //this object is called over and over again when the user pressed on the button
            final Runnable mAction = new Runnable() {
                @Override public void run() {

                    //checks if no fuel left. if true then it disables the button
                    //but its a bad practice (according to google) and can cause memory leaks.
                    //Log.d("DEBUG ", "run: " + gameOver());
                    if(!gameOver())
                    {
                        //start sfx
                        mp.start();
                        MartianLanderSpaceship.mainBooster = true;
                        MartianLanderSpaceship.setPosY -= 6;
                        MartianLanderGauge.fuel();
                        //put a slight delay so the cpu doesn't stress too much and also to put a slight delay on each call. thread.sleep works as well.
                        upHandler.postDelayed(this, 10);

                    }

                }
            };

        });

        //Left Button listener
        leftBtn.setOnTouchListener(new View.OnTouchListener() {

            private Handler leftHandler;

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (leftHandler != null) return true;
                        leftHandler = new Handler();
                        leftHandler.postDelayed(mAction, 10);
                        break;

                    case MotionEvent.ACTION_UP:
                        mp.pause();
                        MartianLanderSpaceship.leftBooster = false;
                        if (leftHandler == null) return true;
                        leftHandler.removeCallbacks(mAction);
                        leftHandler = null;
                        break;
                }
                return false;
            }

            final Runnable mAction = new Runnable() {
                @Override public void run() {
                    if(!gameOver())
                    {
                        mp.start();
                        MartianLanderSpaceship.leftBooster = true;
                        MartianLanderSpaceship.getPosX += 3;
                        MartianLanderGauge.fuel();
                        leftHandler.postDelayed(this, 10);
                    }

                }
            };

        });

        //right button listener
        rightBtn.setOnTouchListener(new View.OnTouchListener() {

            private Handler rightHandler;

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (rightHandler != null) return true;
                        rightHandler = new Handler();
                        rightHandler.postDelayed(mAction, 10);
                        break;

                    case MotionEvent.ACTION_UP:
                        mp.pause();
                        MartianLanderSpaceship.rightBooster = false;
                        if (rightHandler == null) return true;
                        rightHandler.removeCallbacks(mAction);
                        rightHandler = null;
                        break;

                }
                return false;
            }

            final Runnable mAction = new Runnable() {
                @Override public void run() {
                    if(!gameOver())
                    {
                        mp.start();
                        MartianLanderSpaceship.rightBooster = true;
                        MartianLanderSpaceship.getPosX -= 3;
                        MartianLanderGauge.fuel();
                        rightHandler.postDelayed(this, 10);
                    }
                }
            };

        });
    }

    /**
     * @param v onClick listener for restart button, that is declared in the content_main.xml
     */
    public void restart(View v)
    {
        //restarts everything
        //listener for restart button
        //its set on the xml
        MartianLanderSpaceship.reset();
        MartianLanderGauge.reset();
        MartianLanderSprite.reset();
        MartianLanderView.reset();
        bgm.start();

    }
}
