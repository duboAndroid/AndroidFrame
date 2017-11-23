/*
 * 
 */
package com.ab.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

// TODO: Auto-generated Javadoc
/**
 * ���������ܻ�����ViewPager.
 *
 * @author zhaoqp
 * @date��2013-5-17 ����6:46:29
 * @version v1.0
 */
public class AbUnSlideViewPager extends ViewPager {

	/** The enabled. */
	private boolean enabled;

	
	/**
	 * Instantiates a new ab un slide view pager.
	 *
	 * @param context the context
	 */
	public AbUnSlideViewPager(Context context) {
		super(context);
		this.enabled = false;
	}
	
	/**
	 * Instantiates a new ab un slide view pager.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public AbUnSlideViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.enabled = false;
	}

	/**
	 * ����������û�з�Ӧ�Ϳ�����.
	 *
	 * @param event the event
	 * @return true, if successful
	 * @see android.support.v4.view.ViewPager#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (this.enabled) {
			return super.onTouchEvent(event);
		}

		return false;
	}

	/**
	 * ������TODO.
	 *
	 * @param event the event
	 * @return true, if successful
	 * @see android.support.v4.view.ViewPager#onInterceptTouchEvent(android.view.MotionEvent)
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:50
	 * @version v1.0
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (this.enabled) {
			return super.onInterceptTouchEvent(event);
		}

		return false;
	}

	/**
	 * Sets the paging enabled.
	 *
	 * @param enabled the new paging enabled
	 */
	public void setPagingEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
