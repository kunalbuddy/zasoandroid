package com.zaso.linearlayout;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by gajendrasingh on 12/1/2015.
 */
public class FractionTranslateLinearLayout extends LinearLayout {

    private int screenWidth;
    private float fractionX;

    public FractionTranslateLinearLayout(Context context) {
        super(context);
    }


    protected void onSizeChanged(int w, int h, int oldW, int oldh){

        // Assign the actual screen width to our class variable.
        screenWidth = w;

        super.onSizeChanged(w, h, oldW, oldh);
    }

    public float getFractionX(){

        return fractionX;
    }

    public void setFractionX(float xFraction){

        this.fractionX = xFraction;

        // When we modify the xFraction, we want to adjust the x translation
        // accordingly.  Here, the scale is that if xFraction is -1, then
        // the layout is off screen to the left, if xFraction is 0, then the
        // layout is exactly on the screen, and if xFraction is 1, then the
        // layout is completely offscreen to the right.
        setX((screenWidth > 0) ? (xFraction * screenWidth) : 0);
    }
}