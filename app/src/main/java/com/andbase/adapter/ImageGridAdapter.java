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
 * ���ƣ�OverlayGridAdapter
 * ��������Adapter���ͷ�Bitmap
 * @author zhaoqp
 * @date 2011-12-10
 * @version
 */
public class ImageGridAdapter extends BaseAdapter{
  
	private Context mContext;
	//xmlתView����
    private LayoutInflater mInflater;
    //���еĲ���
    private int mResource;
    //�б�չ�ֵ�����
    private List<User> mData;
    //Map�е�key
    private String[] mFrom;
    //view��id
    private int[] mTo;
    private AbImageDownloadQueue mAbImageDownloadQueue = null;
    
   /**
    * ���췽��
    * @param context
    * @param data �б�չ�ֵ�����
    * @param resource ���еĲ���
    * @param from Map�е�key
    * @param to view��id
    */
    public ImageGridAdapter(Context context, List<User> data,
            int resource, String[] from, int[] to){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
    	this.mFrom = from;
    	this.mTo = to;
        //���ڽ�xmlתΪView
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
          
		  //��ȡ���е�����
          final User mUser = (User)mData.get(position);
          String imageUrl = mUser.getPhotoUrl();
          holder.itemsIcon.setImageBitmap(null); 
          if(!AbStrUtil.isEmpty(imageUrl)){
	          //���������� 
	          AbImageDownloadItem item = new AbImageDownloadItem(); 
		      //������ʾ�Ĵ�С
		      item.width = 80;
		      item.height = 80;
		      item.imageUrl = imageUrl; 
		      item.type = AbConstant.SCALEIMG;
	          holder.itemsIcon.setFocusable(false);
		      //������ɺ���½���
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
	 * ViewԪ��
	 */
	static class ViewHolder {
		ImageView itemsIcon;
	}
    
}