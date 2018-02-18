package com.assignment2.nikolaijucutan.martianlander;


import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import static android.content.ContentValues.TAG;

/**
 *@author Nikko Jucutan
 *@version 1.0
 */
class MartianLanderThread extends Thread {

    //Terrain is not included here.
    private MartianLanderView mlv = null;
    private final SurfaceHolder sh;
    private boolean mlRunning = false;

    /**
     *
     * @param mlv pass the view
     * @see MartianLanderView
     *
     */
    MartianLanderThread(MartianLanderView mlv)
    {
        super();
        this.mlv = mlv;
        this.sh = mlv.getHolder();


    }
    //override method to start the thread when mlRunning = true
    //this is set in the MartianLanderView
    @Override
    public void run()
    {
        super.run();
        Canvas canvas;
        //while running
        while (mlRunning)
        {
                canvas = null;
                try
                {
                    //start editing the surface
                    canvas = sh.lockCanvas();
                    //sync with the thread
                    synchronized (sh)
                    {
                        //do game logic first
                        mlv.update();
                        //then do the drawing
                        mlv.render(canvas);

                    }
                }
                finally
                {
                    if (canvas != null)
                    {
                        try
                        {
                            //finish editing
                            sh.unlockCanvasAndPost(canvas);
                            Log.i(TAG, "run: UNLOCKED");
                        }catch (Exception e)
                        {
                            e.printStackTrace();

                        }
                        //sh.unlockCanvasAndPost(canvas);

                    }
                }
            ///////////////////////////////////////////////
        }
    }

    /**
     *
     * @param running boolean true
     * @see MartianLanderView
     */
    void setRunning(boolean running)
    {
        this.mlRunning = running;
    }


}
