/*
 * 
 */
package com.ab.net;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;

// TODO: Auto-generated Javadoc
/**
 * 描述：图片缓存.
 *
 * @author zhaoqp
 * @date：2013-5-23 上午10:10:53
 * @version v1.0
 */

public class AbImageCache {

	// 为了加快速度，在内存中开启缓存（主要应用于重复图片较多时，
	//或者同一个图片要多次被访问，比如在ListView时来回滚动）
	/** The image cache. */
	public static HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();

}
