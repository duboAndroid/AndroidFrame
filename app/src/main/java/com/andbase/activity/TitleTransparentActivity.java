package com.andbase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.andbase.R;
import com.andbase.global.MyApplication;

public class TitleTransparentActivity extends AbActivity {
	
	private MyApplication application;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.base_detail);
        application = (MyApplication)abApplication;
        
        this.setTitleText("透明标题栏");
		this.setLogo(R.drawable.button_selector_back);
		this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
        this.setTitleTextBackgroundResource(R.drawable.drop_down_title_btn);
	    
	    this.getTitleTextButton().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showToast("点了标题哦");
				
			}
		});
        
        logoView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        TextView mText = (TextView)this.findViewById(R.id.mText);
        Intent intent = getIntent();
        String desc = intent.getStringExtra("TEXT");
        mText.setText(desc);
    }    
}


