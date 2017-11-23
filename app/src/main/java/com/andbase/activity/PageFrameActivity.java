package com.andbase.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.ab.activity.AbActivity;
import com.ab.adapter.AbFragmentPagerAdapter;
import com.andbase.R;
import com.andbase.global.MyApplication;

public class PageFrameActivity extends AbActivity {
	private MyApplication application;
	private ViewPager mTabPager;
	private ArrayList<Fragment> pagerItemList = null;
	private ImageView mTab1,mTab2,mTab3,mTab4,mTab5;
	private ImageView mTabImg;// 动画图片
	private int currIndex = 0;// 当前页卡编号
	private int zero = 0;     // 动画图片偏移量
	private int one;          //单个水平动画位移
	private int two;
	private int three;
	private int four;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.page_frame);
		application = (MyApplication) abApplication;
		this.setTitleText(R.string.page_frame);
	    this.setLogo(R.drawable.button_selector_back);
	    this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
		initTitleRightLayout();

		mTabPager = (ViewPager) findViewById(R.id.vPager);
		mTabPager.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
			
		});



		Fragment1 page1 = new Fragment1();
		Fragment2 page2 = new Fragment2();
		Fragment3 page3 = new Fragment3();
		Fragment4 page4 = new Fragment4();
		Fragment5 page5 = new Fragment5();
		pagerItemList = new ArrayList<Fragment>();
		pagerItemList.add(page1);
		pagerItemList.add(page2);
		pagerItemList.add(page3);
		pagerItemList.add(page4);
		pagerItemList.add(page5);
		FragmentManager mFragmentManager = this.getSupportFragmentManager();
		AbFragmentPagerAdapter mFragmentPagerAdapter = new AbFragmentPagerAdapter(
				mFragmentManager, pagerItemList);
		mTabPager.setAdapter(mFragmentPagerAdapter);
		
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mTabImg = (ImageView) findViewById(R.id.img_tab_now);
		mTab1 = (ImageView) findViewById(R.id.img_1);
		mTab2 = (ImageView) findViewById(R.id.img_2);
		mTab3 = (ImageView) findViewById(R.id.img_3);
		mTab4 = (ImageView) findViewById(R.id.img_4);
		mTab5 = (ImageView) findViewById(R.id.img_5);
		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
		mTab5.setOnClickListener(new MyOnClickListener(4));
		
		//获取屏幕的分辨率，以计算偏移量
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int s = width/5;  
		one = s;
		two = one * 2;
		three = one *3;
		four = one *4;
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int arg0) {
			Animation anim = null;
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(R.drawable.home_f));
				if (currIndex == 1) {
					anim = new TranslateAnimation(one, 0, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.take_notes_n));
				}else if (currIndex == 2) {
					anim = new TranslateAnimation(two, 0, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.consult_n));
				}else if (currIndex == 3) {
					anim = new TranslateAnimation(three, 0, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.information_n));
				}else if (currIndex == 4) {
					anim = new TranslateAnimation(four, 0, 0, 0);
					mTab5.setImageDrawable(getResources().getDrawable(R.drawable.m_more_n));
				}
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(R.drawable.take_notes_f));
				if (currIndex == 0) {
					anim = new TranslateAnimation(zero, one, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.home_n));
				}else if (currIndex == 2) {
					anim = new TranslateAnimation(two, one, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.consult_n));
				}else if (currIndex == 3) {
					anim = new TranslateAnimation(three, one, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.information_n));
				}else if (currIndex == 4) {
					anim = new TranslateAnimation(four, one, 0, 0);
					mTab5.setImageDrawable(getResources().getDrawable(R.drawable.m_more_n));
				}
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(R.drawable.consult_f));
				if (currIndex == 0) {
					anim = new TranslateAnimation(zero, two, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.home_n));
				}else if (currIndex == 1) {
					anim = new TranslateAnimation(one, two, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.take_notes_n));
				}else if (currIndex == 3) {
					anim = new TranslateAnimation(three, two, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.information_n));
				}else if (currIndex == 4) {
					anim = new TranslateAnimation(four, two, 0, 0);
					mTab5.setImageDrawable(getResources().getDrawable(R.drawable.m_more_n));
				}
				break;
			case 3:
				mTab4.setImageDrawable(getResources().getDrawable(R.drawable.information_f));
				if (currIndex == 0) {
					anim = new TranslateAnimation(zero, three, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.home_n));
				}else if (currIndex == 1) {
					anim = new TranslateAnimation(one, three, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.take_notes_n));
				}else if (currIndex == 2) {
					anim = new TranslateAnimation(two, three, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.consult_n));
				}else if (currIndex == 4) {
					anim = new TranslateAnimation(four, three, 0, 0);
					mTab5.setImageDrawable(getResources().getDrawable(R.drawable.m_more_n));
				}
				break;
			case 4:
				mTab5.setImageDrawable(getResources().getDrawable(R.drawable.m_more_f));
				if (currIndex == 0) {
					anim = new TranslateAnimation(zero, four, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.home_n));
				}else if (currIndex == 1) {
					anim = new TranslateAnimation(one, four, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.take_notes_n));
				}else if (currIndex == 2) {
					anim = new TranslateAnimation(two, four, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.consult_n));
				}else if (currIndex == 3) {
					anim = new TranslateAnimation(three, four, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.information_n));
				}
				break;
			}
			currIndex = arg0;
			//图片停在偏移的位置
			anim.setFillAfter(true);
			anim.setDuration(150);
			mTabImg.startAnimation(anim);
		}
		
	}
	
	public class MyOnClickListener implements OnClickListener{
		int index = 0;
		public MyOnClickListener(int i) {
			this.index = i;
		}
		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	}

	private void initTitleRightLayout() {

	}
}