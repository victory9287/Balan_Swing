package com.example.victory.balan_swing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by victory on 2017-05-24.
 */

public class MYView extends View {
    int width;
    int height;
    int w10;
    int h10;
    Animation animation;

    public MYView(Context context) {
        super(context);
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        w10 = width / 10;
        h10 = height / 10;
    }

    public void onDraw(Canvas canvas) {
        //animation.setDuration();
        Paint greenPaint = new Paint();
        greenPaint.setColor(0xff00964c);
        greenPaint.setStrokeWidth(11);

        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(11);

        Paint linePaint = new Paint();
        linePaint.setColor(0xff404040);
        linePaint.setTextSize(50);

        int basex;
        int basey;

        double LF, RF = 0.0;
        LF = 5.0;
        RF = 8.0;


        basex = w10;
        basey = h10;
        canvas.drawLine(basex, basey, basex, height - basey, blackPaint);
        canvas.drawLine(basex, height - basey, width - basex, height - basey, blackPaint);
        canvas.drawLine(width - basex, height - basey, width - basex, basey, blackPaint);
        canvas.drawLine(width - basex, basey, basex, basey, blackPaint);
        canvas.drawLine(basex * 2, basey * 3, basex * 8, basey * 3, blackPaint);


        canvas.drawRect(basex * 2, basey * 3, basex * 4, basey * (int) LF, greenPaint);
        canvas.drawText("  왼발", basex * 2, basey * 2, linePaint);

        canvas.drawRect(basex * 6, basey * 3, basex * 8, basey * (int) RF, greenPaint);
        canvas.drawText(" 오른발", basex * 6, basey * 2, linePaint);

        canvas.drawLine(basex * 3, basey * (int) LF, basex * 7, basey * (int) RF, blackPaint);
        canvas.drawCircle(basex * 3, basey * (int) LF, basex / 5, blackPaint);
        canvas.drawCircle(basex * 7, basey * (int) RF, basex / 5, blackPaint);


    }
}
