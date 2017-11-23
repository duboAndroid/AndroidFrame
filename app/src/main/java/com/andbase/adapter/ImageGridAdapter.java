package com.andbase.adapter;




import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ab.global.AbConstant;
import com.ab.net.AbImageDownloadCallback;
import com.ab.net.AbImageDownloadItem;
import com.ab.net.AbImageDownloadQueue;
import com.ab.util.AbFileUtil;
import com.ab.util.AbStrUtil;
import com.andbase.model.User;
/**
 * Copyright (c) 2011 All rights reserved
 * 名称：OverlayGridAdapter
 * 描述：在Adapter中释放Bitmap
 * @author zhaoqp
 * @date 2011-12-10
 * @version
 */
public class ImageGridAdapter extends BaseAdapter{
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List<User> mData;
    //Map中的key
    private String[] mFrom;
    //view的id
    private int[] mTo;
    private AbImageDownloadQueue mAbImageDownloadQueue = null;
    
   /**
    * 构造方法
    * @param context
    * @param data 列表展现的数据
    * @param resource 单行的布局
    * @param from Map中的key
    * @param to view的id
    */
    public ImageGridAdapter(Context context, List<User> data,
            int resource, String[] from, int[] to){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
    	this.mFrom = from;
    	this.mTo = to;
        //用于将xml转为View
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mAbImageDownloadQueue = AbImageDownloadQueue.getInstance();
    }   
    
    @Override
    public int getCount() {
        return mData.size();
    }
    
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position){
      return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	  final ViewHolder holder;
          if(convertView == null){
	           convertView = mInflater.inflate(mResource, parent, false);
			   holder = new ViewHolder();
			   holder.itemsIcon = ((ImageView) convertView.findViewById(mTo[0])) ;
			   convertView.setTag(holder);
          }else{
        	   holder = (ViewHolder) convertView.getTag();
          }
          
		  //获取该行的数据
          final User mUser = (User)mData.get(position);
          String imageUrl = mUser.getPhotoUrl();
          holder.itemsIcon.setImageBitmap(null); 
          if(!AbStrUtil.isEmpty(imageUrl)){
	          //设置下载项 
	          AbImageDownloadItem item = new AbImageDownloadItem(); 
		      //设置显示的大小
		      item.width = 80;
		      item.height = 80;
		      item.imageUrl = imageUrl; 
		      item.type = AbConstant.SCALEIMG;
	          holder.itemsIcon.setFocusable(false);
		      //下载完成后更新界面
		      item.callback = new AbImageDownloadCallback() { 
		            @Override 
		            public void update(Bitmap bitmap, String imageUrl) { 
		            	if(bitmap!=null){
		            		holder.itemsIcon.setImageBitmap(bitmap); 
		            	}else{
		            		holder.itemsIcon.setImageBitmap(AbFileUtil.getBitmapFormSrc("image/image_error.png"));
		            	}
		            } 
		      }; 
		      mAbImageDownloadQueue.download(item); 
          }else{
        	  holder.itemsIcon.setImageBitmap(AbFileUtil.getBitmapFormSrc("image/image_no.png"));
          }
          return convertView;
    }
    
    /**
	 * View元素
	 */
	static class ViewHolder {
		ImageView itemsIcon;
	}
    
}