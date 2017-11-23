package com.andbase.activity;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.protocol.HTTP;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.ab.activity.AbActivity;
import com.ab.adapter.AbImageShowAdapter;
import com.ab.global.AbConstant;
import com.ab.model.AbResult;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpPool;
import com.ab.util.AbFileUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbViewUtil;
import com.andbase.R;
import com.andbase.global.Constant;
import com.andbase.global.MyApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static java.net.Proxy.Type.HTTP;

public class UploadPhotoActivity extends AbActivity {
	private static final String TAG = "AddPhotoActivity";
	private static final boolean D = Constant.DEBUG;

	private MyApplication application;
	private GridView mGridView = null;
	private AbImageShowAdapter mImagePathAdapter = null;
	private ArrayList<String> mPhotoList = new ArrayList<String>();
	private int selectIndex = 0;
	private int camIndex = 0;
	private View mAvatarView = null;
	/* 用来标识请求照相功能的activity */
	private static final int CAMERA_WITH_DATA = 3023;
	/* 用来标识请求gallery的activity */
	private static final int PHOTO_PICKED_WITH_DATA = 3021;
	/* 用来标识请求裁剪图片后的activity */
	private static final int CAMERA_CROP_DATA = 3022;
	/* 拍照的照片存储位置 */
	private  File PHOTO_DIR = null;
	// 照相机拍照得到的图片
	private File mCurrentPhotoFile;
	private String mFileName;
	
	private AbResult mAbResult = null;
	private HashMap<String, String> params = new HashMap<String, String>();
	private HashMap<String, File> files = new HashMap<String, File>();
	private AbHttpPool mAbHttpPool = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.upload_photo);
		application = (MyApplication) abApplication;
		this.setTitleText(R.string.photo_add_name);
		this.setLogo(R.drawable.button_selector_back);
	    this.setTitleLayoutBackground(R.drawable.top_bg);
	    this.setTitleTextMargin(10, 0, 0, 0);
		this.setLogoLine(R.drawable.line);
		
		initTitleRightLayout();
		mPhotoList.add(String.valueOf(R.drawable.cam_photo));
		
		mGridView = (GridView)findViewById(R.id.myGrid);
		Button addBtn = (Button)findViewById(R.id.addBtn);
		mImagePathAdapter = new AbImageShowAdapter(this, mPhotoList,116,116);
		mGridView.setAdapter(mImagePathAdapter);
	    mAvatarView = mInflater.inflate(R.layout.choose_avatar, null);
		
		//初始化图片保存路径
	    String photo_dir = AbFileUtil.getDefaultImageDownPathDir();
	    if(AbStrUtil.isEmpty(photo_dir)){
	    	showToast("存储卡不存在");
	    }else{
	    	PHOTO_DIR = new File(photo_dir);
	    }
		
		Button albumButton = (Button)mAvatarView.findViewById(R.id.choose_album);
		Button camButton = (Button)mAvatarView.findViewById(R.id.choose_cam);
		Button cancelButton = (Button)mAvatarView.findViewById(R.id.choose_cancel);
		
		albumButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				removeDialog(AbConstant.DIALOGBOTTOM);
				// 从相册中去获取
				try {
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
					intent.setType("image/*");
					startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
				} catch (ActivityNotFoundException e) {
					showToast("没有找到照片");
				}
			}
			
		});
		
		camButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				removeDialog(AbConstant.DIALOGBOTTOM);
				doPickPhotoAction();
			}
			
		});
		
		cancelButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				removeDialog(AbConstant.DIALOGBOTTOM);
			}
			
		});
		
		
		mGridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectIndex = position;
				if(selectIndex == camIndex){
					showDialog(1,mAvatarView,40);
				}else{
					for(int i=0;i<mImagePathAdapter.getCount();i++){
						AbImageShowAdapter.ViewHolder mViewHolder = (AbImageShowAdapter.ViewHolder)mGridView.getChildAt(i).getTag();
						if(mViewHolder!=null){
							mViewHolder.mImageView2.setBackgroundDrawable(null);
						}
					}
	
					AbImageShowAdapter.ViewHolder mViewHolder = (AbImageShowAdapter.ViewHolder)view.getTag();
					mViewHolder.mImageView2.setBackgroundResource(R.drawable.photo_select);
				}
			}
			
		});
		
		mAbHttpPool = AbHttpPool.getInstance();
		
		
		addBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				params.clear();
				files.clear();
				if(mPhotoList.size()<2){
					showToast("请先选择要上传的图片");
					return ;
				}
				
				showToast("本例为了演示AbFileUtil.postFile方法的使用");
				try {
					params.put("data1",URLEncoder.encode("中文可处理","UTF_8"));
					params.put("data2","第二个参数");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				//第一个是照相机的图标，上传要去掉
				for(int i=1;i<mPhotoList.size();i++){
					String path = mPhotoList.get(i);
					File file = new File(path);
					files.put(file.getName(),file);
				}
				
				showDialog(AbConstant.DIALOGPROGRESS);
				final AbHttpItem item = new AbHttpItem();
				item.callback = new AbHttpCallback() {

					@Override
					public void update() {
						removeDialog(AbConstant.DIALOGPROGRESS);
						if(mAbResult != null){
							showToast(mAbResult.getResultMsg());
				        	if(mAbResult.getResultCode()==AbConstant.RESULRCODE_OK){
				        		showToast("上传成功");
				        		finish();
				        	}
				        }
					}

					@Override
					public void get() {
			   		    try {
			   		    	//采用post方法上传
			   		    	String responseStr = AbFileUtil.postFile(Constant.ADDOVERLAYURL, params, files);
			   		 	    //服务端的接受代码，采用org.apache.commons.fileupload
			   		    	//FileUploadUtil类在util包下可以copy到web应用中
			   		    	//String newPath = request.getSession().getServletContext().getRealPath(Constant.SEPARATOR + Constant.GFIMAGES + Constant.SEPARATOR);
			   				//FileUploadUtil upload = new FileUploadUtil(new File(newPath));
			   				// 开始上传文件,文件名和路径
			   				//HashMap<String,String> filePaths  = upload.download(request,"GBK");
			   		    	
			   		    	//服务端的接口返回AbResult的json
			   		    	GsonBuilder builder = new GsonBuilder();
						    Gson gson = builder.create();
						    mAbResult = gson.fromJson(responseStr, AbResult.class);
			   		    } catch (Exception e){
			   		    	showToastInThread(e.getMessage());
			   		    }
				  };
				};
				mAbHttpPool.download(item);
			}
		});
		
	}
	
	private void initTitleRightLayout() {
		
	}
	
	/**
	 * 描述：从照相机获取
	 */
	private void doPickPhotoAction() {
		String status = Environment.getExternalStorageState();
		//判断是否有SD卡,如果有sd卡存入sd卡在说，没有sd卡直接转换为图片
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			doTakePhoto();
		} else {
			showToast("没有可用的存储卡");
		}
	}

	/**
	 * 拍照获取图片
	 */
	protected void doTakePhoto() {
		try {
			mFileName = System.currentTimeMillis() + ".jpg";
			mCurrentPhotoFile = new File(PHOTO_DIR, mFileName);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (Exception e) {
			showToast("未找到系统相机程序");
		}
	}
	
	/**
	 * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况,
	 * 他们启动时是这样的startActivityForResult
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent mIntent) {
		if (resultCode != RESULT_OK){
			return;
		}
		switch (requestCode) {
			case PHOTO_PICKED_WITH_DATA:
				Uri uri = mIntent.getData();
				String currentFilePath = getPath(uri);
				if(!AbStrUtil.isEmpty(currentFilePath)){
					Intent intent1 = new Intent(this, CropImageActivity.class);
					intent1.putExtra("PATH", currentFilePath);
					startActivityForResult(intent1, CAMERA_CROP_DATA);
		        }else{
		        	showToast("未在存储卡中找到这个文件");
		        }
				break;
			case CAMERA_WITH_DATA:
				if(D)Log.d(TAG, "将要进行裁剪的图片的路径是 = " + mCurrentPhotoFile.getPath());
				String currentFilePath2 = mCurrentPhotoFile.getPath();
				Intent intent2 = new Intent(this, CropImageActivity.class);
				intent2.putExtra("PATH",currentFilePath2);
				startActivityForResult(intent2,CAMERA_CROP_DATA);
				break;
			case CAMERA_CROP_DATA:
				String path = mIntent.getStringExtra("PATH");
		    	if(D)Log.d(TAG, "裁剪后得到的图片的路径是 = " + path);
		    	mImagePathAdapter.addItem(mImagePathAdapter.getCount()-1,path);
		     	camIndex++;
		    	AbViewUtil.setAbsListViewHeight(mGridView,3,25);
				break;
		}
	}

	/**
	 * 从相册得到的url转换为SD卡中图片路径
	 */
	public String getPath(Uri uri) {
		if(AbStrUtil.isEmpty(uri.getAuthority())){
			return null;
		}
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(column_index);
		return path;
	}
	
	
	

}
