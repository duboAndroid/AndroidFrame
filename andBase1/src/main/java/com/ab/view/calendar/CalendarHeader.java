package com.ab.view.calendar;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;

import com.ab.util.AbGraphical;

/**
 * Copyright (c) 2012 All rights reserved
 * 名称：CalendarHeader.java 
 * 描述：日历控件头部绘制类
 * @author zhaoqp
 * @date：2013-7-9 下午2:07:52
 * @version v1.0
 */
public class CalendarHeader extends View {
	
	/** The tag. */
	private String TAG = "CalendarHeader";
	
	private final Paint mPaint; 
	
	private RectF rect = new RectF();
	
	//星期几
	private int weekDay = Calendar.SUNDAY;
	
	//星期的数据
	private String[] dayName = new String[10];

	/** The width. */
	private int width = 320;    	
	
	/** The height. */
	private int height = 480;
	
	/**每个单元格的宽度*/
	private int cellWidth = 40;
	
	/**文字颜色*/
	private int defaultTextColor = Color.rgb(86, 86, 86);
	
	/**特别文字颜色*/
	private int specialTextColor = Color.rgb(240, 140, 26);
	
	/**字体大小*/
	private int defaultTextSize = 25;
	
	/**字体是否加粗*/
	private boolean defaultTextBold = false;
	
	/**
	 * 是否有设置头部背景
	 */
	private boolean hasBg = false;
	
	/**
	 * 日历头
	 * @param context
	 */
	public CalendarHeader(Context context) {
		this(context, null);
	}
	
	public CalendarHeader(Context context, AttributeSet attributeset) {
		super(context);
		dayName[Calendar.SUNDAY] = "周日";
		dayName[Calendar.MONDAY] = "周一";
		dayName[Calendar.TUESDAY] = "周二";
		dayName[Calendar.WEDNESDAY] = "周三";
		dayName[Calendar.THURSDAY] = "周四";
		dayName[Calendar.FRIDAY] = "周五";
		dayName[Calendar.SATURDAY] = "周六";
		mPaint = new Paint(); 
        mPaint.setColor(defaultTextColor);
        mPaint.setAntiAlias(true); 
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextSize(defaultTextSize);
        
        WindowManager wManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);    	
		Display display = wManager.getDefaultDisplay();    	
		width = display.getWidth();    	
		height = display.getHeight();
		cellWidth = (width-20)/7;
	}
	
	/**
 	 * 描述：设置背景
 	 * @param resid
 	 */
 	public void setHeaderBackgroundResource(int resid){
 		setBackgroundResource(resid);
 		hasBg = true;
 	}
	
	/**
	 * 描述：文字大小
	 */
	public int getTextSize() {
		return defaultTextSize;
	}

	/**
	 * 描述：设置文字大小
	 */
	public void setTextSize(int mTextSize) {
		this.defaultTextSize = mTextSize;
		mPaint.setTextSize(defaultTextSize);
		this.invalidate();
	}  

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if(!hasBg){
		   canvas.drawColor(Color.WHITE);
		   //设置矩形大小
		   rect.set(0, 0, this.getWidth(),this.getHeight());
		   rect.inset(0.5f,0.5f);
		}
		// 绘制日历头部
		drawDayHeader(canvas);
		
	}

	private void drawDayHeader(Canvas canvas) {
		// 写入日历头部，设置画笔参数
		if(!hasBg){
			// 画矩形，并设置矩形画笔的颜色
			mPaint.setColor(Color.rgb(150, 195, 70));
			canvas.drawRect(rect, mPaint);
		}
		
		if(defaultTextBold){
			mPaint.setFakeBoldText(true);
		}
		mPaint.setColor(defaultTextColor);
		
		for (int iDay = 1; iDay < 8; iDay++) {
			if(iDay==1 || iDay==7){
				mPaint.setColor(specialTextColor);
			}
			// draw day name
			final String sDayName = getWeekDayName(iDay);
			
			TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
	        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
	        mTextPaint.setTextSize(defaultTextSize);
	        FontMetrics fm  = mTextPaint.getFontMetrics();
	        //得到行高
	        int textHeight = (int)Math.ceil(fm.descent - fm.ascent);
	        int textWidth = (int)AbGraphical.getStringWidth(sDayName,mTextPaint);
			
			final int iPosX = (int) rect.left +cellWidth*(iDay-1)+(cellWidth-textWidth)/2;
			final int iPosY = (int) (this.getHeight()
					- (this.getHeight() - textHeight) / 2 - mPaint
					.getFontMetrics().bottom);
			canvas.drawText(sDayName, iPosX, iPosY, mPaint);
			mPaint.setColor(defaultTextColor);
		}
		
	}
	
	/**
	 * 描述：获取星期的文字描述
	 * @param calendarDay
	 */
	public String getWeekDayName(int calendarDay) {
		return dayName[calendarDay];
	}
	
}