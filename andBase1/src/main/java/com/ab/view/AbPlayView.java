/*
 * 
 */
package com.ab.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ab.adapter.AbViewPagerAdapter;
import com.ab.global.AbAppData;
import com.ab.util.AbFileUtil;

// TODO: Auto-generated Javadoc
/**
 * 名称：AbPlayView
 * 描述：可播放显示的View.
 *
 * @author zhaoqp
 * @date 2011-11-28
 * @version
 */
public class AbPlayView extends LinearLayout {
	
	/** The tag. */
	private static String TAG = "AbPageView";
	
	/** The Constant D. */
	private static final boolean D = AbAppData.DEBUG;

	/** The context. */
	private Context context;

	/** The m view pager. */
	private ViewPager mViewPager;

	/** The page line layout. */
	private LinearLayout pageLineLayout;

	/** The i. */
	private int count, i;

	/** The hide image. */
	private Bitmap displayImage, hideImage;
	
	/** The m on item click listener. */
	private AbOnItemClickListener mOnItemClickListener;
	
	/** The m ab change listener. */
	private AbOnChangeListener mAbChangeListener;
	
	/** The m ab scrolled listener. */
	private AbOnScrolledListener mAbScrolledListener;
	
	/** The layout params ff. */
	public LinearLayout.LayoutParams layoutParamsFF = null;
	
	/** The layout params fw. */
	public LinearLayout.LayoutParams layoutParamsFW = null;
	
	/** The layout params wf. */
	public LinearLayout.LayoutParams layoutParamsWF = null;
	
	/** The layout params ww. */
	public LinearLayout.LayoutParams layoutParamsWW = null;
	
	/** The m list views. */
	private ArrayList<View> mListViews = null;
	
	/** The m ab view pager adapter. */
	private AbViewPagerAdapter mAbViewPagerAdapter = null;
	
	/** The width. */
	private int width = 320;    	
	
	/** The height. */
	private int height = 480;
	
	/** The page line horizontal gravity. */
	private int pageLineHorizontalGravity = Gravity.RIGHT;

	/**
	 * Instantiates a new ab play view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public AbPlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		layoutParamsFF = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		layoutParamsFW = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWF = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
		layoutParamsWW = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		this.setOrientation(LinearLayout.VERTICAL);
		this.setBackgroundColor(Color.rgb(255, 255, 255));
		
		RelativeLayout mRelativeLayout = new RelativeLayout(context);
		
		mViewPager = new ViewPager(context);
		//位置的点
		pageLineLayout = new LinearLayout(context);
		
		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        lp1.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        lp1.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
		mRelativeLayout.addView(mViewPager,lp1);
		
		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
		mRelativeLayout.addView(pageLineLayout,lp2);
		addView(mRelativeLayout,layoutParamsFW);
		
		displayImage = AbFileUtil.getBitmapFormSrc("image/play_display.png");
		hideImage = AbFileUtil.getBitmapFormSrc("image/play_hide.png");
		
		mListViews = new ArrayList<View>();
		mAbViewPagerAdapter = new AbViewPagerAdapter(context,mListViews);
		mViewPager.setAdapter(mAbViewPagerAdapter);
		mViewPager.setFadingEdgeLength(0);
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				makesurePosition();
				onPageSelectedCallBack(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				onPageScrolledCallBack(position);
			}

		});
		
		mViewPager.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			}
			
		});
		WindowManager wManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);    	
		Display display = wManager.getDefaultDisplay();    	
		width = display.getWidth();    	
		height = display.getHeight();
	}

	/**
	 * Creat index.
	 */
	public void creatIndex() {
		pageLineLayout.removeAllViews();
		pageLineLayout.setHorizontalGravity(pageLineHorizontalGravity);
		count = mListViews.size();
		for (int j = 0; j < count; j++) {
			ImageView imageView = new ImageView(context);
			layoutParamsWW.setMargins(2, 2, 2, 2);
			imageView.setLayoutParams(layoutParamsWW);
			if (j == 0) {
				imageView.setImageBitmap(displayImage);
			} else {
				imageView.setImageBitmap(hideImage);
			}
			pageLineLayout.addView(imageView, j);
		}
	}


	/**
	 * Makesure position.
	 */
	public void makesurePosition() {
		i = mViewPager.getCurrentItem();
		for (int j = 0; j < count; j++) {
			if (i == j) {
				((ImageView)pageLineLayout.getChildAt(i)).setImageBitmap(displayImage);
			} else {
				((ImageView)pageLineLayout.getChildAt(j)).setImageBitmap(hideImage);
			}
		}
	}
	
	/**
	 * Sets the on item click listener.
	 *
	 * @param onItemClickListener the new on item click listener
	 */
	public void setOnItemClickListener(AbOnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}
	
	/**
	 * 描述：添加可播放视图.
	 *
	 * @param view the view
	 */
	public void addView(View view){
		mListViews.add(view);
		mAbViewPagerAdapter.notifyDataSetChanged();
		creatIndex();    
	}
	
	/**
	 * 描述：添加可播放视图列表.
	 *
	 * @param views the views
	 */
	public void addViews(List<View> views){
		mListViews.addAll(views);
		mAbViewPagerAdapter.notifyDataSetChanged();
		creatIndex();
	}
	
	/**
	 * 描述：删除可播放视图.
	 *
	 */
	@Override
	public void removeAllViews(){
		mListViews.clear();
		mAbViewPagerAdapter.notifyDataSetChanged();
		creatIndex();
	}
	
	
	
	/**
	 * 描述：设置页面切换事件.
	 *
	 * @param position the position
	 */
	private void onPageScrolledCallBack(int position) {
		if(mAbScrolledListener!=null){
			mAbScrolledListener.onScroll(position);
		}
		
	}
	
	/**
	 * 描述：设置页面切换事件.
	 *
	 * @param position the position
	 */
	private void onPageSelectedCallBack(int position) {
		if(mAbChangeListener!=null){
			mAbChangeListener.onChange(position);
		}
		
	}
	
	
	/** The handler. */
	private Handler handler = new Handler();  
	
	/** The runnable. */
	private Runnable runnable = new Runnable() {  
	    public void run() {  
	    	if(mViewPager!=null){
				mViewPager.post(new Runnable() {
					public void run() {
						int count = mListViews.size();
						int i = mViewPager.getCurrentItem();
						if(i<count-1 || i==0){
							i++;
						}else{
							i--;
						}
						mViewPager.setCurrentItem(i, true);
					}
				});
			} 
	        handler.postDelayed(this, 5000);  
	    }  
	};  

	
	/**
	 * 描述：自动轮播.
	 */
	public void startPlay(){
		if(handler!=null){
		   handler.postDelayed(runnable, 5000);  
		}
	}
	
	/**
	 * 描述：自动轮播.
	 */
	public void stopPlay(){
		if(handler!=null){
			handler.removeCallbacks(runnable);  
		}
	}
	
	/**
	 * 描述：设置页面切换的监听器.
	 *
	 * @param abChangeListener the new on page change listener
	 */
    public void setOnPageChangeListener(AbOnChangeListener abChangeListener) {
    	mAbChangeListener = abChangeListener;
	}
    
    /**
     * 描述：设置页面滑动的监听器.
     *
     * @param abScrolledListener the new on page scrolled listener
     */
    public void setOnPageScrolledListener(AbOnScrolledListener abScrolledListener) {
    	mAbScrolledListener = abScrolledListener;
	}


	/**
	 * Sets the page line image.
	 *
	 * @param displayImage the display image
	 * @param hideImage the hide image
	 */
	public void setPageLineImage(Bitmap displayImage,Bitmap hideImage) {
		this.displayImage = displayImage;
		this.hideImage = hideImage;
		creatIndex();
		
	}

	/**
	 * 描述：获取这个滑动的ViewPager类.
	 *
	 * @return the view pager
	 */
	public ViewPager getViewPager() {
		return mViewPager;
	}
	
	/**
	 * 描述：获取当前的View的数量.
	 *
	 * @return the count
	 */
	public int getCount() {
		return mListViews.size();
	}
	
	/**
	 * 描述：设置页显示条的位置,在AddView前设置.
	 *
	 * @param horizontalGravity the new page line horizontal gravity
	 */
	public void setPageLineHorizontalGravity(int horizontalGravity) {
		pageLineHorizontalGravity = horizontalGravity;
	}
	
}
