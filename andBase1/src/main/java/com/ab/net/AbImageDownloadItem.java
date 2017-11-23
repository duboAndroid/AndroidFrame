/*
 * 
 */
package com.ab.net;

import android.graphics.Bitmap;

// TODO: Auto-generated Javadoc
/**
 * 描述：图片下载单位.
 *
 * @author zhaoqp
 * @date 2011-12-10
 * @version v1.0
 */
public class AbImageDownloadItem {
	
	/** 需要下载的图片的互联网地址. */
	public String imageUrl;
	
	/** 显示的图片的宽. */
	public int width;
	
	/** 显示的图片的高. */
	public int height;
	
	/** 图片的处理类型（剪切或者缩放到指定大小，参考AbConstant类）. */
	public int type;
	
	/** 下载完成的到的Bitmap对象. */
	public Bitmap bitmap;
	
	/** 下载完成的回调接口. */
	public AbImageDownloadCallback callback;

}
