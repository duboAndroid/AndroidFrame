/*
 * 
 */
package com.ab.net;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;

import com.ab.global.AbAppData;

// TODO: Auto-generated Javadoc
/**
 * 描述： 数据下载线程（按队列下载）.
 *
 * @author zhaoqp
 * @date 2011-11-10
 * @version v1.0
 */
public class AbHttpQueue extends Thread { 
	
	/** The tag. */
	private static String TAG = "AbHttpQueue";
	
	/** The Constant D. */
	private static final boolean D = AbAppData.DEBUG;
	
	/** 下载队列. */
	private List<AbHttpItem> mNetHttpList = null;
	
	/** 下载线程单例类. */
	private static AbHttpQueue mNetGetThread = null; 
	
	/** 下载完成后的消息句柄. */
    private static Handler handler = new Handler() { 
        @Override 
        public void handleMessage(Message msg) { 
        	AbHttpItem item = (AbHttpItem)msg.obj; 
            item.callback.update(); 
        } 
    }; 
	
	/**
	 * 构造下载线程队列.
	 */
    private AbHttpQueue() {
    	mNetHttpList = new ArrayList<AbHttpItem>();
    } 
    
    /**
     * 单例构造下载线程.
     *
     * @return single instance of AbHttpQueue
     */
    public static AbHttpQueue getInstance() { 
        if (mNetGetThread == null) { 
        	mNetGetThread = new AbHttpQueue(); 
            //创建后立刻运行
        	mNetGetThread.start(); 
        } 
        return mNetGetThread; 
    } 
    
    /**
     * 开始一个下载任务.
     *
     * @param item 下载单位
     */
    public void download(AbHttpItem item) { 
         addDownloadItem(item); 
    } 
    
    /**
     * 开始一个下载任务并清除原来队列.
     *
     * @param item 下载单位
     */
    public void downloadBeforeClean(AbHttpItem item) { 
    	 mNetHttpList.clear();
         addDownloadItem(item); 
    } 
     
    /**
     * 描述：添加到下载线程队列.
     *
     * @param mNetHttp the m net http
     */
    private synchronized void addDownloadItem(AbHttpItem mNetHttp) { 
    	mNetHttpList.add(mNetHttp);
        //添加了下载项就激活本线程 
        this.notify();
    } 
 
    /**
     * 描述：线程运行.
     *
     * @see java.lang.Thread#run()
     */
    @Override 
    public void run() { 
        while(true) { 
            while(mNetHttpList.size() > 0) { 
            	AbHttpItem item  = mNetHttpList.remove(0);
            	//定义了回调
                if (item.callback != null) { 
                	item.callback.get();
                	//交由UI线程处理 
                    Message msg = handler.obtainMessage(); 
                    msg.obj = item; 
                    handler.sendMessage(msg); 
                } 
            } 
            try { 
            	//没有下载项时等待 
                synchronized(this) { 
                    this.wait();
                } 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
        } 
    } 

}

