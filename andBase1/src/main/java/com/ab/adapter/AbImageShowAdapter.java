/*
 * 
 */
package com.ab.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ab.global.AbConstant;
import com.ab.net.AbImageDownloadCallback;
import com.ab.net.AbImageDownloadItem;
import com.ab.net.AbImageDownloadQueue;
import com.ab.util.AbFileUtil;

// TODO: Auto-generated Javadoc
/**
 * 适配器 网络URL的图片.
 */
public class AbImageShowAdapter extends BaseAdapter {
	
	/** The m context. */
	private Context mContext;
	
	/** The m image paths. */
	private List<String> mImagePaths = null;
	
	/** The m width. */
	private int mWidth;
	
	/** The m height. */
	private int mHeight;
	
	/** The m ab image download queue. */
	private AbImageDownloadQueue mAbImageDownloadQueue = null;
	
	/**
	 * Instantiates a new ab image show adapter.
	 * @param context the context
	 * @param imagePaths the image paths
	 * @param width the width
	 * @param height the height
	 */
	public AbImageShowAdapter(Context context,List<String> imagePaths,int width,int height) {
		mContext = context;
		this.mImagePaths = imagePaths;
		this.mWidth = width;
		this.mHeight = height;
		this.mAbImageDownloadQueue = AbImageDownloadQueue.getInstance();
	}

	/**
	 * 描述：获取数量.
	 *
	 * @return the count
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		return mImagePaths.size();
	}

	/**
	 * 描述：获取索引位置的路径.
	 *
	 * @param position the position
	 * @return the item
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int position) {
		return mImagePaths.get(position);
	}

	/**
	 * 描述：获取位置.
	 *
	 * @param position the position
	 * @return the item id
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 描述：显示View.
	 *
	 * @param position the position
	 * @param convertView the convert view
	 * @param parent the parent
	 * @return the view
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			LinearLayout mLinearLayout = new LinearLayout(mContext);
			RelativeLayout mRelativeLayout = new RelativeLayout(mContext);
			ImageView mImageView1 = new ImageView(mContext);
			mImageView1.setScaleType(ScaleType.FIT_CENTER);
			ImageView mImageView2 = new ImageView(mContext);
			mImageView2.setScaleType(ScaleType.FIT_CENTER);
			holder.mImageView1 = mImageView1;
			holder.mImageView2 = mImageView2;
			LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
			lp1.gravity = Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
			RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(mWidth,mHeight);
	        lp2.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
	        lp2.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
			mRelativeLayout.addView(mImageView1,lp2);
			mRelativeLayout.addView(mImageView2,lp2);
			mLinearLayout.addView(mRelativeLayout,lp1);
			
			convertView = mLinearLayout;
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.mImageView1.setImageBitmap(null);
		holder.mImageView2.setBackgroundDrawable(null);
		
		String imagePath = mImagePaths.get(position);
		holder.mImageView1.setImageBitmap(AbFileUtil.getBitmapFormSrc("image/image_loading.png"));
		if(!TextUtils.isEmpty(imagePath)){
			if(imagePath.indexOf("http://")!=-1){
				  //设置下载项 
		          AbImageDownloadItem item = new AbImageDownloadItem(); 
			      //设置显示的大小
			      item.width = mWidth;
			      item.height = mHeight;
			      item.imageUrl = imagePath; 
			      item.type = AbConstant.SCALEIMG;
			      //下载完成后更新界面
			      item.callback = new AbImageDownloadCallback() { 
			            @Override 
			            public void update(final Bitmap bitmap, String imageUrl) { 
			            	if(bitmap!=null){
			            		holder.mImageView1.post(new Runnable() {
									@Override
									public void run() {
										holder.mImageView1.setImageBitmap(bitmap); 
									}
								});
			            	}else{
			            		holder.mImageView1.setImageBitmap(AbFileUtil.getBitmapFormSrc("image/image_error.png"));
			            	}
			            } 
			      }; 
			      mAbImageDownloadQueue.download(item); 
			}else if(imagePath.indexOf("/")==-1){
				//索引图片
				try {
					int res  = Integer.parseInt(imagePath);
					holder.mImageView1.setImageDrawable(mContext.getResources().getDrawable(res));
				} catch (Exception e) {
					holder.mImageView1.setImageBitmap(AbFileUtil.getBitmapFormSrc("image/image_error.png"));
				}
			}else{
				Bitmap mBitmap = AbFileUtil.getBitmapFromSD(new File(imagePath), AbConstant.SCALEIMG, mWidth, mHeight);
				if(mBitmap!=null){
					holder.mImageView1.setImageBitmap(mBitmap);
				}else{
					// 无图片时显示
					holder.mImageView1.setImageBitmap(AbFileUtil.getBitmapFormSrc("image/image_error.png"));
				}
			}
			
		}else{
			// 无图片时显示
			holder.mImageView1.setImageBitmap(AbFileUtil.getBitmapFormSrc("image/image_no.png"));
	    }
		holder.mImageView1.setAdjustViewBounds(true);
	    return convertView;
	}
	
	
	/**
	 * 增加并改变视图.
	 * @param position the position
	 * @param imagePaths the image paths
	 */
	public void addItem(int position,String imagePaths) {
		mImagePaths.add(position,imagePaths);
		notifyDataSetChanged();
	}
	
	/**
	 * 增加多条并改变视图.
	 * @param imagePaths the image paths
	 */
	public void addItems(List<String> imagePaths) {
		mImagePaths.addAll(imagePaths);
		notifyDataSetChanged();
	}
	
	/**
	 * 增加多条并改变视图.
	 */
	public void clearItems() {
		mImagePaths.clear();
		notifyDataSetChanged();
	}
	
	/**
	 * View元素.
	 */
	public static class ViewHolder {
		
		/** The m image view1. */
		public ImageView mImageView1;
		
		/** The m image view2. */
		public ImageView mImageView2;
	}

}
