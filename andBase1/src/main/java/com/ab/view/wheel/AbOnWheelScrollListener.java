/*
 * 
 */
package com.ab.view.wheel;

// TODO: Auto-generated Javadoc
/**
 * Wheel scrolled listener interface.
 *
 * @see AbOnWheelScrollEvent
 */
public interface AbOnWheelScrollListener {
	/**
	 * Callback method to be invoked when scrolling started.
	 * @param wheel the wheel view whose state has changed.
	 */
	void onScrollingStarted(AbWheelView wheel);
	
	/**
	 * Callback method to be invoked when scrolling ended.
	 * @param wheel the wheel view whose state has changed.
	 */
	void onScrollingFinished(AbWheelView wheel);
}
