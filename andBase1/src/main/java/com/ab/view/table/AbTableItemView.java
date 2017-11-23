/*
 * 
 */
package com.ab.view.table;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.global.AbAppData;
import com.ab.view.AbOnItemClickListener;

// TODO: Auto-generated Javadoc
/**
 * ���������һ�е���ͼ.
 *
 * @author zhaoqp
 * @date��2013-1-28 ����3:56:29
 * @version v1.0
 */
public class AbTableItemView extends LinearLayout {
	
	/** The tag. */
	private static String TAG = "AbTableItemView";
	
	/** The Constant D. */
	private static final boolean D = AbAppData.DEBUG;
	
	/** The m context. */
	private Context mContext;
	//���еĵ�Ԫ������
	/** The cell count. */
	private int cellCount;
	//���е�Ԫ���ı�����
	/** The row cell. */
	private View[] rowCell;
	
	/** View���б��е�λ��. */
	private int mPosition;
	
	/** The m table. */
	private AbTable mTable = null;
	
	/** The m adapter. */
	private AbTableArrayAdapter mAdapter = null;
	
	/**
	 * ����һ�е�View.
	 *
	 * @param context  Context
	 * @param adapter the adapter
	 * @param position the position
	 * @param tableRow ������
	 * @param table the table
	 */
	public AbTableItemView(Context context,AbTableArrayAdapter adapter,int position,AbTableRow tableRow,AbTable table) {
		super(context);
		mPosition = position;
		mContext = context;
		mTable = table;
		mAdapter = adapter;
		//ˮƽ����
		this.setOrientation(LinearLayout.HORIZONTAL);
		//��ʼ�������е�����
		cellCount = tableRow.getCellSize();
		//��ʼ�����е�Ԫ���ı�View����
		rowCell = new View[cellCount];
		//����Ԫ�������ӵ���
		LinearLayout.LayoutParams layoutParams = null;
		for (int i = 0; i < cellCount; i++) {
			    final int index = i;
				final AbTableCell tableCell = tableRow.getCellValue(index);
				//���ո�Ԫָ���Ĵ�С���ÿռ�
				layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, tableRow.height);
				if (tableCell.type == AbCellType.STRING) {
						TextView textCell = new TextView(mContext);
						textCell.setMinimumWidth(tableCell.width);
						textCell.setMinimumHeight(tableRow.height);
						if(D)Log.d(TAG, "�иߣ�"+tableRow.height);
						textCell.setLines(1);
						textCell.setGravity(Gravity.CENTER);
						textCell.setTextColor(tableRow.textColor);
						if(mPosition==0){
						   if(D)Log.d(TAG, "����������ɫ��"+tableRow.textColor);
						   //����
					       textCell.setTypeface(Typeface.DEFAULT_BOLD);
					       textCell.setBackgroundResource(mTable.getTableResource()[1]);
						}else{
						  //��ͨ����
						  textCell.setTypeface(Typeface.DEFAULT);
						  textCell.setBackgroundResource(mTable.getTableResource()[3]);
						}
						//���õ�Ԫ������
						textCell.setText(String.valueOf(tableCell.value));
						textCell.setTextSize(tableRow.textSize);
						rowCell[i] = textCell;
						addView(textCell, layoutParams);
				} 
				//�����Ԫ��ͼ������
				else if (tableCell.type == AbCellType.IMAGE) {
					//���ո�Ԫָ���Ĵ�С���ÿռ�
					LinearLayout mLinearLayout = new LinearLayout(mContext);
					mLinearLayout.setMinimumWidth(tableCell.width);
					ImageView imgCell = new ImageView(mContext);
					if(mPosition==0){
						imgCell.setImageDrawable(null);
						mLinearLayout.setGravity(Gravity.CENTER);
						mLinearLayout.addView(imgCell,layoutParams);
						mLinearLayout.setBackgroundResource(mTable.getTableResource()[1]);
						addView(mLinearLayout, layoutParams);
					}else{
						imgCell.setImageResource((int)Integer.parseInt((String)tableCell.value));
						mLinearLayout.setGravity(Gravity.CENTER);
						mLinearLayout.addView(imgCell,layoutParams);
						mLinearLayout.setBackgroundResource(mTable.getTableResource()[3]);
						addView(mLinearLayout, layoutParams);
						imgCell.setOnTouchListener(new View.OnTouchListener(){

							@Override
							public boolean onTouch(View v, MotionEvent event) {
								if(event.getAction()==MotionEvent.ACTION_DOWN){
									AbOnItemClickListener mAbOnItemClickListener = mTable.getItemCellTouchListener();
									if(mAbOnItemClickListener!=null){
										mAbOnItemClickListener.onClick(mPosition);
									}
								}
								return false;
							}
							
						});
					}
					rowCell[i] = imgCell;
					
				//�����Ԫ�Ǹ�ѡ��	
				}else if (tableCell.type == AbCellType.CHECKBOX) {
					
					LinearLayout mLinearLayout = new LinearLayout(mContext);
					mLinearLayout.setMinimumWidth(tableCell.width);
					final CheckBox mCheckBox = new CheckBox(context);
					mCheckBox.setGravity(Gravity.CENTER);
					//����������¼�
					mCheckBox.setOnCheckedChangeListener(null);
					mCheckBox.setFocusable(false);
					int isCheck = Integer.parseInt(String.valueOf(tableCell.value));
					if(isCheck==1){
						mCheckBox.setChecked(true);
					}else{
						mCheckBox.setChecked(false);
					}
					if(mPosition==0){
						mLinearLayout.setGravity(Gravity.CENTER);
						mLinearLayout.addView(mCheckBox,layoutParams);
						mLinearLayout.setBackgroundResource(mTable.getTableResource()[1]);
						addView(mLinearLayout, layoutParams);
					}else{
						mLinearLayout.setGravity(Gravity.CENTER);
						mLinearLayout.addView(mCheckBox,layoutParams);
						mLinearLayout.setBackgroundResource(mTable.getTableResource()[3]);
						addView(mLinearLayout, layoutParams);
					}
								
					mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							
							if(mPosition==0){
								//ȫѡ
								if (isChecked) {
									mTable.getTitles()[index]= "1";
									for (int i = 0; i < mTable.getContents().size(); i++) {
										mTable.getContents().get(i)[index]= "1";
									}
								} else {
									mTable.getTitles()[index]= "0";
									for (int i = 0; i < mTable.getContents().size(); i++) {
										mTable.getContents().get(i)[index]= "0";
									}
								}
							}else{
								//����
								if (isChecked) {
									mTable.getContents().get(mPosition-1)[index]= "1";
								} else {
									mTable.getContents().get(mPosition-1)[index]= "0";
								}
							}
							mAdapter.notifyDataSetChanged();
							AbOnItemClickListener itemCellCheckListener = mTable.getItemCellCheckListener();
							if(itemCellCheckListener!=null){
								itemCellCheckListener.onClick(mPosition);
							}
							
						}
					});
					rowCell[index] = mCheckBox;		
				}
		}
	}
	
	/**
	 * ���±��һ������.
	 *
	 * @param position the position
	 * @param tableRow �е�����
	 */
	public void setTableRowView(int position,AbTableRow tableRow){
		    mPosition = position;
			for (int i = 0; i < cellCount; i++) {
				final int index = i;
				final AbTableCell tableCell = tableRow.getCellValue(index);
				if (tableCell.type == AbCellType.STRING) {
					TextView textCell = (TextView)rowCell[index];
					textCell.setMinimumWidth(tableCell.width);
					textCell.setMinimumHeight(tableRow.height);
					textCell.setLines(1);
					textCell.setGravity(Gravity.CENTER);
					textCell.setText(String.valueOf(tableCell.value));
					textCell.setTextColor(tableRow.textColor);
					textCell.setTextSize(tableRow.textSize);
					if(mPosition==0){
					   //����
				       textCell.setTypeface(Typeface.DEFAULT_BOLD);
				       textCell.setBackgroundResource(mTable.getTableResource()[1]);
					}else{
					  //��ͨ����
					  textCell.setTypeface(Typeface.DEFAULT);
					  textCell.setBackgroundResource(mTable.getTableResource()[3]);
					}
					//�����Ԫ��ͼ������
				}else if (tableCell.type == AbCellType.IMAGE) {
					if(mPosition==0){
						ImageView imgCell = (ImageView)rowCell[index];
						imgCell.setImageDrawable(null);
						((LinearLayout)imgCell.getParent()).setBackgroundResource(mTable.getTableResource()[1]);
					}else{
						ImageView imgCell = (ImageView)rowCell[index];
						((LinearLayout)imgCell.getParent()).setBackgroundResource(mTable.getTableResource()[3]);
						imgCell.setImageResource((int)Integer.parseInt((String)tableCell.value));
						imgCell.setOnTouchListener(new View.OnTouchListener(){

							@Override
							public boolean onTouch(View v, MotionEvent event) {
								if(event.getAction()==MotionEvent.ACTION_DOWN){
									AbOnItemClickListener mAbOnItemClickListener = mTable.getItemCellTouchListener();
									if(mAbOnItemClickListener!=null){
										mAbOnItemClickListener.onClick(mPosition);
									}
								}
								return false;
							}
							
						});
					}
						
				}else if (tableCell.type == AbCellType.CHECKBOX) {
					final CheckBox mCheckBox = (CheckBox)rowCell[index];
					//����������¼�
					mCheckBox.setOnCheckedChangeListener(null);
					int isCheck = Integer.parseInt(String.valueOf(tableCell.value));
					
					if(isCheck==1){
						mCheckBox.setChecked(true);
					}else{
						mCheckBox.setChecked(false);
					}
					if(mPosition==0){
						((LinearLayout)mCheckBox.getParent()).setBackgroundResource(mTable.getTableResource()[1]);
					}else{
						((LinearLayout)mCheckBox.getParent()).setBackgroundResource(mTable.getTableResource()[3]);
					}
					mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if(mPosition==0){
								//ȫѡ
								if (isChecked) {
									mTable.getTitles()[index]= "1";
									for (int i = 0; i < mTable.getContents().size(); i++) {
										mTable.getContents().get(i)[index]= "1";
									}
								} else {
									mTable.getTitles()[index]= "0";
									for (int i = 0; i < mTable.getContents().size(); i++) {
										mTable.getContents().get(i)[index]= "0";
									}
								}
							}else{
								//����
								if (isChecked) {
									mTable.getContents().get(mPosition-1)[index]= "1";
								} else {
									mTable.getContents().get(mPosition-1)[index]= "0";
								}
							}
							mAdapter.notifyDataSetChanged();
							AbOnItemClickListener itemCellCheckListener = mTable.getItemCellCheckListener();
							if(itemCellCheckListener!=null){
								itemCellCheckListener.onClick(mPosition);
							}
						}
					});
					mCheckBox.setFocusable(false);
				}
			}
		}
}


