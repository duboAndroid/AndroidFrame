package com.andbase.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.andbase.R;
import com.andbase.global.MyApplication;

public class WelcomeActivity extends AbActivity {
	
	private MyApplication application;
	private ImageView mShowPicture;
	private TextView mShowText;
	private Button mRegister;
	private Button mWhoIKnow;
	private Button mLogin;
	/**
	 * �����л��Ķ���
	 */
	private Animation mFadeIn;
	private Animation mFadeInScale;
	private Animation mFadeOut;
	/**
	 * ����ͼƬ
	 */
	private Drawable mPicture_1;
	private Drawable mPicture_2;
	private Drawable mPicture_3;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.welcome);
        this.setTitleText(R.string.welcome_name);
        this.setLogo(R.drawable.button_selector_back);
		this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
        
        titleLayout.setVisibility(View.GONE);
        application = (MyApplication)abApplication;
        mShowPicture = (ImageView) findViewById(R.id.guide_picture);
		mShowText = (TextView) findViewById(R.id.guide_content);
		init();
		setListener();
    }
    
    /**
	 * �����¼�
	 */
	private void setListener() {
		/**
		 * �����л�ԭ��:��ʼʱ���õ�һ�����ֶ���,����һ����������ʱ��ʼ�ڶ����Ŵ󶯻�,���ڶ�����������ʱ���õ�������������,
		 * ��������������ʱ�޸���ʾ�����ݲ������µ��õ�һ������,�Ӷ��ﵽѭ��Ч��
		 */
		mFadeIn.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				mShowPicture.startAnimation(mFadeInScale);
			}
		});
		mFadeInScale.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				mShowPicture.startAnimation(mFadeOut);
			}
		});
		mFadeOut.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				/**
				 * ������ʵ��Щд�Ĳ���,�����Բ��ø���ķ�ʽ���жϵ�ǰ��ʾ���ǵڼ���,�Ӷ��޸�����,
				 * ������ֻ�Ǽ򵥵Ĳ��û�ȡ��ǰ��ʾ��ͼƬ�������жϡ�
				 */
				if (mShowPicture.getDrawable().equals(mPicture_1)) {
					mShowText.setText("ͬѧ��,�����");
					mShowPicture.setImageDrawable(mPicture_2);
				} else if (mShowPicture.getDrawable().equals(mPicture_2)) {
					mShowText.setText("���ܶ�,ͬ����");
					mShowPicture.setImageDrawable(mPicture_3);
				} else if (mShowPicture.getDrawable().equals(mPicture_3)) {
					mShowText.setText("��ʱ��,Ī����");
					mShowPicture.setImageDrawable(mPicture_1);
				}
				mShowPicture.startAnimation(mFadeIn);
			}
		});
		
	}

	/**
	 * ��ʼ��
	 */
	private void init() {
		initAnim();
		initPicture();
		/**
		 * ����տ�ʼ��ʾ������
		 */
		mShowPicture.setImageDrawable(mPicture_1);
		mShowText.setText("��ʱ��,Ī����");
		mShowPicture.startAnimation(mFadeIn);
	}

	/**
	 * ��ʼ������
	 */
	private void initAnim() {
		mFadeIn = AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.welcome_fade_in);
		mFadeIn.setDuration(1000);
		mFadeInScale = AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.welcome_fade_in_scale);
		mFadeInScale.setDuration(6000);
		mFadeOut = AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.welcome_fade_out);
		mFadeOut.setDuration(1000);
	}

	/**
	 * ��ʼ��ͼƬ
	 */
	private void initPicture() {
		mPicture_1 = getResources().getDrawable(R.drawable.guide_pic1);
		mPicture_2 = getResources().getDrawable(R.drawable.guide_pic2);
		mPicture_3 = getResources().getDrawable(R.drawable.guide_pic3);
	}
    
}


