package com.na.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * @actor:taotao
 * @DATE: 16/11/17
 */
public class NaProgressBar extends View {

//    private static final String TAG = "NaProgressBar";

    //中心圆画笔
    private Paint mCirclePaint;
    //中心圆背景色
    private int mCircleBgColor;

    //背景圆弧画笔
    private Paint mArcBgPaint;
    //背景圆弧画笔颜色
    private int mArcBgColor;

    //圆弧画笔
    private Paint mArcPaint;
    //圆弧画笔颜色
    private int mArcColor;

    //圆弧宽度
    private float mArcWidth;
    //圆弧与中心圆的间隔
    private float mArcInternal;

    //圆弧长度 360：封闭弧
    private float mCurrentAngel;
    //圆弧起始位置 270：顶部 180：左边 90：底部 0：右边
    private float mStartAngel;

    private float mMax;
    private final long TIME_INTERVAL = 100L;
    private final int MSG_START = 100;
    private final int MSG_STOP = 200;

    public float mIntervalAngel;
    public float mStopIntervalAngel;
    ArrayList<Float> mStopList = new ArrayList();

    private final byte STATUS_INIT = 1;
    private final byte STATUS_RUNNING = 2;
    private final byte STATUS_STOPING = 4;
    private final byte STATUS_REALESE = 8;
    private byte mStatus = STATUS_INIT;

    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case MSG_START: {
                    if (setProgressAngel()) {
                        if (mStatus == STATUS_RUNNING) {
                            mHandler.sendEmptyMessageDelayed(MSG_START, TIME_INTERVAL);
                        } else {
                            mStopList.add(mCurrentAngel);
                            mStatus = STATUS_INIT;
                        }
                    } else {
                        mStatus = STATUS_REALESE;
                    }
                    break;
                }
                case MSG_STOP: {
                    mStatus = STATUS_STOPING;
                    break;
                }
                default: {
                    break;
                }
            }
        }
    };

    public NaProgressBar(Context context) {
        super(context);
        initView();
    }

    public NaProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public NaProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NaProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {

        mCurrentAngel = 0.0f;
        mStartAngel = 270.0f;
        mArcWidth = dipToPx(5.0f);
        mArcInternal = dipToPx(2.5f);
        mArcBgColor = Color.WHITE;
        mArcColor = Color.BLUE;
        mCircleBgColor = Color.BLUE;

        //中心圆
        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleBgColor);

        //整个弧形
        mArcBgPaint = new Paint();
        mArcBgPaint.setAntiAlias(true);
        mArcBgPaint.setStyle(Paint.Style.STROKE);
        mArcBgPaint.setStrokeWidth(mArcWidth);
        mArcBgPaint.setColor(mArcBgColor);
        mArcBgPaint.setStrokeCap(Paint.Cap.ROUND);

        //当前进度的弧形
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setStrokeWidth(mArcWidth);
        mArcPaint.setColor(mArcColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        if (w > 50 && h > 50) {
            float centerX = w / 2.0f;
            float centerY = h / 2.0f;

            float radius = w / 2.0f;
            if (w > h) {
                radius = h / 2.0f;
            }

            float arcRadius = radius - mArcWidth / 2.0f;
            if (arcRadius <= 0.0f) {
                return;
            }

            RectF bgRect = new RectF();
            bgRect.top = centerY - arcRadius;
            bgRect.left = centerX - arcRadius;
            bgRect.bottom = centerY + arcRadius;
            bgRect.right = centerX + arcRadius;
            canvas.drawArc(bgRect, mStartAngel, 360.0f, false, mArcBgPaint);

            if (mCurrentAngel > 0.0f) {
                float astart = 0.0f;
                float angel = 0.0f;
                for (int i = 0; i < mStopList.size(); ++i){
                    float p = mStopList.get(i);
                    if (p == mCurrentAngel) {
                        angel = p - astart;
                    } else {
                        angel = p - astart - mStopIntervalAngel;
                    }
                    if (angel > 0.0f) {
                        canvas.drawArc(bgRect, mStartAngel + astart, angel, false, mArcPaint);
                    }
                    astart = p;
                }
                angel = mCurrentAngel - astart;
                if (angel > 0.0f) {
                    canvas.drawArc(bgRect, mStartAngel + astart, mCurrentAngel - astart, false, mArcPaint);
                }
            }
            float circleRadius = radius - mArcInternal - mArcWidth;

            if (circleRadius <= 0.0f) {
                return;
            }
            canvas.drawCircle(centerX, centerY, circleRadius, mCirclePaint);

        }
    }

    private int dipToPx(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    public void setCircleBgColor(int color) {
        this.mCircleBgColor = color;
    }

    public void setArcBgColor(int color) {
        this.mArcBgColor = color;
    }

    public void setArcColor(int color) {
        this.mArcColor = color;
    }

    public void setArcWidth(float width) {
        this.mArcWidth = dipToPx(width);
    }

    public void setArcInternal(float internal) {
        this.mArcInternal = dipToPx(internal);
    }

    //an angel max is 360  this interal default is 360 / max
    public void setStopIntervalAngel(float interval) {
        this.mStopIntervalAngel = interval;
    }

    public boolean isRunning() {
        return mStatus == STATUS_RUNNING;
    }

    public void setMax(float max) {
        this.mMax = max;
        mIntervalAngel = 360.0f / (max * (1000.0f / TIME_INTERVAL));
        mStopIntervalAngel = 360.0f / max;
    }

    private boolean setProgressAngel(){
        this.mCurrentAngel += mIntervalAngel;
//        Log.e("NaProgressBar", "setProgressAngel mCurrentAngel=" + mCurrentAngel);
        if (this.mCurrentAngel > 360.0f){
            this.mCurrentAngel = 360.0f;
            return false;
        }
        postInvalidate();
        return true;
    }

    public void start() {
        if (mStatus == STATUS_INIT) {
            mStatus = STATUS_RUNNING;
            mHandler.sendEmptyMessageDelayed(MSG_START, TIME_INTERVAL);
        } else if (mStatus == STATUS_REALESE){
            release();
            mStatus = STATUS_RUNNING;
            mHandler.sendEmptyMessageDelayed(MSG_START, TIME_INTERVAL);
        }
    }

    public void stop() {
        if (mStatus == STATUS_RUNNING) {
            if (!mHandler.hasMessages(MSG_STOP)) {
                mHandler.sendEmptyMessageDelayed(MSG_STOP, 2000l);
            }
        }
    }

    public void release() {
        mStatus = STATUS_INIT;
        mStopList.clear();
        mHandler.removeMessages(MSG_START);
        mHandler.removeMessages(MSG_STOP);
        mCurrentAngel = 0.0f;
        postInvalidate();
    }
}