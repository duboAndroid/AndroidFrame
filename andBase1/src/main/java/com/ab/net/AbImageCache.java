/*
 * 
 */
package com.ab.net;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;

// TODO: Auto-generated Javadoc
/**
 * ������ͼƬ����.
 *
 * @author zhaoqp
 * @date��2013-5-23 ����10:10:53
 * @version v1.0
 */

public class AbImageCache {

	// Ϊ�˼ӿ��ٶȣ����ڴ��п������棨��ҪӦ�����ظ�ͼƬ�϶�ʱ��
	//����ͬһ��ͼƬҪ��α����ʣ�������ListViewʱ���ع�����
	/** The image cache. */
	public static HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();

}
