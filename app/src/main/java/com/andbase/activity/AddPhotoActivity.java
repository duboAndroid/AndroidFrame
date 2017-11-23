package com.andbase.activity;

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
import com.ab.util.AbFileUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbViewUtil;
import com.andbase.R;
import com.andbase.global.Constant;
import com.andbase.global.MyApplication;

import java.io.File;
import java.util.ArrayList;

public class AddPhotoActivity extends AbActivity {
	private static final String TAG = "AddPhotoActivity";
	private static final boolean D = Constant.DEBUG;

	private MyApplication application;
	private GridView mGridView = null;
	private AbImageShowAdapter mImagePathAdapter = null;
	private ArrayList<String> mPhotoList = new ArrayList<String>();
	private int selectIndex = 0;
	private int camIndex = 0;
	private View mAvatarView = null;
	/* ������ʶ�������๦�ܵ�activity */
	private static final int CAMERA_WITH_DATA = 3023;
	/* ������ʶ����gallery��activity */
	private static final int PHOTO_PICKED_WITH_DATA = 3021;
	/* ������ʶ����ü�ͼƬ���activity */
	private static final int CAMERA_CROP_DATA = 3022;
	/* ���յ���Ƭ�洢λ�� */
	private  File PHOTO_DIR = null;
	// ��������յõ���ͼƬ
	private File mCurrentPhotoFile;
	private String mFileName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.add_photo);
		application = (MyApplication) abApplication;
		this.setTitleText(R.string.photo_add_name);
		this.setLogo(R.drawable.button_selector_back);
	    this.setTitleLayoutBackground(R.drawable.top_bg);
	    this.setTitleTextMargin(10, 0, 0, 0);
		this.setLogoLine(R.drawable.line);
		
		initTitleRightLayout();
		mPhotoList.add(String.valueOf(R.drawable.cam_photo));
		
		mGridView = (GridView)findViewById(R.id.myGrid);
		mImagePathAdapter = new AbImageShowAdapter(this, mPhotoList,116,116);
		mGridView.setAdapter(mImagePathAdapter);
	    mAvatarView = mInflater.inflate(R.layout.choose_avatar, null);
		
		//��ʼ��ͼƬ����·��
	    String photo_dir = AbFileUtil.getDefaultImageDownPathDir();
	    if(AbStrUtil.isEmpty(photo_dir)){
	    	showToast("�洢��������");
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
				// �������ȥ��ȡ
				try {
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
					intent.setType("image/*");
					startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
				} catch (ActivityNotFoundException e) {
					showToast("û���ҵ���Ƭ");
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
		
	}
	
	private void initTitleRightLayout() {
		
	}
	
	/**
	 * ���������������ȡ
	 */
	private void doPickPhotoAction() {
		String status = Environment.getExternalStorageState();
		//�ж��Ƿ���SD��,�����sd������sd����˵��û��sd��ֱ��ת��ΪͼƬ
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			doTakePhoto();
		} else {
			showToast("û�п��õĴ洢��");
		}
	}

	/**
	 * ���ջ�ȡͼƬ
	 */
	protected void doTakePhoto() {
		try {
			mFileName = System.currentTimeMillis() + ".jpg";
			mCurrentPhotoFile = new File(PHOTO_DIR, mFileName);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (Exception e) {
			showToast("δ�ҵ�ϵͳ�������");
		}
	}
	
	/**
	 * ��������Ϊ������Camera��Gally����Ҫ�ж����Ǹ��Եķ������,
	 * ��������ʱ��������startActivityForResult
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
		        	showToast("δ�ڴ洢�����ҵ�����ļ�");
		        }
				break;
			case CAMERA_WITH_DATA:
				if(D)Log.d(TAG, "��Ҫ���вü���ͼƬ��·���� = " + mCurrentPhotoFile.getPath());
				String currentFilePath2 = mCurrentPhotoFile.getPath();
				Intent intent2 = new Intent(this, CropImageActivity.class);
				intent2.putExtra("PATH",currentFilePath2);
				startActivityForResult(intent2,CAMERA_CROP_DATA);
				break;
			case CAMERA_CROP_DATA:
				String path = mIntent.getStringExtra("PATH");
		    	if(D)Log.d(TAG, "�ü���õ���ͼƬ��·���� = " + path);
		    	mImagePathAdapter.addItem(mImagePathAdapter.getCount()-1,path);
		     	camIndex++;
		    	AbViewUtil.setAbsListViewHeight(mGridView,3,25);
				break;
		}
	}

	/**
	 * �����õ���urlת��ΪSD����ͼƬ·��
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
