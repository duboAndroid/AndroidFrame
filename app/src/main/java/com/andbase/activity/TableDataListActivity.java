package com.andbase.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ab.activity.AbActivity;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpPool;
import com.ab.view.table.AbCellType;
import com.ab.view.table.AbTable;
import com.ab.view.table.AbTableArrayAdapter;
import com.andbase.R;
import com.andbase.global.MyApplication;
import com.andbase.model.Stock;


public class TableDataListActivity extends AbActivity {
	
	private MyApplication application;
	/////////////////////////////////////////
	//�����������Դ
	private List<String[]> contents;
	//����������Դ
	private String[] titles = null;
	private AbTable table = null;
	private ListView mListView = null;
	private int[] cellTypes = null;
	private int[] cellWidth = null;
	private int[] rowHeight = null;
	// (6)�����ִ�С������0���⣬1�����б�
	private int[] rowTextSize = null;
	// (7)��������ɫ������0���⣬1�����б�
	private int[] rowTextColor = null;
	// (8)������Դ
	private int[] tableResource = null;
	
	// ����Adapter
	private AbTableArrayAdapter tableAdapter;
	
	///////////////////////////////////////////////
	
	private View noView = null;
	private ArrayList<Stock> mStockList = null;
	private AbHttpPool mAbHttpPool = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (MyApplication)abApplication;
		setAbContentView(mInflater.inflate(R.layout.table_data_list, null));
        setTitleText(R.string.table_list_name);
        setLogo(R.drawable.button_selector_back);
        this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
        
        logoView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        mAbHttpPool = AbHttpPool.getInstance();
		
		noView = LayoutInflater.from(this).inflate(R.layout.no_data, null);
		
		// (1)��������
		titles = new String[] { "����1", "����2", "����3", "����4","����5"};
		// (2)�����б�����
		contents = new ArrayList<String[]>();
		// (3)����������
		cellTypes = new int[] { AbCellType.STRING, AbCellType.STRING, AbCellType.STRING, AbCellType.STRING,AbCellType.STRING};
		// (4)�п�����(%) ����100% ���Ժ��򻬶�
		cellWidth = new int[] {50,25,25,25,25};
		// (5)�иߣ�����0������ߣ�1�������б�ߣ�
		rowHeight = new int[] { 35, 35 };
		// (6)�����ִ�С������0���⣬1�����б�
		rowTextSize = new int[] { 15, 12};
		// (7)��������ɫ������0���⣬1�����б�
		rowTextColor = new int[] {Color.rgb(255, 255, 255),Color.rgb(113, 113, 113) };
		// (8)������Դ
		tableResource = new int[] {android.R.color.transparent,R.drawable.title_cell,android.R.color.transparent,R.drawable.content_cell};
		// (9)���ʵ��
		table = AbTable.newAbTable(this,5); 
		table.setTitles(titles);
		table.setContents(contents);
		table.setCellTypes(cellTypes);
		table.setCellWidth(cellWidth);
		table.setRowHeight(rowHeight);
		table.setRowTextSize(rowTextSize);
		table.setTableResource(tableResource);
		table.setRowTextColor(rowTextColor);
		
		// (10)TableAdapter����
		tableAdapter = new AbTableArrayAdapter(TableDataListActivity.this, table);
		// (12)ListView
		mListView = (ListView)findViewById(R.id.mListView);
		// (11)����Adapter
		mListView.setAdapter(tableAdapter);
		// -------------- ���ؼ�-------end------------------
		// ����¼�
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				
			}
		});
		
		loadData();
	}

	
	public void loadData(){
		
		//��ѯ����
		showDialog(0);
		final AbHttpItem item = new AbHttpItem();
		item.callback = new AbHttpCallback() {

			@Override
			public void update() {
				removeDialog(0);
				
				if (mStockList != null && mStockList.size() > 0) {
					contents.clear();
					Stock mStock = null;
					for (int i = 0; i < mStockList.size(); i++) {
						mStock = mStockList.get(i);
						String[] data1 = new String[] {mStock.id,mStock.text1,mStock.text2,mStock.text3,mStock.text4};
						contents.add(data1);
					}
					tableAdapter.notifyDataSetChanged();
				} else {
					contentLayout.removeAllViews();
					contentLayout.addView(noView,layoutParamsFF);
				}
			}

			@Override
			public void get() {
				try {
					mStockList = new ArrayList<Stock> ();
					Stock mStock1 = null;
					for(int i=0;i<20;i++){
						mStock1 = new Stock();
						mStock1.setId(String.valueOf(i));
						mStock1.setText1("Text1");
						mStock1.setText2("Text2");
						mStock1.setText3("Text3");
						mStock1.setText4("Text4");
						mStock1.setText5("Text5");
						mStockList.add(mStock1);
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
					showToastInThread(e.getMessage());
				}
		  };
		};
		mAbHttpPool.download(item);
		
	}
	
	
	
}
