/*
 * 
 */
package com.ab.net;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ab.global.AbAppData;
import com.ab.util.AbFileUtil;
import com.ab.util.AbStrUtil;

// TODO: Auto-generated Javadoc
/**
 * ������ͼƬ�����̣߳����������أ��ȼ��SD���Ƿ������ͬ�ļ��������������أ�����ٴ�SD���ж�ȡ��.
 *
 * @author zhaoqp
 * @date 2011-12-10
 * @version v1.0
 */
public class AbImageDownloadQueue extends Thread { 
	
	/** The tag. */
	private static String TAG = "AbImageDownloadQueue";
	
	/** The Constant D. */
	private static final boolean D = AbAppData.DEBUG;
	
	/** ���ض���. */
	private List<AbImageDownloadItem> queue;
	
	/** ͼƬ�����̵߳�����. */
    private static AbImageDownloadQueue imageDownloadThread = null; 
    
    /** ������ɺ����Ϣ���. */
    private static Handler handler = new Handler() { 
        @Override 
        public void handleMessage(Message msg) { 
        	//if(D)Log.d(TAG, "����callback handleMessage...");
            AbImageDownloadItem item = (AbImageDownloadItem)msg.obj; 
            item.callback.update(item.bitmap, item.imageUrl); 
        } 
    }; 
    
    /**
     * ����ͼƬ�����̶߳���.
     */
    private AbImageDownloadQueue() {
		queue = new ArrayList<AbImageDownloadItem>();
    } 
    
    /**
     * ��������ͼƬ�����߳�.
     *
     * @return single instance of AbImageDownloadQueue
     */
    public static AbImageDownloadQueue getInstance() { 
        if (imageDownloadThread == null) { 
            imageDownloadThread = new AbImageDownloadQueue(); 
            //��������������
            imageDownloadThread.start(); 
        } 
        return imageDownloadThread; 
    } 
     
    /**
     * ��ʼһ����������.
     *
     * @param item ͼƬ���ص�λ
     * @return Bitmap ������ɺ�õ���Bitmap
     */
    public void download(AbImageDownloadItem item) { 
    	//���ͼƬ·��
    	String url = item.imageUrl;
    	if(AbStrUtil.isEmpty(url)){
    		if(D)Log.d(TAG, "ͼƬURLΪ�գ������ж�");
    	}else{
    		url = url.trim();
    	}
    	if(AbFileUtil.getFileNameFromUrl(url) != null){
    		//���SD�������ͼƬ, �ȵ�SD�������ͼƬ
        	if(AbImageCache.imageCache.containsKey(item.imageUrl)){
        		item.bitmap = AbImageCache.imageCache.get(item.imageUrl).get();
        		if(item.bitmap == null){
        			addDownloadItem(item); 
        		}else{
	        		if (item.callback != null) {
	                    Message msg = handler.obtainMessage(); 
	                    msg.obj = item; 
	                    handler.sendMessage(msg); 
	                }
        	    }
        	}else{
        		addDownloadItem(item); 
        	}
    	}else{
    		if (item.callback != null) {
                Message msg = handler.obtainMessage(); 
                msg.obj = item; 
                handler.sendMessage(msg); 
            }
    	}
    } 
    
    /**
     * ��������ӵ�ͼƬ�����̶߳���.
     *
     * @param item ͼƬ���ص�λ
     */
    private synchronized void addDownloadItem(AbImageDownloadItem item) { 
        queue.add(item); 
        //�����������ͼ���߳� 
        this.notify();
    } 
    
    /**
     * ��ʼһ�������������ԭ������.
     *
     * @param item ���ص�λ
     */
    public void downloadBeforeClean(AbImageDownloadItem item) { 
    	queue.clear();
        addDownloadItem(item); 
    } 
 
    /**
     * �������߳�����.
     *
     * @see java.lang.Thread#run()
     */
    @Override 
    public void run() { 
        while(true) { 
        	//if(D)Log.d(TAG, "�����С��"+queue.size());
            while(queue.size() > 0) { 
                AbImageDownloadItem item = queue.remove(0); 
                //��ʼ����
                item.bitmap = AbFileUtil.getBitmapFromSDCache(item.imageUrl,item.type,item.width,item.height);
                //����ͼƬ·��
				AbImageCache.imageCache.put(item.imageUrl,new SoftReference<Bitmap>(item.bitmap)); 
                //��Ҫִ�лص�����ʾͼƬ
                if (item.callback != null) { 
                	//if(D)Log.d(TAG, "����callback...");
                    //����UI�̴߳��� 
                    Message msg = handler.obtainMessage(); 
                    msg.obj = item; 
                    handler.sendMessage(msg); 
                } 
            } 
            try { 
            	//û��������ʱ�ȴ� 
                synchronized(this) { 
                    this.wait();
                } 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
        } 
    } 

}

