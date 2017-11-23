/*
 * 
 */
package com.ab.view.table;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.ab.view.AbOnItemClickListener;
// TODO: Auto-generated Javadoc
/**
 * ���������ؼ�ʵ����
 *  <p>(1)��������
	<p>titles = new String[] { "����1", "����2", "����3", "����4","����5"};
	<p>(2)�����б�����(��ʼΪ�յ�)
	<p>contents = new ArrayList<String[]>();
	<p>(3)���������ã�����AbCellType��
	<p>cellTypes = new int[] { AbCellType.STRING, AbCellType.STRING, AbCellType.STRING, AbCellType.STRING,AbCellType.STRING};
	<p>(4)�п�����(%) ����100% ���Ժ��򻬶�
	<p>cellWidth = new int[] {20,50,10,20,50};
	<p>(5)�иߣ�����0������ߣ�1�������б�ߣ�
	<p>rowHeight = new int[] { 35, 35 };
	<p>(6)�����ִ�С������0���⣬1�����б�
	<p>rowTextSize = new int[] { 15, 12};
	<p>(7)��������ɫ������0���⣬1�����б�
	<p>rowTextColor = new int[] {Color.rgb(255, 255, 255),Color.rgb(113, 113, 113) };
	<p>(8)������Դ������0�����б�����1���ⵥԪ�񱳾���2�����б��б�����3������ݵ�Ԫ�񱳾���
	<p>tableResource = new int[] {android.R.color.transparent,R.drawable.title_cell,android.R.color.transparent,R.drawable.content_cell};
	<p> (9)���ʵ�壨ͨ��newAbTableʵ��������ʼ�����������Զ����AbTable�����ã�
	<p>table = AbTable.newAbTable(this,5); 
	<p>table.setTitles(titles);
	<p>table.setContents(contents);
	<p>table.setCellTypes(cellTypes);
	<p>table.setCellWidth(cellWidth);
	<p>table.setRowHeight(rowHeight);
	<p>table.setRowTextSize(rowTextSize);
	<p>table.setTableResource(tableResource);
	<p>table.setRowTextColor(rowTextColor);
	<p>(10)AbTableArrayAdapter����
	<p>mAbTableArrayAdapter = new AbTableArrayAdapter(this, table);
	<p>(12)ListView�����ֲ��գ���
	<p> &nbsp;&nbsp; < HorizontalScrollView
    <p> &nbsp;&nbsp;&nbsp;&nbsp; android:id="@+id/horView"
    <p> &nbsp;&nbsp;&nbsp;&nbsp; android:layout_width="fill_parent"
    <p> &nbsp;&nbsp;&nbsp;&nbsp; android:layout_height="fill_parent" >
    <p>
    <p> &nbsp;&nbsp;&nbsp;&nbsp; <ListView
    <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  android:id="@+id/mListView"
    <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; android:layout_width="wrap_content"
    <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; android:layout_height="wrap_content"
    <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; android:cacheColorHint="#00000000"
    <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; android:divider="@android:color/transparent"
    <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; android:dividerHeight="0dip" >
    <p> &nbsp;&nbsp;&nbsp;&nbsp;  < /ListView>
    <p> &nbsp;&nbsp;< /HorizontalScrollView>
	<p>mListView = (ListView)findViewById(R.id.mListView);
	<p>(11)����Adapter
	<p>mListView.setAdapter(tableAdapter);
 * @author zhaoqp
 * @date��2013-1-28 ����3:54:41
 * @version v1.0
 */
public class AbTable {
	
	/** AbTableʵ��. */
	private static AbTable mAbTable = null;
	
	/** ��Ļ�Ŀ�. */
	private static int mScreenWidth = 0;
	
	/** ��Ļ�ĸ�. */
	private static int mScreenHeight = 0;
	
	/** ��������. */
	private String[] titles;
	
	/** �����б�����. */
	private List<String[]> contents;
	
	/** ��Ԫ����������. */
	private int[] cellTypes;
	
    /** ��Ԫ��������. */
	private int[] cellWidth;
	
	/** �и߶����飨����0���⣬1�����б�. */
	private int[]  rowHeight;
	
	/** �����ִ�С���飨����0���⣬1�����б�. */
	private int[] rowTextSize;
	
	/** ��������ɫ���飨����0���⣬1�����б�. */
	private int[] rowTextColor;
	
	/** �����Դ���飨����0�����б�����1���ⵥԪ�񱳾���2�����б��б�����3������ݵ�Ԫ�񱳾���. */
	private int[] tableResource;
	
	/** The item cell touch listener. */
	private AbOnItemClickListener itemCellTouchListener;
	
	/** The item cell check listener. */
	private AbOnItemClickListener itemCellCheckListener;
	
	
	/**
	 * ��������������.
	 *
	 * @param context context����
	 * @param columnSize ����
	 * @return the ab table
	 */
	public static AbTable newAbTable(Context context,int columnSize) {
		if(columnSize<=0){
			Toast.makeText(context,"�����ñ�������>0!", Toast.LENGTH_SHORT).show();
		    return null;
		}
		WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		mScreenWidth = display.getWidth();
		mScreenHeight = display.getHeight();
		
		mAbTable = new AbTable();
		//��������
		mAbTable.titles = new String[columnSize];
		for(int i=0;i<columnSize;i++){
			mAbTable.titles[i] = "����"+i;
		}
		//����������
		mAbTable.cellTypes = new int[columnSize];
		for(int i=0;i<columnSize;i++){
			mAbTable.cellTypes[i] = AbCellType.STRING;
		}
		
		//�п�����(%)
		mAbTable.cellWidth = new int[columnSize];
		for(int i=0;i<columnSize;i++){
			mAbTable.cellWidth[i] = mScreenWidth/columnSize;
		}
		
		//�и����ã�����0���⣬1�����б�
		mAbTable.rowHeight = new int[] { 30, 30 };
		//�����ִ�С������0���⣬1�����б�
		mAbTable.rowTextSize = new int[] { 18, 16};
		//��������ɫ������0���⣬1�����б�
		mAbTable.rowTextColor = new int[] { Color.rgb(113, 113, 113), Color.rgb(113, 113, 113) };
		//������Դ������0�����б�����1���ⵥԪ�񱳾���2�����б��б�����3������ݵ�Ԫ�񱳾���
		mAbTable.tableResource = new int[] {android.R.color.transparent,android.R.color.transparent,android.R.color.transparent,android.R.color.transparent};
		
		return mAbTable;
	}


	/**
	 * ��������ȡ���ı���.
	 *
	 * @return the titles
	 */
	public String[] getTitles() {
		return titles;
	}

	/**
	 * ���������ñ��ı���.
	 *
	 * @param titles ���ı�������
	 */
	public void setTitles(String[] titles) {
		for(int i=0;i<titles.length;i++){
			mAbTable.titles[i] = titles[i];
		}
	}

	/**
	 * Gets the contents.
	 *
	 * @return the contents
	 */
	public List<String[]> getContents() {
		return contents;
	}
	
	/**
	 * �����������б����ݵ�����.
	 *
	 * @param contents �б����ݵ�����
	 */
	public void setContents(List<String[]> contents) {
		this.contents = contents;
	}

	/**
	 * Gets the cell types.
	 *
	 * @return the cell types
	 */
	public int[] getCellTypes() {
		return cellTypes;
	}

	/**
	 * ���������������ã�����AbCellType��.
	 *
	 * @param cellTypes ������
	 */
	public void setCellTypes(int[] cellTypes) {
		this.cellTypes = cellTypes;
	}

	/**
	 * Gets the cell width.
	 *
	 * @return the cell width
	 */
	public int[] getCellWidth() {
		return cellWidth;
	}

    /**
     * �������п�����(%) ����100% ���Ժ��򻬶�.
     *
     * @param cellWidth �п�İٷֱ�
     */
	public void setCellWidth(int[] cellWidth) {
		for(int i=0;i<cellWidth.length;i++){
			mAbTable.cellWidth[i] = mScreenWidth*cellWidth[i]/100;
		}
	}

	/**
	 * Gets the row height.
	 *
	 * @return the row height
	 */
	public int[] getRowHeight() {
		return rowHeight;
	}

	/**
	 * �����������иߣ�����0������ߣ�1�������б�ߣ�.
	 *
	 * @param rowHeight �и�
	 */
	public void setRowHeight(int[] rowHeight) {
		for(int i=0;i<rowHeight.length;i++){
			mAbTable.rowHeight[i] = rowHeight[i];
		}
	}

	/**
	 * Gets the row text size.
	 *
	 * @return the row text size
	 */
	public int[] getRowTextSize() {
		return rowTextSize;
	}

	/**
	 * ���������������ֵĴ�С������0���⣬1�����б�.
	 *
	 * @param rowTextSize  �����ֵĴ�С
	 */
	public void setRowTextSize(int[] rowTextSize) {
		for(int i=0;i<rowTextSize.length;i++){
			mAbTable.rowTextSize[i] = rowTextSize[i];
		}
	}


	/**
	 * Gets the row text color.
	 *
	 * @return the row text color
	 */
	public int[] getRowTextColor() {
		return rowTextColor;
	}

	/**
	 * ���������������ֵ���ɫ.
	 *
	 * @param rowTextColor  �����ֵ���ɫ
	 */
	public void setRowTextColor(int[] rowTextColor) {
		for(int i=0;i<rowTextColor.length;i++){
			mAbTable.rowTextColor[i] = rowTextColor[i];
		}
	}


	/**
	 * Gets the table resource.
	 *
	 * @return the table resource
	 */
	public int[] getTableResource() {
		return tableResource;
	}

	/**
	 * ���������ı�����Դ������0�����б�����1���ⵥԪ�񱳾���2�����б��б�����3������ݵ�Ԫ�񱳾���.
	 *
	 * @param tableResource the new table resource
	 */
	public void setTableResource(int[] tableResource) {
		for(int i=0;i<tableResource.length;i++){
			this.tableResource[i] = tableResource[i];
		}
	}


	/**
	 * Gets the item cell touch listener.
	 *
	 * @return the item cell touch listener
	 */
	public AbOnItemClickListener getItemCellTouchListener() {
		return itemCellTouchListener;
	}

	/**
	 * ������ͼƬcell��һ���е�ĳһ��cell�����.
	 *
	 * @param itemCellTouchListener the new item cell touch listener
	 */
	public void setItemCellTouchListener(AbOnItemClickListener itemCellTouchListener) {
		this.itemCellTouchListener = itemCellTouchListener;
	}


	/**
	 * Gets the item cell check listener.
	 *
	 * @return the item cell check listener
	 */
	public AbOnItemClickListener getItemCellCheckListener() {
		return itemCellCheckListener;
	}

	/**
	 * ��������ѡ��cell��һ���е�ĳһ��cell�����.
	 *
	 * @param itemCellCheckListener the new item cell check listener
	 */
	public void setItemCellCheckListener(AbOnItemClickListener itemCellCheckListener) {
		this.itemCellCheckListener = itemCellCheckListener;
	}
	
	
	
	 
	
}

