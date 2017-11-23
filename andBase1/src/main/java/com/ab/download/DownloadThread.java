/*
 * 
 */
package com.ab.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.ab.global.AbAppData;
import com.ab.model.DownFile;

// TODO: Auto-generated Javadoc
/**
 * �����������߳���.
 *
 * @author zhaoqp
 * @date��2013-3-14 ����5:01:31
 * @version v1.0
 */
public class DownloadThread extends Thread {
	
	/** The Constant TAG. */
	private static final String TAG = "DownloadThread";
	
	/** The Constant D. */
	private static final boolean D = AbAppData.DEBUG;
	
	/** The save file. */
	private File saveFile;
	
	/** The m down file. */
	private DownFile mDownFile = null;
	
	/** The finish. */
	private boolean finish = false;
	
	/** The flag. */
	private boolean flag = false;
	
	/** The downloader. */
	private FileDownloader downloader;
	
	/**
	 * Instantiates a new download thread.
	 *
	 * @param downloader the downloader
	 * @param downFile the down file
	 * @param saveFile the save file
	 */
	public DownloadThread(FileDownloader downloader, DownFile downFile, File saveFile) {
		this.saveFile = saveFile;
		this.downloader = downloader;
		this.mDownFile = downFile;
	}
	
	/**
	 * ����������.
	 *
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		flag = true;
		InputStream inStream = null;
		RandomAccessFile threadfile = null;
		//δ�������
		if(mDownFile.getDownLength() < mDownFile.getTotalLength()){
			try {
				//ʹ��Get��ʽ����
				URL mUrl = new URL(mDownFile.getDownUrl());
				HttpURLConnection http = (HttpURLConnection) mUrl.openConnection();
				http.setConnectTimeout(5 * 1000);
				http.setRequestMethod("GET");
				http.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
				http.setRequestProperty("Accept-Language", "zh-CN");
				http.setRequestProperty("Referer", mDownFile.getDownUrl()); 
				http.setRequestProperty("Charset", "UTF-8");
				//���û�ȡʵ�����ݵķ�Χ
				http.setRequestProperty("Range", "bytes=" + mDownFile.getDownLength() + "-"+ mDownFile.getTotalLength());
				http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
				http.setRequestProperty("Connection", "Keep-Alive");
				
				inStream = http.getInputStream();
				byte[] buffer = new byte[1024];
				int offset = 0;
				threadfile = new RandomAccessFile(this.saveFile, "rwd");
				threadfile.seek(mDownFile.getDownLength());
				
				while (flag && (offset = inStream.read(buffer, 0, 1024)) != -1) {
					if(D)Log.d(TAG, "offset:"+offset);
					if(offset!=0){
						threadfile.write(buffer, 0, offset);
						mDownFile.setDownLength(mDownFile.getDownLength()+offset);
						offset = 0;
						if(D)Log.d(TAG, "DownLength:"+mDownFile.getDownLength()+"/"+mDownFile.getTotalLength());
						downloader.update(mDownFile);
						
						if(mDownFile.getDownLength() == mDownFile.getTotalLength()){
							this.finish = true;
							flag = false;
							break;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				mDownFile.setDownLength(-1);
			} finally{
				try {
					if(threadfile!=null){
						threadfile.close();
					}
					if(inStream!=null){
						inStream.close();
					}
				} catch (IOException e) {
				}
			}
		}
	}
	
	
	/**
	 * �����Ƿ����.
	 *
	 * @return true, if is finish
	 */
	public boolean isFinish() {
		return finish;
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
	 * Sets the flag.
	 *
	 * @param flag the new flag
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}
