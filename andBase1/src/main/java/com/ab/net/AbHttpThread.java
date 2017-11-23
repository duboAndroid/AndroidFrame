/*
 * 
 */
package com.ab.net;

import android.os.Handler;
import android.os.Message;

// TODO: Auto-generated Javadoc
/**
 * 描述： 数据下载线程.
 *
 * @author zhaoqp
 * @date 2011-11-10
 * @version v1.0
 */
public class AbHttpThread extends Thread { 
	
	/** The tag. */
	private static String TAG = "AbHttpThread";
	
	/** The Constant D. */
	private static final boolean D = true;
	
	/** 下载单位. */
	public AbHttpItem mNetGetItem = null;
	
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
    public AbHttpThread() {
    }
    
    /**
     * 开始一个下载任务.
     *
     * @param item 下载单位
     */
    public void download(AbHttpItem item) { 
    	 mNetGetItem = item;
         this.start();
    } 
 
    /**
     * 描述：线程运行.
     *
     * @see java.lang.Thread#run()
     */
    @Override 
    public void run() { 
            if(mNetGetItem!=null) { 
            	//定义了回调
                if (mNetGetItem.callback != null) { 
                	mNetGetItem.callback.get();
                	//交由UI线程处理 
                    Message msg = handler.obtainMessage(); 
                    msg.obj = mNetGetItem; 
                    handler.sendMessage(msg); 
                } 
        } 
    } 

}

