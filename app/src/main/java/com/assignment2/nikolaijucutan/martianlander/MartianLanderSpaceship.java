package com.assignment2.nikolaijucutan.martianlander;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import static android.content.ContentValues.TAG;


/**
 *@author Nikko Jucutan
 *@version 1.0
 */
class MartianLanderSpaceship {

    //setPosY and getPosX are called in MainActivity class to position the bitmap,
    static int setPosY = 0;
    static int getPosX = 0;
    private static double speed = 0;


    //setPosX = getPosX
    static int setPosX = 0;
    static int bitmapWidth;
    static int bitmapHeight;

    private static Bitmap spaceshipModelBmp;
    private final Bitmap miniBoosterBmp;
    private final Bitmap mainBoosterBmp;
    private final Bitmap bg;

    //these booleans are called in MainActivity class
    static boolean leftBooster = false;
    static boolean rightBooster = false;
    static boolean  mainBooster = false;

    //rect for the spaceship, the size of this rect automatically adjust by getting the image width and height.
    private final Rect spaceshipBounds;
    static boolean colissionDetected = false;

    //calculates the pos of the background
    private int x;

    /**
     * @param context allows access to the resources
     */
    MartianLanderSpaceship(Context context)
    {
        //initialize object variables spaceship pos and background color
        super();
        //spaceship bitmap
        spaceshipModelBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.craftmain);
        bitmapHeight = spaceshipModelBmp.getHeight();
        bitmapWidth = spaceshipModelBmp.getWidth();
        //mini thruster bitmap
        miniBoosterBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.thruster);
        //main thruster bitmap
        mainBoosterBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.main_flame);
        //crater

        bg =BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);

        //initialize the position to appear in the top center of the screen
        //centers the image. screen width - image width / 2
        ScreenInformation screenInfo = new ScreenInformation();
        setPosX = (screenInfo.getWidth(context) - bitmapWidth) /2;
        getPosX = setPosX;
        setPosY = 0;

        Paint _paint = new Paint();
        _paint.setColor(Color.RED);//ToDo Delete after coding is done. Set background color to red. (border identifier)
        _paint.setStyle(Paint.Style.STROKE);
        _paint.setStrokeWidth(5);

        spaceshipBounds = new Rect(0,0,bitmapWidth,bitmapHeight);//sets the rect position(current position of the spaceship) and the size (size of the spaceship).
        Log.i(TAG, "BITMAP WIDTH: " + bitmapWidth + "RECT WIDTH: " + spaceshipBounds.width());



    }

    void move()
    {
        //this method handles the movement and collision of the spaceship
        //and also the infinite scroll of the background.
        moveAndCollision();
        x-=1;
        if(x<-ScreenInformation.screenWidth)
        {
            x=0;

        }
    }


    /**
     * @param canvas holds draw calls.
     */
    void onDrawMLS(Canvas canvas)
    {
        //Method that animates the spaceship
        //the animation is done by drawing a background color and the bitmap alternately at a very fast rate
        //paint -> bitmap -> paint -> bitmap and so on.

        //canvas.drawPaint(backgroundColor);

        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bg,x,0,null);
        //this rect is for debugging.
        //uncomment this if you want to see how the colission works between the landing zone (LZ) and spaceship
        //canvas.drawRect(spaceshipBounds,_paint);
        if(x<0)
        {
            //canvas.getWidth() works as well but i used my own class just to be more clear
            canvas.drawBitmap(bg, x + ScreenInformation.screenWidth, 0,null);
        }
        //canvas.drawRect(spaceshipBounds, _paint);//ToDo Delete after coding is done. Set background color to red. (border identifier)
        if(!colissionDetected)
        {
            canvas.drawBitmap(spaceshipModelBmp, setPosX, setPosY, null);

            if(mainBooster)
            {
                canvas.drawBitmap(mainBoosterBmp, (setPosX + (spaceshipModelBmp.getWidth() / 2)) - (mainBoosterBmp.getWidth() / 2), setPosY + spaceshipModelBmp.getHeight(), null);
            }

            if(leftBooster)
            {
                canvas.drawBitmap(miniBoosterBmp, setPosX, setPosY + spaceshipModelBmp.getHeight(), null);
            }

            if(rightBooster)
            {
                canvas.drawBitmap(miniBoosterBmp,(setPosX + spaceshipModelBmp.getWidth()) - miniBoosterBmp.getWidth(), setPosY + spaceshipModelBmp.getHeight(), null);
            }
        }

    }


    private void moveAndCollision()
    {
        //gravitational pull
        //speed increase
        speed = speed + 0.01;
        Log.d(TAG, "moveAndCollision: " + speed);
        boolean lowerLeft = contains(setPosX, setPosY + bitmapHeight);
        //Log.i(TAG, "move: " + lowerLeft);
        boolean lowerRight = contains(setPosX + bitmapWidth, setPosY + bitmapHeight);
        boolean lowerCenter = contains(setPosX + bitmapWidth /2, setPosY + bitmapHeight);
        boolean edgeLeft = setPosX <= 0;
        boolean edgeRight = setPosX + bitmapWidth >= MartianLanderTerrain._width;
        //Log.i(TAG, "move: " + edgeLeft);


        spaceshipBounds.set(0,0,bitmapWidth,bitmapHeight);
        spaceshipBounds.offset(setPosX,setPosY);
        //Log.d(TAG, "moveAndCollision: " + setPosY + " landing zone  " + MartianLanderTerrain.tempArrayX.get(18) + " " + MartianLanderTerrain.tempArrayY.get(18));

        //if spaceship bounds and landinzing zone intersects
        //do nothing.
        if (!spaceshipBounds.intersect(MartianLanderTerrain.lzColision)) {
            //if spaceship collided with something else
            if(lowerLeft && lowerRight && lowerCenter)
            {
                setPosY += 3 + speed;
                setPosX = getPosX;
                spaceshipBounds.offsetTo(setPosX,setPosY);
            }
            //if not, check if lower right and left edge of the screen are colliding
            else if(lowerRight && edgeLeft)
            {
                setPosY += 3 + speed;
                setPosX = getPosX;
                spaceshipBounds.offsetTo(setPosX,setPosY);
                //repos X on the on the opposite side of the screen
                getPosX = MartianLanderTerrain._width - bitmapWidth;
            }
            //check lower left/right/center of the bitmap if it's colliding with the path.
            else if(lowerLeft && edgeRight)
            {
                setPosY += 3 + speed;
                setPosX = getPosX;
                spaceshipBounds.offsetTo(setPosX,setPosY);
                getPosX = 0;
            }
            else
            {
                spaceshipBounds.offsetTo(setPosX,setPosY);
                //Log.i(TAG, "COLLISION DETECTED ON X: " + setPosX + "AND Y: " + setPosY);
                colissionDetected = true;

            }
        }

    }

    /**
     *
     * @param x0 double - value X value of the spaceship (setPosX).
     * @param y0 double - value Y of the current pos of spaceship (setPosY).
     * @return boolean if collided return true.
     */
    private boolean contains(double x0, double y0) {
        //Source: John Casey's colission formula.
        //i changed it a bit so its easier to reuse.
        //i put the array list in the method because array will always be the same.
        //so when you re-use you only need to pass x and y values.
        int crossings = 0;
        int i;

        for (i = 0; i < MartianLanderTerrain.tempArrayX.size() - 1; i++) {

            int x1 = MartianLanderTerrain.tempArrayX.get(i);//first index
            int x2 = MartianLanderTerrain.tempArrayX.get(i + 1);//first + 1 index, so 2nd

            int y1 = MartianLanderTerrain.tempArrayY.get(i);
            int y2 = MartianLanderTerrain.tempArrayY.get(i + 1);

            int dy = y2 - y1;
            int dx = x2 - x1;

            double slope = 0;
            if (dx != 0) {
                slope = (double) dy / dx;
            }

            boolean cond1 = (x1 <= x0) && (x0 < x2); // is it in the range?
            boolean cond2 = (x2 <= x0) && (x0 < x1); // is it in the reverse
            // range?
            boolean above = (y0 < slope * (x0 - x1) + y1); // point slope y - y1

            if ((cond1 || cond2) && above) {
                crossings++;
            }


        }
        return (crossings % 2 != 0); // even or odd
    }

    /**
     * resets setPosX, setPosY and collisionDetected
     * @see MainActivity
     */
    static void reset()
    {
        setPosX = ScreenInformation.screenWidth / 2;
        setPosY = 0;
        speed = 0;
        colissionDetected = false;
    }
}
