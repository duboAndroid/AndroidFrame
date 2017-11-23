/*
 * 
 */
package com.ab.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;
// TODO: Auto-generated Javadoc
/**
 * 
 * Copyright (c) 2012 All rights reserved
 * 名称：AbPagerScrollView.java 
 * 描述：与AbPlayView一起使用，可混合拖动事件，不冲突
 * @author zhaoqp
 * @date：2013-5-21 下午3:59:38
 * @version v1.0
 */
public class AbPagerScrollView extends ScrollView {

    /** The m gesture detector. */
    private GestureDetector mGestureDetector;

    /**
     * Instantiates a new ab pager scroll view.
     *
     * @param context the context
     * @param attrs the attrs
     */
    public AbPagerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(new YScrollDetector());
        setFadingEdgeLength(0);
		//  this.post(new Runnable(){
		//       public void run() {  
		//          fullScroll(ScrollView.FOCUS_UP);  
		//       }  
		//  });
        
    }

    /**
     * 描述：TODO.
     *
     * @param ev the ev
     * @return true, if successful
     * @see android.widget.ScrollView#onInterceptTouchEvent(android.view.MotionEvent)
     * @author: zhaoqp
     * @date：2013-6-17 上午9:04:50
     * @version v1.0
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }
    
    /**
     * The Class YScrollDetector.
     */
    class YScrollDetector extends SimpleOnGestureListener {
        
        /**
         * 描述：TODO.
         *
         * @param e1 the e1
         * @param e2 the e2
         * @param distanceX the distance x
         * @param distanceY the distance y
         * @return true, if successful
         * @see android.view.GestureDetector.SimpleOnGestureListener#onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float)
         * @author: zhaoqp
         * @date：2013-6-17 上午9:04:50
         * @version v1.0
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(distanceY!=0&&distanceX!=0){
                    
            }
            if(Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }
    
    
}
