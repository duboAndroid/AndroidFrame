/*
 * 
 */
package com.ab.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

import com.ab.global.AbAppData;
import com.ab.global.AbConstant;

// TODO: Auto-generated Javadoc
/**
 * ������ͼƬ������.
 *
 * @author zhaoqp
 * @date 2011-12-10
 * @version v1.0
 */
public class AbImageUtil {
	
	/** The tag. */
	private static String TAG = "AbImageUtil";
	
	/** The Constant D. */
	private static final boolean D = AbAppData.DEBUG;
	
	/**
	 * ֱ�ӻ�ȡ�������ϵ�ͼƬ.
	 * @param imageUrl Ҫ�����ļ��������ַ
	 * @param type ͼƬ�Ĵ������ͣ����л������ŵ�ָ����С���ο�AbConstant�ࣩ
	 * @param newWidth ��ͼƬ�Ŀ�
	 * @param newHeight ��ͼƬ�ĸ�
	 * @return Bitmap ��ͼƬ
	 */
	public static Bitmap getBitmapFormURL(String imageUrl,int type,int newWidth,int newHeight){
		Bitmap bm = null;
		URLConnection con = null;
		InputStream is = null;
		try {
			URL url = new URL(imageUrl);
			con = url.openConnection();
			con.setDoInput(true);
			con.connect();
			is = con.getInputStream();
			//��ȡ��ԴͼƬ
			Bitmap wholeBm =  BitmapFactory.decodeStream(is,null,null); 
			if(type==AbConstant.CUTIMG){
				bm = AbImageUtil.cutImg(wholeBm,newWidth,newHeight);
		 	}else if(type==AbConstant.SCALEIMG){
		 		bm = AbImageUtil.scaleImg(wholeBm,newWidth,newHeight);
		 	}
		} catch (Exception e) {
			if(D) Log.d(TAG, ""+e.getMessage());
		}finally{
			try {
				if(is!=null){
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bm;
   }   
	
   /**
    * ����������ͼƬ.ѹ��
	* @param file File����
	* @param newWidth ��ͼƬ�Ŀ�
	* @param newHeight ��ͼƬ�ĸ�
	* @return Bitmap ��ͼƬ
	*/
	public static Bitmap scaleImg(File file,int newWidth, int newHeight){ 
		Bitmap resizeBmp = null;
	    BitmapFactory.Options opts = new BitmapFactory.Options(); 
	    //����Ϊtrue,decodeFile�Ȳ������ڴ� ֻ��ȡһЩ����߽���Ϣ��ͼƬ��С��Ϣ
	    opts.inJustDecodeBounds = true;	
	    BitmapFactory.decodeFile(file.getPath(),opts);
	    if(newWidth!=-1 && newHeight!=-1){
		    //inSampleSize=2��ʾͼƬ��߶�Ϊԭ���Ķ���֮һ����ͼƬΪԭ�����ķ�֮һ
		    //���ſ��Խ����ص��
			int srcWidth = opts.outWidth;  // ��ȡͼƬ��ԭʼ���
			int srcHeight = opts.outHeight;// ��ȡͼƬԭʼ�߶�
			int destWidth = 0;
			int destHeight = 0;
			// ���ŵı���
			double ratio = 0.0;
			if (srcWidth < newWidth || srcHeight < newHeight) {
				ratio = 0.0;
				destWidth = srcWidth;
				destHeight = srcHeight;
			// �������������ź��ͼƬ��С
			} else if (srcWidth > srcHeight) {
				ratio = (double) srcWidth / newWidth;
				destWidth = newWidth;
				destHeight = (int) (srcHeight / ratio);
			} else {
				ratio = (double) srcHeight / newHeight;
				destHeight = newHeight;
				destWidth = (int) (srcWidth / ratio);
			}
			// ���ŵı����������Ǻ��Ѱ�׼���ı����������ŵģ�Ŀǰ��ֻ����ֻ��ͨ��inSampleSize���������ţ���ֵ�������ŵı�����SDK�н�����ֵ��2��ָ��ֵ
			opts.inSampleSize = (int) ratio + 1;
		    // ���ô�С
			opts.outHeight = destHeight;
			opts.outWidth = destWidth;
	    }else{
	    	opts.inSampleSize = 1;
	    }
	    //�����ڴ�
		opts.inJustDecodeBounds = false;	
	    //ʹͼƬ������  
		opts.inDither = false;   
	    resizeBmp = BitmapFactory.decodeFile(file.getPath(),opts);
	    return resizeBmp;
	  }
	  
	/**
	 * ����������ͼƬ,��ѹ��������.
	 *
	 * @param bitmap the bitmap
	 * @param newWidth ��ͼƬ�Ŀ�
	 * @param newHeight ��ͼƬ�ĸ�
	 * @return Bitmap ��ͼƬ
	 */
	  public static Bitmap scaleImg(Bitmap bitmap, int newWidth, int newHeight) {
	        if(bitmap == null){
	        	return null;
	        }
	        if(newHeight<=0 || newWidth<=0){
	        	return bitmap;
	        }
		    // ���ͼƬ�Ŀ��
	        int width = bitmap.getWidth();
	        int height = bitmap.getHeight();
	        
	        if(width <= 0 || height <= 0){
		 		  return null;
		 	 }
	        // �������ű���
	        float scaleWidth = ((float) newWidth) / width;
	        float scaleHeight = ((float) newHeight) / height;
	        // ȡ����Ҫ���ŵ�matrix����
	        Matrix matrix = new Matrix();
	        matrix.postScale(scaleWidth, scaleHeight);
	        //�õ��µ�ͼƬ
	        Bitmap newBm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,true);
	        return newBm;
	  }
	  
   /**
    * ����������ͼƬ.
    *
    * @param bitmap the bitmap
    * @param scale ����
    * @return Bitmap ��ͼƬ
    */
	  public static Bitmap scaleImg(Bitmap bitmap,int scale){
			Bitmap resizeBmp = null;
			try {
				//��ȡBitmap��Դ�Ŀ�͸�
				int bmpW = bitmap.getWidth();
				int bmpH = bitmap.getHeight();
				//ע�����Matirx��android.graphics���µ��Ǹ�
				Matrix mt = new Matrix();
				//��������ϵ�����ֱ�Ϊԭ����0.8��0.8
				mt.postScale(scale, scale);
				resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpW, bmpH, mt, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        return resizeBmp;
	  }
	  
	  /**
	   * �������ü�ͼƬ.
	   * @param file  File����
	   * @param newWidth ��ͼƬ�Ŀ�
	   * @param newHeight ��ͼƬ�ĸ�
	   * @return Bitmap ��ͼƬ
	   */
	   public static Bitmap cutImg(File file,int newWidth, int newHeight){ 
		    Bitmap newBitmap = null;
		    BitmapFactory.Options opts = new BitmapFactory.Options(); 
		    //����Ϊtrue,decodeFile�Ȳ������ڴ� ֻ��ȡһЩ����߽���Ϣ��ͼƬ��С��Ϣ
		    opts.inJustDecodeBounds = true;	
		    BitmapFactory.decodeFile(file.getPath(),opts);
		    if(newWidth!=-1 && newHeight!=-1){
			    //inSampleSize=2��ʾͼƬ��߶�Ϊԭ���Ķ���֮һ����ͼƬΪԭ�����ķ�֮һ
			    //���ſ��Խ����ص��,�ü�ǰ��ͼƬ����һЩ
				int srcWidth = opts.outWidth;  // ��ȡͼƬ��ԭʼ���
				int srcHeight = opts.outHeight;// ��ȡͼƬԭʼ�߶�
				int destWidth = 0;
				int destHeight = 0;
				int cutSrcWidth = newWidth*2;
				int cutSrcHeight = newHeight*2;
				
				// ���ŵı���
				double ratio = 0.0;
				if (srcWidth < cutSrcWidth || srcHeight < cutSrcHeight) {
					ratio = 0.0;
					destWidth = srcWidth;
					destHeight = srcHeight;
				// �������������ź��ͼƬ��С
				} else if (srcWidth > srcHeight) {
					ratio = (double) srcWidth / cutSrcWidth;
					destWidth = cutSrcWidth;
					destHeight = (int) (srcHeight / ratio);
				} else {
					ratio = (double) srcHeight / cutSrcHeight;
					destHeight = cutSrcHeight;
					destWidth = (int) (srcWidth / ratio);
				}
				// ���ŵı����������Ǻ��Ѱ�׼���ı����������ŵģ�Ŀǰ��ֻ����ֻ��ͨ��inSampleSize���������ţ���ֵ�������ŵı�����SDK�н�����ֵ��2��ָ��ֵ
				opts.inSampleSize = (int) ratio + 1;
			    // ���ô�С
				opts.outHeight = destHeight;
				opts.outWidth = destWidth;
		    }else{
		    	opts.inSampleSize = 1;
		    }
		    //�����ڴ�
		    opts.inJustDecodeBounds = false;	
		    //ʹͼƬ������  
		    opts.inDither = false;   		
		    Bitmap resizeBmp = BitmapFactory.decodeFile(file.getPath(),opts);
		    if(resizeBmp!=null){
		    	newBitmap = cutImg(resizeBmp,newWidth,newHeight);
		    }
		    if(newBitmap!=null){
		    	return newBitmap;
		    }else{
		    	return resizeBmp;
		    }
	  }
	  
	  /**
  	 * �������ü�ͼƬ.
  	 *
  	 * @param bitmap the bitmap
  	 * @param newWidth ��ͼƬ�Ŀ�
  	 * @param newHeight ��ͼƬ�ĸ�
  	 * @return Bitmap ��ͼƬ
  	 */
	  public static Bitmap cutImg(Bitmap bitmap, int newWidth, int newHeight) {
		  if(bitmap == null){
	        	return null;
	      }
		  Bitmap newBitmap = null;
	 	  if(newHeight <= 0 || newWidth <= 0){
	 		  return bitmap;
	 	  }
	      int width = bitmap.getWidth();
	      int height = bitmap.getHeight();
	      
	      if(width <= 0 || height <= 0){
	 		  return null;
	 	  }
	      int offsetX = 0;
	      int offsetY = 0;
	      
	      if(width>newWidth){
	    	  offsetX = (width-newWidth)/2;
	      }
	      if(height>newHeight){
	    	  offsetY = (height-newHeight)/2;
	      }
	      
	      newBitmap = Bitmap.createBitmap(bitmap,offsetX,offsetY,newWidth,newHeight);
	      return newBitmap;
	  }
	  
	  /**
	   * DrawableתBitmap.
	   * @param drawable Ҫת����Drawable
	   * @return Bitmap
	   */
	  public static Bitmap drawableToBitmap(Drawable drawable) {
	        Bitmap bitmap = Bitmap.createBitmap(
	                drawable.getIntrinsicWidth(),
	                drawable.getIntrinsicHeight(),
	                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565
	        );
	        Canvas canvas = new Canvas(bitmap);
	        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
	        drawable.draw(canvas);
	        return bitmap;
	}
	  
	 /**
	  * Drawable����ת��Bitmap����.
	  * @param bitmap Ҫת����Bitmap����
	  * @return Drawable ת����ɵ�Drawable����
	  */
	  public static Drawable bitmapToDrawable(Bitmap bitmap) {
		  BitmapDrawable mBitmapDrawable = null;
		  try {
			if(bitmap==null){
				 return null; 
			  }
			  mBitmapDrawable = new BitmapDrawable(bitmap);   
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  return mBitmapDrawable;
	}
  
   /**
    * ��Bitmapת��Ϊbyte[].
    *
    * @param bitmap the bitmap
    * @param mCompressFormat ͼƬ��ʽ Bitmap.CompressFormat.JPEG,CompressFormat.PNG
    * @param needRecycle �Ƿ���Ҫ����
    * @return byte[] ͼƬ��byte[]
    */
	public static byte[] bitmap2Bytes(Bitmap bitmap,Bitmap.CompressFormat mCompressFormat,final boolean needRecycle){  
		byte[] result = null;
		ByteArrayOutputStream output = null;
		try {
			output = new ByteArrayOutputStream();    
			bitmap.compress(mCompressFormat, 100, output); 
			result = output.toByteArray();
			if (needRecycle) {
				bitmap.recycle();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(output!=null){
				try {
					output.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	    return result;
	 }  
	
	/**
	 * ��������byte[]ת��ΪBitmap.
	 * @param b ͼƬ��ʽ��byte[]����
	 * @return bitmap �õ���Bitmap
	 */
	public static  Bitmap bytes2Bimap(byte[] b){  
		Bitmap bitmap = null;
        try {
			if(b.length!=0){  
				bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
        return bitmap;
  }  
	
	/**
	 * ��ImageViewת��ΪBitmap.
	 * @param view Ҫת��Ϊbitmap��View
	 * @return byte[] ͼƬ��byte[]
	 */
	public static Bitmap imageView2Bitmap(ImageView view){  
		Bitmap bitmap = null;
		try {
			bitmap = Bitmap.createBitmap(view.getDrawingCache());
			view.setDrawingCacheEnabled(false);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	    return bitmap;
	 }  
	
	
	/**
	 * ��Viewת��ΪDrawable.��Ҫ���ϲ㲼��ΪLinearlayout
	 * @param view Ҫת��ΪDrawable��View
	 * @return BitmapDrawable Drawable
	 */
	public static Drawable view2Drawable(View view){  
		    BitmapDrawable mBitmapDrawable = null;
			try {
				Bitmap newbmp = view2Bitmap(view);
				if(newbmp!=null){
					mBitmapDrawable = new BitmapDrawable(newbmp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mBitmapDrawable;
	 }  
	
	/**
	 * ��Viewת��ΪBitmap.��Ҫ���ϲ㲼��ΪLinearlayout
	 * @param view Ҫת��Ϊbitmap��View
	 * @return byte[] ͼƬ��byte[]
	 */
	public static Bitmap view2Bitmap(View view){  
		Bitmap bitmap = null;
		try {
			if (view != null) {
				view.setDrawingCacheEnabled(true);
				view.measure(
						MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
						MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
				view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
				view.buildDrawingCache();
				bitmap = view.getDrawingCache();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	 }  
	
	/**
	 * ��Viewת��Ϊbyte[].
	 *
	 * @param view Ҫת��Ϊbyte[]��View
	 * @param compressFormat the compress format
	 * @return byte[] ViewͼƬ��byte[]
	 */
	public static byte[] view2Bytes(View view,Bitmap.CompressFormat compressFormat){  
		byte[] b = null;
		try {
			Bitmap bitmap = AbImageUtil.view2Bitmap(view);
			b = AbImageUtil.bitmap2Bytes(bitmap,compressFormat,true);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	    return b;
	 } 
	
	/**
	 * ��������תBitmapΪһ���ĽǶ�.
	 *
	 * @param bitmap the bitmap
	 * @param degrees the degrees
	 * @return the bitmap
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap,float degrees){  
		Bitmap mBitmap = null;
		try {
			Matrix m = new Matrix();
			m.setRotate(degrees%360);
			mBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),m,false);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	    return mBitmap;
   } 
	
	/**
	 * ��������תBitmapΪһ���ĽǶȲ����ܰ�������.
	 *
	 * @param bitmap the bitmap
	 * @param degrees the degrees
	 * @return the bitmap
	 */
	public static Bitmap rotateBitmapTranslate(Bitmap bitmap,float degrees){  
		Bitmap mBitmap = null;
		int width;
		int height;
		try {
			Matrix matrix = new Matrix();
	        if ((degrees / 90) % 2!= 0) {
	        	width =  bitmap.getWidth();
	        	height =  bitmap.getHeight();
            } else {
            	width = bitmap.getHeight();
            	height =  bitmap.getWidth();
            }
            int cx = width / 2;
            int cy = height/ 2;
            matrix.preTranslate(-cx, -cy);
            matrix.postRotate(degrees);
            matrix.postTranslate(cx, cy);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	    return mBitmap;
   } 
	
	/**
	 * ת��ͼƬת����Բ��.
	 *
	 * @param bitmap ����Bitmap����
	 * @return the bitmap
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		if(bitmap == null){
			return null;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}
	  
   /**
	* �ͷ�Bitmap����.
	* @param bitmap Ҫ�ͷŵ�Bitmap
	*/
	public static void releaseBitmap(Bitmap bitmap){
		if(bitmap!=null){
			try {
				if(!bitmap.isRecycled()){
					if(D) Log.d(TAG, "Bitmap�ͷ�"+bitmap.toString());
					bitmap.recycle();
				}
			} catch (Exception e) {
			}
			bitmap = null;
		}
	}
	
	/**
	* �ͷ�Bitmap����.
	* @param bitmaps Ҫ�ͷŵ�Bitmap����
	*/
	public static void releaseBitmapArray(Bitmap[] bitmaps){
		if(bitmaps!=null){
			try {
				for(Bitmap bitmap:bitmaps){
					if(bitmap!=null && !bitmap.isRecycled()){
						if(D) Log.d(TAG, "Bitmap�ͷ�"+bitmap.toString());
						bitmap.recycle();
					}
				}
			} catch (Exception e) {
			}
		}
	}

}
