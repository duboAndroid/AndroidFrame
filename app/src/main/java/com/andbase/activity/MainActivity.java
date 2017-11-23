package com.andbase.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.ab.activity.AbActivity;
import com.ab.global.AbConstant;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpPool;
import com.ab.util.AbStrUtil;
import com.andbase.R;
import com.andbase.adapter.MyListViewAdapter;
import com.andbase.global.MyApplication;
import com.andbase.web.SettingWeb;

public class MainActivity extends AbActivity {
	
	private MyApplication application;
	private AbHttpPool mAbHttpPool = AbHttpPool.getInstance();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.main);
        application = (MyApplication)abApplication;
        this.setTitleText(R.string.app_name);
		this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
		this.setTitleLayoutGravity(Gravity.CENTER, Gravity.CENTER);
        
        initTitleRightLayout();
        
		final AbHttpItem item = new AbHttpItem();
		item.callback = new AbHttpCallback() {

			@Override
			public void update() {
				if(application.ad){
		        	//DydsAdPush adm = DydsAdPush.getInstance(MainActivity.this);
		            //adm.receiveMessage(2);
		        }
			}

			@Override
			public void get() {
				try {
					String ret = SettingWeb.adSetting();
					if("true".equals(AbStrUtil.parseEmpty(ret))){
						application.ad = true;
					}
				} catch (Exception e) {
				}
		  };
		};
		mAbHttpPool.download(item);
        
	    //获取ListView对象
        ListView mListView = (ListView)this.findViewById(R.id.mListView);
        
        //ListView数据
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "1.标题背景透明");
    	map.put("itemsText", "标题背景透明");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "2.图片下载与处理");
    	map.put("itemsText", "图片下载与处理(裁剪，缩放)");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "3.下拉刷新的分页ListView");
    	map.put("itemsText", "支持下拉刷新，上拉加载下一页");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "4.纯文本表格ListView");
    	map.put("itemsText", "纯文本适配的表格（支持文本，图片，复选框）");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "5.文本+图片表格ListView");
    	map.put("itemsText", "文本+图片适配的表格");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "6.文本+图片+复选框表格ListView");
    	map.put("itemsText", "文本+图片+复选框适配的表格");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "7.滑动按钮");
    	map.put("itemsText", "滑动按钮");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "8.图片联播");
    	map.put("itemsText", "图片联播");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "9.下载器");
    	map.put("itemsText", "多线程，断点续传");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "10.动画欢迎页面");
    	map.put("itemsText", "从远到近的显示的图片切换");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "11.侧边栏");
    	map.put("itemsText", "左右侧边栏");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "12.下拉刷新的分页GridView");
    	map.put("itemsText", "下拉刷新的分页GridView");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "13.底部Tab Menu的框架");
    	map.put("itemsText", "底部Tab Menu的框架");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "14.仿Iphone轮子选择控件");
    	map.put("itemsText", "仿Iphone轮子选择控件");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "15.拍照和相册选取图片");
    	map.put("itemsText", "拍照和相册选取图片");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "16.多张图片上传");
    	map.put("itemsText", "多张图片上传到服务器");
    	list.add(map);
    	
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "18.图表");
    	map.put("itemsText", "线状图，柱状图，饼状图，等级条图");
    	list.add(map);
    	
    	map = new HashMap<String, Object>();
    	map.put("itemsIcon",R.drawable.image_bg);
    	map.put("itemsTitle", "19.日历选择器");
    	map.put("itemsText", "日历选择器哦");
    	list.add(map);
    	
        /*//够造SimpleAdapter对象，适配数据
    	SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,R.layout.list_items,
    				new String[] { "itemsIcon", "itemsTitle","itemsText" }, new int[] {R.id.itemsIcon,
    						R.id.itemsTitle,R.id.itemsText});
    	mListView.setAdapter(simpleAdapter);*/
    	
    	//使用自定义的Adapter
    	MyListViewAdapter myListViewAdapter = new MyListViewAdapter(this, list,R.layout.list_items,
				new String[] { "itemsIcon", "itemsTitle","itemsText" }, new int[] { R.id.itemsIcon,
						R.id.itemsTitle,R.id.itemsText });
    	mListView.setAdapter(myListViewAdapter);
    	//item被点击事件
    	mListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = null;
				switch (position) {
				case 0:
					intent = new Intent(MainActivity.this,TitleTransparentActivity.class);
					intent.putExtra("TEXT",MainActivity.this.getResources().getString(R.string.title_transparent_desc));
					//设置标题栏透明
					intent.putExtra(AbConstant.TITLE_TRANSPARENT_FLAG, AbConstant.TITLE_TRANSPARENT);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(MainActivity.this,ImageListActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(MainActivity.this,PullToRefreshListActivity.class);
					startActivity(intent);
					break;
				case 3:
					intent = new Intent(MainActivity.this,TableDataListActivity.class);
					startActivity(intent);
					break;
				case 4:
					intent = new Intent(MainActivity.this,TableDataListActivity1.class);
					startActivity(intent);
					break;
				case 5:
					intent = new Intent(MainActivity.this,TableDataListActivity2.class);
					startActivity(intent);
					break;
				case 6:
					intent = new Intent(MainActivity.this,SliderButtonActivity.class);
					startActivity(intent);
					break;
				case 7:
					intent = new Intent(MainActivity.this,ViewPlayActivity.class);
					startActivity(intent);
					break;
				case 8:
					intent = new Intent(MainActivity.this,DownListActivity.class);
					startActivity(intent);
					break;
				case 9:
					intent = new Intent(MainActivity.this,WelcomeActivity.class);
					startActivity(intent);
					break;
				case 10:
					intent = new Intent(MainActivity.this,FlipperActivity.class);
					startActivity(intent);
					break;
				case 11:
					intent = new Intent(MainActivity.this,PullToRefreshGridActivity.class);
					startActivity(intent);
					break;
				case 12:
					intent = new Intent(MainActivity.this,PageFrameActivity.class);
					startActivity(intent);
					break;
				case 13:
					intent = new Intent(MainActivity.this,WheelActivity.class);
					startActivity(intent);
					break;
				case 14:
					intent = new Intent(MainActivity.this,AddPhotoActivity.class);
					startActivity(intent);
					break;
				case 15:
					intent = new Intent(MainActivity.this,UploadPhotoActivity.class);
					startActivity(intent);
					break;
				case 16:
					intent = new Intent(MainActivity.this,ChartActivity.class);
					startActivity(intent);
					break;
				case 17:
					intent = new Intent(MainActivity.this,CalendarActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
			}
    	});
	    
    }
    
    
    private void initTitleRightLayout(){
    	clearRightView();
    	View rightViewMore = mInflater.inflate(R.layout.more_btn, null);
    	View rightViewApp = mInflater.inflate(R.layout.app_btn, null);
    	addRightView(rightViewApp);
    	addRightView(rightViewMore);
    	Button about = (Button)rightViewMore.findViewById(R.id.moreBtn);
    	Button appBtn = (Button)rightViewApp.findViewById(R.id.appBtn);
    	
    	appBtn.setOnClickListener(new View.OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				//Intent intent = new Intent(MainActivity.this,DankeActivity.class); 
 				//startActivity(intent);
 			}
         });
    	
    	about.setOnClickListener(new View.OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				Intent intent = new Intent(MainActivity.this,AboutActivity.class); 
 				startActivity(intent);
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
	
	private static Boolean isExit = false;  
    private static Boolean hasTask = false;  
    Timer tExit = new Timer();  
    TimerTask task = new TimerTask() {  
        @Override 

        public void run() {  
            isExit = true;  
            hasTask = true;  
        }  
    };  

	
	@Override 
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK) {  
            if(isExit == false ) {  
                isExit = true;  
                showToast("再按一次退出程序");  
                if(!hasTask) {  
                    tExit.schedule(task, 2000);  
                }  
            } else {  
                finish();  
                System.exit(0);  
            }  
        }  
        return false;  

    } 

}


