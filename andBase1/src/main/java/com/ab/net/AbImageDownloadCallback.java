/*
 * 
 */
package com.ab.net;

import android.graphics.Bitmap;

// TODO: Auto-generated Javadoc
/**
 * ������ͼƬ���صĻص��ӿ�.
 *
 * @author zhaoqp
 * @date 2011-12-10
 * @version v1.0
 */
public interface AbImageDownloadCallback {
	
	/**
	 * ������������ͼ.
	 *
	 * @param bitmap ���ط��ص�Bitmap
	 * @param imageUrl ԭ���������ص�ַ
	 */
    public void update(Bitmap bitmap, String imageUrl); 

}
