package com.example.administrator.myapplication.SeekBarTest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.example.administrator.myapplication.R;


public class TextThumbSeekBar extends AppCompatSeekBar {

 private int mThumbSize;//绘制滑块宽度
 private TextPaint mTextPaint;//绘制文本的大小
 private int mSeekBarMin=0;//滑块开始值

 private Rect textbounds = new Rect();

 public TextThumbSeekBar(Context context) {
  this(context, null);
 }

 public TextThumbSeekBar(Context context, AttributeSet attrs) {
  this(context, attrs, android.R.attr.seekBarStyle);
 }

 public TextThumbSeekBar(Context context,AttributeSet attrs,int defStyleAttr){
  super(context,attrs,defStyleAttr);
  mThumbSize=20;
  mTextPaint = new TextPaint();
  mTextPaint.setColor(Color.WHITE);
  mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_16_sp));
  mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
  mTextPaint.setTextAlign(Paint.Align.CENTER);
 }



 @Override
 protected synchronized void onDraw(Canvas canvas) {
  super.onDraw(canvas);
  int unsignedMin = mSeekBarMin < 0 ? mSeekBarMin * -1 : mSeekBarMin;
  String progressText = String.valueOf(getProgress()+unsignedMin);
  mTextPaint.getTextBounds(progressText, 0, progressText.length(), textbounds);

  int leftPadding = getPaddingLeft() - getThumbOffset();
  int rightPadding = getPaddingRight() - getThumbOffset();
  int width = getWidth() - leftPadding - rightPadding;
  float progressRatio = (float) getProgress() / getMax();
  float thumbOffset = mThumbSize * (.5f - progressRatio);
  float thumbX = progressRatio * width + leftPadding + thumbOffset;
  float thumbY = getHeight() / 2f -getThumb().getIntrinsicHeight()/2;
  //float thumbY = -getHeight()  + bounds.height() ;
  canvas.drawText(progressText, thumbX, thumbY, mTextPaint);
 }

 public void setMix(int min){
  mSeekBarMin=min;
 }
}