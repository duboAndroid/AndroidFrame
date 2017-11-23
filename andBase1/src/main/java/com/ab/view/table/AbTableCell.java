/*
 * 
 */
package com.ab.view.table;

// TODO: Auto-generated Javadoc
/**
 * 描述：表格的一个单元格.
 *
 * @author zhaoqp
 * @date：2013-1-28 下午3:56:18
 * @version v1.0
 */
public class AbTableCell {
	
	/** 按列类型取值. */
	public Object value;
	
	/** 列宽. */
	public int width;
	
	/** 单元格类型. */
	public int type;

	/**
	 * 一个单元格.
	 *
	 * @param value 单元格的值
	 * @param width 列宽
	 * @param type 单元格类型
	 */
	public AbTableCell(Object value, int width,int type) {
		this.value = value;
		this.width = width;
		this.type = type;
	}

}
