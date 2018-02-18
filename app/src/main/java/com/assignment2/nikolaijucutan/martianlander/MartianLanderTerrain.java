package com.assignment2.nikolaijucutan.martianlander;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 *@author Nikko Jucutan
 *@version 1.0
 */
class MartianLanderTerrain{
    //i used view here because you only have to draw the 'terrain' once,
    //and i thought its a waste of resources if you include it in the thread. or use surfaceview
    static int _width;
    private static int _height;

    static final List<Integer> tempArrayX = new ArrayList<>();
    static final List<Integer> tempArrayY = new ArrayList<>();

    private Path _path;
    private Paint _fillPaint;//terrain background

    private Bitmap lz;
    static Rect lzColision;

    /**
     * Initialize all variables
     *
     * @param context allows the use of application resources.
     */
    MartianLanderTerrain(Context context)
    {
        init(context);

    }

    /**
     * draw the path
     *
     * @param canvas holds the onDraw Calls.
     * @see android.view.SurfaceView
     * @see android.view.View
     */
    void drawPath(Canvas canvas)
    {
        canvas.drawPath(_path, _fillPaint);
        //Log.d(TAG, "INIT MLT");
    }

    /**
     *
     * @param context allows the use of application resources.
     * called in the constructor
     */
    private void init(Context context)
    {
        //initialize the screen width and height from ScreenInformation class to a local var.
        ScreenInformation screenInfo = new ScreenInformation();
        _width = screenInfo.getWidth(context);
        _height = screenInfo.getHeight(context);

        //initialize the image from the res. folder
        Bitmap _bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mars);
        //initialize the shader, set bitmap to tile mode.
        BitmapShader _bitmapShader = new BitmapShader(_bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        //initialize LZ
        lz = BitmapFactory.decodeResource(context.getResources(), R.drawable.lz);

        //Initialize the fillPaint object
        _fillPaint = new Paint();
        //assign the shader object to this paint object
        //_fillPaint.setStyle(Paint.Style.STROKE);
        //_fillPaint.setColor(Color.RED);
        _fillPaint.setShader(_bitmapShader);

        lzColision = new Rect();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);

        //Initialize path, loops run until all the coords are drawn
        //initialize the array path (initial coords)
        //initalize path
        adrawPath(context);
    }

    /**
     * Initialize paths
     *
     * @param context allows the use of application resources.
     */
    private void adrawPath(Context context)
    {
        Log.d(TAG, "drawPath: ");
        initPath(context);
        _path.moveTo(0,_height);

        for (int i = 0; i < tempArrayX.size(); i++) {
            _path.lineTo(tempArrayX.get(i),tempArrayY.get(i));

        }
        _path.lineTo(_width,_height);
        _path.close();
    }

    /**
     * Initialize the arrays
     * converts to density pixels and store it in tempArray
     *
     * @param context allows the use of the application resources
     */
    private void initPath(Context context)
    {

        //!---EXTENSION SCREEN RESOLUTION FRIENDLY---!
        //terrainX and terrainY are converted to pixels.
        //and put it in a new list,
        //I added this extension because this game won't work correctly on other phones with higher resolution other than 720p.
        //As a developer you must always think about the usability of your program. especially for mobile application
        //it is important to support as many device as possible.

        _path = new Path();
        //start of line - lower left of the screen

        int terrainX[] = {0, 20, 20, 40, 40, 20, 20, 40, 40, 60, 60, 80, 80, 60, 60, 80, 80, 100, 100, 180, 180, 200, 220, 220, 220, 200, 220, 240, 240, 220, 240, 260, 280, 300, 280, 280, 300, 320, 300, 300, 340, 320, 340, 320, 360, 360,};
        int terrainY[] = {380,380,360,360,340,340,320,320,300,300,320,320,340,340,360,360,380, 380, 340,340,380,380,380,360,340, 340,320,320,300, 300,280,280,280,300,300,320,320,340,340,380,380,360,340,320, 320,380};
        int arrayLen = terrainX.length;
        Log.i(TAG, "X . LEN" + terrainX.length + " Y . LEN" + terrainY.length);



        //for (int i = 0; i < terrainX.length; i++) -> this one is slower according to developers.android.com  because its not optimized yet.
        //https://developer.android.com/training/articles/perf-tips.html
        for (int i = 0; i < arrayLen; i++) //This way is faster because it retrieves everything from a local variable and avoid lookups.
        {
            //converts every dp in the array to pixels
            int x;
            int y;
            if (_width == 1080) {

                if(i==arrayLen-1)
                {
                    x = ScreenInformation.dpToP(terrainX[i]+240, context);
                    y = ScreenInformation.dpToP(terrainY[i]+60, context);
                    //Log.i(TAG, "i>0 1440 " + i);
                }
                else {
                    x = ScreenInformation.dpToP(terrainX[i], context);
                    y = ScreenInformation.dpToP(terrainY[i]+60, context);
                    //Log.i(TAG, "i==0 1440 " + i);
                }

            } else if (_width == 1440) {
                if(i==arrayLen-1)
                {
                    x = ScreenInformation.dpToP(terrainX[i]+120, context);
                    y = ScreenInformation.dpToP(terrainY[i]+120, context);
                    //Log.i(TAG, "LAST " + i);
                }
                else {
                    x = ScreenInformation.dpToP(terrainX[i], context);
                    y = ScreenInformation.dpToP(terrainY[i]+120, context);
                    //Log.i(TAG, "i==0 1440 " + i);
                }
            } else {

                x = ScreenInformation.dpToP(terrainX[i], context);
                y = ScreenInformation.dpToP(terrainY[i], context);

            }
            //new value are stored in a list.
            //its used for collision.
            //i used List because you cant resize an array dynamically.
            tempArrayX.add(x);
            tempArrayY.add(y);
        }
    }

    /**
     * draw the the landing zone
     *
     * @param canvas holds the onDraw Calls.
     * @see android.view.SurfaceView
     * @see android.view.View
     */
    void drawLZ(Canvas canvas)
    {
        //array 19 is landing zone

        // LZ pos - width/height of the bitmap minus array 19
        int x = tempArrayX.get(19)-lz.getWidth();
        int y = tempArrayY.get(19)-lz.getHeight();

        lzColision.set(0,0,lz.getWidth(),lz.getHeight());
        lzColision.offset(x,y);

        canvas.drawBitmap(lz, x,y,null);
        //canvas.drawRect(lzColision,paint);
    }


}
