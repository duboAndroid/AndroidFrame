/*
 * 
 */

package com.ab.view.wheel;

import java.util.List;

import com.ab.util.AbStrUtil;

// TODO: Auto-generated Javadoc
/**
 * The simple Array wheel adapter.
 */
public class AbStringWheelAdapter implements AbWheelAdapter {
	
	/** The default items length. */
	public static final int DEFAULT_LENGTH = -1;
	// items
	/** The items. */
	private List<String> items;
	// length
	/** The length. */
	private int length = -1;
	
	/**
	 * Constructor.
	 *
	 * @param items the items
	 * @param length the max items length
	 */
	public AbStringWheelAdapter(List<String> items, int length) {
		this.items = items;
		this.length = length;
	}
	
	/**
	 * Constructor.
	 *
	 * @param items the items
	 */
	public AbStringWheelAdapter(List<String> items) {
		this(items, DEFAULT_LENGTH);
	}
	

	/**
	 * ÃèÊö£ºTODO.
	 *
	 * @param index the index
	 * @return the item
	 * @see com.ab.view.wheel.AbWheelAdapter#getItem(int)
	 * @author: zhaoqp
	 * @date£º2013-6-17 ÉÏÎç9:04:49
	 * @version v1.0
	 */
	@Override
	public String getItem(int index) {
		if (index >= 0 && index < items.size()) {
			return items.get(index);
		}
		return null;
	}

	/**
	 * ÃèÊö£ºTODO.
	 *
	 * @return the items count
	 * @see com.ab.view.wheel.AbWheelAdapter#getItemsCount()
	 * @author: zhaoqp
	 * @date£º2013-6-17 ÉÏÎç9:04:49
	 * @version v1.0
	 */
	@Override
	public int getItemsCount() {
		return items.size();
	}

	/**
	 * ÃèÊö£ºTODO.
	 *
	 * @return the maximum length
	 * @see com.ab.view.wheel.AbWheelAdapter#getMaximumLength()
	 * @author: zhaoqp
	 * @date£º2013-6-17 ÉÏÎç9:04:49
	 * @version v1.0
	 */
	@Override
	public int getMaximumLength() {
		if(length!=-1){
			return length;
		}
		int maxLength = 0;
		for(int i=0;i<items.size();i++){
			String cur = items.get(i);
			int l = AbStrUtil.strLength(cur);
			if(maxLength<l){
				maxLength = l;
			}
		}
		return maxLength;
	}

}
