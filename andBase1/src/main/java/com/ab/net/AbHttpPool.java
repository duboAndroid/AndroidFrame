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
 * ���ƣ�AbHttpPool.java 
 * �������̳߳�ͼƬ����
 * @author zhaoqp
 * @date��2013-5-23 ����10:10:53
 * @version v1.0
 */

public class AbHttpPool{
	
	/** The tag. */
	private static String TAG = "AbHttpPool";
	
	/** The Constant D. */
	private static final boolean D = AbAppData.DEBUG;
	
	//��������
	/** The http pool. */
	private static AbHttpPool httpPool = null; 
	
	/** The n threads. */
	private static int nThreads  = 5;
	// �̶�����߳���ִ������ 
	/** The executor service. */
	private ExecutorService executorService = null; 
	
	/** ������ɺ����Ϣ���. */
    private static Handler handler = new Handler() { 
        @Override 
        public void handleMessage(Message msg) { 
        	AbHttpItem item = (AbHttpItem)msg.obj; 
            item.callback.update(); 
        } 
    }; 
	
	/**
	 * ����ͼƬ������.
	 *
	 * @param nThreads the n threads
	 */
    protected AbHttpPool(int nThreads) {
    	executorService = Executors.newFixedThreadPool(nThreads); 
    } 
	
	/**
	 * ��������ͼƬ������.
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
    	// ������û��ͼ�����������ȡ�����ݣ�����ȡ�������ݻ��浽�ڴ���
    	executorService.submit(new Runnable() { 
    		public void run() {
    			try {
    				//�����˻ص�
                    if (item.callback != null) { 
                    	item.callback.get();
                    	//����UI�̴߳��� 
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
