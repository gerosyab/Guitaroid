package net.gerosyab.guitaroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by donghe on 2017-05-06.
 */

public class CircleProgressButton extends android.support.v7.widget.AppCompatButton {
    Paint paint = new Paint();
    private float progress;
    boolean zoom = false;
    private float radius;
    private int speed = 1;

    public CircleProgressButton(Context context) {
        super(context);
        paint.setStyle(Paint.Style.FILL);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#1A237E"));
    }

    public CircleProgressButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setStyle(Paint.Style.FILL);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#1A237E"));
    }

    public CircleProgressButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setStyle(Paint.Style.FILL);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#1A237E"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        // or super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, progress, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = w / 2;
    }

    Runnable animator = new Runnable() {
        @Override
        public void run() {
            progress += speed;
            if (progress >= radius) {
                progress = 0f;
            }
            invalidate();
            postDelayed(this, 30);
        }

    };

    public void startAnimation(int speed){
        progress = 0f;
        post(animator);
    }

    public void stopAnimation(){
        progress = 0f;
        removeCallbacks(animator);
        invalidate();
    }
}
