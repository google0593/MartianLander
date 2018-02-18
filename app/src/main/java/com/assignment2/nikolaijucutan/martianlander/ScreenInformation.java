package com.assignment2.nikolaijucutan.martianlander;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Martian Lander
 * Created by Nikolai Jucutan on 9/15/2016.
 */

class ScreenInformation {
    /**
     *----------Extension----------
     *
     *The purpose of this class is to ensure the compatibility of the app for all screen sizes.
     *Another purpose of this class is to make it easier to draw the terrain/path of the application
     *and also to make it easier to implement 'collision' part of the assignment.
     * It will make it easier because if you need to get the screen size, convert dp to px (vice versa) you can just call this class.
     * I used this on my touchpaint assignment assignment as well and this code is really useful.
     *
     *
     *for more info: http://stackoverflow.com/questions/12242111/application-skeleton-to-support-multiple-screen/12258061#12258061
     *
     */
    static int screenWidth;
    private static int screenHeight;

    //converts pixels to density  pixels
    private static float pToDP(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    //converts density pixels to pixels
    static int dpToP(int dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    //sets the screen resolution in pixels
    private void setScreenWidthHeight(Context context)
    {
        Resources R = context.getResources();
        DisplayMetrics dm = R.getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        //Comment the code above and uncomment the codes below if you want to use density pixels.
        //screenWidth = pToDP(screenHeight,context);
        //screenHeight = pToDP(screenHeight,context);
    }

    //when this method is called it returns the screen width
    int getWidth(Context context)
    {
        setScreenWidthHeight(context);
        Log.i("WIDTH PIXELS X ","--------------------" + screenWidth + "px" + "--------------------" );
        Log.i("WIDTH DENSITY PIXELS X ", "--------------------" + pToDP(screenWidth,context) + "dp" + "--------------------" );
        return screenWidth;
    }

    //when this method is called it returns the screen height
    int getHeight(Context context)
    {
        setScreenWidthHeight(context);
        Log.i("HEIGHT PIXELS Y ","--------------------" + screenHeight + "px" + "--------------------" );
        Log.i("HEIGHT DENSITY Y ", "--------------------" + pToDP(screenHeight,context) + "dp" + "--------------------" );
        return screenHeight;
    }
}
