package com.example.administrator.myapplication.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.administrator.myapplication.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by WangSiPeng on 18/4/01
 * 一个点击到底部中间的饼图
 */

public class PieChatView extends View implements ValueAnimator.AnimatorUpdateListener {

    /**
     * 存放事物的品种与其对应的数量
     */
    private ArrayList<PieData> pieDataList = new ArrayList<>();
    private float valueSum = 0;
    private final Paint mPaint;//饼状画笔
    private final Paint mTextPaint; // 文字画笔
    private static final int DEFAULT_RADIUS = 150;
    /*****默认外圆的半径*****/
    private int mRadius = DEFAULT_RADIUS;
    private RectF oval;
    private String centerTitle = "";
    private String centerTitleTwo = "";
    private String centerTitleThree = "";
    private String onlycenterTitle = "";
    private String onlycenterTitleTwo = "";
    private String onlycenterTitleThree = "";
    /**
     * 是否有数据
     **/
    private boolean ishasData = true;
    private String NoDataText = "";
    private int TextSize = 25;
    /*****画布旋转的角度*****/
    private float degree;
    private ValueAnimator valueAnimator;
    private final long DURATION = 1500;//动画时长 毫秒
    private float offsetDegree = 0;           //初始偏移量
    private boolean isFirstDraw = true;
    /***圆内白色三角形的偏移**/
    private final int Indicator_Triangle = mRadius;
    private initFinish initFinish;
    private final DecimalFormat deci = new DecimalFormat("##0.00");
    private int picWidth, picHeight;

    public PieChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mTextPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
    }

    public PieChatView(Context context) {
        this(context, null, 0);

    }

    public PieChatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wideSize = MeasureSpec.getSize(widthMeasureSpec);
        int wideMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width, height;
        if (wideMode == MeasureSpec.EXACTLY) { //精确值 或matchParent
            width = wideSize;
        } else {
            width = mRadius * 2 + getPaddingLeft() + getPaddingRight();
            if (wideMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, wideSize);
            }

        }

        if (heightMode == MeasureSpec.EXACTLY) { //精确值 或matchParent
            height = heightSize;
        } else {
            height = mRadius * 2 + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }

        }
        picHeight = height;
        picWidth = width;
        setMeasuredDimension(width, height);
        mRadius = (int) (Math.min(width - getPaddingLeft() - getPaddingRight(),
                height - getPaddingTop() - getPaddingBottom()) * 1.0f / 2);
        oval = new RectF(-mRadius, -mRadius, mRadius, mRadius);
    }

    @Override
    protected void onDraw(Canvas mCanvas) {
        super.onDraw(mCanvas);
        mCanvas.translate((getWidth() + getPaddingLeft() - getPaddingRight()) >> 1, (getHeight() + getPaddingTop() - getPaddingBottom()) >> 1);
        mCanvas.save();
        mCanvas.rotate(degree);
        paintPie(mCanvas);
        mCanvas.restore();
        DrawCenterCircle(mCanvas);
//        for (int i = 0; i < pieDataList.size(); i++) {
//            if(pieDataList.get(i).getStartAngel()<=90&&(pieDataList.get(i).getAngel()+pieDataList.get(i).getStartAngel())>=90){
//                drawCenterText(mCanvas, centerTitle,centerTitleTwo,centerTitleThree, 0, -mRadius/4, mTextPaint);
//            }
//        }
        //drawCenterText(mCanvas, centerTitle,"351.5Kw.h","67%", 0, -mRadius/4, mTextPaint);

        if (isFirstDraw && !onlycenterTitle.equals("")) {
            centerTitle = onlycenterTitle;
            centerTitleTwo = onlycenterTitleTwo;
            centerTitleThree = onlycenterTitleThree;

        }
        drawCenterText(mCanvas, centerTitle, centerTitleTwo, centerTitleThree, 0, -mRadius >> 2, mTextPaint);
        if (isFirstDraw) {
            if(initFinish!=null)
            initFinish.finish();
        }
    }

    private void DrawCenterCircle(final Canvas mCanvas) {
        //----------------------------------
        if (valueSum == 0)
            return;
        mPaint.setColor(Color.WHITE);
        //内圆外侧的半透明圆
        //mCanvas.drawCircle(0, 0, mRadius / 2 + dp2px(2), mPaint);
        //内圆
        mPaint.setColor(Color.parseColor("#6394bf"));
        mCanvas.drawCircle(0, 0, mRadius >> 1, mPaint);
        //外圆白色边缘线
//        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(5);
//        mCanvas.drawCircle(0,0,mRadius,paint);
        // ****************************/

        RectF rectF = new RectF((-mRadius >> 1) + Indicator_Triangle - 2, -mRadius / 2 + Indicator_Triangle + Indicator_Triangle - 2, (mRadius >> 1) - Indicator_Triangle + 2, (mRadius >> 1) + Indicator_Triangle - Indicator_Triangle + 2);
        mPaint.setColor(Color.WHITE);
        mCanvas.drawArc(rectF, 70, 40, true, mPaint);

        // mCanvas.drawBitmap(bitmap,-bitmap.getWidth()/2,mRadius/2-bitmap.getHeight()/2,new Paint());

        //-------------------------------------
    }

    private void paintPie(final Canvas mCanvas) {
        if (pieDataList != null && centerTitle != null) {
            float currentAngle = 0.0f;
            for (int j = 0; j < pieDataList.size(); j++) {
                float num = pieDataList.get(j).getValue();
                float needDrawAngle = num * 1.0f / valueSum * 360;
                if (needDrawAngle == 360) {
                    onlycenterTitle = pieDataList.get(j).getValueText();
                    onlycenterTitleTwo = pieDataList.get(j).getValue() + "";
                    if (pieDataList.get(j).getValue() != 0) {
                        onlycenterTitleThree = deci.format(pieDataList.get(j).getValue() * 100 / valueSum) + "%";
                    } else {
                        onlycenterTitleThree = "";
                    }
                }
                if (needDrawAngle >= 0) {
                    mPaint.setColor(Color.parseColor(pieDataList.get(j).getColor()));
                    //每个扇形之间的间隔
                    int arcDistance = 0;
                    if (pieDataList.size() <= 1) {
                        mCanvas.drawArc(oval, currentAngle, needDrawAngle, true, mPaint);
                    } else {
                        mCanvas.drawArc(oval, currentAngle, needDrawAngle - arcDistance, true, mPaint);
                    }
                    mPaint.setColor(Color.WHITE);
                    if (isFirstDraw) {
                        pieDataList.get(j).setStartAngel(currentAngle);
                        pieDataList.get(j).setAngel(needDrawAngle - arcDistance);
                    }
                    mCanvas.drawArc(oval, currentAngle - arcDistance, arcDistance, true, mPaint);
                }
                currentAngle = currentAngle + needDrawAngle;
            }

        }


    }

    //画中间文字标题
    private void drawCenterText(Canvas mCanvas, String text, float x, float y, Paint mPaint) {
        Rect rect = new Rect();
        mTextPaint.setTextSize(sp2px(20));
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        mCanvas.drawText(text, x, y + (rect.height() >> 1), mTextPaint);
    }

    private void drawCenterText(Canvas mCanvas, String text1, String text2, String text3, float x, float y, Paint mPaint) {
        Rect rect = new Rect();
        mTextPaint.setTextSize(sp2px(TextSize));
        mTextPaint.getTextBounds(text1, 0, text1.length(), rect);
        mCanvas.drawText(text1, x, y + (rect.height() >> 1), mTextPaint);

        Rect rect2 = new Rect();
        mTextPaint.getTextBounds(text2, 0, text2.length(), rect2);

        mCanvas.drawText(text2, x, y + (rect.height() >> 1) + rect.height() + 10, mTextPaint);

        Rect rect3 = new Rect();
        mTextPaint.getTextBounds(text3, 0, text3.length(), rect3);

        mCanvas.drawText(text3, x, y + (rect.height() >> 1) + rect.height() + rect2.height() + 30, mTextPaint);
    }

    public interface initFinish {
        void finish();
    }

    public void setData(ArrayList<PieData> arrayList) {
        if (arrayList.isEmpty()) {
            centerTitle = "";
            centerTitleTwo = "";
            centerTitleThree = NoDataText;

            onlycenterTitle = "";
            onlycenterTitleTwo = "";
            onlycenterTitleThree = "";
        }
        this.pieDataList = arrayList;
        //初始化数据
        isFirstDraw = true;
        offsetDegree = 0;
        degree = 0;
        this.valueSum = 0;
        getSum(pieDataList);
        if (valueSum == 0) {
            centerTitle = "";
            centerTitleTwo = "";
            //ishasData = false;
            centerTitleThree = NoDataText;
            TextSize = 12;
        }
        invalidate();
    }

    private void getSum(ArrayList<PieData> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            this.valueSum = this.valueSum + arrayList.get(i).getValue();
        }
    }

    private void TouchCUl(float x, float y) {

        if (picWidth > picHeight) {
            if (picWidth > 2 * mRadius) {
                x -= ((picWidth >> 1) - mRadius);
            }
        }

        //我们触摸点所在的角度从0度开始顺时针计算
        double touchAngle = -1;
        if (x > mRadius && y < mRadius) {
            //第一象限
            Log.e("坐标", "第一象限");
            touchAngle = Math.atan((x - mRadius) / (mRadius - y)) * 180 / Math.PI + 270;
        } else if (x > mRadius && y > mRadius) {
            //第四象限
            Log.e("坐标", "第四象限");
            touchAngle = Math.atan((y - mRadius) / (x - mRadius)) * 180 / Math.PI;
        } else if (x < mRadius && y > mRadius) {
            //第三象限
            Log.e("坐标", "第三象限");
            touchAngle = Math.atan((mRadius - x) / (y - mRadius)) * 180 / Math.PI + 90;
        } else if (x < mRadius && y < mRadius) {
            //第二象限
            Log.e("坐标", "第二象限");
            touchAngle = Math.atan((mRadius - y) / (mRadius - x)) * 180 / Math.PI + 180;
        }
        float center = -1;
        float ro = 0;
        if (pieDataList != null) {
            //在这个循环里寻找触摸点处于哪一个块的范围
            for (int i = 0; i < pieDataList.size(); i++) {
                //这是一种特殊情况起始点在第一象限 末尾在第二象限的情况
                if (pieDataList.get(i).getStartAngel() + pieDataList.get(i).getAngel() > 360) {
                    if (touchAngle > pieDataList.get(i).getStartAngel() && touchAngle < 360 || touchAngle < pieDataList.get(i).getStartAngel() + pieDataList.get(i).getAngel() - 360) {
                        center = pieDataList.get(i).getStartAngel() + pieDataList.get(i).getAngel() / 2;
                        if (center >= 360) {
                            //中心在第二象限 转化为从0度开始的角度
                            center = center - 360;
                        }
                        centerTitle = pieDataList.get(i).getValueText();
                        centerTitleTwo = pieDataList.get(i).getValue() + "";
                        if (pieDataList.get(i).getValue() != 0) {
                            centerTitleThree = deci.format(pieDataList.get(i).getValue() * 100 / valueSum) + "%";
                        } else {
                            centerTitleThree = "";
                        }
                        break;
                    }
                } else if (touchAngle > pieDataList.get(i).getStartAngel() && touchAngle < pieDataList.get(i).getStartAngel() + pieDataList.get(i).getAngel()) {

                    center = pieDataList.get(i).getStartAngel() + pieDataList.get(i).getAngel() / 2;
                    centerTitle = pieDataList.get(i).getValueText();
                    centerTitleTwo = pieDataList.get(i).getValue() + "";
                    if (pieDataList.get(i).getValue() != 0) {

                        centerTitleThree = deci.format(pieDataList.get(i).getValue() * 100 / valueSum) + "%";
                    } else {
                        centerTitleThree = "";
                    }
                    break;
                }

            }

            if (center < 270 && center >= 90) {
                //三四象限
                ro = -center + 90;
            } else if (center >= 270 && center < 360) {
                //第二象限

                ro = 360 - center + 90;
            } else if (center >= 0 && center < 90) {
                //第一象限//
                ro = 90 - center;
            }


            if (ro > 0) {
                for (int i = 0; i < pieDataList.size(); i++) {
                    if (pieDataList.get(i).getStartAngel() + ro < 360) {
                        pieDataList.get(i).setStartAngel(pieDataList.get(i).getStartAngel() + ro);
                    } else {
                        pieDataList.get(i).setStartAngel(pieDataList.get(i).getStartAngel() + ro - 360);
                    }
                }
            } else {
                for (int i = 0; i < pieDataList.size(); i++) {
                    if (pieDataList.get(i).getStartAngel() + ro > 0) {
                        pieDataList.get(i).setStartAngel(pieDataList.get(i).getStartAngel() + ro);
                    } else {
                        pieDataList.get(i).setStartAngel(360 + pieDataList.get(i).getStartAngel() + ro);
                    }
                }
            }
            isFirstDraw = false;
            rotation(ro);
        }

    }

    public void initRO(int p) {

        if (pieDataList.isEmpty())
            return;

        float ro = 0;

        float center = pieDataList.get(p).getStartAngel() + pieDataList.get(p).getAngel() / 2;
        if (pieDataList.get(p).getValueText() != null)
            centerTitle = pieDataList.get(p).getValueText();
        centerTitleTwo = pieDataList.get(p).getValue() + "";
        if (pieDataList.get(p).getValue() != 0) {
            centerTitleThree = deci.format(pieDataList.get(p).getValue() * 100 / valueSum) + "%";
        } else {
            centerTitleThree = "";
        }

        if (center < 270 && center >= 90) {
            //第三四象限
            ro = -center + 90;
        } else if (center >= 270 && center < 360) {
            //第二象限

            ro = 360 - center + 90;
        } else if (center >= 0 && center < 90) {
            //第一象限
            ro = 90 - center;
        }


        if (ro > 0) {
            for (int i = 0; i < pieDataList.size(); i++) {
                if (pieDataList.get(i).getStartAngel() + ro < 360) {
                    pieDataList.get(i).setStartAngel(pieDataList.get(i).getStartAngel() + ro);
                } else {
                    pieDataList.get(i).setStartAngel(pieDataList.get(i).getStartAngel() + ro - 360);
                }
            }
        } else {
            for (int i = 0; i < pieDataList.size(); i++) {
                if (pieDataList.get(i).getStartAngel() + ro > 0) {
                    pieDataList.get(i).setStartAngel(pieDataList.get(i).getStartAngel() + ro);
                } else {
                    pieDataList.get(i).setStartAngel(360 + pieDataList.get(i).getStartAngel() + ro);
                }
            }
        }
        isFirstDraw = false;
        //degree = ro;
        rotation(ro);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (pieDataList.isEmpty() || pieDataList.size() == 1)
            return super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("坐标", "X" + event.getX() + "  " + "Y" + event.getY());
                TouchCUl(event.getRawX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                Log.d("坐标", "X" + event.getX() + "  " + "Y" + event.getY());
                break;
        }
        return super.onTouchEvent(event);
    }

    private void rotation(float degree) {
        if (this.degree != degree) {
            offsetDegree = offsetDegree + degree;
            animateToValue(offsetDegree);
        }
    }


    private void animateToValue(float value) {
        if (valueAnimator == null) {
            valueAnimator = createAnimator(value);
        }
        valueAnimator.setFloatValues(this.degree, value);
        valueAnimator.setDuration(DURATION);

        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
        valueAnimator.start();
    }

    private ValueAnimator createAnimator(float value) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(this.degree, value);
        valueAnimator.setDuration(DURATION);
        valueAnimator.setInterpolator(new OvershootInterpolator());
        valueAnimator.addUpdateListener(this);
        return valueAnimator;
    }


    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    private int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        degree = Float.valueOf(valueAnimator.getAnimatedValue().toString());
        //Debug.e("打印",degree+"");
        invalidate();
    }

    public int getmRadius() {
        return mRadius;
    }

    public void setmRadius(int mRadius) {
        this.mRadius = mRadius;
    }

    public String getCenterTitle() {
        return centerTitle;
    }

    public void setCenterTitle(String centerTitle) {
        this.centerTitle = centerTitle;
    }

    public ArrayList<PieData> getPieDataList() {
        return pieDataList;
    }

    public void setPieDataList(ArrayList<PieData> pieDataList) {
        this.pieDataList = pieDataList;
    }

    public void addData(PieData pieData) {
        pieDataList.add(pieData);
        isFirstDraw = true;
        offsetDegree = 0;
        degree = 0;
        getSum(pieDataList);
        invalidate();
    }

    public void setInitFinish(PieChatView.initFinish initFinish) {
        this.initFinish = initFinish;
    }

    public String getNoDataText() {
        return NoDataText;
    }

    public void setNoDataText(String noDataText) {
        NoDataText = noDataText;
    }

    public int getTextSize() {
        return TextSize;
    }

    public void setTextSize(int textSize) {
        TextSize = textSize;
    }
}
