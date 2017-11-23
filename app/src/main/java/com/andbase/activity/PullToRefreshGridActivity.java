package com.andbase.activity;

import java.util.ArrayList;
import java.util.Random;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.ab.activity.AbActivity;
import com.ab.global.AbConstant;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpQueue;
import com.ab.view.AbPullToRefreshGridView;
import com.andbase.R;
import com.andbase.adapter.ImageGridAdapter;
import com.andbase.global.MyApplication;
import com.andbase.model.User;

public class PullToRefreshGridActivity extends AbActivity {
	
	private int currentPage = 1;
	private MyApplication application;
	private ArrayList<User> mUserList = null;
	private ArrayList<User> mNewUserList = null;
	private AbPullToRefreshGridView mAbPullToRefreshGridView = null;
	private GridView mGridView = null;
	private ImageGridAdapter myGridViewAdapter = null;
	private ArrayList<String> mPhotoList = new ArrayList<String>();
	private AbHttpQueue mAbHttpQueue = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setAbContentView(R.layout.photo_grid);
	    
	    this.setTitleText(R.string.pull_list_name);
        this.setLogo(R.drawable.button_selector_back);
        this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
        
	    mAbHttpQueue = AbHttpQueue.getInstance();
	    mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i3/13215035600700175/T1C2mzXthaXXXXXXXX_!!0-item_pic.jpg_230x230.jpg");  
		mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i2/13215025617307680/T1AQqAXqpeXXXXXXXX_!!0-item_pic.jpg_230x230.jpg");
		mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i1/13215035569460099/T16GuzXs0cXXXXXXXX_!!0-item_pic.jpg_230x230.jpg");
		mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i2/13215023694438773/T1lImmXElhXXXXXXXX_!!0-item_pic.jpg_230x230.jpg");
		mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i3/13215023521330093/T1BWuzXrhcXXXXXXXX_!!0-item_pic.jpg_230x230.jpg");  
		mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i4/13215035563144015/T1Q.eyXsldXXXXXXXX_!!0-item_pic.jpg_230x230.jpg");  
		mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i3/13215023749568975/T1UKWCXvpXXXXXXXXX_!!0-item_pic.jpg_230x230.jpg");  
	    
		application = (MyApplication) this.getApplication();
		mAbPullToRefreshGridView = (AbPullToRefreshGridView)findViewById(R.id.mPhotoGridView);
		mGridView = mAbPullToRefreshGridView.getGridView();
		mGridView.setColumnWidth(100);
		mGridView.setGravity(Gravity.CENTER);
		mGridView.setHorizontalSpacing(5);
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade);
	    //得到一个LayoutAnimationController对象;
	    LayoutAnimationController lac = new LayoutAnimationController(animation);
		mGridView.setLayoutAnimation(lac);
		mGridView.setNumColumns(GridView.AUTO_FIT);
		mGridView.setPadding(5, 5, 5, 5);
		mGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		mGridView.setVerticalSpacing(5);
		// ListView数据
		mUserList = new ArrayList<User>();
		// 使用自定义的Adapter
		myGridViewAdapter = new ImageGridAdapter(this, mUserList,
				R.layout.photo_grid_items, new String[] { "itemsIcon" },
				new int[] { R.id.itemsIcon });
		mAbPullToRefreshGridView.setAdapter(myGridViewAdapter);
		
		showDialog(0);
		
    	//定义两种查询的事件
    	AbHttpItem item1 = new AbHttpItem();
		item1.callback = new AbHttpCallback() {

			@Override
			public void update() {
				removeDialog(0);
				mUserList.clear();
				if(mNewUserList!=null && mNewUserList.size()>0){
					mUserList.addAll(mNewUserList);
					mNewUserList.clear();
   		    	}
				mAbPullToRefreshGridView.onRefreshComplete();
			}

			@Override
			public void get() {
	   		    try {
	   		    	currentPage = 1;
	   		    	Thread.sleep(1000);
	   		    	mNewUserList =  new ArrayList<User>() ;
	   				
	   				for (int i = 0; i < 40; i++) {
	   					final User mUser = new User();
	   					mUser.setPhotoUrl(mPhotoList.get(new Random().nextInt(mPhotoList.size())));
	   					mNewUserList.add(mUser);
	   				}
	   		    } catch (Exception e) {
	   		    	showToastInThread(e.getMessage());
	   		    }
		  };
		};
		
		AbHttpItem item2 = new AbHttpItem();
		item2.callback = new AbHttpCallback() {

			@Override
			public void update() {
				if(mNewUserList!=null && mNewUserList.size()>0){
					mUserList.addAll(mNewUserList);
                	mNewUserList.clear();
                	mAbPullToRefreshGridView.onScrollComplete(AbConstant.HAVE);
                }else{
                	//没有新数据了
                	mAbPullToRefreshGridView.onScrollComplete(AbConstant.NOTHAVE);
                }
				
			}

			@Override
			public void get() {
	   		    try {
	   		    	currentPage++;
	   		    	Thread.sleep(1000);
	   		    	mNewUserList =  new ArrayList<User>() ;
	   				for (int i = 0; i < 8; i++) {
	   					final User mUser = new User();
	   					mUser.setPhotoUrl(mPhotoList.get(new Random().nextInt(mPhotoList.size())));
	   					mNewUserList.add(mUser);
	   				}
	   		    } catch (Exception e) {
	   		    	currentPage--;
	   		    	mNewUserList.clear();
	   		    	showToastInThread(e.getMessage());
	   		    }
	   		   
		  };
		};
		//设置两种查询的事件
		mAbPullToRefreshGridView.setRefreshItem(item1);
		mAbPullToRefreshGridView.setScrollItem(item2);
    	//第一次下载数据
		mAbHttpQueue.downloadBeforeClean(item1);
		
		mAbPullToRefreshGridView.getGridView().setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showToast(""+position);
				Uri uri = Uri. parse ("http://shop62288665.taobao.com/");
				Intent intent = new Intent(Intent.ACTION_VIEW); 
				intent.setData(uri); 
				startActivity(intent);
			}
    		
    	});

	}


}
