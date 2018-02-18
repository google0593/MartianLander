package com.assignment2.nikolaijucutan.martianlander;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 *@author Nikko Jucutan
 *@version 1.0
 */

class MartianLanderSprite {

    private final Bitmap explosionSprite;
    private final Bitmap wreckageSprite;
    private final Bitmap remains;

    private final int expSpriteX;
    private final int expSpriteY;

    private final int wreckSpriteX;
    private final int wreckSpriteY;

    //its -1 because the sprite sheet counter starts at 0
    private static int currentRow = -1;
    private static int currentColumn = 0;
    //animation delay
    private static int delay = 0;

    //its -1 because the sprite sheet counter starts at 0
    private int currentRowWreck = -1;
    private int currentColumnWreck = 0;

    //sprite sheet pos
    private int x;
    private int y;

    private int x1;
    private int y1;

    private final int bitmapHeight;
    private final int bitmapWidth;

    /**
     * Initialize all variables
     *
     * @param context allows the use of application resources.
     */
    MartianLanderSprite(Context context)
    {
        //initialize exp sprite sheet
        explosionSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion);
        //bitmap width / 5. because expSprite has 5 rows
        expSpriteX = explosionSprite.getWidth() / 5;
        //bitmap width / 3. because expSprite has 3 rows
        expSpriteY = explosionSprite.getHeight() / 3;

        //initialize wreckage sprite sheet
        wreckageSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.wreckage);
        //initialize remains :P
        remains = BitmapFactory.decodeResource(context.getResources(), R.drawable.remains);
        //bitmap width / 5. because expSprite has 5 rows
        wreckSpriteX = wreckageSprite.getWidth() / 5;
        //bitmap width / 5. because expSprite has 5 rows
        wreckSpriteY = wreckageSprite.getHeight() / 5;

        bitmapHeight = MartianLanderSpaceship.bitmapHeight;
        bitmapWidth = MartianLanderSpaceship.bitmapWidth;

    }



    /**
     * draw the main explosion sprite
     *
     * @param canvas holds the onDraw Calls.
     * @see android.view.SurfaceView
     * @see android.view.View
     */
    void onDrawSprite(Canvas canvas)
    {
        update();
        //sprite row
        int frameX = currentRow * expSpriteX;
        //sprite column
        int frameY = currentColumn * expSpriteY;
        //shows what part of the bitmap will be displayed.
        //this will depend on the current frame that is assigned by update(); method.
        Rect src = new Rect(frameX, frameY, expSpriteX+frameX, expSpriteY+frameY);
        //to scale and position
        Rect dst = new Rect(x, y, expSpriteX+x, expSpriteY+y);

        canvas.drawBitmap(explosionSprite,src,dst,null);
    }

    /**
     * draw the wreckage fire sprite
     *
     * @param canvas holds the onDraw Calls.
     * @see android.view.SurfaceView
     * @see android.view.View
     */
    void onDrawWreckage(Canvas canvas)
    {
        update();
        //sprite row
        int frameX = currentRowWreck * wreckSpriteX;
        //sprite column
        int frameY = currentColumnWreck * wreckSpriteY;
        //shows what part of the bitmap will be displayed.
        //this will depend on the current frame that is assigned by update(); method.
        Rect src = new Rect(frameX, frameY, wreckSpriteX+frameX, wreckSpriteY+frameY);
        //to scale and position
        Rect dst = new Rect(x1, y1, wreckSpriteX+x1, wreckSpriteY+y1);
        canvas.drawBitmap(remains,x1,y1,null);
        canvas.drawBitmap(wreckageSprite,src,dst,null);
    }


    /**
     * gets the pos from MLS class and sets the position locally
     * that is then is used to position the sprites
     *
     * @see MartianLanderSpaceship
     */
    private void update()
    {

        x1 = MartianLanderSpaceship.setPosX;
        y1 = MartianLanderSpaceship.setPosY;
        currentRowWreck += 1;
        if(currentRowWreck == 5)
        {
            currentRowWreck = 0;
            currentColumnWreck += 1;
        }else if(currentColumnWreck == 5){
            currentRowWreck = 0;
            currentColumnWreck = 0;
        }

        x = MartianLanderSpaceship.setPosX - bitmapWidth/2;
        y = MartianLanderSpaceship.setPosY - bitmapHeight;

        currentRow += 1;
        if(currentRow == 5)
        {
            currentRow = 0;
            currentColumn += 1;
        }



        //put a delay for the first 20 loop
        //i did this for slow motion effect
        //its when the main explosion occur
        if(delay < 20)
        {
            delay++;
            //50ms delay
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Resets currentRow, currentColumn and delay
     *
     * @see MartianLanderSpaceship
     *
     */
    static void reset()
    {
        currentRow = -1;
        currentColumn = 0;
        delay = 0;
    }
}
