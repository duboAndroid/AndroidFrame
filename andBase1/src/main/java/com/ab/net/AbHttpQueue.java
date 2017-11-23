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
 * ������ ���������̣߳����������أ�.
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
	
	/** ���ض���. */
	private List<AbHttpItem> mNetHttpList = null;
	
	/** �����̵߳�����. */
	private static AbHttpQueue mNetGetThread = null; 
	
	/** ������ɺ����Ϣ���. */
    private static Handler handler = new Handler() { 
        @Override 
        public void handleMessage(Message msg) { 
        	AbHttpItem item = (AbHttpItem)msg.obj; 
            item.callback.update(); 
        } 
    }; 
	
	/**
	 * ���������̶߳���.
	 */
    private AbHttpQueue() {
    	mNetHttpList = new ArrayList<AbHttpItem>();
    } 
    
    /**
     * �������������߳�.
     *
     * @return single instance of AbHttpQueue
     */
    public static AbHttpQueue getInstance() { 
        if (mNetGetThread == null) { 
        	mNetGetThread = new AbHttpQueue(); 
            //��������������
        	mNetGetThread.start(); 
        } 
        return mNetGetThread; 
    } 
    
    /**
     * ��ʼһ����������.
     *
     * @param item ���ص�λ
     */
    public void download(AbHttpItem item) { 
         addDownloadItem(item); 
    } 
    
    /**
     * ��ʼһ�������������ԭ������.
     *
     * @param item ���ص�λ
     */
    public void downloadBeforeClean(AbHttpItem item) { 
    	 mNetHttpList.clear();
         addDownloadItem(item); 
    } 
     
    /**
     * ��������ӵ������̶߳���.
     *
     * @param mNetHttp the m net http
     */
    private synchronized void addDownloadItem(AbHttpItem mNetHttp) { 
    	mNetHttpList.add(mNetHttp);
        //�����������ͼ���߳� 
        this.notify();
    } 
 
    /**
     * �������߳�����.
     *
     * @see java.lang.Thread#run()
     */
    @Override 
    public void run() { 
        while(true) { 
            while(mNetHttpList.size() > 0) { 
            	AbHttpItem item  = mNetHttpList.remove(0);
            	//�����˻ص�
                if (item.callback != null) { 
                	item.callback.get();
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

