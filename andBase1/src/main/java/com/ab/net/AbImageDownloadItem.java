/*
 * 
 */
package com.ab.net;

import android.graphics.Bitmap;

// TODO: Auto-generated Javadoc
/**
 * ������ͼƬ���ص�λ.
 *
 * @author zhaoqp
 * @date 2011-12-10
 * @version v1.0
 */
public class AbImageDownloadItem {
	
	/** ��Ҫ���ص�ͼƬ�Ļ�������ַ. */
	public String imageUrl;
	
	/** ��ʾ��ͼƬ�Ŀ�. */
	public int width;
	
	/** ��ʾ��ͼƬ�ĸ�. */
	public int height;
	
	/** ͼƬ�Ĵ������ͣ����л������ŵ�ָ����С���ο�AbConstant�ࣩ. */
	public int type;
	
	/** ������ɵĵ���Bitmap����. */
	public Bitmap bitmap;
	
	/** ������ɵĻص��ӿ�. */
	public AbImageDownloadCallback callback;

}
