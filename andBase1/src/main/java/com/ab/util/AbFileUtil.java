/*
 * 
 */
package com.ab.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.ab.global.AbAppData;
import com.ab.global.AbAppException;
import com.ab.global.AbConstant;

// TODO: Auto-generated Javadoc
/**
 * �������ļ�������.
 *
 * @author zhaoqp
 * @date 2011-12-10
 * @version v1.0
 */
public class AbFileUtil {
	
	/** The tag. */
	private static String TAG = "AbFileUtil";
	
	/** The Constant D. */
	private static final boolean D = AbAppData.DEBUG;
	
	/** Ĭ�������ļ���ַ. */
    public static  String downPathRootDir = File.separator + "download" + File.separator;
	
    /** Ĭ������ͼƬ�ļ���ַ. */
    public static  String downPathImageDir = File.separator + "download"
			+ File.separator + "cache_images" + File.separator;
    
    /** Ĭ�������ļ���ַ. */
    public static  String downPathFileDir = File.separator + "download"
			+ File.separator + "cache_files" + File.separator;
	
	/**
	 * ���������ļ���SD����.���SD�д���ͬ���ļ�����������
	 * @param url Ҫ�����ļ��������ַ
	 * @return ���غõı����ļ���ַ
	 */
	 public static String downFileToSD(String url){
		 InputStream in = null;
		 FileOutputStream fileOutputStream = null;
		 HttpURLConnection con = null;
		 String downFilePath = null;
		 try {
	    	if(!isCanUseSD()){
	    		return null;
	    	}
	    	File path = Environment.getExternalStorageDirectory();
	    	File fileDirectory = new File(path.getAbsolutePath() + downPathImageDir);
			if(!fileDirectory.exists()){
				fileDirectory.mkdirs();
			}
			File f = new File(fileDirectory,getFileNameFromUrl(url));
			if(!f.exists()){
				f.createNewFile();
			}else{
				//�ļ��Ѿ�����
				if(f.length()!=0){
					return f.getPath();
				}
			}
			downFilePath = f.getPath();
			URL mUrl = new URL(url);
			con = (HttpURLConnection)mUrl.openConnection();
			con.connect();
			in = con.getInputStream();
			fileOutputStream = new FileOutputStream(f);
			byte[] b = new byte[1024];
			int temp = 0;
			while((temp=in.read(b))!=-1){
				fileOutputStream.write(b, 0, temp);
			}
		}catch(Exception e){
			if(D)Log.d(TAG, ""+e.getMessage());
			return null;
		}finally{
			try {
				if(in!=null){
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(fileOutputStream!=null){
					fileOutputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(con!=null){
					con.disconnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return downFilePath;
	 }
	 
	 /**
	  * ������ͨ���ļ��������ַ��SD���ж�ȡͼƬ�����SD��û�����Զ����ز�����.
	  * @param url �ļ��������ַ
	  * @param type ͼƬ�Ĵ������ͣ����л������ŵ�ָ����С���ο�AbConstant�ࣩ
	  * @param newWidth ��ͼƬ�Ŀ�
	  * @param newHeight ��ͼƬ�ĸ�
	  * @return Bitmap ��ͼƬ
	  */
	 public static Bitmap getBitmapFromSDCache(String url,int type,int newWidth, int newHeight){
		 Bitmap bit = null;
		 try {
			 //SD���Ƿ����
			 if(!isCanUseSD()){
				 bit = getBitmapFormURL(url,type,newWidth,newHeight);
				 return bit;
		     }
			 //�ļ��Ƿ����
			 File path = Environment.getExternalStorageDirectory();
			 File fileDirectory = new File(path.getAbsolutePath() + downPathImageDir);
			 File f = new File(fileDirectory,getFileNameFromUrl(url));
			 if(!f.exists()){
				 downFileToSD(url);
				 return getBitmapFromSD(f,type,newWidth,newHeight);
			 }else{
				 if(D)Log.d(TAG, "Ҫ��ȡ��ͼƬ·��Ϊ��"+f.getPath());
				 //�ļ�����
				 if(type == AbConstant.CUTIMG){
			 		bit = AbImageUtil.cutImg(f,newWidth,newHeight);
			 	 }else{
			 		bit = AbImageUtil.scaleImg(f,newWidth,newHeight);
			 	 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bit;
		
	 }
	 
	 /**
	  * ������ͨ���ļ��������ַ��SD���ж�ȡͼƬ.
	  * @param url �ļ��������ַ
	  * @param type ͼƬ�Ĵ������ͣ����л������ŵ�ָ����С���ο�AbConstant�ࣩ
	  * @param newWidth ��ͼƬ�Ŀ�
	  * @param newHeight ��ͼƬ�ĸ�
	  * @return Bitmap ��ͼƬ
	  */
	 public static Bitmap getBitmapFromSD(String url,int type,int newWidth, int newHeight){
		 Bitmap bit = null;
		 try {
			 //SD���Ƿ����
			 if(!isCanUseSD()){
				 return null;
		     }
			 //�ļ��Ƿ����
			 File path = Environment.getExternalStorageDirectory();
			 File fileDirectory = new File(path.getAbsolutePath() + downPathImageDir);
			 File f = new File(fileDirectory,getFileNameFromUrl(url));
			 if(!f.exists()){
				 return null;
			 }else{
				 //�ļ�����
				 if(type == AbConstant.CUTIMG){
			 		bit = AbImageUtil.cutImg(f,newWidth,newHeight);
			 	 }else{
			 		bit = AbImageUtil.scaleImg(f,newWidth,newHeight);
			 	 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bit;
		
	 }
	 
	 /**
 	 * ������ͨ���ļ��ı��ص�ַ��SD����ȡͼƬ.
 	 *
 	 * @param file the file
 	 * @param type ͼƬ�Ĵ������ͣ����л������ŵ�ָ����С���ο�AbConstant�ࣩ
 	 * @param newWidth ��ͼƬ�Ŀ�
 	 * @param newHeight ��ͼƬ�ĸ�
 	 * @return Bitmap ��ͼƬ
 	 */
	 public static Bitmap getBitmapFromSD(File file,int type,int newWidth, int newHeight){
		 Bitmap bit = null;
		 try {
			 //SD���Ƿ����
			 if(!isCanUseSD()){
		    	return null;
		     }
			 //�ļ��Ƿ����
			 if(!file.exists()){
				 return null;
			 }
			 //�ļ�����
			 if(type==AbConstant.CUTIMG){
		 		bit = AbImageUtil.cutImg(file,newWidth,newHeight);
		 	 }else{
		 		bit = AbImageUtil.scaleImg(file,newWidth,newHeight);
		 	 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bit;
	 }
	 
	 /**
	  * ��������ͼƬ��byte[]д�뱾���ļ�.
	  * @param imgByte ͼƬ��byte[]����
	  * @param fileName �ļ����ƣ���Ҫ������׺����.jpg
	  * @param type ͼƬ�Ĵ������ͣ����л������ŵ�ָ����С���ο�AbConstant�ࣩ
	  * @param newWidth ��ͼƬ�Ŀ�
	  * @param newHeight ��ͼƬ�ĸ�
	  * @return Bitmap ��ͼƬ
	  */
     public static Bitmap getBitmapFormByte(byte[] imgByte,String fileName,int type,int newWidth, int newHeight){
    	   FileOutputStream fos = null;
    	   DataInputStream dis = null;
    	   ByteArrayInputStream bis = null;
    	   Bitmap b = null;
    	   try {
    		   if(imgByte!=null){
    			   File sdcardDir = Environment.getExternalStorageDirectory();
    			   String path = sdcardDir.getAbsolutePath()+downPathImageDir;
    			   File file = new File(path+fileName);
    				 
    			   if(!file.getParentFile().exists()){
    			          file.getParentFile().mkdirs();
    			   }
    			   if(!file.exists()){
    			          file.createNewFile();
    			   }
    			   fos = new FileOutputStream(file);
    			   int readLength = 0;
    			   bis = new ByteArrayInputStream(imgByte);
    			   dis = new DataInputStream(bis);
    			   byte[] buffer = new byte[1024];
    			   
    			   while ((readLength = dis.read(buffer))!=-1) {
    				   fos.write(buffer, 0, readLength);
    			       try {
    						Thread.sleep(500);
    				   } catch (Exception e) {
    				   }
    			   }
    			   fos.flush();
    		   }
			   b = getBitmapFormURL("/"+fileName,type,newWidth,newHeight);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dis!=null){
				try {
					dis.close();
				} catch (Exception e) {
				}
			}    
			if(bis!=null){
				try {
					bis.close();
				} catch (Exception e) {
				}
			}
			if(fos!=null){
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
		}
        return  b;
     }
	    
	/**
	 * ����������URL�ӻ�������ȡͼƬ.
	 * @param url Ҫ�����ļ��������ַ
	 * @param type ͼƬ�Ĵ������ͣ����л������ŵ�ָ����С���ο�AbConstant�ࣩ
	 * @param newWidth ��ͼƬ�Ŀ�
	 * @param newHeight ��ͼƬ�ĸ�
	 * @return Bitmap ��ͼƬ
	 */
	public static Bitmap getBitmapFormURL(String url,int type,int newWidth, int newHeight){
		Bitmap bit = null;
		try {
			bit = AbImageUtil.getBitmapFormURL(url, type, newWidth, newHeight);
	    } catch (Exception e) {
	    	 if(D)Log.d(TAG, "����ͼƬ�쳣��"+e.getMessage());
		}
	    if(D)Log.d(TAG, "���ص�Bitmap��"+bit);
		return bit;
	}
	
	/**
	 * ��������ȡsrc�е�ͼƬ��Դ.
	 *
	 * @param src ͼƬ��src·�����磨��image/arrow.png����
	 * @return Bitmap ͼƬ
	 */
	public static Bitmap getBitmapFormSrc(String src){
		Bitmap bit = null;
		try {
			bit = BitmapFactory.decodeStream(AbFileUtil.class.getResourceAsStream(src));
	    } catch (Exception e) {
	    	 if(D)Log.d(TAG, "��ȡͼƬ�쳣��"+e.getMessage());
		}
	    if(D)Log.d(TAG, "���ص�Bitmap��"+bit);
		return bit;
	}
	
	/**
	 * ��������ȡ�����ļ��Ĵ�С.
	 *
	 * @param Url ͼƬ������·��
	 * @return int �����ļ��Ĵ�С
	 */
	public static int getContentLengthFormUrl(String Url){
		int mContentLength = 0;
		try {
			 URL url = new URL(Url);
			 HttpURLConnection mHttpURLConnection = (HttpURLConnection) url.openConnection();
			 mHttpURLConnection.setConnectTimeout(5 * 1000);
			 mHttpURLConnection.setRequestMethod("GET");
			 mHttpURLConnection.setRequestProperty("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			 mHttpURLConnection.setRequestProperty("Accept-Language", "zh-CN");
			 mHttpURLConnection.setRequestProperty("Referer", Url);
			 mHttpURLConnection.setRequestProperty("Charset", "UTF-8");
			 mHttpURLConnection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			 mHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			 mHttpURLConnection.connect();
			 if (mHttpURLConnection.getResponseCode() == 200){
				 // ������Ӧ��ȡ�ļ���С
				 mContentLength = mHttpURLConnection.getContentLength();
			 }
	    } catch (Exception e) {
	    	 e.printStackTrace();
	    	 if(D)Log.d(TAG, "��ȡ�����쳣��"+e.getMessage());
		}
		return mContentLength;
	}
	
	/**
	 * HTTP�ļ��ϴ�.
	 *
	 * @param actionUrl Ҫʹ�õ�URL
	 * @param params ������
	 * @param files Ҫ�ϴ����ļ��б�
	 * @return  http���صĽ�� ��http��Ӧ��
	 * @throws AbAppException the ab app exception
	 */
	public static String postFile(String actionUrl, HashMap<String, String> params,
			HashMap<String, File> files) throws AbAppException{
		//��ʶÿ���ļ��ı߽�
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--";
		String LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		HttpURLConnection conn = null;
		DataOutputStream outStream = null;
		String retStr = "200";
		try {
			URL uri = new URL(actionUrl);
			conn = (HttpURLConnection) uri.openConnection();
			//��������
			conn.setDoInput(true);
			//�������
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			// Post��ʽ
			conn.setRequestMethod("POST");
			//����request header ����
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
					+ ";boundary=" + BOUNDARY);
			//��װ����������
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINEND);
				sb.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"" + LINEND);
				sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
				sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
				sb.append(LINEND);
				sb.append(entry.getValue());
				sb.append(LINEND);
			}
			//��ȡ���ӷ��Ͳ�������
			outStream = new DataOutputStream(conn.getOutputStream());
			outStream.write(sb.toString().getBytes());
			// �����ļ�����
			if (files != null)
				for (Map.Entry<String, File> file : files.entrySet()) {
					StringBuilder sb1 = new StringBuilder();
					sb1.append(PREFIX);
					sb1.append(BOUNDARY);
					sb1.append(LINEND);
					sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
							+ file.getKey() + "\"" + LINEND);
					sb1.append("Content-Type: application/octet-stream; charset="
							+ CHARSET + LINEND);
					sb1.append(LINEND);
					//����ͷ����������һ�����У���������\r\n����ʾ����ͷ������
					if(D)Log.d("TAG", "request start:"+sb1.toString());
					outStream.write(sb1.toString().getBytes());
					InputStream is = new FileInputStream(file.getValue());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						outStream.write(buffer, 0, len);
					}
					is.close();
					//һ���ļ�����һ������
					outStream.write(LINEND.getBytes());
				}
				//��������ı߽��ӡ
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
				Log.d("TAG","request end:"+ new String(end_data).toString());
				outStream.write(end_data);
				outStream.flush();
				outStream.close();
				// ��ȡ��Ӧ��
				int ret = conn.getResponseCode();
				retStr = String.valueOf(ret);
				if(ret == 200){
					String result = AbStrUtil.convertStreamToString(conn.getInputStream());
					return result;
				}
		} catch (Exception e) {
			throw new AbAppException(e);
		} finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		return retStr;
	}
	 
	/**
	 * ��ȡ�ļ�����ͨ�������ȡ���������ʧ�ܽ�ȡ�ļ���ַURL��Ϊ�ļ�������ȡ�������һ����/���Ժ���ַ���.
	 * @param strUrl �ļ���ַ
	 * @return �ļ���
	 */
	public static String getFileNameFromUrl(String strUrl){
		String name = null;
		try {
			if(AbStrUtil.isEmpty(strUrl) || strUrl.indexOf("/")==-1 || strUrl.length()<2){
				 URL url = new URL(strUrl);
				 HttpURLConnection mHttpURLConnection = (HttpURLConnection) url.openConnection();
				 mHttpURLConnection.setConnectTimeout(5 * 1000);
				 mHttpURLConnection.setRequestMethod("GET");
				 mHttpURLConnection.setRequestProperty("Accept","image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
				 mHttpURLConnection.setRequestProperty("Accept-Language", "zh-CN");
				 mHttpURLConnection.setRequestProperty("Referer", strUrl);
				 mHttpURLConnection.setRequestProperty("Charset", "UTF-8");
				 mHttpURLConnection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
				 mHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
				 mHttpURLConnection.connect();
				 if (mHttpURLConnection.getResponseCode() == 200){
					 for (int i = 0;; i++) {
							String mine = mHttpURLConnection.getHeaderField(i);
							if (mine == null){
								break;
							}
							if ("content-disposition".equals(mHttpURLConnection.getHeaderFieldKey(i).toLowerCase())) {
								Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
								if (m.find())
									return m.group(1);
							}
						}
					    // Ĭ��ȡһ���ļ���
						return AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMDHMS) + ".tmp";
				 }
				 return null;
			}else{
			   int index = strUrl.lastIndexOf("/");
			   name = strUrl.substring(index+1);
			}
		} catch (Exception e) {
		}
		return name;
    }
	
	/**
	 * ��������sd���е��ļ���ȡ��byte[].
	 *
	 * @param path sd�����ļ�·��
	 * @return byte[]
	 */
	public static byte[] getByteArrayFromSD(String path) {  
		byte[] bytes = null; 
		ByteArrayOutputStream out = null;
	    try {
	    	File file = new File(path);  
	    	//SD���Ƿ����
			if(!isCanUseSD()){
				 return null;
		    }
			//�ļ��Ƿ����
			if(!file.exists()){
				 return null;
			}
	    	
	    	long fileSize = file.length();  
	    	if (fileSize > Integer.MAX_VALUE) {  
	    	      return null;  
	    	}  

			FileInputStream in = new FileInputStream(path);  
		    out = new ByteArrayOutputStream(1024);  
			byte[] buffer = new byte[1024];  
			int size=0;  
			while((size=in.read(buffer))!=-1) {  
			   out.write(buffer,0,size);  
			}  
			in.close();  
            bytes = out.toByteArray();  
   
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(out!=null){
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	    return bytes;
    }  
	
	/**
	 * ��������byte����д���ļ�.
	 *
	 * @param path the path
	 * @param content the content
	 * @param create the create
	 */
	 public static void writeByteArrayToSD(String path, byte[] content,boolean create){  
	    
		 FileOutputStream fos = null;
		 try {
	    	File file = new File(path);  
	    	//SD���Ƿ����
			if(!isCanUseSD()){
				 return;
		    }
			//�ļ��Ƿ����
			if(!file.exists()){
				if(create){
					File parent = file.getParentFile();
					if(!parent.exists()){
						parent.mkdirs();
						file.createNewFile();
					}
				}else{
				    return;
				}
			}
			fos = new FileOutputStream(path);  
			fos.write(content);  
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
		}
   }  
	 
	/**
	 * ������SD���Ƿ�����.
	 *
	 * @return true ����,false������
	 */
	public static boolean isCanUseSD() { 
	    try { 
	        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); 
	    } catch (Exception e) { 
	        e.printStackTrace(); 
	    } 
	    return false; 
    } 

	/**
	 * ��������õ�ǰ���صĵ�ַ.
	 * @return ���صĵ�ַ��Ĭ��SD��download��
	 */
	public static String getDownPathImageDir() {
		return downPathImageDir;
	}

	/**
	 * ����������ͼƬ�ļ������ر���·����Ĭ��SD��download/cache_images��.
	 * @param downPathImageDir ͼƬ�ļ������ر���·��
	 */
	public static void setDownPathImageDir(String downPathImageDir) {
		AbFileUtil.downPathImageDir = downPathImageDir;
	}

	
	/**
	 * Gets the down path file dir.
	 *
	 * @return the down path file dir
	 */
	public static String getDownPathFileDir() {
		return downPathFileDir;
	}

	/**
	 * �����������ļ������ر���·����Ĭ��SD��download/cache_files��.
	 * @param downPathFileDir �ļ������ر���·��
	 */
	public static void setDownPathFileDir(String downPathFileDir) {
		AbFileUtil.downPathFileDir = downPathFileDir;
	}
	
	/**
	 * ��������ȡĬ�ϵ�ͼƬ����ȫ·��.
	 *
	 * @return the default image down path dir
	 */
	public static String getDefaultImageDownPathDir(){
		String pathDir = null;
	    try {
			if(!isCanUseSD()){
				return null;
			}
			//��ʼ��ͼƬ����·��
			File fileRoot = Environment.getExternalStorageDirectory();
			File dirFile = new File(fileRoot.getAbsolutePath() + AbFileUtil.downPathImageDir);
			if(!dirFile.exists()){
				dirFile.mkdirs();
			}
			pathDir = dirFile.getPath();
		} catch (Exception e) {
		}
	    return pathDir;
	}
	
	
	
}
