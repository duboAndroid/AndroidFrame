package com.ab.view.calendar;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout.LayoutParams;

import com.ab.view.AbOnItemClickListener;

/**
 * 
 * Copyright (c) 2012 All rights reserved
 * ���ƣ�CalendarCell.java 
 * �����������ؼ���Ԫ�������
 * @author zhaoqp
 * @date��2013-7-9 ����3:54:16
 * @version v1.0
 */
public class CalendarCell extends View {
	
	// �����С
	private int textSize = 22;
	
	// ����Ԫ��
	private AbOnItemClickListener mOnItemClickListener;
	private Paint pt = new Paint();
	private RectF rect = new RectF();
	
	//��ʾ������
	private String textDateValue = "";

	// ��ǰ����
	private int iDateYear = 0;
	private int iDateMonth = 0;
	private int iDateDay = 0;

	// ��������
	private boolean isSelected = false;
	private boolean isActiveMonth = false;
	private boolean isToday = false;
	private boolean bTouchedDown = false;
	private boolean isHoliday = false;
	private boolean hasRecord = false;
	
	//��ǰcell�����
	private int position = 0;

	public static int ANIM_ALPHA_DURATION = 100;
	
	/*��ѡ�е�cell��ɫ*/
	private int selectCellColor = Color.rgb(150, 195, 70);
	
	/*��󱳾���ɫ*/
	private int bgColor = Color.rgb(163,163, 163);
	
	/*������ɫ*/
	private int numberColor = Color.rgb(86, 86, 86);
	
	/*cell������ɫ*/
	private int cellColor = Color.WHITE;
	
	/*�Ǳ��µ�������ɫ*/
	private int notActiveMonthColor = Color.rgb(178, 178, 178);
	
	/*����cell��ɫ*/
	private int todayColor = Color.rgb(150, 200, 220);
	

	// ���캯��
	public CalendarCell(Context context, int position,int iWidth, int iHeight) {
		super(context);
		setFocusable(true);
		setLayoutParams(new LayoutParams(iWidth, iHeight));
		this.position = position;
	}

	/**
	 * ��������ȡ���Cell������
	 * @return
	 */
	public Calendar getThisCellDate() {
		Calendar calDate = Calendar.getInstance();
		calDate.clear();
		calDate.set(Calendar.YEAR, iDateYear);
		calDate.set(Calendar.MONTH, iDateMonth);
		calDate.set(Calendar.DAY_OF_MONTH, iDateDay);
		return calDate;
	}

	/**
	 * 
	 * �������������Cell������
	 * @param iYear
	 * @param iMonth
	 * @param iDay
	 * @param bToday
	 * @param bHoliday
	 * @param iActiveMonth
	 * @param hasRecord
	 */
	public void setThisCellDate(int iYear, int iMonth, int iDay, Boolean isToday,Boolean isSelected,
			Boolean isHoliday, int isActiveMonth, boolean hasRecord) {
		iDateYear = iYear;
		iDateMonth = iMonth;
		iDateDay = iDay;

		this.textDateValue = Integer.toString(iDateDay);
		this.isActiveMonth = (iDateMonth == isActiveMonth);
		this.isToday = isToday;
		this.isHoliday = isHoliday;
		this.hasRecord = hasRecord;
		this.isSelected = isSelected;
	}

	/**
	 * ���������ػ��Ʒ���
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawColor(bgColor);
		rect.set(0, 0, this.getWidth(), this.getHeight());
		rect.inset(0.5f, 0.5f);

		final boolean bFocused = IsViewFocused();

		drawDayView(canvas, bFocused);
		drawDayNumber(canvas);
	}

	public boolean IsViewFocused() {
		return (this.isFocused() || bTouchedDown);
	}

	/**
	 * ������������������
	 * @param canvas
	 * @param bFocused
	 */
	private void drawDayView(Canvas canvas, boolean bFocused) {

		pt.setColor(getCellColor());
		canvas.drawRect(rect, pt);

		if (hasRecord) {
			createReminder(canvas,Color.RED);
		}
		
	}

	/**
	 * ���������������е�����
	 * @param canvas
	 */
	public void drawDayNumber(Canvas canvas) {
		// draw day number
		pt.setTypeface(null);
		pt.setAntiAlias(true);
		pt.setShader(null);
		pt.setFakeBoldText(true);
		pt.setTextSize(textSize);
		pt.setColor(numberColor);
		pt.setUnderlineText(false);
		
		if (!isActiveMonth){
			pt.setColor(notActiveMonthColor);
		}

		final int iPosX = (int) rect.left + ((int) rect.width() >> 1) - ((int) pt.measureText(textDateValue) >> 1);
		final int iPosY = (int) (this.getHeight() - (this.getHeight() - getTextHeight()) / 2 - pt.getFontMetrics().bottom);
		canvas.drawText(textDateValue, iPosX, iPosY, pt);
	}

	/**
	 * �������õ�����߶�
	 */
	private int getTextHeight() {
		return (int) (-pt.ascent() + pt.descent());
	}

	/**
	 * �����������������ز�ͬ��ɫֵ
	 * @param bHoliday
	 * @param bToday
	 */
	public int getCellColor() {
		if (isToday){
			return todayColor;
		}
		
		if (isSelected){
			return selectCellColor;
		}
		
		//������ĩ�����ⱳ��ɫ
		if (isHoliday){
		   return cellColor;
		}
		
		//Ĭ���ǰ�ɫ�ĵ�Ԫ��
		return cellColor;
	}

	/**
	 * �����������Ƿ�ѡ��
	 */
	@Override
	public void setSelected(boolean bEnable) {
		if (this.isSelected != bEnable) {
			this.isSelected = bEnable;
			this.invalidate();
		}
	}

	/**
	 * 
	 * ���������õ���¼�
	 * @param onItemClickListener
	 */
	public void setOnItemClickListener(AbOnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

	/**
	 * ������ִ�е���¼�
	 */
	public void doItemClick() {
		if (mOnItemClickListener != null){
			mOnItemClickListener.onClick(position);
	    }
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean bHandled = false;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			bHandled = true;
			bTouchedDown = true;
			invalidate();
			startAlphaAnimIn(CalendarCell.this);
		}
		if (event.getAction() == MotionEvent.ACTION_CANCEL) {
			bHandled = true;
			bTouchedDown = false;
			invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			bHandled = true;
			bTouchedDown = false;
			invalidate();
			doItemClick();
		}
		return bHandled;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean bResult = super.onKeyDown(keyCode, event);
		if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
				|| (keyCode == KeyEvent.KEYCODE_ENTER)) {
			doItemClick();
		}
		return bResult;
	}

	/**
	 * ������������͸���Ƚ���
	 * @param view
	 */
	public static void startAlphaAnimIn(View view) {
		AlphaAnimation anim = new AlphaAnimation(0.5F, 1);
		anim.setDuration(ANIM_ALPHA_DURATION);
		anim.startNow();
		view.startAnimation(anim);
	}

	/**
	 * 
	 * �������м�¼ʱ������
	 * @param canvas
	 * @param Color
	 */
	public void createReminder(Canvas canvas, int Color) {
		pt.setUnderlineText(true);
		pt.setStyle(Paint.Style.FILL_AND_STROKE);
		pt.setColor(Color);
		Path path = new Path();
		path.moveTo(rect.right - rect.width() / 4, rect.top);
		path.lineTo(rect.right, rect.top);
		path.lineTo(rect.right, rect.top + rect.width() / 4);
		path.lineTo(rect.right - rect.width() / 4, rect.top);
		path.close();
		canvas.drawPath(path, pt);
		pt.setUnderlineText(false);
	}

	/**
	 * 
	 * �������Ƿ�Ϊ�����
	 * @return
	 */
	public boolean isActiveMonth() {
		return isActiveMonth;
	}
	
	
	
	
	
}