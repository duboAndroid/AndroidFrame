package com.andbase.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.ab.activity.AbActivity;
import com.andbase.R;
import com.andbase.adapter.ImageListAdapter;
import com.andbase.global.MyApplication;

public class ImageListActivity extends AbActivity {
	
	private MyApplication application;
	private ArrayList<String> mPhotoList = new ArrayList<String>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.main);
        application = (MyApplication)abApplication;
        this.setTitleText(R.string.down_image_name);
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
        
	    //获取ListView对象
        ListView mListView = (ListView)this.findViewById(R.id.mListView);
        
        //ListView数据
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	Map<String, Object> map = null;
    	
    	for (int i = 0; i < 50; i++) {
    		map = new HashMap<String, Object>();
			map.put("itemsIcon",mPhotoList.get(new Random().nextInt(mPhotoList.size())));
	    	map.put("itemsTitle", "item"+i);
	    	map.put("itemsText", "item..."+i);
	    	list.add(map);
		}
    	
        /*//够造SimpleAdapter对象，适配数据
    	SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,R.layout.list_items,
    				new String[] { "itemsIcon", "itemsTitle","itemsText" }, new int[] {R.id.itemsIcon,
    						R.id.itemsTitle,R.id.itemsText});
    	mListView.setAdapter(simpleAdapter);*/
    	
    	//使用自定义的Adapter
    	ImageListAdapter myListViewAdapter = new ImageListAdapter(this, list,R.layout.list_items,
				new String[] { "itemsIcon", "itemsTitle","itemsText" }, new int[] { R.id.itemsIcon,
						R.id.itemsTitle,R.id.itemsText });
    	mListView.setAdapter(myListViewAdapter);
    	//item被点击事件
    	mListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
    	});
	    
    }
    
    
    private void initTitleRightLayout(){
    	clearRightView();
    	View rightViewApp = mInflater.inflate(R.layout.app_btn, null);
    	addRightView(rightViewApp);
    	Button appBtn = (Button)rightViewApp.findViewById(R.id.appBtn);
    	
    	appBtn.setOnClickListener(new View.OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				//Intent intent = new Intent(ImageListActivity.this,DankeActivity.class); 
 				//startActivity(intent);
 			}
         });
         
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


