/*
 * 
 */
package com.ab.view.table;

// TODO: Auto-generated Javadoc
/**
 * ����������һ����Ԫ��.
 *
 * @author zhaoqp
 * @date��2013-1-28 ����3:56:18
 * @version v1.0
 */
public class AbTableCell {
	
	/** ��������ȡֵ. */
	public Object value;
	
	/** �п�. */
	public int width;
	
	/** ��Ԫ������. */
	public int type;

	/**
	 * һ����Ԫ��.
	 *
	 * @param value ��Ԫ���ֵ
	 * @param width �п�
	 * @param type ��Ԫ������
	 */
	public AbTableCell(Object value, int width,int type) {
		this.value = value;
		this.width = width;
		this.type = type;
	}

}
