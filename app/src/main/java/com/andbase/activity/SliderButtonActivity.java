package com.andbase.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ab.activity.AbActivity;
import com.andbase.R;
import com.andbase.adapter.CheckListViewAdapter;
import com.andbase.global.MyApplication;

public class SliderButtonActivity extends AbActivity {
	
	private MyApplication application;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.slider_button);
        application = (MyApplication)abApplication;
        this.setTitleText(R.string.slider_button_name);
        this.setLogo(R.drawable.button_selector_back);
        this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
        
        initTitleRightLayout();
        
	    //获取ListView对象
        ListView mListView = (ListView)this.findViewById(R.id.mListView);
        
        //ListView数据
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	//0
    	map = new HashMap<String, Object>();
    	map.put("itemsTitle", "item1");
    	map.put("itemsText", "item1...");
    	map.put("itemsCheck", false);
    	list.add(map);
    	
    	//1
    	map = new HashMap<String, Object>();
    	map.put("itemsTitle", "item2");
    	map.put("itemsText", "item2...");
    	map.put("itemsCheck", true);
    	list.add(map);
    	
    	//2
    	map = new HashMap<String, Object>();
    	map.put("itemsTitle", "item3");
    	map.put("itemsText", "item3...");
    	map.put("itemsCheck", true);
    	list.add(map);
    	
    	//3
    	map = new HashMap<String, Object>();
    	map.put("itemsTitle", "item4");
    	map.put("itemsText", "item4...");
    	map.put("itemsCheck", true);
    	list.add(map);
    	
    	//4
    	map = new HashMap<String, Object>();
    	map.put("itemsTitle", "item5");
    	map.put("itemsText", "item5...");
    	map.put("itemsCheck", false);
    	list.add(map);
    	
    	//5
    	map = new HashMap<String, Object>();
    	map.put("itemsTitle", "item6");
    	map.put("itemsText", "item6...");
    	map.put("itemsCheck", false);
    	list.add(map);
    	
    	//6
    	map = new HashMap<String, Object>();
    	map.put("itemsTitle", "item7");
    	map.put("itemsText", "item7...");
    	map.put("itemsCheck", false);
    	list.add(map);
    	
        //够造SimpleAdapter对象，适配数据
    	CheckListViewAdapter simpleAdapter = new CheckListViewAdapter(this, list,R.layout.list_items_button,
    				new String[] {"itemsTitle","itemsText","itemsCheck" }, new int[] {
    						R.id.itemsTitle,R.id.itemsText,R.id.mSliderBtn});
    	mListView.setAdapter(simpleAdapter);
    	
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


