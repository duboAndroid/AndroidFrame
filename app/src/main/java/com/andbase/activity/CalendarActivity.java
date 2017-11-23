package com.andbase.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.util.AbStrUtil;
import com.ab.view.AbOnItemClickListener;
import com.ab.view.AbPlayView;
import com.ab.view.calendar.CalendarView;
import com.andbase.R;

public class CalendarActivity extends AbActivity {
	
    private CalendarView mCalendarView =  null;
	private List<String> monthList = null;
	private int currentMonthIndex = 0;
	private TextView monthText = null;
	private String currentMonth = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.calendar);
        this.setTitleText(R.string.calendar_name);
        this.setLogo(R.drawable.button_selector_back);
		this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
        
        initTitleRightLayout();
        
        LinearLayout mLinearLayout = (LinearLayout)this.findViewById(R.id.layout01);
		mCalendarView = new  CalendarView(this);
		mLinearLayout.addView(mCalendarView);
		initTitleRightLayout(); 
		
		mCalendarView.setHeaderHeight(45);
		mCalendarView.setHeaderTextSize(20);
		
		mCalendarView.setBackgroundResource(R.drawable.calendar);
		mCalendarView.setHeaderBackgroundResource(R.drawable.week_bg);
		mCalendarView.setOnItemClickListener(new AbOnItemClickListener() {
			
			@Override
			public void onClick(int position) {
				String date = mCalendarView.getSelectDate(position);
				showToast("点击了"+position+"值："+date);
			}
		});
		
		Calendar calendar = Calendar.getInstance();
		monthList = new ArrayList<String>();
		//
		int curYear = calendar.get(Calendar.YEAR); // 得到系统年份
		int curMonth = calendar.get(Calendar.MONTH) + 1; // 得到系统月份
		
		int preYear = curYear-1;
		for (int i = 1; i < 12; i++) {
			monthList.add(preYear + "-" + AbStrUtil.strFormat2(String.valueOf(i)));
		}
		
		for (int i = 1; i <= curMonth; i++) {
			monthList.add(curYear + "-" + AbStrUtil.strFormat2(String.valueOf(i)));
		}
		
		currentMonthIndex = monthList.size()-1;
		currentMonth = monthList.get(currentMonthIndex);
		
		monthText = (TextView)findViewById(R.id.monthText);
		monthText.setText(currentMonth);
		
		Button leftBtn = (Button)findViewById(R.id.leftBtn);
		Button rightBtn = (Button)findViewById(R.id.rightBtn);
		
        leftBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				currentMonthIndex--;
				if(currentMonthIndex>=0){
					currentMonth =  monthList.get(currentMonthIndex);
					monthText.setText(currentMonth);
					String [] yearmonth = currentMonth.split("-");
					Calendar cal_select = Calendar.getInstance();
					cal_select.set(Calendar.YEAR, Integer.parseInt(yearmonth[0]));
					cal_select.set(Calendar.MONTH, Integer.parseInt(yearmonth[1])-1);
					cal_select.set(Calendar.DAY_OF_MONTH, 1);
					mCalendarView.rebuildCalendar(cal_select);
					//showDialog(0);
					//netGet.downloadBeforeClean(item1);
				}else{
					currentMonthIndex++;
				}
			}
		});
		
		rightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				currentMonthIndex++;
				if(currentMonthIndex<monthList.size()){
					currentMonth =  monthList.get(currentMonthIndex);
					monthText.setText(currentMonth);
					String [] yearmonth = currentMonth.split("-");
					Calendar cal_select = Calendar.getInstance();
					cal_select.set(Calendar.YEAR, Integer.parseInt(yearmonth[0]));
					cal_select.set(Calendar.MONTH, Integer.parseInt(yearmonth[1])-1);
					cal_select.set(Calendar.DAY_OF_MONTH, 1);
					mCalendarView.rebuildCalendar(cal_select);
					//mActivity.showDialog(0);
					//netGet.downloadBeforeClean(item1);
				}else{
					currentMonthIndex--;
				}
			}
		});
        
    }
    

	private void initTitleRightLayout(){
    	clearRightView();
    }

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	public void onPause() {
		super.onPause();

	}
   

}


