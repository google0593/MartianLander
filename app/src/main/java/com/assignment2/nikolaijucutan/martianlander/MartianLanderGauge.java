package com.assignment2.nikolaijucutan.martianlander;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 *@author Nikko Jucutan
 *@version 1.0
 */
class MartianLanderGauge{

    //GAUGE  PATH POINTS (TRIANGLE - BORDER)
    //A - upper left
    //B - upper right
    //C - Bottom

    //fuel border
    private final float _a;
    private final float _b;
    private final float _c;

    //deduct fuel
    private static double fuelConsumptionA = 0;
    private static double fuelConsumptionC = 0;

    //fuel
    private static float fuelA;
    private static float fuelB;
    private static float fuelC;

    private Path fuelBorder;
    private Path fuel;

    private static Paint _Paint;
    private final Paint _PaintFill;
    private final Context _cont;//initalized from the constructor

    private static boolean hasFuel;
    static boolean zeroFuel = false;//this is not necessary, this will just make the code a little bit understandable and consistent.

    /**
     *
     * @param context allows access to the resources
     */
    MartianLanderGauge(Context context) {
        //----!Explanation of my Gauge---
        //im using pathing for this part of the assignment
        //first i initialize 2 paint object, one is for stroke (_Paint) another is for the fill
        //therefore it creates the illusion of a shape with a border by overlapping them together
        //every time the user press a button the path (_fuel) is deducted by fuelConsumptionA and C

        _Paint = new Paint();
        _Paint.setStyle(Paint.Style.STROKE);
        _Paint.setStrokeWidth(7);
        _Paint.setAntiAlias(true);
        _Paint.setColor(Color.GREEN);

        _PaintFill = new Paint();
        _PaintFill.setStyle(Paint.Style.FILL);
        _PaintFill.setAntiAlias(true);
        _PaintFill.setColor(Color.parseColor("#03a9f4"));//material blue color

        _a = ScreenInformation.dpToP(10,context);
        _b = ScreenInformation.dpToP(150,context);
        _c = ScreenInformation.dpToP(50,context);

        fuelA = _a;
        fuelB = _b;
        fuelC = _c;
        _cont = context;

    }

    /**
     * @param canvas holds the draw calls.
     */
    void render(Canvas canvas)
    {
        path(_cont);
        canvas.drawPath(fuel, _PaintFill);

        canvas.drawPath(fuelBorder,_Paint);
    }

    /**
     *
     * @param context allows access to the resources
     */
    private void path(Context context)
    {

        //Log.i(TAG, "A " + fuelA + " C " + fuelC);
        fuelBorder = new Path();
        fuelBorder.moveTo(_a,_a);
        fuelBorder.lineTo(_a,_a);
        fuelBorder.lineTo(_a,_b);
        fuelBorder.lineTo(_c,_a);
        fuelBorder.close();
        fuelBorder.offset(ScreenInformation.dpToP(20,context),ScreenInformation.dpToP(5,context));
        
        fuel = new Path();
        //x,y
        fuel.moveTo(_a, (float) (fuelA + fuelConsumptionA));
        fuel.lineTo(_a, (float) (fuelA + fuelConsumptionA));


        fuel.lineTo(_a,fuelB);//dont touch
        //fuel C 5px increment
        //fuel A 15px increment
        fuel.lineTo((float)(fuelC - fuelConsumptionC), (float)(fuelA + fuelConsumptionA));
        fuel.close();


        fuel.offset(ScreenInformation.dpToP(20,context),ScreenInformation.dpToP(5,context));

        hasFuel = fuelConsumptionA < fuelB-fuelA;

        if(zeroFuel)
        {
            _Paint.setColor(Color.RED);
        }else
        {
            _Paint.setColor(Color.GREEN);
        }
        //Log.i(TAG, "path: " + hasFuel);
        //A 280 C 75


        //Log.i(TAG, "fuel    " + fuelA + " " + fuelB + " " + fuelC);
        //Log.i(TAG, "consumptionFuel     " + fuelConsumptionA + " " + (fuelB-fuelA));

    }


    static void fuel()
    {
        //fuel count is calculated by fuelB - fuelA, end point of triangle
        //Bottom of the triangle
        //if
        if(hasFuel)
        {
            fuelConsumptionA = fuelConsumptionA + 0.30;
            fuelConsumptionC = fuelConsumptionC + 0.08;
        }
        else
        {
            _Paint.setColor(Color.RED);
            zeroFuel = true;
        }

    }

    static void reset()
    {
        zeroFuel = false;
        fuelConsumptionA = 0;
        fuelConsumptionC = 0;
    }
}
