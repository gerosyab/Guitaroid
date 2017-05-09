package net.gerosyab.guitaroid.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import net.gerosyab.guitaroid.R;

/**
 * Created by donghe on 2017-05-06.
 */

public class CircleProgressButton extends View {
    private static final String TAG = "CircleProgressButton";
    public static final int DEFAULT_BACKGROUND_COLOR = Color.TRANSPARENT;
    public static final int DEFAULT_ANIMATION_DURATION = 1000;
    Paint progressPaint = new Paint();
    Paint outerPaint = new Paint();
    Paint backgroundPaint = new Paint();
    Paint textPaint = new Paint();
    private float progress;
    private float radius;
    private int speed = 1;
    private float textSize = 72;
    private Typeface typeFace = Typeface.DEFAULT;
    private RectF areaRect = new RectF();
    private String text;
    private float centerX, centerY;
    private ValueAnimator progressAnimator;
    private int animationDuration = DEFAULT_ANIMATION_DURATION;
    private boolean animated = false;

    public CircleProgressButton(Context context) {
        super(context);
        init(context, null);
    }

    public CircleProgressButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleProgressButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleProgressButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int backgroundColor = DEFAULT_BACKGROUND_COLOR;
        int outerColor = ContextCompat.getColor(context, R.color.colorCircleOuter);
        int progressColor = ContextCompat.getColor(context, R.color.colorCircleProgress);
        int textColor = ContextCompat.getColor(context, R.color.colorCircleProgressText);
        float textSize = getResources().getDimension(R.dimen.circleProgressTextSize);
        float outerSize = getResources().getDimension(R.dimen.circleProgressOuterSize);

        float progress = 0f;


        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressButton, 0, 0);
            textSize = a.getDimension(R.styleable.CircleProgressButton_textSize, textSize);
            textColor = a.getColor(R.styleable.CircleProgressButton_textColor, textColor);
            text = a.getString(R.styleable.CircleProgressButton_text);
            outerColor = a.getColor(R.styleable.CircleProgressButton_outerColor, outerColor);
            progressColor = a.getColor(R.styleable.CircleProgressButton_progressColor, progressColor);
            backgroundColor = a.getColor(R.styleable.CircleProgressButton_backgroundColor, backgroundColor);
        }

        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        outerPaint.setColor(outerColor);
        outerPaint.setStyle(Paint.Style.STROKE);
        outerPaint.setStrokeWidth(10);
        outerPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(typeFace);
        textPaint.setTextAlign(Paint.Align.CENTER);

        progressPaint.setColor(progressColor);
        progressPaint.setStyle(Paint.Style.FILL);
        progressPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        // or super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        areaRect.set(0, 0, widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX, centerY, progress, progressPaint);
        canvas.drawCircle(centerX, centerY, radius - outerPaint.getStrokeWidth(), outerPaint);
        Log.i(TAG, "text : " + text);


//        textPaint.getTextBounds(text, 0, text.length(), textRect);
        // center the text
        canvas.drawText(String.valueOf(text), centerX, centerY, textPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = centerX = centerY = w/ 2;
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
        this.speed = (int) radius / speed;
        post(animator);
    }

    public void stopAnimation(){
        progress = 0f;
        removeCallbacks(animator);
        invalidate();
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }
}
