/*
 * 
 */
package com.ab.view.table;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

// TODO: Auto-generated Javadoc
/**
 * ������Table�ؼ�������.
 *
 * @author zhaoqp
 * @date��2013-1-28 ����3:55:19
 * @version v1.0
 */

public class AbTableArrayAdapter extends BaseAdapter {

	/** The context. */
	private Context context;
	//����View
	/** The table view. */
	private ArrayList<View> tableView;
	
	/** ������������. */
	private String[] titles;
	//�������
	/** The contents. */
	private List<String[]>  contents;
	//��Ԫ����
	/** The columns. */
	private int columns;
	//��Ԫ����
	/** The cell width. */
	private int[] cellWidth;
	
	/** The cell types. */
	private int[] cellTypes;
	//�����Դ ������0���ⱳ����1�����б�����2��񱳾���
	/** The table resource. */
	private int[] tableResource;
	//�и߶�
	/** The row height. */
	private int[] rowHeight;
	//�����ִ�С������0���⣬1�����б�
	/** The row text size. */
	private int[] rowTextSize;
	//��������ɫ������0���⣬1�����б�
	/** The row text color. */
	private int[] rowTextColor;
	
	/** The table. */
	private AbTable table;

	/**
	 * Table�ؼ�������.
	 *
	 * @param context the context
	 * @param table  Table����
	 */
	public AbTableArrayAdapter(Context context,AbTable table) {
		this.context = context;
		tableView = new ArrayList<View>();
		setTable(table);
	}
	
	/**
	 * ����������Table����.
	 *
	 * @param table the new table
	 */
	public void setTable(AbTable table) {
		this.table = table;
		this.titles = table.getTitles();
		this.contents = table.getContents();
		this.cellTypes = table.getCellTypes();
		this.cellWidth = table.getCellWidth();
		this.rowHeight = table.getRowHeight();
		this.rowTextSize = table.getRowTextSize();
		this.rowTextColor = table.getRowTextColor();
		this.tableResource = table.getTableResource();
		this.columns = this.cellTypes.length;
		tableView.clear();
	}
	

	/**
	 * ��������ȡ����.
	 *
	 * @return the count
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return contents.size()+1;
	}

	/**
	 * ��������ȡλ��.
	 *
	 * @param position the position
	 * @return the item id
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * ��������ȡ����λ�õ�View.
	 *
	 * @param position the position
	 * @return the item
	 * @see android.widget.Adapter#getItem(int)
	 */
	public AbTableItemView getItem(int position) {
		return (AbTableItemView)tableView.get(position);
	}

	/**
	 * ����������View.
	 *
	 * @param position the position
	 * @param convertView the convert view
	 * @param parent the parent
	 * @return the view
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			//����
			if(position==0){
				AbTableCell[] tableCells = new AbTableCell[columns];
				for (int j = 0; j < columns; j++) {
					tableCells[j] = new AbTableCell(titles[j],cellWidth[j],cellTypes[j]);
				}
				convertView = new AbTableItemView(context,this,position,new AbTableRow(tableCells,rowHeight[0],rowTextSize[0],rowTextColor[0],tableResource[1]),table);
				convertView.setBackgroundResource(tableResource[0]);
			}else{
				//����
				AbTableCell[] tableCells = new AbTableCell[columns];
				String []content = contents.get(position-1);
				int size = contents.size();
				if(size>0){
						for (int j = 0; j < columns; j++) {
							tableCells[j] = new AbTableCell(content[j],cellWidth[j],cellTypes[j]);
						}
						convertView = new AbTableItemView(context,this,position,new AbTableRow(tableCells,rowHeight[1],rowTextSize[1],rowTextColor[1],tableResource[3]),table);
				}else{
					//Ĭ����ʾһ��������
			    }
				convertView.setBackgroundResource(tableResource[2]);
			}
		}else{
			if(position==0){
				//��ֵ����
			    AbTableItemView rowView = (AbTableItemView)convertView;
				//����
				AbTableCell[] tableCells = new AbTableCell[columns];
				for (int j = 0; j < columns; j++) {
					tableCells[j] = new AbTableCell(titles[j],cellWidth[j],cellTypes[j]);
				}
				rowView.setTableRowView(position,new AbTableRow(tableCells,rowHeight[0],rowTextSize[0],rowTextColor[0],tableResource[1]));
				convertView.setBackgroundResource(tableResource[0]);
			}else{
			    //��ֵ����
			    AbTableItemView rowView = (AbTableItemView)convertView;
				//����
				AbTableCell[] tableCells = new AbTableCell[columns];
				String []content = contents.get(position-1);
				int size = contents.size();
				if(size>0){
						for (int j = 0; j < columns; j++) {
							tableCells[j] = new AbTableCell(content[j],cellWidth[j],cellTypes[j]);
						}
						rowView.setTableRowView(position,new AbTableRow(tableCells,rowHeight[1],rowTextSize[1],rowTextColor[1],tableResource[3]));
				}else{
					//Ĭ����ʾһ��������
			    }
				convertView.setBackgroundResource(tableResource[2]);
			}
		}
		//���µ�Viewά����tableView
		if(tableView.size()>position){
			tableView.set(position, convertView);
		}else{
			tableView.add(position,convertView);
		}
		return convertView;
	}
	
	/**
	 * ����һ��.
	 *
	 * @param row �е�����
	 */
	public void addItem(String[] row) {
		contents.add(row);
        this.notifyDataSetChanged();
	}
	
}

