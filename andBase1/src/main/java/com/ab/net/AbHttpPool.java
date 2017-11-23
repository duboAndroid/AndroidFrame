/*
 * 
 */
package com.ab.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ab.global.AbAppData;
import com.ab.util.AbAppUtil;
// TODO: Auto-generated Javadoc
/**
 * 
 * Copyright (c) 2012 All rights reserved
 * 名称：AbHttpPool.java 
 * 描述：线程池图片下载
 * @author zhaoqp
 * @date：2013-5-23 上午10:10:53
 * @version v1.0
 */

public class AbHttpPool{
	
	/** The tag. */
	private static String TAG = "AbHttpPool";
	
	/** The Constant D. */
	private static final boolean D = AbAppData.DEBUG;
	
	//单例对象
	/** The http pool. */
	private static AbHttpPool httpPool = null; 
	
	/** The n threads. */
	private static int nThreads  = 5;
	// 固定五个线程来执行任务 
	/** The executor service. */
	private ExecutorService executorService = null; 
	
	/** 下载完成后的消息句柄. */
    private static Handler handler = new Handler() { 
        @Override 
        public void handleMessage(Message msg) { 
        	AbHttpItem item = (AbHttpItem)msg.obj; 
            item.callback.update(); 
        } 
    }; 
	
	/**
	 * 构造图片下载器.
	 *
	 * @param nThreads the n threads
	 */
    protected AbHttpPool(int nThreads) {
    	executorService = Executors.newFixedThreadPool(nThreads); 
    } 
	
	/**
	 * 单例构造图片下载器.
	 *
	 * @return single instance of AbHttpPool
	 */
    public static AbHttpPool getInstance() { 
        if (httpPool == null) { 
        	nThreads = AbAppUtil.getNumCores();
        	httpPool = new AbHttpPool(nThreads*5); 
        } 
        return httpPool;
    } 
    
    /**
     * Download.
     *
     * @param item the item
     * @return the drawable
     */
    public Drawable download(final AbHttpItem item) {    
    	// 缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
    	executorService.submit(new Runnable() { 
    		public void run() {
    			try {
    				//定义了回调
                    if (item.callback != null) { 
                    	item.callback.get();
                    	//交由UI线程处理 
                        Message msg = handler.obtainMessage(); 
                        msg.obj = item; 
                        handler.sendMessage(msg); 
                    }                              
    			} catch (Exception e) { 
    				throw new RuntimeException(e);
    			}                         
    		}                 
    	});                 
    	return null; 
    	
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
