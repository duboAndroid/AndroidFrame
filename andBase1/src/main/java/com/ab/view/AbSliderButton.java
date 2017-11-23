/*
 * 
 */
package com.ab.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.ab.util.AbFileUtil;
import com.ab.util.AbImageUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class AbSliderButton.
 */
public class AbSliderButton extends ViewGroup implements OnGestureListener{
	
	/** The m relative layout. */
	private RelativeLayout mRelativeLayout;
	
	/** The Constant ID_BTN1. */
	private static final int ID_BTN1 = 1;
    
    /** The Constant ID_BTN2. */
    private static final int ID_BTN2 = 2;
    
    /** The Constant ID_BTN3. */
    private static final int ID_BTN3 = 3;
    
    /** The Constant ID_BTN4. */
    private static final int ID_BTN4 = 4;
    
	/** The current btn. */
	private ImageButton btnLeft,btnRight,currentBtn;
	
	/** The is open. */
	private boolean isOpen = false;
	
	/** The is aimation moving. */
	private boolean isAimationMoving = false;
	//0��  1��  2����  3����  
	/** The state. */
	private int state = 0;
	
	/** The a. */
	private int a = 0;
	
	/** The btn width. */
	int btnWidth = 40;
	
	/** The btn height. */
	int btnHeight = 40;
	//�ƶ��ľ���
	/** The move width. */
	int moveWidth = 45;
	//ÿ���ƶ��ľ���
	/** The move p dis. */
	int movePDis = 5;
	//���ǰ�ť�Ŀ�
	/** The Width offset. */
	int WidthOffset = 5;
	
	/** The detector. */
	private GestureDetector detector;
	//�ı��¼�
	/** The m switcher change listener. */
	private AbOnChangeListener mSwitcherChangeListener;
	
	/** The m handler. */
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//��
			if (msg.what == 0) {
				state = 2;
				int left = btnRight.getLeft();
				int right = btnRight.getRight();
				//0,45
				if(left-movePDis<=0){
					isOpen = false;
					isAimationMoving = false;
					state = 0;
				}else{
				     currentBtn.layout(left-movePDis, 0, right-movePDis, btnHeight);
				}
			}
			// ��
			else if (msg.what == 1) {
				state = 3;
				int left = btnLeft.getLeft();
				int right = btnLeft.getRight();
				//35,80
				if(left+movePDis>=moveWidth){
					isOpen = true;
					isAimationMoving = false;
					state = 1;
				}else{
				    currentBtn.layout(left+movePDis, 0,right+movePDis, btnHeight);
			    }
			//��
			}else if (msg.what == 3) {
				currentBtn = btnLeft;
				btnRight.setVisibility(View.GONE);
				btnLeft.setVisibility(View.VISIBLE);
				isOpen = false;
				isAimationMoving = false;
				state = 0;
			}
			// ��
			else if (msg.what == 4) {
				currentBtn = btnRight;
				btnLeft.setVisibility(View.GONE);
				btnRight.setVisibility(View.VISIBLE);
				isOpen = true;
				isAimationMoving = false;
				state = 1;
			}
		}
	};

	/**
	 * Instantiates a new ab slider button.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	public AbSliderButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Instantiates a new ab slider button.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public AbSliderButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		
		
		btnWidth = 40;
		btnHeight = 40;
		//�ƶ��ľ���
		moveWidth = 45;
		//ÿ���ƶ��ľ���
		movePDis = 5;
		//���ǰ�ť�Ŀ�
		WidthOffset = 5;
		
		WindowManager wManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);    	
		Display display = wManager.getDefaultDisplay();    	
		int width = display.getWidth();    	
		int height = display.getHeight();
		
		/*
		 2 px = 3 dip if dpi == 80(ldpi), 320x240 screen
		 1 px = 1 dip if dpi == 160(mdpi), 480x320 screen
		 3 px = 2 dip if dpi == 240(hdpi), 840x480 screen
		*/


		if(width<320){
			btnWidth = btnWidth-10;
			btnHeight =  btnHeight-10;
			moveWidth = moveWidth-10;
		}else if(width>320 && width<450){
			btnWidth = btnWidth+10;
			btnHeight =  btnHeight+10;
			moveWidth = moveWidth+10;
		}else if(width>=450){
			btnWidth = btnWidth+20;
			btnHeight =  btnHeight+20;
			moveWidth = moveWidth+20;
		}
		
		
		detector = new GestureDetector(this);
		
		mRelativeLayout = new RelativeLayout(context);
		
		ImageButton btnOn = new ImageButton(context);
		ImageButton btnOff = new ImageButton(context);
		btnLeft = new ImageButton(context);
		btnRight = new ImageButton(context);
		
		btnOn.setFocusable(false);
		btnOff.setFocusable(false);
		btnLeft.setFocusable(false);
		btnRight.setFocusable(false);

		
		Bitmap onImage = AbFileUtil.getBitmapFormSrc("image/button_on_bg.png");
		Bitmap offImage = AbFileUtil.getBitmapFormSrc("image/button_off_bg.png");
		Bitmap blockImage = AbFileUtil.getBitmapFormSrc("image/button_block.png");
		
		btnOn.setId(ID_BTN1);
		//btnOn.setImageBitmap(onImage);
		btnOn.setMinimumWidth(btnWidth);
		btnOn.setMinimumHeight(btnHeight);
		btnOn.setBackgroundDrawable(AbImageUtil.bitmapToDrawable(onImage));
		
		btnOff.setId(ID_BTN2);
		btnOff.setMinimumWidth(btnWidth);
		btnOff.setMinimumHeight(btnHeight);
		//btnOff.setImageBitmap(offImage);
		btnOff.setBackgroundDrawable(AbImageUtil.bitmapToDrawable(offImage));
		
		btnLeft.setId(ID_BTN3);
		btnLeft.setMinimumWidth(btnWidth+WidthOffset);
		btnLeft.setMinimumHeight(btnHeight);
		//btnLeft.setImageBitmap(blockImage);
		btnLeft.setBackgroundDrawable(AbImageUtil.bitmapToDrawable(blockImage));
		
		btnRight.setId(ID_BTN4);
		btnRight.setMinimumWidth(btnWidth+WidthOffset);
		btnRight.setMinimumHeight(btnHeight);
		//btnRight.setImageBitmap(blockImage);
		btnRight.setBackgroundDrawable(AbImageUtil.bitmapToDrawable(blockImage));
		
		mRelativeLayout.addView(btnOn);
		mRelativeLayout.setFocusable(false);
		
		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.RIGHT_OF, ID_BTN1);
		mRelativeLayout.addView(btnOff, lp2);
		
		RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp3.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
		mRelativeLayout.addView(btnLeft, lp3);
		
		RelativeLayout.LayoutParams lp4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
		mRelativeLayout.addView(btnRight, lp4);
		
		addView(mRelativeLayout);
		
		mSwitcherChangeListener = new AbOnChangeListener(){

			@Override
			public void onChange(int position) {
				
			}
			
		};
		
		
		
	}
 
	/**
	 * Instantiates a new ab slider button.
	 *
	 * @param context the context
	 */
	public AbSliderButton(Context context) {
		super(context);
	}
	
	
	/**
	 * ������TODO.
	 *
	 * @param widthMeasureSpec the width measure spec
	 * @param heightMeasureSpec the height measure spec
	 * @see android.view.View#onMeasure(int, int)
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:46
	 * @version v1.0
	 */
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        for (int index = 0; index < getChildCount(); index++) {
            final View child = getChildAt(index);
            // measure
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


	/**
	 * ������TODO.
	 *
	 * @param changed the changed
	 * @param l the l
	 * @param t the t
	 * @param r the r
	 * @param b the b
	 * @see android.view.ViewGroup#onLayout(boolean, int, int, int, int)
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:46
	 * @version v1.0
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int count = getChildCount();
        for(int i=0;i<count;i++){
            final View child = this.getChildAt(i);
            btnWidth = child.getMeasuredWidth();
            btnHeight = child.getMeasuredHeight();
            child.layout(0, 0, btnWidth, btnHeight);
        }
	}
	
	/**
	 * Sets the checked.
	 *
	 * @param checked the checked
	 * @param anim the anim
	 * @param changeEvent the change event
	 * @return true, if successful
	 */
	public boolean setChecked(boolean checked,boolean anim,final boolean changeEvent){
		isOpen = checked;
		if(anim){
			if(checked){
				a = 0;
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						if(a<moveWidth){
							isAimationMoving = true;
							mHandler.sendEmptyMessage(1);
							mHandler.postDelayed(this,0);
						}else if(a==moveWidth){
						    isAimationMoving = true;
							mHandler.sendEmptyMessage(1);
							mHandler.removeCallbacks(this);
							if(changeEvent){
								mSwitcherChangeListener.onChange(1);
							}
							
						}else{
							isAimationMoving = false;
							state = 1;
						}
						a+=5;
					}
				},0);
			}else{
				a = 0;
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						if(a<moveWidth){
							isAimationMoving = true;
							//����С��move_x   arg1���ƶ����룬arg2���Ƿ������
							mHandler.sendEmptyMessage(0);
							mHandler.postDelayed(this,0);
						}else if(a==moveWidth){
						    isAimationMoving = true;
							mHandler.sendEmptyMessage(0);
							mHandler.removeCallbacks(this);
							if(changeEvent){
								mSwitcherChangeListener.onChange(0);
							}
						}else{
							isAimationMoving = false;
							state = 0;
						}
						a+=movePDis;
						
					}
				}, 0);
			}
		}else{
            if(checked){
            	mHandler.sendEmptyMessage(4);
            	if(changeEvent){
					mSwitcherChangeListener.onChange(1);
				}
			}else{
				mHandler.sendEmptyMessage(3);
				if(changeEvent){
					mSwitcherChangeListener.onChange(0);
				}
			}
		}
		return true;
		
	}
	
	/**
	 * �������ı��¼�.
	 *
	 * @param switcherChangeListener the new switcher change listener
	 * @date��2012-8-8 ����3:53:00
	 * @version v1.0
	 */
	public void setSwitcherChangeListener(AbOnChangeListener switcherChangeListener){
		this.mSwitcherChangeListener = switcherChangeListener;
	}
	
	/**
	 * ������TODO.
	 *
	 * @param ev the ev
	 * @return true, if successful
	 * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:46
	 * @version v1.0
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		detector.onTouchEvent(ev);
		super.dispatchTouchEvent(ev);
		return true;
	}

	/**
	 * ������TODO.
	 *
	 * @param e the e
	 * @return true, if successful
	 * @see android.view.GestureDetector.OnGestureListener#onDown(android.view.MotionEvent)
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:46
	 * @version v1.0
	 */
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	/**
	 * ������TODO.
	 *
	 * @param e the e
	 * @see android.view.GestureDetector.OnGestureListener#onShowPress(android.view.MotionEvent)
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:46
	 * @version v1.0
	 */
	@Override
	public void onShowPress(MotionEvent e) {
	}

	/**
	 * ������TODO.
	 *
	 * @param e the e
	 * @return true, if successful
	 * @see android.view.GestureDetector.OnGestureListener#onSingleTapUp(android.view.MotionEvent)
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:46
	 * @version v1.0
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		if (isAimationMoving) {
			return true;
		}
		if (isOpen) {
			a = 0;
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					if(a<moveWidth){
						isAimationMoving = true;
						mHandler.sendEmptyMessage(0);
						mHandler.postDelayed(this,0);
					}else if(a==moveWidth){
					    isAimationMoving = true;
						mHandler.sendEmptyMessage(3);
						mHandler.removeCallbacks(this);
						//��
						mSwitcherChangeListener.onChange(0);
					}else{
						isAimationMoving = false;
						state = 0;
					}
					a+=movePDis;
					
				}
			}, 0);
		} else {
			a = 0;
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					if(a<moveWidth){
						isAimationMoving = true;
						mHandler.sendEmptyMessage(1);
						mHandler.postDelayed(this,0);
					}else if(a==moveWidth){
					    isAimationMoving = true;
						mHandler.sendEmptyMessage(4);
						mHandler.removeCallbacks(this);
						//��
						mSwitcherChangeListener.onChange(1);
					}else{
						isAimationMoving = false;
						state = 1;
					}
					a+=movePDis;
				}
			}, 0);
		}
		return true;
	}

	/**
	 * ������TODO.
	 *
	 * @param e1 the e1
	 * @param e2 the e2
	 * @param distanceX the distance x
	 * @param distanceY the distance y
	 * @return true, if successful
	 * @see android.view.GestureDetector.OnGestureListener#onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:46
	 * @version v1.0
	 */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	/**
	 * ������TODO.
	 *
	 * @param e the e
	 * @see android.view.GestureDetector.OnGestureListener#onLongPress(android.view.MotionEvent)
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:46
	 * @version v1.0
	 */
	@Override
	public void onLongPress(MotionEvent e) {
	}

	/**
	 * ������TODO.
	 *
	 * @param e1 the e1
	 * @param e2 the e2
	 * @param velocityX the velocity x
	 * @param velocityY the velocity y
	 * @return true, if successful
	 * @see android.view.GestureDetector.OnGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:46
	 * @version v1.0
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return true;
	}
	
}
