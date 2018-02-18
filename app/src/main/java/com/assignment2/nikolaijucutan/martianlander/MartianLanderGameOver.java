package com.assignment2.nikolaijucutan.martianlander;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 *@author Nikko Jucutan
 *@version 1.0
 */
class MartianLanderGameOver {

    private Bitmap crater;
    private int bmpHeight;

    /**
     * @param context allows access to the resources
     */
    MartianLanderGameOver(Context context) {
        init(context);
    }

    /**
     * @param context allows access to the resources
     */
    private void init(Context context)
    {
        //initialize crater image.
        crater = BitmapFactory.decodeResource(context.getResources(), R.drawable.crater);
        bmpHeight= crater.getHeight();
        //setWillNotDraw(false);
        //Log.i(TAG, "init: " + willNotDraw());
    }

    /**
     * @param canvas holds the draw calls.
     */
    void crater(Canvas canvas)
    {
        int x = MartianLanderSpaceship.setPosX;
        int y = MartianLanderSpaceship.setPosY + bmpHeight;
        //Log.i(TAG, "invalidate" + x + " " + y);
        canvas.drawBitmap(crater, x, y, null);
    }
}
