/*
 * 
 */
package com.ab.net;

import java.lang.ref.SoftReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ab.global.AbAppData;
import com.ab.util.AbAppUtil;
import com.ab.util.AbFileUtil;
import com.ab.util.AbStrUtil;
// TODO: Auto-generated Javadoc
/**
 * 
 * Copyright (c) 2012 All rights reserved
 * 名称：AbImageDownload.java 
 * 描述：线程池图片下载
 * @author zhaoqp
 * @date：2013-5-23 上午10:10:53
 * @version v1.0
 */

public class AbImageDownloadPool{
	
	/** The tag. */
	private static String TAG = "AbImageDownloadPool";
	
	/** The Constant D. */
	private static final boolean D = AbAppData.DEBUG;
	
	//单例对象
	/** The image download. */
	private static AbImageDownloadPool imageDownload = null; 
	
	/** The n threads. */
	private static int nThreads  = 5;
	// 固定五个线程来执行任务 
	/** The executor service. */
	private ExecutorService executorService = null; 
	
	/** 下载完成后的消息句柄. */
    private static Handler handler = new Handler() { 
        @Override 
        public void handleMessage(Message msg) { 
            AbImageDownloadItem item = (AbImageDownloadItem)msg.obj; 
            item.callback.update(item.bitmap, item.imageUrl); 
        } 
    }; 
	
	/**
	 * 构造图片下载器.
	 *
	 * @param nThreads the n threads
	 */
    protected AbImageDownloadPool(int nThreads) {
    	executorService = Executors.newFixedThreadPool(nThreads); 
    } 
	
	/**
	 * 单例构造图片下载器.
	 *
	 * @return single instance of AbImageDownloadPool
	 */
    public static AbImageDownloadPool getInstance() { 
        if (imageDownload == null) { 
        	nThreads = AbAppUtil.getNumCores();
        	imageDownload = new AbImageDownloadPool(nThreads*5); 
        } 
        return imageDownload;
    } 
    
    /**
     * Download.
     *
     * @param item the item
     */
    public void download(final AbImageDownloadItem item) {    
    	String urlImage = item.imageUrl;
    	if(AbStrUtil.isEmpty(urlImage)){
    		if(D)Log.d(TAG, "图片URL为空，请先判断");
    	}else{
    		urlImage = urlImage.trim();
    	}
    	final String url = urlImage;
    	if(AbFileUtil.getFileNameFromUrl(url) != null){
    		// 如果缓存过就从缓存中取出数据                
        	if (AbImageCache.imageCache.containsKey(url)) {                         
        		SoftReference<Bitmap> softReference = AbImageCache.imageCache.get(url);
        		item.bitmap =  softReference.get(); 
        		if(D) Log.d(TAG, "缓存中获取的:"+item.bitmap);
        	}
        	
        	if(item.bitmap == null){
        		// 缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
    	    	executorService.submit(new Runnable() { 
    	    		public void run() {
    	    			try {
    	    				item.bitmap = AbFileUtil.getBitmapFromSDCache(item.imageUrl,item.type,item.width,item.height);
    	    				if(D) Log.d(TAG, "下载从SD卡得到的:"+item.bitmap);
    	    				AbImageCache.imageCache.put(url, new SoftReference<Bitmap>(item.bitmap));                                           
    	    				if (item.callback != null) { 
    	    	                Message msg = handler.obtainMessage(); 
    	    	                msg.obj = item; 
    	    	                handler.sendMessage(msg); 
    	    	            } 
    	    			} catch (Exception e) { 
    	    				e.printStackTrace();
    	    			}                         
    	    		}                 
    	    	});  
        	}else{
        		if (item.callback != null) { 
                    Message msg = handler.obtainMessage(); 
                    msg.obj = item; 
                    handler.sendMessage(msg); 
                } 
        	}
    	}else{
    		if(D) Log.d(TAG, "图片链接不合法:"+url);
    		if (item.callback != null) { 
                Message msg = handler.obtainMessage(); 
                msg.obj = item; 
                handler.sendMessage(msg); 
            } 
    	}
    	
    }
    
    /**
     * 描述：立即关闭.
     */
    public void shutdownNow(){
    	if(!executorService.isTerminated()){
    		executorService.shutdownNow();
    		listenShutdown();
    	}
    	
    }
    
    /**
     * 描述：平滑关闭.
     */
    public void shutdown(){
    	if(!executorService.isTerminated()){
    	   executorService.shutdown();
    	   listenShutdown();
    	}
    }
    
    /**
     * 描述：关闭监听.
     */
    public void listenShutdown(){
    	try {
			while(!executorService.awaitTermination(1, TimeUnit.MILLISECONDS)) { 
				if(D) Log.d(TAG, "线程池未关闭");
			}  
			if(D) Log.d(TAG, "线程池已关闭");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
