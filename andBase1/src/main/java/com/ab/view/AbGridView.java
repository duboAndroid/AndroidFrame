/*
 * 
 */
package com.ab.view;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;


// TODO: Auto-generated Javadoc
/**
 * The Class AbGridView.
 */
public class AbGridView extends LinearLayout {
	
	/** The context. */
	private Context context;
	
	/** The m grid view. */
	private GridView mGridView = null;
	
	/** The layout params fw. */
	private LinearLayout.LayoutParams layoutParamsFW = null;
	
	/** The m linear layout header. */
	private LinearLayout mLinearLayoutHeader = null;
	
	/** The m linear layout footer. */
	private LinearLayout mLinearLayoutFooter = null;
    
    /**
     * Instantiates a new ab grid view.
     *
     * @param context the context
     */
    public AbGridView(Context context) {
        super(context);
    }
    

	/**
	 * Instantiates a new ab grid view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public AbGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		
		layoutParamsFW = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		this.setOrientation(LinearLayout.VERTICAL);
		this.setBackgroundColor(Color.rgb(255, 255, 255));
		mLinearLayoutHeader = new LinearLayout(context);
		mLinearLayoutFooter = new LinearLayout(context);
		mGridView = new GridView(context);
		addView(mLinearLayoutHeader,layoutParamsFW);
		LinearLayout.LayoutParams layoutParamsFW1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		layoutParamsFW1.weight = 1;
		mGridView.setLayoutParams(layoutParamsFW1);
		addView(mGridView);
		
		addView(mLinearLayoutFooter,layoutParamsFW);
	}
  
    /**
     * Adds the header view.
     *
     * @param v the v
     */
    public void addHeaderView(View v) {
    	mLinearLayoutHeader.addView(v,layoutParamsFW);
    }
    
    /**
     * Adds the footer view.
     *
     * @param v the v
     */
    public void addFooterView(View v) {
    	LinearLayout.LayoutParams layoutParamsFW1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
    	layoutParamsFW1.topMargin = 2;
    	mLinearLayoutFooter.addView(v,layoutParamsFW1);
    }

	/**
	 * Gets the grid view.
	 *
	 * @return the grid view
	 */
	public GridView getGridView() {
		return mGridView;
	}


	/**
	 * Sets the grid view.
	 *
	 * @param mGridView the new grid view
	 */
	public void setGridView(GridView mGridView) {
		this.mGridView = mGridView;
	}

}

