package com.andbase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andbase.R;
import com.andbase.global.MyApplication;

public class Fragment2 extends Fragment {
	
	private Activity mActivity = null;
	private MyApplication application;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = (Activity) this.getActivity();
		application = (MyApplication) mActivity.getApplication();
		View view = inflater.inflate(R.layout.text2, null);
		return view;
	}
	

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}
