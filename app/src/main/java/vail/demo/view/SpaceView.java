package vail.demo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import vail.demo.R;


/**
 * Created by VailWei on 2017/5/4/004.
 * 仿照android.support.v4.widget.Space 编写的一个Space
 */
public class SpaceView extends View {

    private static final int DEFAULT_COLOR = Color.TRANSPARENT;

    private int mHeight = 1;
    private int mColor;

    private Paint mPaint;

    public SpaceView(Context context) {
        this(context, null);
    }

    public SpaceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SpaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SpaceView);
        mHeight = ta.getDimensionPixelSize(R.styleable.SpaceView_space_height, 1);
        mColor = ta.getColor(R.styleable.SpaceView_space_backgroundColor, Color.TRANSPARENT);
        ta.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mColor == DEFAULT_COLOR || mHeight == 0) {
            return;
        }
        canvas.drawLine(getPaddingLeft(), 0, getMeasuredWidth() - getPaddingRight(), 0, mPaint);
    }
}
