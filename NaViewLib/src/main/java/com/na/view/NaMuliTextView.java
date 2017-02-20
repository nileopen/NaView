package com.na.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @actor:taotao
 * @DATE: 17/2/4
 */
public class NaMuliTextView extends View {
    /**
     * 需要绘制的文字
     */
    private String mText;
    /**
     * 文本的颜色
     */
    private int mTextColor;
    /**
     * 文本的大小
     */
    private int mTextSize;

    private float mLineSpacingMultiplier;

    private int mInitialSizeMultiple;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private TextPaint mPaint;

    public NaMuliTextView(Context context) {
        super(context);
        initAttr(context, null, 0);
    }

    public NaMuliTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs, 0);
    }

    public NaMuliTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NaMuliTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs, defStyleAttr);
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NaMuliTextView, defStyleAttr, 0);
        if (a != null) {
            mTextColor = a.getColor(R.styleable.NaMuliTextView_textColor, Color.BLACK);
            mTextSize = a.getDimensionPixelSize(R.styleable.NaMuliTextView_textSize, 16);
            mLineSpacingMultiplier = a.getFloat(R.styleable.NaMuliTextView_lineSpacingMultiplier, 1.0f);
            a.recycle();
        }

        mPaint = new TextPaint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        setInitialSizeMultiple(2);
    }

    public void setText(String text) {
        mText = text;
        invalidate();
    }

    public void setInitialSizeMultiple(int mInitialSizeMultiple) {
        if (mInitialSizeMultiple > 0) {
            this.mInitialSizeMultiple = mInitialSizeMultiple;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int vWidth = 0;
        int vHeight = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY){
            vHeight = heightSize;
            vWidth = widthSize;
        } else {
            if (mText != null) {
                mPaint.setTextSize(mTextSize * mInitialSizeMultiple * mLineSpacingMultiplier);
                int width = widthSize;
                StaticLayout initialLayout = new StaticLayout(mText.substring(0, 1),
                        mPaint, width, Layout.Alignment.ALIGN_NORMAL, mLineSpacingMultiplier, 0.0f, false);
                vHeight = initialLayout.getHeight();
                int intaialWidth = vHeight / 2;
                vWidth = intaialWidth;

                width = width - intaialWidth;
                if (mText.length() > 1) {
                    mPaint.setTextSize(mTextSize);
                    StaticLayout layout = new StaticLayout(mText.substring(1),
                            mPaint, width, Layout.Alignment.ALIGN_NORMAL, mLineSpacingMultiplier, 0.0f, false);
                    int lineCount = layout.getLineCount();
                    if (lineCount > 1){
                        vWidth += width;
                    } else {
                        vWidth += layout.getHeight();
                    }

                    if (lineCount > mInitialSizeMultiple) {
                        int end = layout.getLineStart(mInitialSizeMultiple);
                        StaticLayout smallLayout = new StaticLayout(mText.substring(1, end),
                                mPaint, width, Layout.Alignment.ALIGN_NORMAL, mLineSpacingMultiplier, 0.0f, false);

                        StaticLayout bigLayout = new StaticLayout(mText.substring(end),
                                mPaint, width + intaialWidth, Layout.Alignment.ALIGN_NORMAL, mLineSpacingMultiplier, 0.0f, false);
                        int h = smallLayout.getHeight() + bigLayout.getHeight();
                        if (h > vHeight){
                            vHeight = h;
                        }
                    }
                }
            }

            if (widthMode == MeasureSpec.EXACTLY){
                vWidth = widthSize;
            }

            if (heightMode == MeasureSpec.EXACTLY) {
                vHeight = heightSize;
            }
        }


        setMeasuredDimension(vWidth, vHeight);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mText != null) {
            mPaint.setTextSize(mTextSize * mInitialSizeMultiple * mLineSpacingMultiplier);
            int width = getWidth();
            StaticLayout initialLayout = new StaticLayout(mText.substring(0, 1),
                    mPaint, width, Layout.Alignment.ALIGN_NORMAL, mLineSpacingMultiplier, 0.0f, false);
            int intaialWidth = initialLayout.getHeight() / 2;
            initialLayout.draw(canvas);
            canvas.save();
            width = width - intaialWidth;
            if (mText.length() > 1) {
                mPaint.setTextSize(mTextSize);
                StaticLayout layout = new StaticLayout(mText.substring(1),
                        mPaint, width, Layout.Alignment.ALIGN_NORMAL, mLineSpacingMultiplier, 0.0f, false);
                int lineCount = layout.getLineCount();
                if (lineCount > mInitialSizeMultiple){
                    int end = layout.getLineStart(mInitialSizeMultiple);
                    StaticLayout smallLayout = new StaticLayout(mText.substring(1, end),
                            mPaint, width, Layout.Alignment.ALIGN_NORMAL, mLineSpacingMultiplier, 0.0f, false);
                    canvas.translate(intaialWidth, 0);
                    smallLayout.draw(canvas);

                    StaticLayout bigLayout = new StaticLayout(mText.substring(end),
                            mPaint, width + intaialWidth, Layout.Alignment.ALIGN_NORMAL, mLineSpacingMultiplier, 0.0f, false);
                    canvas.restore();
                    canvas.translate(0, smallLayout.getHeight());
                    bigLayout.draw(canvas);
                } else {
                    canvas.translate(intaialWidth, 0);
                    layout.draw(canvas);
                }
            }
        }
    }
}
