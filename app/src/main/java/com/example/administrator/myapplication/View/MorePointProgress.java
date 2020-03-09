package com.example.administrator.myapplication.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个点控进度条
 * Created by Wangsipeng on 2018/4/17.
 */

public class MorePointProgress extends View {

    private Paint mTextPaint;      //绘制文字的画笔
    private Paint mLinePaint;      //绘制基准线的画笔
    private Paint mCirclePaint;    //绘制基准线上灰色圆圈的画笔
    private Paint mCircleSelPaint; //绘制被选中位置的蓝色圆圈的画笔

    /***被选中时文字颜色**/
    private int mColorSelectTextDef = Color.GRAY;

    /***被选中点的颜色***/
    private final String mColorSelected = "#64A9D7";


    /***底线的颜色***/
    private final String mColorDef = "#A49D9D";

    private final int mTextSize = 30;

    private final int mLineHight = 5;

    /***圆圈的直径**/
    private int mCircleHight = 50;

    /***默认圆圈的直径**/
    private int mCircleDefaultHight = 20;


    /***基准线宽度***/
    private final int mBottomLineStroke = 10;

    private final int mCircleSelStroke = 2;

    /**
     * 文字与基准线的距离
     **/
    private int mMarginTop = 20;

    private List<IDataBean> textList = new ArrayList<>();
    private ArrayList<Rect> mBounds;

    /***当前选中的index******/
    private int selectIndex = -1;

    /**
     * 每段线的长度
     ******/
    private int dividWidth;

    private int defaultHeight;

    private Bitmap reduceBitmap;
    private Bitmap addBitmap;

    private OnProgressChangedListener onProgressChangedListener;

    private long downTime = 0;//记录down时的时间

    public MorePointProgress(Context context) {
        this(context, null);
        initPaint();
        measureText();
        measureHeight();
    }

    public MorePointProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        initPaint();
        measureText();
        measureHeight();
    }

    public MorePointProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        measureText();
        measureHeight();
    }

    private void initPaint() {

        reduceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.home_fan_reduce);
        addBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.home_fan_add);

        mLinePaint = new Paint();
        mCirclePaint = new Paint();
        mTextPaint = new Paint();
        // 被点击时
        mCircleSelPaint = new Paint();
        Paint mTextSelPaint = new Paint();

        //绘制底部灰色基准线
        mLinePaint.setColor(Color.parseColor(mColorDef));
        mLinePaint.setStyle(Paint.Style.FILL);//设置填充
        mLinePaint.setStrokeWidth(mLineHight);//笔宽像素
        mLinePaint.setAntiAlias(true);//锯齿不显示
        //noinspection SuspiciousNameCombination
        mLinePaint.setStrokeWidth(mBottomLineStroke);


        //绘制节点实心圆圈
        mCirclePaint.setColor(Color.parseColor(mColorSelected));
        mCirclePaint.setStyle(Paint.Style.FILL);//设置填充
        mCirclePaint.setStrokeWidth(mCircleSelStroke);//笔宽像素
        mCirclePaint.setAntiAlias(true);//锯齿不显示

        //绘制节点被选中时的实心圆圈
        mCircleSelPaint.setColor(Color.parseColor(mColorSelected));
        mCircleSelPaint.setStyle(Paint.Style.FILL);    //实心圆圈
        mCircleSelPaint.setStrokeWidth(mCircleSelStroke);
        mCircleSelPaint.setAntiAlias(true);

        mTextPaint.setTextSize(mTextSize);//文本 画笔
        //默认文字颜色
        int mColorTextDef = Color.GRAY;
        mTextPaint.setColor(mColorTextDef);
        mLinePaint.setAntiAlias(true);

        mTextSelPaint.setTextSize(mTextSize);//选中后的文本画笔
        mTextSelPaint.setColor(Color.parseColor(mColorSelected));
        mTextSelPaint.setAntiAlias(true);

    }


    /**
     * 测量文字的长宽
     */
    private void measureText() {//
        mBounds = new ArrayList<>();
        for (IDataBean name : textList) {
            Rect mBound = new Rect();
            mTextPaint.getTextBounds(name.getTextName(), 0, name.getTextName().length(), mBound);
            mBounds.add(mBound);
        }
    }

    private void measureHeight() {//测量view的高度
        if (mBounds != null && !mBounds.isEmpty())
            defaultHeight = mCircleHight + mMarginTop + mCircleSelStroke + mBounds.get(0).height() / 2;
        else
            defaultHeight = mCircleHight + mMarginTop + mCircleSelStroke;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {//宽高都设置为wrap_content
            setMeasuredDimension(widthSpecSize, defaultHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {//宽设置为wrap_content
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {//高设置为wrap_content
            setMeasuredDimension(widthSpecSize, defaultHeight);
        } else {//宽高都设置为match_parenth或具体的dp值
            setMeasuredDimension(widthSpecSize, heightSpecSize);
            mCircleHight = heightSpecSize / 4;
            mCircleDefaultHight = heightSpecSize / 8;
            mMarginTop = heightSpecSize / 5;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (textList == null || textList.isEmpty())
            return;
        mLinePaint.setColor(Color.parseColor(mColorDef));
        canvas.translate(0, reduceBitmap.getHeight() + (mCircleHight >> 1) - (mBottomLineStroke >> 1));
        canvas.drawLine((mCircleHight >> 1), (mCircleHight >> 1) + mCircleSelStroke, getWidth() - (mCircleHight >> 1), (mCircleHight >> 1) + mCircleSelStroke, mLinePaint);
        int circleCount = textList.size();//画灰色圆的个数
        dividWidth = (getWidth() - mCircleHight) / (circleCount - 1);//每个圆相隔的距离
        for (int i = 0; i <= circleCount; i++) {
            //noinspection StatementWithEmptyBody
            if (i == 0 && i != selectIndex) {
                //                if (i == selectIndex) {
                //                    canvas.drawCircle((mCircleHight >> 1) + i * dividWidth, (mCircleHight >> 1) + mCircleSelStroke, (mCircleHight >> 1), mCircleSelPaint);
                //                }
            } else if (i == selectIndex) {
                canvas.drawCircle((mCircleHight >> 1) + i * dividWidth, (mCircleHight >> 1) + mCircleSelStroke, (mCircleHight >> 1), mCircleSelPaint);
            } else if (i < selectIndex) {
                canvas.drawCircle((mCircleHight >> 1) + i * dividWidth, (mCircleHight >> 1) + mCircleSelStroke, mCircleDefaultHight >> 1, mCircleSelPaint);
            } else {
                canvas.drawCircle((mCircleHight >> 1) + i * dividWidth, (mCircleHight >> 1) + mCircleSelStroke, mCircleDefaultHight >> 1, mCirclePaint);
            }
        }
        if (selectIndex != -1) {
            mLinePaint.setColor(Color.parseColor(mColorSelected));
            canvas.drawLine((mCircleHight >> 1), (mCircleHight >> 1) + mCircleSelStroke, (mCircleHight >> 1) + selectIndex * dividWidth, (mCircleHight >> 1) + mCircleSelStroke, mLinePaint);
        }

        for (int i = 0; i < textList.size(); i++) {
            int currentTextWidth = mBounds.get(i).width();
            if (i == 0) {
                //第一个点
                if (i <= selectIndex) {
                    canvas.drawText(textList.get(i).getTextName(), (mCircleHight >> 1) + i * dividWidth - (currentTextWidth >> 1), mCircleHight + mMarginTop + mCircleSelStroke + mBounds.get(i).height() / 2, mTextPaint);
                    canvas.drawBitmap(reduceBitmap, (mCircleHight >> 1) - (reduceBitmap.getWidth() >> 1), -(mCircleHight >> 1) - (reduceBitmap.getHeight() >> 1), new Paint());
                } else {
                    canvas.drawText(textList.get(i).getTextName(), (mCircleHight >> 1) + i * dividWidth - (currentTextWidth >> 1), mCircleHight + mMarginTop + mCircleSelStroke + mBounds.get(i).height() / 2, mTextPaint);
                    canvas.drawBitmap(reduceBitmap, (mCircleHight >> 1) - (reduceBitmap.getWidth() >> 1), -(mCircleHight >> 1) - (reduceBitmap.getHeight() >> 1), new Paint());
                }
            } else if (i == textList.size() - 1) {
                //最后一个点
                if (i <= selectIndex) {
                    canvas.drawText(textList.get(i).getTextName(), (mCircleHight >> 1) + i * dividWidth - (currentTextWidth >> 1), mCircleHight + mMarginTop + mCircleSelStroke + mBounds.get(i).height() / 2, mTextPaint);
                    canvas.drawBitmap(addBitmap, (mCircleHight >> 1) + i * dividWidth - (addBitmap.getWidth() >> 1), -(mCircleHight >> 1) - (addBitmap.getHeight() >> 1), new Paint());
                } else {
                    canvas.drawText(textList.get(i).getTextName(), (mCircleHight >> 1) + i * dividWidth - (currentTextWidth >> 1), mCircleHight + mMarginTop + mCircleSelStroke + mBounds.get(i).height() / 2, mTextPaint);
                    canvas.drawBitmap(addBitmap, (mCircleHight >> 1) + i * dividWidth - (addBitmap.getWidth() >> 1), -(mCircleHight >> 1) - (addBitmap.getHeight() >> 1), new Paint());
                }
            } else {
                if (i <= selectIndex) {
                    canvas.drawText(textList.get(i).getTextName(), (mCircleHight >> 1) + i * dividWidth - (currentTextWidth >> 1), mCircleHight + mMarginTop + mCircleSelStroke + mBounds.get(i).height() / 2, mTextPaint);
                } else {
                    canvas.drawText(textList.get(i).getTextName(), (mCircleHight >> 1) + i * dividWidth - (currentTextWidth >> 1), mCircleHight + mMarginTop + mCircleSelStroke + mBounds.get(i).height() / 2, mTextPaint);
                }
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX;
        //        float eventY;
        int i = event.getAction();
        if (i == MotionEvent.ACTION_DOWN) {
            Log.e("onTouchEvent", "ACTION_DOWN");
            downTime = System.currentTimeMillis();
        } else if (i == MotionEvent.ACTION_MOVE) {
            Log.e("onTouchEvent", "ACTION_MOVE");
        } else if (i == MotionEvent.ACTION_UP) {
            Log.e("onTouchEvent", "ACTION_UP");
            //记录up时的时间
            long upTime = System.currentTimeMillis();

            if (upTime - downTime > 500)//如果是长点击就什么都不做
            {
                return false;
            } else {
                eventX = event.getX();
                //                eventY = event.getY();

                float select = eventX / dividWidth; //计算选中的index
                float xs = select - (int) (select);
                selectIndex = (int) select + (xs > 0.5 ? 1 : 0);
                if (onProgressChangedListener != null)
                    onProgressChangedListener.Progress(selectIndex);
                invalidate();
                return true;
            }
        }
        return true;
    }

    public void setOnProgressChangedListener(OnProgressChangedListener onProgressChangedListener) {
        this.onProgressChangedListener = onProgressChangedListener;
    }

    public interface OnProgressChangedListener {
        void Progress(int index);
    }

    public void setProgress(int index) {
        if (index > textList.size())
            return;
        selectIndex = index;
        invalidate();

    }

    public int getProgress() {
        if (selectIndex == -1) {
            return 0;
        } else {
            return selectIndex;
        }
    }

    public void setData(List<IDataBean> t) {
        textList = t;
        measureText();
        measureHeight();
        invalidate();
    }

}
