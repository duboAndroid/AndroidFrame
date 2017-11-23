/*
 * 
 */
package com.ab.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

// TODO: Auto-generated Javadoc
/**
 * The Class AbInnerViewPager.
 */
public class AbInnerViewPager extends ViewPager {

	/** The parent scroll view. */
	private ScrollView parentScrollView;
	
	/** The max height. */
	private int maxHeight;
	
	/**
	 * Instantiates a new ab inner view pager.
	 *
	 * @param context the context
	 */
	public AbInnerViewPager(Context context) {
		super(context);
	}

	/**
	 * Instantiates a new ab inner view pager.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public AbInnerViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * √Ë ˆ£∫TODO.
	 *
	 * @param widthMeasureSpec the width measure spec
	 * @param heightMeasureSpec the height measure spec
	 * @see android.support.v4.view.ViewPager#onMeasure(int, int)
	 * @author: zhaoqp
	 * @date£∫2013-6-17 …œŒÁ9:04:49
	 * @version v1.0
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (maxHeight > -1) {
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight,
					MeasureSpec.AT_MOST);
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	

	/**
	 * √Ë ˆ£∫TODO.
	 *
	 * @param ev the ev
	 * @return true, if successful
	 * @see android.support.v4.view.ViewPager#onInterceptTouchEvent(android.view.MotionEvent)
	 * @author: zhaoqp
	 * @date£∫2013-6-17 …œŒÁ9:04:49
	 * @version v1.0
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				setParentScrollAble(false);
				break;
				
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
	
			case MotionEvent.ACTION_CANCEL:
				setParentScrollAble(true);
				break;
			default:
				break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * Sets the parent scroll able.
	 *
	 * @param flag the new parent scroll able
	 */
	private void setParentScrollAble(boolean flag) {
		parentScrollView.requestDisallowInterceptTouchEvent(!flag);
	}
	
	/**
	 * Gets the parent scroll view.
	 *
	 * @return the parent scroll view
	 */
	public ScrollView getParentScrollView() {
		return parentScrollView;
	}

	/**
	 * Sets the parent scroll view.
	 *
	 * @param parentScrollView the new parent scroll view
	 */
	public void setParentScrollView(ScrollView parentScrollView) {
		this.parentScrollView = parentScrollView;
	}

	/**
	 * Gets the max height.
	 *
	 * @return the max height
	 */
	public int getMaxHeight() {
		return maxHeight;
	}

	/**
	 * Sets the max height.
	 *
	 * @param maxHeight the new max height
	 */
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

}
