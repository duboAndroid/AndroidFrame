/*
 * 
 */
package com.ab.view.calendar;


import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ab.view.AbOnItemClickListener;


/**
 * The Class CalendarView.
 */
public class CalendarView extends LinearLayout {
	
	/** The tag. */
	private String TAG = "CalendarView";
	
	/** The context. */
	private Context context;
	
	/** The layout params fw. */
	private LinearLayout.LayoutParams layoutParamsFW = null;
	
	/** The m linear layout header. */
	private LinearLayout mLinearLayoutHeader = null;
	private LinearLayout mLinearLayoutContent = null;
	
	private CalendarHeader  mCalendarHeader = null;
	
	/** The width. */
	private int width = 320;    	
	
	/** The height. */
	private int height = 480;
	
	/**����ͷ���и�*/
	private int headerHeight = 45;
	
	//�и�
	private int rowHeight = 40;
	//ÿ����Ԫ��Ŀ��
	private int cellWidth = 40;
	
	// ���ڱ���
	public static Calendar calStartDate = Calendar.getInstance();
	private Calendar calToday = Calendar.getInstance();
	private Calendar calSelected = null;
	//�ۼ�����
	private Calendar calCalendar = Calendar.getInstance();
	
	private int currentMonth = 0;
	private int currentYear = 0;
	
	//�������ĵ�һ����Ԫ�������
	private int firstDayOfWeek = Calendar.SUNDAY;

	
	//��ǰ��ʾ�ĵ�Ԫ��
	private ArrayList<CalendarCell> mCalendarCells = new ArrayList<CalendarCell>();
	private ArrayList<Boolean> mCalendarCellsRecord = new ArrayList<Boolean>();
    
	private AbOnItemClickListener mOnItemClickListener;
	
    /**
     * Instantiates a new ab grid view.
     *
     * @param context the context
     */
    public CalendarView(Context context) {
    	this(context, null);
    }

	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		
		layoutParamsFW = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		this.setOrientation(LinearLayout.VERTICAL);
		this.setBackgroundColor(Color.rgb(255, 255, 255));
		
		WindowManager wManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);    	
		Display display = wManager.getDefaultDisplay();    	
		width = display.getWidth();    	
		height = display.getHeight();
		
		mLinearLayoutHeader = new LinearLayout(context);
		mLinearLayoutHeader.setLayoutParams(new LayoutParams(width,headerHeight));
		mLinearLayoutHeader.setOrientation(LinearLayout.VERTICAL);
		mCalendarHeader = new CalendarHeader(context);
		mCalendarHeader.setLayoutParams(new LayoutParams(width,headerHeight));
		mLinearLayoutHeader.addView(mCalendarHeader,new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		addView(mLinearLayoutHeader);
		
		
		mLinearLayoutContent = new LinearLayout(context);
		mLinearLayoutContent.setOrientation(LinearLayout.VERTICAL);
		addView(mLinearLayoutContent);
		
		cellWidth = (width-20)/7;
		rowHeight = cellWidth;
		
		//��ʼ��ѡ�н���
		calSelected = Calendar.getInstance();
		initRow();
		initStartDateForMonth();
		initCalendar();
	}
	
	public void initRow(){
		mLinearLayoutContent.removeAllViews();
		mCalendarCells.clear();
		for (int iRow = 0; iRow < 6; iRow++) {
			LinearLayout mLinearLayoutRow = new LinearLayout(context);
			mLinearLayoutRow.setLayoutParams(new LayoutParams(width,rowHeight));
			mLinearLayoutRow.setOrientation(LinearLayout.HORIZONTAL);
			for (int iDay = 0; iDay < 7; iDay++) {
				CalendarCell dayCell = new CalendarCell(context,(iRow*7)+iDay,cellWidth,rowHeight);
				dayCell.setOnItemClickListener(mOnDayCellClick);
				mLinearLayoutRow.addView(dayCell);
				mCalendarCells.add(dayCell);
			}
			mLinearLayoutContent.addView(mLinearLayoutRow);
		}
	}
	
	
	/**
	 * ���������������ϵ����ڶ��Ǵ����տ�ʼ�ģ������һ����Ԫ�������
	 */
	private void initStartDateForMonth() {
		calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
		//��ȡ��ǰ��
		currentMonth = calStartDate.get(Calendar.MONTH);
		currentYear = calStartDate.get(Calendar.YEAR);
		
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.HOUR_OF_DAY, 0);
		calStartDate.set(Calendar.MINUTE, 0);
		calStartDate.set(Calendar.SECOND, 0);
		
		int iDay = 0;
		int iStartDay = firstDayOfWeek;
		
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		
		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);
	}
	
	/**
	 * ��������������
	 */
	private void updateCalendar() {
		final boolean bIsSelection = (calSelected.getTimeInMillis() != 0);
		final int iSelectedYear = calSelected.get(Calendar.YEAR);
		final int iSelectedMonth = calSelected.get(Calendar.MONTH);
		final int iSelectedDay = calSelected.get(Calendar.DAY_OF_MONTH);
		
		boolean isThisMonth  = false;
		//�����ڵ�ǰ�£���ȥ��Ĭ��ѡ�е�1��
		if (calToday.get(Calendar.YEAR) == iSelectedYear) {
			if (calToday.get(Calendar.MONTH) == iSelectedMonth) {
				isThisMonth = true;
			}
		}
		
		calCalendar.setTimeInMillis(calStartDate.getTimeInMillis());
		for (int i = 0; i < mCalendarCells.size(); i++) {
			CalendarCell dayCell = mCalendarCells.get(i);
			//
			final int iYear = calCalendar.get(Calendar.YEAR);
			final int iMonth = calCalendar.get(Calendar.MONTH);
			final int iDay = calCalendar.get(Calendar.DAY_OF_MONTH);
			final int iDayOfWeek = calCalendar.get(Calendar.DAY_OF_WEEK);
			

			// �ж��Ƿ���
			boolean bToday = false;
			// �Ƿ�ѡ��
			boolean bSelected = false;
			// check holiday
			boolean bHoliday = false;
			// �Ƿ��м�¼
			boolean hasRecord = false;
			
			if (calToday.get(Calendar.YEAR) == iYear) {
				if (calToday.get(Calendar.MONTH) == iMonth) {
					if (calToday.get(Calendar.DAY_OF_MONTH) == iDay) {
						bToday = true;
					}
				}
			}
			
			if ((iDayOfWeek == Calendar.SATURDAY) || (iDayOfWeek == Calendar.SUNDAY)){
				bHoliday = true;
			}
			if ((iMonth == Calendar.JANUARY) && (iDay == 1)){
				bHoliday = true;
			}

			if (bIsSelection){
				if ((iSelectedDay == iDay) && (iSelectedMonth == iMonth) && (iSelectedYear == iYear)) {
					bSelected = true;
				}else{
					bSelected = false;
				}
			}
			
			if(iDay==1 && isThisMonth){
				bSelected = false;
			}

			dayCell.setThisCellDate(iYear, iMonth, iDay, bToday,bSelected, bHoliday,currentMonth, hasRecord);

			calCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		invalidate();
	}
	
	/**
	 * ��������������
	 */
	private void initCalendar() {
		final boolean bIsSelection = (calSelected.getTimeInMillis() != 0);
		final int iSelectedYear = calSelected.get(Calendar.YEAR);
		final int iSelectedMonth = calSelected.get(Calendar.MONTH);
		final int iSelectedDay = calSelected.get(Calendar.DAY_OF_MONTH);
		
		calCalendar.setTimeInMillis(calStartDate.getTimeInMillis());
		for (int i = 0; i < mCalendarCells.size(); i++) {
			CalendarCell dayCell = mCalendarCells.get(i);
			//
			final int iYear = calCalendar.get(Calendar.YEAR);
			final int iMonth = calCalendar.get(Calendar.MONTH);
			final int iDay = calCalendar.get(Calendar.DAY_OF_MONTH);
			final int iDayOfWeek = calCalendar.get(Calendar.DAY_OF_WEEK);
			

			// �ж��Ƿ���
			boolean bToday = false;
			// �Ƿ�ѡ��
			boolean bSelected = false;
			// check holiday
			boolean bHoliday = false;
			// �Ƿ��м�¼
			boolean hasRecord = false;
			
			if (calToday.get(Calendar.YEAR) == iYear) {
				if (calToday.get(Calendar.MONTH) == iMonth) {
					if (calToday.get(Calendar.DAY_OF_MONTH) == iDay) {
						bToday = true;
					}
				}
			}
			
			if ((iDayOfWeek == Calendar.SATURDAY) || (iDayOfWeek == Calendar.SUNDAY)){
				bHoliday = true;
			}
			if ((iMonth == Calendar.JANUARY) && (iDay == 1)){
				bHoliday = true;
			}

			if (bIsSelection){
				if ((iSelectedDay == iDay) && (iSelectedMonth == iMonth) && (iSelectedYear == iYear)) {
					bSelected = true;
				}else{
					bSelected = false;
				}
			}
			

			dayCell.setThisCellDate(iYear, iMonth, iDay, bToday,bSelected, bHoliday,currentMonth, hasRecord);

			calCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		invalidate();
	}
	

	public void setOnItemClickListener(
			AbOnItemClickListener mAbOnItemClickListener) {
		this.mOnItemClickListener = mAbOnItemClickListener;
	}

	public void setHeaderHeight(int height) {
		headerHeight = height;
		mLinearLayoutHeader.removeAllViews();
		mCalendarHeader.setLayoutParams(new LayoutParams(width,headerHeight));
		mLinearLayoutHeader.addView(mCalendarHeader,new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		this.invalidate();
		
	}
	
	public void setHeaderTextSize(int size) {
		mCalendarHeader.setTextSize(size);
		this.invalidate();
	}
	
	public void rebuildCalendar(Calendar calendar) {
		//��ʼ��ѡ��1��
		calSelected.setTimeInMillis(calendar.getTimeInMillis());
		initRow();
		initStartDateForMonth();
		updateCalendar();
	}
    
    /**
     * ��������������¼�
     */
 	private AbOnItemClickListener mOnDayCellClick = new AbOnItemClickListener(){

		@Override
		public void onClick(int position) {
			CalendarCell mCalendarCell = mCalendarCells.get(position);
			if(mCalendarCell.isActiveMonth()){
				calSelected.setTimeInMillis(mCalendarCell.getThisCellDate().getTimeInMillis());
	            initRow();
	    		initStartDateForMonth();
	            initCalendar();
	 			if(mOnItemClickListener!=null){
	 				mOnItemClickListener.onClick(position);
	 			}
			}
		}
 		
 	};
 	
 	/**
 	 * ���������ñ��ⱳ��
 	 * @param resid
 	 */
 	public void setHeaderBackgroundResource(int resid){
 		mCalendarHeader.setHeaderBackgroundResource(resid);
 	}
 	
 	/**
 	 * ����������������ȡѡ�������
 	 * @param position
 	 */
 	public String getSelectDate(int position){
 		CalendarCell mCalendarCell = mCalendarCells.get(position);
 		Calendar calendar = mCalendarCell.getThisCellDate();
 		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DATE);
 		return year+"-"+month+"-"+day;
 	}

}

