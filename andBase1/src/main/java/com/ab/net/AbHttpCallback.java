/*
 * 
 */
package com.ab.net;

// TODO: Auto-generated Javadoc
/**
 * 描述：数据下载的接口.
 *
 * @author zhaoqp
 * @date 2011-12-10
 * @version v1.0
 */
public interface AbHttpCallback {
	
	/**
	 * 描述：下载开始后回调.
	 */
	public void get(); 
	
	/**
	 * 描述：下载完成后回调.
	 */
    public void update(); 

}
