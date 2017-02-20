package com.na.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @actor:taotao
 * @DATE: 17/2/4
 */
public class NaTextView extends TextView {
    private String mInitial;
    private Paint mInitialPaint;
    private Rect mInitialRect;

    public NaTextView(Context context) {
        super(context);
        initInitialPaint();
    }

    public NaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInitialPaint();
    }

    public NaTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInitialPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NaTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInitialPaint();
    }

    private void initInitialPaint() {
        mInitialPaint = new Paint();
        mInitialPaint.setTextSize(getTextSize() * 3);
        mInitialPaint.setColor(getCurrentTextColor());

        mInitialRect = new Rect();
        mInitialPaint.getTextBounds(mInitial, 0, mInitial.length(), mInitialRect);
    }

    public void setInitial(String mInitial) {
        this.mInitial = mInitial;
    }

    public void setText(String text) {
        if (text != null && text.length() > 0){
            setInitial(text.substring(0, 1));
            if (text.length() > 1){
                super.setText(text.substring(1));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mInitial != null){
//            canvas.drawText(mInitial, 0, 0, mInitialPaint);
//            canvas.drawText(mInitial, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mInitialPaint);
        }
//        super.onDraw(canvas);
    }
//    protected void onDraw(Canvas canvas){
//
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
        int width = 0;
        int height = 0;
        if (mInitial != null){
            width = mInitialRect.width();
            height = mInitialRect.height();
        }

    }
}
