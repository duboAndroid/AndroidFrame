/*
 * 
 */
package com.ab.net;

import android.os.Handler;
import android.os.Message;

// TODO: Auto-generated Javadoc
/**
 * ������ ���������߳�.
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
	
	/** ���ص�λ. */
	public AbHttpItem mNetGetItem = null;
	
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
    public AbHttpThread() {
    }
    
    /**
     * ��ʼһ����������.
     *
     * @param item ���ص�λ
     */
    public void download(AbHttpItem item) { 
    	 mNetGetItem = item;
         this.start();
    } 
 
    /**
     * �������߳�����.
     *
     * @see java.lang.Thread#run()
     */
    @Override 
    public void run() { 
            if(mNetGetItem!=null) { 
            	//�����˻ص�
                if (mNetGetItem.callback != null) { 
                	mNetGetItem.callback.get();
                	//����UI�̴߳��� 
                    Message msg = handler.obtainMessage(); 
                    msg.obj = mNetGetItem; 
                    handler.sendMessage(msg); 
                } 
        } 
    } 

}

