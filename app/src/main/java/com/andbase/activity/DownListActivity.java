package com.andbase.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.ab.activity.AbActivity;
import com.ab.db.DownFileDao;
import com.ab.model.DownFile;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpPool;
import com.ab.util.AbFileUtil;
import com.andbase.R;
import com.andbase.adapter.MyExpandableListAdapter;
import com.andbase.global.Constant;
import com.andbase.global.MyApplication;

public class DownListActivity extends AbActivity{
	
	private MyApplication application;
	private DownFileDao mDownFileDao = null;
	private ArrayList<DownFile> mDownFileList1 = null;
	private ArrayList<DownFile> mDownFileList2 = null;
	private ArrayList<ArrayList<DownFile>> mGroupDownFileList = null;
	private MyExpandableListAdapter mExpandableListAdapter = null;
	private AbHttpPool mAbHttpPool = null;
    
    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.down_list);
        this.setTitleText(R.string.down_list);
        this.setLogo(R.drawable.button_selector_back);
        this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
	    
        application = (MyApplication)abApplication;
        mDownFileDao = DownFileDao.getInstance(this);
        mDownFileList1 = new ArrayList<DownFile>();
        mDownFileList2 = new ArrayList<DownFile>();
        mGroupDownFileList = new ArrayList<ArrayList<DownFile>>();
        mGroupDownFileList.add(mDownFileList1);
        mGroupDownFileList.add(mDownFileList2);
        
        mAbHttpPool = AbHttpPool.getInstance();
        
        String[] mDownFileGroupTitle = new String[]{this.getResources().getString(R.string.download_complete_title),this.getResources().getString(R.string.undownLoad_title)};
        
        //创建一个BaseExpandableListAdapter对象
      	mExpandableListAdapter = new MyExpandableListAdapter(this,mGroupDownFileList,mDownFileGroupTitle);
      	ExpandableListView mExpandListView = (ExpandableListView)findViewById(R.id.mExpandableListView);
      	mExpandListView.setAdapter(mExpandableListAdapter);
        //Indicator靠右
        int width = getWindowManager().getDefaultDisplay().getWidth();
        mExpandListView.setIndicatorBounds(width-40, width-25);
        mExpandListView.setChildIndicatorBounds(5, 53);
        
        initDownFileList();
     }

	
	
	/**
	 * 初始化所有文件
	 */
	private void initDownFileList() {

		List<DownFile> mDownFileList = new ArrayList<DownFile>();
		
		DownFile mDownFile1 = new DownFile();
		mDownFile1.setName("咔布咔");
		mDownFile1.setDescription("长期肆虐广大苹果粉丝智商的游戏大作");
		mDownFile1.setPakageName("");
		mDownFile1.setState(Constant.undownLoad);
		mDownFile1.setIcon(String.valueOf(R.drawable.image_bg));
		mDownFile1.setDownUrl("http://gdown.baidu.com/data/wisegame/8c459c47b29219a4/kabuka_201.apk");
		mDownFileList.add(mDownFile1); 
		
		DownFile mDownFile2 = new DownFile();
		mDownFile2.setName("滚吧水果");
		mDownFile2.setPakageName("");
		mDownFile2.setDescription("精品物理益智游戏，玩法易懂，操作简单");
		mDownFile2.setState(Constant.undownLoad);
		mDownFile2.setIcon(String.valueOf(R.drawable.image_bg));
		mDownFile2.setDownUrl("http://gdown.baidu.com/data/wisegame/2fe5e761daa8916b/gunbashuiguo.apk");
		mDownFileList.add(mDownFile2); 
		
		DownFile mDownFile3 = new DownFile();
		mDownFile3.setName("猫狗大战2");
		mDownFile3.setPakageName("");
		mDownFile3.setDescription("自古以来，猫狗势不两立，最常见的宠物");
		mDownFile3.setState(Constant.undownLoad);
		mDownFile3.setIcon(String.valueOf(R.drawable.image_bg));
		mDownFile3.setDownUrl("http://gdown.baidu.com/data/wisegame/377ac8656deb965f/CatWar2_19.apk");
		mDownFileList.add(mDownFile3); 
		
		//初始化文件已经下载的长度，计算已下载的进度
		for(DownFile mDownFile:mDownFileList){
			  //本地数据
			  DownFile mDownFileT = mDownFileDao.getDownFile(mDownFile.getDownUrl());
	          if(mDownFileT != null){
	        	  mDownFile = mDownFileT;
	        	  if(mDownFile.getDownLength() == mDownFile.getTotalLength()){
	    	    	  mDownFile.setState(Constant.downloadComplete);
	    	    	  mDownFileList1.add(mDownFile);
	    	    	  mExpandableListAdapter.notifyDataSetChanged();
				  }else{
					  //显示为暂停状态
		        	  mDownFile.setState(Constant.downLoadPause);
	        	      mDownFileList2.add(mDownFile);
	        	      mExpandableListAdapter.notifyDataSetChanged();
				  }
	          }else{
	        	    final DownFile mDownNewFile = mDownFile ;
					AbHttpItem item = new AbHttpItem();
					item.callback = new AbHttpCallback() {

						@Override
						public void update() {
			        	    mDownFileList2.add(mDownNewFile);
			        	    mExpandableListAdapter.notifyDataSetChanged();
						}

						@Override
						public void get() {
							try {
								  int totalLength = AbFileUtil.getContentLengthFormUrl(mDownNewFile.getDownUrl());
								  mDownNewFile.setTotalLength(totalLength);
								  mDownNewFile.setState(Constant.undownLoad);
							} catch (Exception e) {
								e.printStackTrace();
							}
					  };
					};
					mAbHttpPool.download(item);
	          }
	    }
	}
	
	
	@Override
	public void finish() {
		super.finish();
		//释放所有的下载线程
		mExpandableListAdapter.releaseThread();

		
	}
	

}