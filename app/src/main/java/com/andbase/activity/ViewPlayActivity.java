package com.andbase.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.AbPlayView;
import com.andbase.R;

public class ViewPlayActivity extends AbActivity {
	
	AbPlayView mAbAutoPlayView = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.view_play);
        this.setTitleText(R.string.view_play_name);
        this.setLogo(R.drawable.button_selector_back);
		this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
        
	    mAbAutoPlayView = (AbPlayView)findViewById(R.id.mAbAutoPlayView);
        
	    final View mPlayView = mInflater.inflate(R.layout.play_view_item, null);
		ImageView mPlayImage = (ImageView) mPlayView.findViewById(R.id.mPlayImage);
		TextView mPlayText = (TextView) mPlayView.findViewById(R.id.mPlayText);
		mPlayText.setText("1111111111111");
		mPlayImage.setBackgroundResource(R.drawable.guide_pic1);
		
		final View mPlayView1 = mInflater.inflate(R.layout.play_view_item, null);
		ImageView mPlayImage1 = (ImageView) mPlayView1.findViewById(R.id.mPlayImage);
		TextView mPlayText1 = (TextView) mPlayView1.findViewById(R.id.mPlayText);
		mPlayText1.setText("2222222222222");
		mPlayImage1.setBackgroundResource(R.drawable.guide_pic2);
		
		final View mPlayView2 = mInflater.inflate(R.layout.play_view_item, null);
		ImageView mPlayImage2 = (ImageView) mPlayView2.findViewById(R.id.mPlayImage);
		TextView mPlayText2 = (TextView) mPlayView2.findViewById(R.id.mPlayText);
		mPlayText2.setText("33333333333333333");
		mPlayImage2.setBackgroundResource(R.drawable.guide_pic2);

		mAbAutoPlayView.setPageLineHorizontalGravity(Gravity.RIGHT);
        mAbAutoPlayView.addView(mPlayView);
        mAbAutoPlayView.addView(mPlayView1);
        
        
        initTitleRightLayout();
        
        Button addButton = (Button)findViewById(R.id.addBtn);
        Button removeButton = (Button)findViewById(R.id.removeBtn);
        Button addAllButton = (Button)findViewById(R.id.addAllBtn);
        Button startButton = (Button)findViewById(R.id.startBtn);
        Button stopButton = (Button)findViewById(R.id.stopBtn);
        
        
        addButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				View mPlayView = mInflater.inflate(R.layout.play_view_item, null);
				ImageView mPlayImage = (ImageView) mPlayView.findViewById(R.id.mPlayImage);
				TextView mPlayText = (TextView) mPlayView.findViewById(R.id.mPlayText);
				mPlayText.setText("这是第"+mAbAutoPlayView.getCount()+"个");
				mPlayImage.setBackgroundResource(R.drawable.guide_pic2);
				mAbAutoPlayView.addView(mPlayView);
			}
        	
        });
        
        addAllButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mAbAutoPlayView.removeAllViews();
				mAbAutoPlayView.addView(mPlayView);
				mAbAutoPlayView.addView(mPlayView1);
				mAbAutoPlayView.addView(mPlayView2);
			}
        	
        });
        
        removeButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mAbAutoPlayView.removeAllViews();
			}
        	
        });
        
        startButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mAbAutoPlayView.startPlay();
			}
        	
        });
        
        stopButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mAbAutoPlayView.stopPlay();
			}
        	
        });
        
    }
    
    

	private void initTitleRightLayout(){
    	clearRightView();
    }

	@Override
	protected void onResume() {
		super.onResume();
		initTitleRightLayout();
	}
	
	public void onPause() {
		super.onPause();

	}
   

}


