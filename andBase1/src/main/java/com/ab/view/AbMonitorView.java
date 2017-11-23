/*
 * 
 */
package com.ab.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.ab.global.AbAppData;
import com.ab.util.AbGraphical;

// TODO: Auto-generated Javadoc
/**
 * ��������������View(��ʾҳ��ͣ��ʱ�䣬�����������������).
 * 
 * @author zhaoqp
 * @date��2013-1-15 ����2:50:44
 * @version v1.0
 */
public class AbMonitorView extends View  {
	
	/** The tag. */
	private static String TAG = "AbMonitorView";
	
	/** The Constant D. */
	private static final boolean D = AbAppData.DEBUG;

	/** The m start time. */
	private long mStartTime = -1;
	
	/** The m counter. */
	private int mCounter;
	
	/** The m fps. */
	private int mFps;
	
	/** The m paint. */
	private final Paint mPaint; 

	/**
	 * ������������View.
	 *
	 * @param context the context
	 */
	public AbMonitorView(Context context) {
		this(context, null);
	}

	/**
	 * ������������View.
	 *
	 * @param context the context
	 * @param attributeset the attributeset
	 */
	public AbMonitorView(Context context, AttributeSet attributeset) {
		super(context);
		mPaint = new Paint(); 
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true); 
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextSize(16);
	}

	/**
	 * ������TODO.
	 *
	 * @param canvas the canvas
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:49
	 * @version v1.0
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		
		//if(D)Log.d(TAG, "--AbMonitorView onDraw--");
		canvas.drawColor(Color.argb(80, 0, 0, 0));
		if (mStartTime == -1) {
			mStartTime = SystemClock.elapsedRealtime();
			mCounter = 0;
		}

		long now = SystemClock.elapsedRealtime();
		long delay = now - mStartTime;
		
		if(delay!=0){
			// ����֡����
	        mFps = (int)(mCounter * 1000 / delay);
		}
      
        String text = mFps + " fps";
        //��ȡֵ���ı��ĸ߶�
        TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTypeface(Typeface.DEFAULT);
        mTextPaint.setTextSize(16);
        FontMetrics fm  = mTextPaint.getFontMetrics();
        //�õ��и�
        int textHeight = (int)Math.ceil(fm.descent - fm.ascent)+2;
        int textWidth = (int)AbGraphical.getStringWidth(text,mTextPaint);
        
        
		canvas.drawText(text,(this.getWidth()-textWidth)/2, textHeight, mPaint);
		if (delay > 1000L) {
			mStartTime = now;
			mFps = mCounter;
			mCounter = 0;
		}
		mCounter++;
		super.onDraw(canvas);
	}

}
