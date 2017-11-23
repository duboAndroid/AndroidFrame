package com.andbase.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.ab.activity.AbActivity;
import com.ab.global.AbConstant;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpQueue;
import com.ab.view.AbPullToRefreshListView;
import com.andbase.R;
import com.andbase.adapter.ImageListAdapter;
import com.andbase.global.MyApplication;

public class PullToRefreshListActivity extends AbActivity {
	
	private MyApplication application;
	private List<Map<String, Object>> list = null;
	private List<Map<String, Object>> newList = null;
	private AbPullToRefreshListView mPullToRefreshListView = null;
	private int currentPage = 1;
	private AbHttpQueue mAbHttpQueue = null;
	private ArrayList<String> mPhotoList = new ArrayList<String>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.pull_list);
        application = (MyApplication)abApplication;
        this.setTitleText(R.string.pull_list_name);
        this.setLogo(R.drawable.button_selector_back);
        this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
        initTitleRightLayout();
        
        mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i3/13215035600700175/T1C2mzXthaXXXXXXXX_!!0-item_pic.jpg_230x230.jpg");  
		mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i2/13215025617307680/T1AQqAXqpeXXXXXXXX_!!0-item_pic.jpg_230x230.jpg");
		mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i1/13215035569460099/T16GuzXs0cXXXXXXXX_!!0-item_pic.jpg_230x230.jpg");
		mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i2/13215023694438773/T1lImmXElhXXXXXXXX_!!0-item_pic.jpg_230x230.jpg");
		mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i3/13215023521330093/T1BWuzXrhcXXXXXXXX_!!0-item_pic.jpg_230x230.jpg");  
		mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i4/13215035563144015/T1Q.eyXsldXXXXXXXX_!!0-item_pic.jpg_230x230.jpg");  
		mPhotoList.add("http://img01.taobaocdn.com/bao/uploaded/i3/13215023749568975/T1UKWCXvpXXXXXXXXX_!!0-item_pic.jpg_230x230.jpg"); 
        mAbHttpQueue = AbHttpQueue.getInstance();
	    //获取ListView对象
        mPullToRefreshListView = (AbPullToRefreshListView)this.findViewById(R.id.mListView);
        
        //ListView数据
    	list = new ArrayList<Map<String, Object>>();
    	
    	//使用自定义的Adapter
    	ImageListAdapter myListViewAdapter = new ImageListAdapter(this, list,R.layout.list_items,
				new String[] { "itemsIcon", "itemsTitle","itemsText" }, new int[] { R.id.itemsIcon,
						R.id.itemsTitle,R.id.itemsText });
    	mPullToRefreshListView.setAdapter(myListViewAdapter);
    	//item被点击事件
    	mPullToRefreshListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
    	});
    	
    	showDialog(0);

    	//定义两种查询的事件
    	AbHttpItem item1 = new AbHttpItem();
		item1.callback = new AbHttpCallback() {

			@Override
			public void update() {
				removeDialog(0);
				list.clear();
				if(newList!=null && newList.size()>0){
	                list.addAll(newList);
	                newList.clear();
   		    	}
				mPullToRefreshListView.onRefreshComplete();
			}

			@Override
			public void get() {
	   		    try {
	   		    	Thread.sleep(1000);
	   		    	currentPage = 1;
	   		    	newList = new ArrayList<Map<String, Object>>();
	   		    	Map<String, Object> map = null;
	   		    	
	   		    	for (int i = 0; i < 10; i++) {
	   		    		map = new HashMap<String, Object>();
	   					map.put("itemsIcon",mPhotoList.get(new Random().nextInt(mPhotoList.size())));
		   		    	map.put("itemsTitle", "item"+i);
		   		    	map.put("itemsText", "item..."+i);
		   		    	newList.add(map);
	   				}
	   		    } catch (Exception e) {
	   		    }
		  };
		};
		
		AbHttpItem item2 = new AbHttpItem();
		item2.callback = new AbHttpCallback() {

			@Override
			public void update() {
				if(newList!=null && newList.size()>0){
					list.addAll(newList);
					newList.clear();
                	mPullToRefreshListView.onScrollComplete(AbConstant.HAVE);
                }else{
                	//没有新数据了
                	mPullToRefreshListView.onScrollComplete(AbConstant.NOTHAVE);
                }
				
			}

			@Override
			public void get() {
	   		    try {
	   		    	currentPage++;
	   		    	Thread.sleep(1000);
	   		    	newList = new ArrayList<Map<String, Object>>();
	   		    	Map<String, Object> map = null;
	   		    	map = new HashMap<String, Object>();
	   		    	map.put("itemsIcon",mPhotoList.get(new Random().nextInt(mPhotoList.size())));
	   		    	map.put("itemsTitle", "item-上拉");
	   		    	map.put("itemsText", "item-上拉...");
	   		    	newList.add(map);
	   		    } catch (Exception e) {
	   		    	currentPage--;
	   		    	newList.clear();
	   		    	showToastInThread(e.getMessage());
	   		    }
		  };
		};
		//设置两种查询的事件
		mPullToRefreshListView.setRefreshItem(item1);
		mPullToRefreshListView.setScrollItem(item2);
    	//第一次下载数据
		mAbHttpQueue.downloadBeforeClean(item1);
	    
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


