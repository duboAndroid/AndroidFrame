/*
 * 
 */
package com.ab.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class AbScrollTextView.
 */
public class AbScrollTextView extends TextView {

	/**
	 * Instantiates a new ab scroll text view.
	 *
	 * @param context the context
	 */
	public AbScrollTextView(Context context) {
		super(context);
	}

	/**
	 * Instantiates a new ab scroll text view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public AbScrollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Instantiates a new ab scroll text view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	public AbScrollTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * √Ë ˆ£∫TODO.
	 *
	 * @return true, if is focused
	 * @see android.view.View#isFocused()
	 * @author: zhaoqp
	 * @date£∫2013-6-17 …œŒÁ9:04:47
	 * @version v1.0
	 */
	@Override
	public boolean isFocused() {
		return true;
	}

}