/*
 * 
 */
package com.ab.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

// TODO: Auto-generated Javadoc
/**
 * ÃèÊö£º²à±ßÀ¸×ó±ßÊµÏÖ.
 *
 * @author zhaoqp
 * @date£º2013-4-24 ÏÂÎç3:46:47
 * @version v1.0
 */
public class AbFlipperLeftView {
	
	/** The m context. */
	private Context mContext;
	
	/** The m left view. */
	private View mLeftView;

	/** The m on change view listener. */
	private OnChangeViewListener mOnChangeViewListener;

	/**
	 * Instantiates a new ab flipper left view.
	 *
	 * @param context the context
	 * @param res the res
	 */
	public AbFlipperLeftView(Context context, int res) {
		mContext = context;
		mLeftView = LayoutInflater.from(context).inflate(res, null);
	}

	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public View getView() {
		return mLeftView;
	}

	/**
	 * The listener interface for receiving onChangeView events.
	 * The class that is interested in processing a onChangeView
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addOnChangeViewListener<code> method. When
	 * the onChangeView event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see OnChangeViewEvent
	 */
	public interface OnChangeViewListener {
		
		/**
		 * On change view.
		 *
		 * @param arg0 the arg0
		 */
		public abstract void onChangeView(int arg0);
	}

	/**
	 * Sets the on change view listener.
	 *
	 * @param onChangeViewListener the new on change view listener
	 */
	public void setOnChangeViewListener(OnChangeViewListener onChangeViewListener) {
		mOnChangeViewListener = onChangeViewListener;
	}
}
