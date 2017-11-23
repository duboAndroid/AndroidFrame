/*
 * 
 */
package com.ab.download;

import java.io.File;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.ab.db.DownFileDao;
import com.ab.global.AbAppData;
import com.ab.model.DownFile;
import com.ab.util.AbFileUtil;

// TODO: Auto-generated Javadoc
/**
 * ���������߳�֧�ֶϵ�����������.
 *
 * @author zhaoqp
 * @date��2013-3-14 ����5:00:52
 * @version v1.0
 */
public class FileDownloader {

	/** The Constant TAG. */
	private static final String TAG = "FileDownloader";
	
	/** The Constant D. */
	private static final boolean D = AbAppData.DEBUG;
	
	/** The context. */
	private Context context;
	
	/** The threads. */
	private DownloadThread threads;
	
	/** The m down file dao. */
	private DownFileDao mDownFileDao;
	
	/** ���ر����ļ�. */
	private File saveFile;
	
	/** The m down file. */
	private DownFile mDownFile = null;
	
	/** The m thread num. */
	private int mThreadNum = 1;
	
	/** The flag. */
	private boolean flag = true;
	
	/**
	 * ����ָ���߳�������ص�λ��.
	 *
	 * @param downFile the down file
	 */
	protected synchronized void update(DownFile downFile) {
		this.mDownFileDao.update(downFile);
	}

	/**
	 * �����ļ�������.
	 *
	 * @param context the context
	 * @param downFile the down file
	 * @param threadNum �����߳���
	 */
	public FileDownloader(Context context,DownFile downFile,int threadNum) {
		try {
			this.context = context;
			this.mDownFile = downFile;
			this.mThreadNum = threadNum;
			mDownFileDao = new DownFileDao(context);
			// ���������ļ�
			saveFile = new File(Environment.getExternalStorageDirectory().getPath()+File.separator+AbFileUtil.getDownPathFileDir()+AbFileUtil.getFileNameFromUrl(mDownFile.getDownUrl()));
			if (!saveFile.getParentFile().exists()){
				saveFile.getParentFile().mkdirs();
			}
			if (!saveFile.exists()){
				saveFile.createNewFile();
				//ɾ��ԭ������������
				mDownFileDao.delete(downFile.getDownUrl());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʼ�����ļ�.
	 *
	 * @param listener �������������ı仯,�������Ҫ�˽�ʵʱ���ص�����,��������Ϊnull
	 * @return �������ļ���С
	 * @throws Exception the exception
	 */
	public void download(DownloadProgressListener listener) throws Exception {
		try {
			this.threads = new DownloadThread(this,mDownFile,saveFile);
			this.threads.setPriority(7);
			this.threads.start();
			this.mDownFileDao.save(mDownFile);
			// ѭ���ж������߳��Ƿ��������
			while (flag && mDownFile.getDownLength() <= mDownFile.getTotalLength()) {
				Thread.sleep(2000);
				// �������ʧ��,����������
				if (mDownFile.getDownLength() == -1) {
					//����ʧ��
					return;
				}
				
				// û�������֪ͨĿǰ�Ѿ�������ɵ����ݳ���
				if (listener != null){
					listener.onDownloadSize(mDownFile.getDownLength());
				}
				if(mDownFile.getDownLength() == mDownFile.getTotalLength()){
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡHttp��Ӧͷ�ֶ�.
	 *
	 * @param http the http
	 * @return the http response header
	 */
	public static Map<String, String> getHttpResponseHeader(HttpURLConnection http) {
		Map<String, String> header = new LinkedHashMap<String, String>();
		for (int i = 0;; i++) {
			String mine = http.getHeaderField(i);
			if (mine == null)
				break;
			header.put(http.getHeaderFieldKey(i), mine);
		}
		return header;
	}

	/**
	 * ��ӡHttpͷ�ֶ�.
	 *
	 * @param http the http
	 */
	public static void printResponseHeader(HttpURLConnection http) {
		Map<String, String> header = getHttpResponseHeader(http);
		for (Map.Entry<String, String> entry : header.entrySet()) {
			String key = entry.getKey() != null ? entry.getKey() + ":" : "";
			Log.i(TAG, key + entry.getValue());
		}
	}

	/**
	 * Gets the threads.
	 *
	 * @return the threads
	 */
	public DownloadThread getThreads() {
		return threads;
	}

	/**
	 * Gets the save file.
	 *
	 * @return the save file
	 */
	public File getSaveFile() {
		return saveFile;
	}

	/**
	 * Gets the flag.
	 *
	 * @return the flag
	 */
	public boolean getFlag() {
		return flag;
	}

	/**
	 * Sets the flag.
	 *
	 * @param flag the new flag
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
	
}
