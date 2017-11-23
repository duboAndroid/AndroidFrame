/*
 * 
 */

package com.ab.view.wheel;

// TODO: Auto-generated Javadoc
/**
 * Numeric Wheel adapter.
 */
public class AbNumericWheelAdapter implements AbWheelAdapter {

	/** The default min value. */
	public static final int DEFAULT_MAX_VALUE = 9;

	/** The default max value. */
	private static final int DEFAULT_MIN_VALUE = 0;

	// Values
	/** The min value. */
	private int minValue;

	/** The max value. */
	private int maxValue;

	// format
	/** The format. */
	private String format;

	/**
	 * Default constructor.
	 */
	public AbNumericWheelAdapter() {
		this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
	}

	/**
	 * Constructor.
	 *
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 */
	public AbNumericWheelAdapter(int minValue, int maxValue) {
		this(minValue, maxValue, null);
	}

	/**
	 * Constructor.
	 *
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 * @param format the format string
	 */
	public AbNumericWheelAdapter(int minValue, int maxValue, String format) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.format = format;
	}

	/**
	 * ������TODO.
	 *
	 * @param index the index
	 * @return the item
	 * @see com.ab.view.wheel.AbWheelAdapter#getItem(int)
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:51
	 * @version v1.0
	 */
	@Override
	public String getItem(int index) {
		if (index >= 0 && index < getItemsCount()) {
			int value = minValue + index;
			return format != null ? String.format(format, value) : Integer.toString(value);
		}
		return null;
	}

	/**
	 * ������TODO.
	 *
	 * @return the items count
	 * @see com.ab.view.wheel.AbWheelAdapter#getItemsCount()
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:51
	 * @version v1.0
	 */
	@Override
	public int getItemsCount() {
		return maxValue - minValue + 1;
	}

	/**
	 * ������TODO.
	 *
	 * @return the maximum length
	 * @see com.ab.view.wheel.AbWheelAdapter#getMaximumLength()
	 * @author: zhaoqp
	 * @date��2013-6-17 ����9:04:51
	 * @version v1.0
	 */
	@Override
	public int getMaximumLength() {
		int max = Math.max(Math.abs(maxValue), Math.abs(minValue));
		int maxLen = Integer.toString(max).length();
		if (minValue < 0) {
			maxLen++;
		}
		return maxLen;
	}
}
