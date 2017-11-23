/*
 * 
 */
package com.ab.net;

import android.graphics.Bitmap;

// TODO: Auto-generated Javadoc
/**
 * 描述：图片下载的回调接口.
 *
 * @author zhaoqp
 * @date 2011-12-10
 * @version v1.0
 */
public interface AbImageDownloadCallback {
	
	/**
	 * 描述：更新视图.
	 *
	 * @param bitmap 下载返回的Bitmap
	 * @param imageUrl 原互联网下载地址
	 */
    public void update(Bitmap bitmap, String imageUrl); 

}
