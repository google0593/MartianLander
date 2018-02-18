package com.assignment2.nikolaijucutan.martianlander;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 *@author Nikko Jucutan
 *@version 1.0
 */
public class MartianLanderView extends SurfaceView implements SurfaceHolder.Callback {

    public static MartianLanderThread mlt;
    private final MartianLanderSpaceship mls;
    private final MartianLanderGauge mlg;
    private final MartianLanderSprite mlSprite;
    private final MartianLanderGameOver mlGG;
    private final MartianLanderTerrain mlTerrain;
    //!----need to change to true when restart------!
    private static boolean isPlayed = false;

    /**
     * Initialize all the  draw classes and variables
     *
     * @param context allows the use of application resources.
     * @param attrs needed for GUI editor/XML Viewer
     */
    public MartianLanderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        mlt = new MartianLanderThread(this);
        mls = new MartianLanderSpaceship(context);
        mlg = new MartianLanderGauge(context);
        mlSprite = new MartianLanderSprite(context);
        mlGG = new MartianLanderGameOver(context);
        mlTerrain = new MartianLanderTerrain(context);

    }


    /**
     * Just like onCreate();
     * this is first called when the view is created
     * It also starts the thread. by setting mlRunning = true;
     *
     * @param surfaceHolder holder of the display, allows the control of the size, pixels etc.
     * @see MainActivity
     * @see android.view.View
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        mlt.setRunning(true);
        mlt.start();
    }

    /**
     * Called when the surface change i.e. screen roate. in this case its not needed because this game is only
     * portrait mode.
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {
        boolean retry = true;
        //mlt.setRunning(false);
        while (retry) {
            try {
                mlt.join();
                retry = false;  // stop trying if successful
            }
            catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
    }

    /**
     * method for game logic
     */
    public void update()
    {
        mls.move();

    }

    /**
     * method for drawing the drwables in the canvas
     * @param canvas holds the onDraw Calls.
     * @see android.view.SurfaceView
     * @see android.view.View
     */
    public void render(Canvas canvas)
    {

        if(!MartianLanderSpaceship.colissionDetected)
        {
            mls.onDrawMLS(canvas);
            mlg.render(canvas);
            mlTerrain.drawPath(canvas);
            mlTerrain.drawLZ(canvas);
        }else {

            sfx();
            mls.onDrawMLS(canvas);
            mlg.render(canvas);
            //if colission is detected. Render sprite.
            mlTerrain.drawPath(canvas);
            mlTerrain.drawLZ(canvas);
            mlGG.crater(canvas);
            mlSprite.onDrawWreckage(canvas);
            mlSprite.onDrawSprite(canvas);
        }
    }

    /**
     * Main sound explosion so that it only loop once.
     */
    private void sfx(){

        if(!isPlayed)
        {
            MainActivity.colSfx.start();
            isPlayed = true;

        }
    }

    public static void reset()
    {
        isPlayed = false;
    }

    //sets thread to false means it will stop running
    public void onPause()
    {
        mlt.setRunning(false);
    }
    //sets thread to true. continue running.
    public void onResume()
    {
        mlt.setRunning(true);
    }

}
