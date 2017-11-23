package com.andbase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.ab.view.AbFlipperLayout;
import com.ab.view.AbFlipperLayout.OnOpenListener;
import com.ab.view.AbFlipperLeftView;
import com.andbase.R;
import com.andbase.global.MyApplication;

public class FlipperActivity extends Activity{
	private LayoutInflater mInflater = null;
	private AbFlipperLayout mAbFlipperLayout;
	private MyApplication application = null;
	private OnOpenListener mOnOpenListener;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		application = (MyApplication) this.getApplication();
		mInflater = LayoutInflater.from(this);
		LinearLayout.LayoutParams layoutParamsFF = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		mAbFlipperLayout = new AbFlipperLayout(this);
		mAbFlipperLayout.setLayoutParams(layoutParamsFF);
		//AbFlipperLeftView的布局中必须有个ListView 且android:layout_height="fill_parent"
		AbFlipperLeftView mAbFlipperLeftView = new AbFlipperLeftView(this,R.layout.left);
		View mainView = mInflater.inflate(R.layout.right, null);
		mAbFlipperLayout.addView(mAbFlipperLeftView.getView(), layoutParamsFF);
		mAbFlipperLayout.addView(mainView, layoutParamsFF);
		setContentView(mAbFlipperLayout);
		
		Button rightBtn = (Button)mainView.findViewById(R.id.rightBtn);
		rightBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (mAbFlipperLayout.getScreenState() == AbFlipperLayout.SCREEN_STATE_CLOSE) {
					mAbFlipperLayout.open();
				}
			}
		});
    }

	
    
}


