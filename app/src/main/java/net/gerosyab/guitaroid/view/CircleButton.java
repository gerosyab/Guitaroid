package net.gerosyab.guitaroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by donghe on 2017-05-06.
 */

public class CircleButton extends android.support.v7.widget.AppCompatButton {

    public CircleButton(Context context) {
        super(context);
    }

    public CircleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        // or super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }

}
