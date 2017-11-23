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
 * ���ƣ�AbImageDownload.java 
 * �������̳߳�ͼƬ����
 * @author zhaoqp
 * @date��2013-5-23 ����10:10:53
 * @version v1.0
 */

public class AbImageDownloadPool{
	
	/** The tag. */
	private static String TAG = "AbImageDownloadPool";
	
	/** The Constant D. */
	private static final boolean D = AbAppData.DEBUG;
	
	//��������
	/** The image download. */
	private static AbImageDownloadPool imageDownload = null; 
	
	/** The n threads. */
	private static int nThreads  = 5;
	// �̶�����߳���ִ������ 
	/** The executor service. */
	private ExecutorService executorService = null; 
	
	/** ������ɺ����Ϣ���. */
    private static Handler handler = new Handler() { 
        @Override 
        public void handleMessage(Message msg) { 
            AbImageDownloadItem item = (AbImageDownloadItem)msg.obj; 
            item.callback.update(item.bitmap, item.imageUrl); 
        } 
    }; 
	
	/**
	 * ����ͼƬ������.
	 *
	 * @param nThreads the n threads
	 */
    protected AbImageDownloadPool(int nThreads) {
    	executorService = Executors.newFixedThreadPool(nThreads); 
    } 
	
	/**
	 * ��������ͼƬ������.
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
    		if(D)Log.d(TAG, "ͼƬURLΪ�գ������ж�");
    	}else{
    		urlImage = urlImage.trim();
    	}
    	final String url = urlImage;
    	if(AbFileUtil.getFileNameFromUrl(url) != null){
    		// ���������ʹӻ�����ȡ������                
        	if (AbImageCache.imageCache.containsKey(url)) {                         
        		SoftReference<Bitmap> softReference = AbImageCache.imageCache.get(url);
        		item.bitmap =  softReference.get(); 
        		if(D) Log.d(TAG, "�����л�ȡ��:"+item.bitmap);
        	}
        	
        	if(item.bitmap == null){
        		// ������û��ͼ�����������ȡ�����ݣ�����ȡ�������ݻ��浽�ڴ���
    	    	executorService.submit(new Runnable() { 
    	    		public void run() {
    	    			try {
    	    				item.bitmap = AbFileUtil.getBitmapFromSDCache(item.imageUrl,item.type,item.width,item.height);
    	    				if(D) Log.d(TAG, "���ش�SD���õ���:"+item.bitmap);
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
    		if(D) Log.d(TAG, "ͼƬ���Ӳ��Ϸ�:"+url);
    		if (item.callback != null) { 
                Message msg = handler.obtainMessage(); 
                msg.obj = item; 
                handler.sendMessage(msg); 
            } 
    	}
    	
    }
    
    /**
     * �����������ر�.
     */
    public void shutdownNow(){
    	if(!executorService.isTerminated()){
    		executorService.shutdownNow();
    		listenShutdown();
    	}
    	
    }
    
    /**
     * ������ƽ���ر�.
     */
    public void shutdown(){
    	if(!executorService.isTerminated()){
    	   executorService.shutdown();
    	   listenShutdown();
    	}
    }
    
    /**
     * �������رռ���.
     */
    public void listenShutdown(){
    	try {
			while(!executorService.awaitTermination(1, TimeUnit.MILLISECONDS)) { 
				if(D) Log.d(TAG, "�̳߳�δ�ر�");
			}  
			if(D) Log.d(TAG, "�̳߳��ѹر�");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
