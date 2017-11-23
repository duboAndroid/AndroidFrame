package com.andbase.adapter;




import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.global.AbConstant;
import com.ab.net.AbImageDownloadCallback;
import com.ab.net.AbImageDownloadItem;
import com.ab.net.AbImageDownloadQueue;
import com.ab.util.AbFileUtil;
import com.ab.util.AbStrUtil;
import com.andbase.global.Constant;
/**
 * Copyright (c) 2011 All rights reserved
 * 名称：MyListViewAdapter
 * 描述：在Adapter中释放Bitmap
 * @author zhaoqp
 * @date 2011-12-10
 * @version
 */
public class ImageListAdapter extends BaseAdapter{
	
	private static String TAG = "ImageListAdapter";
	private static final boolean D = Constant.DEBUG;
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List mData;
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
    public ImageListAdapter(Context context, List data,
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
	           //使用自定义的list_items作为Layout
	           convertView = mInflater.inflate(mResource, parent, false);
	           //减少findView的次数
			   holder = new ViewHolder();
	           //初始化布局中的元素
			   holder.itemsIcon = ((ImageView) convertView.findViewById(mTo[0])) ;
			   holder.itemsTitle = ((TextView) convertView.findViewById(mTo[1]));
			   holder.itemsText = ((TextView) convertView.findViewById(mTo[2]));
			   convertView.setTag(holder);
          }else{
        	   holder = (ViewHolder) convertView.getTag();
          }
          
		  //获取该行的数据
          final Map<String, Object>  obj = (Map<String, Object>)mData.get(position);
          String imageUrl = (String)obj.get("itemsIcon");
          holder.itemsTitle.setText((String)obj.get("itemsTitle"));
          holder.itemsText.setText((String)obj.get("itemsText"));
          if(!AbStrUtil.isEmpty(imageUrl)){
        	  //设置下载项 
              AbImageDownloadItem item = new AbImageDownloadItem(); 
    	      //设置显示的大小
    	      item.width = 80;
    	      item.height = 80;
    	      //设置为缩放
    	      item.type = AbConstant.SCALEIMG;
    	      item.imageUrl = imageUrl;
    	     
              holder.itemsIcon.setImageBitmap(AbFileUtil.getBitmapFormSrc("image/image_loading.png"));
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
		TextView itemsTitle;
		TextView itemsText;
		ImageButton itemsBtn;
	}
    
}