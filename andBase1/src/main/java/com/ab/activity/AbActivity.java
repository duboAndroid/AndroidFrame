/*
 * 
 */
package com.ab.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextPaint;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ab.global.AbAppData;
import com.ab.global.AbConstant;
import com.ab.util.AbStrUtil;
import com.ab.util.AbViewUtil;
import com.ab.view.AbMonitorView;

// TODO: Auto-generated Javadoc
/**
 * �������̳����Activityʹ���Activityӵ��һ��������.
 * @author zhaoqp
 * @date��2013-1-15 ����3:27:05
 * @version v1.0
 */

public abstract class AbActivity extends FragmentActivity {
	
	/** The tag. */
	private String TAG = "AbActivity";
	
	/** The debug. */
	private boolean D = AbAppData.DEBUG;

	/** ���ؿ������˵��. */
	public String mProgressMessage = "���Ժ�...";
	
	/** ȫ�ֵ�LayoutInflater�����Ѿ���ɳ�ʼ��. */
	public LayoutInflater mInflater;
	
	/** ȫ�ֵļ��ؿ�����Ѿ���ɳ�ʼ��. */
	public ProgressDialog mProgressDialog;
	
	/** �ײ�������Dialog. */
	private Dialog mBottomDialog;
	
	/** ���е�����Dialog. */
	private Dialog mCenterDialog;
	
	/** �ײ�������Dialog��View. */
	private View mBottomDialogView = null;
	
	/** ���е�����Dialog��View. */
	private View mCenterDialogView = null;
	
	/** ȫ�ֵ�Application�����Ѿ���ɳ�ʼ��. */
	public Application abApplication = null;
	
	/** ȫ�ֵ�SharedPreferences�����Ѿ���ɳ�ʼ��. */
	public SharedPreferences  abSharedPreferences = null;
	
	/**
	 * LinearLayout.LayoutParams���Ѿ���ʼ��ΪFILL_PARENT, FILL_PARENT
	 */
	public LinearLayout.LayoutParams layoutParamsFF = null;
	
	/**
	 * LinearLayout.LayoutParams���Ѿ���ʼ��ΪFILL_PARENT, WRAP_CONTENT
	 */
	public LinearLayout.LayoutParams layoutParamsFW = null;
	
	/**
	 * LinearLayout.LayoutParams���Ѿ���ʼ��ΪWRAP_CONTENT, FILL_PARENT
	 */
	public LinearLayout.LayoutParams layoutParamsWF = null;
	
	/**
	 * LinearLayout.LayoutParams���Ѿ���ʼ��ΪWRAP_CONTENT, WRAP_CONTENT
	 */
	public LinearLayout.LayoutParams layoutParamsWW = null;
	
	/** ����Logoͼ��View. */
	protected ImageView logoView = null;
	
	/** ����Logoͼ���ұߵķָ���View. */
	protected ImageView logoLineView = null;
	
	/** �ܲ���. */
	public RelativeLayout ab_base = null;
	
	/** ����������. */
	protected LinearLayout titleLayout = null;
	
	/** ���Ⲽ��. */
	protected LinearLayout titleTextLayout = null;
	
	/** �����ı��Ķ������. */
	private LinearLayout.LayoutParams titleTextLayoutParams = null;
	
	/** �ұ߲��ֵĵĶ������. */
	private LinearLayout.LayoutParams rightViewLayoutParams = null;
	
	/** ����������ID. */
	private static final int titleLayoutID = 1;
	
	/** �����ݲ���. */
	protected RelativeLayout contentLayout = null;
	
	/** ��ʾ�������ֵ�View. */
	protected Button titleTextBtn = null;
	
	/** �ұߵ�View�������Զ�����ʾʲô. */
	protected LinearLayout rightLayout = null;
	
	/** The diaplay width. */
	public int diaplayWidth  = 320;
	
	/** The diaplay height. */
	public int diaplayHeight  = 480;
	
	/** ���ܲ���. */
	private boolean mMonitorOpened = false;
	
	/** The m ab monitor view. */
	private AbMonitorView mAbMonitorView  = null;
	
	/** The m monitor handler. */
	private Handler mMonitorHandler = new Handler();
	
	/** The m monitor runnable. */
	private Runnable mMonitorRunnable = null;
	
	/** The m window manager. */
	private WindowManager mWindowManager = null;
	
	/** The m monitor params. */
	private WindowManager.LayoutParams mMonitorParams = null;
	
    
	/**
	 * ��ҪHandler�࣬���߳��п���
	 * what��0.��ʾ�ı���Ϣ,1.�ȴ���   ,2.�Ƴ��ȴ��� 
	 */
	private Handler baseHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case AbConstant.SHOW_TOAST:
					showToast(msg.getData().getString("Msg"));
					break;
				case AbConstant.SHOW_PROGRESS:
					showProgressDialog(mProgressMessage);
					break;
				case AbConstant.REMOVE_PROGRESS:
					removeProgressDialog();
					break;
				case AbConstant.REMOVE_DIALOGBOTTOM:
					removeDialog(AbConstant.DIALOGBOTTOM);
				case AbConstant.REMOVE_DIALOGCENTER:
					removeDialog(AbConstant.DIALOGCENTER);
				default:
					break;
			}
		}
	};

	/**
	 * ����������.
	 *
	 * @param savedInstanceState the saved instance state
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mInflater = LayoutInflater.from(this);
		
		mWindowManager = getWindowManager();
		Display display = mWindowManager.getDefaultDisplay();
		diaplayWidth = display.getWidth();
		diaplayHeight = display.getHeight();
		
		layoutParamsFF = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		layoutParamsFW = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWF = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
		layoutParamsWW = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titleTextLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1);
		titleTextLayoutParams.gravity = Gravity.CENTER_VERTICAL;
		rightViewLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,0);
		rightViewLayoutParams.gravity = Gravity.CENTER_VERTICAL;
		ab_base = new RelativeLayout(this);
		ab_base.setBackgroundColor(Color.rgb(255, 255, 255));
		
		titleLayout = new LinearLayout(this);
		titleLayout.setId(titleLayoutID);
		titleLayout.setOrientation(LinearLayout.HORIZONTAL);
		titleLayout.setGravity(Gravity.CENTER_VERTICAL);
		titleLayout.setPadding(0, 0, 0, 0);
		
		contentLayout = new RelativeLayout(this);
		contentLayout.setPadding(0, 0, 0, 0);
		
		titleTextLayout = new LinearLayout(this);
		titleTextLayout.setOrientation(LinearLayout.HORIZONTAL);
		titleTextLayout.setGravity(Gravity.CENTER_VERTICAL);
		titleTextLayout.setPadding(0, 0, 0, 0);
		
		titleTextBtn = new Button(this);
		titleTextBtn.setTextColor(Color.rgb(255, 255, 255));
		titleTextBtn.setTextSize(20);
		titleTextBtn.setPadding(5, 0, 5, 0);
		titleTextBtn.setGravity(Gravity.CENTER_VERTICAL);
		titleTextBtn.setBackgroundDrawable(null);
		titleTextLayout.addView(titleTextBtn,layoutParamsWF);
		
		logoView = new ImageView(this);
		logoView.setVisibility(View.GONE);
		logoLineView = new ImageView(this);
		logoLineView.setVisibility(View.GONE);
		
		titleLayout.addView(logoView,layoutParamsWW);
		titleLayout.addView(logoLineView,layoutParamsWW);
		titleLayout.addView(titleTextLayout,titleTextLayoutParams);
		
		Intent intent = this.getIntent();
		int titleTransparentFlag = intent.getIntExtra(AbConstant.TITLE_TRANSPARENT_FLAG,AbConstant.TITLE_NOTRANSPARENT);
		
		if(titleTransparentFlag == AbConstant.TITLE_TRANSPARENT){
			ab_base.addView(contentLayout,layoutParamsFW);
			RelativeLayout.LayoutParams layoutParamsFW2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParamsFW2.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
			ab_base.addView(titleLayout,layoutParamsFW2);
		}else{
			ab_base.addView(titleLayout,layoutParamsFW);
			RelativeLayout.LayoutParams layoutParamsFW1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParamsFW1.addRule(RelativeLayout.BELOW, titleLayoutID);
			ab_base.addView(contentLayout, layoutParamsFW1);
		}
		
		// ���ұߵİ�ť
		rightLayout = new LinearLayout(this);
		rightLayout.setOrientation(LinearLayout.HORIZONTAL);
		rightLayout.setGravity(Gravity.RIGHT);
		rightLayout.setPadding(0, 0, 0, 0);
		rightLayout.setHorizontalGravity(Gravity.RIGHT);
		rightLayout.setGravity(Gravity.CENTER_VERTICAL);
		rightLayout.setVisibility(View.GONE);
		titleLayout.addView(rightLayout,rightViewLayoutParams);
		
		abApplication = getApplication();
		abSharedPreferences = getSharedPreferences(AbConstant.SHAREPATH, Context.MODE_PRIVATE);
		
        logoView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        mAbMonitorView  = new AbMonitorView(this);
		mMonitorParams = new WindowManager.LayoutParams();
        // ����window type
		mMonitorParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        /*
         * �������Ϊparams.type = WindowManager.LayoutParams.TYPE_PHONE;
         * ��ô���ȼ��ή��һЩ, ������֪ͨ�����ɼ�
         */
		//����ͼƬ��ʽ��Ч��Ϊ����͸��
		mMonitorParams.format = PixelFormat.RGBA_8888; 
        // ����Window flag
		mMonitorParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                              | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        /*
         * �����flags���Ե�Ч����ͬ����������
         * ���������ɴ������������κ��¼�,ͬʱ��Ӱ�������¼���Ӧ��
        wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL
                               | LayoutParams.FLAG_NOT_FOCUSABLE
                               | LayoutParams.FLAG_NOT_TOUCHABLE;
         */
        // �����������ĳ��ÿ�
		mMonitorParams.width = 60;
		mMonitorParams.height = 30;
		//if(D) this.openMonitor();
        setContentView(ab_base,layoutParamsFF);
	}
	
	/**
     * ������Toast��ʾ�ı�.
     * @param text  �ı�
     */
	public void showToast(String text) {
		Toast.makeText(this,""+text, Toast.LENGTH_SHORT).show();
	}
	
	/**
     * ������Toast��ʾ�ı�.
     * @param resId  �ı�����ԴID
     */
	public void showToast(int resId) {
		Toast.makeText(this,""+this.getResources().getText(resId), Toast.LENGTH_SHORT).show();
	}
	
	/**
     * ���������ñ����ı�.
     * @param text  �ı�
     */
	public void setTitleText(String text) {
		titleTextBtn.setText(text);
	}
	
	/**
     * ���������ñ����ı�.
     * @param resId  �ı�����ԴID
     */
	public void setTitleText(int resId) {
		titleTextBtn.setText(resId);
	}
	
	/**
     * ����������Logo�ı���ͼ.
     * @param drawable  Logo��ԴDrawable
     */
	public void setLogo(Drawable drawable) {
		logoView.setVisibility(View.VISIBLE);
		logoView.setBackgroundDrawable(drawable);
	}
	
	/**
     * ����������Logo�ı�����Դ.
     * @param resId  Logo��ԴID
     */
	public void setLogo(int resId) {
		logoView.setVisibility(View.VISIBLE);
		logoView.setBackgroundResource(resId);
	}
	
	/**
     * ����������Logo�ָ��ߵı�����Դ.
     * @param resId  Logo��ԴID
     */
	public void setLogoLine(int resId) {
		logoLineView.setVisibility(View.VISIBLE);
		logoLineView.setBackgroundResource(resId);
	}
	
	/**
     * ����������Logo�ָ��ߵı���ͼ.
     * @param drawable  Logo��ԴDrawable
     */
	public void setLogoLine(Drawable drawable) {
		logoLineView.setVisibility(View.VISIBLE);
		logoLineView.setBackgroundDrawable(drawable);
	}
	
	
	/**
     * ��������ָ����View��ӵ��������ұ�.
     * @param rightView  ָ����View
     */
	public void addRightView(View rightView) {
		rightLayout.setVisibility(View.VISIBLE);
		rightLayout.addView(rightView,layoutParamsWF);
	}
	
	/**
     * ��������ָ����ԴID��ʾ��View��ӵ��������ұ�.
     * @param resId  ָ����View����ԴID
     */
	public void addRightView(int resId) {
		rightLayout.setVisibility(View.VISIBLE);
		rightLayout.addView(mInflater.inflate(resId, null),layoutParamsWF);
	}
	
	/**
     * ����������������ұߵ�View.
     */
	public void clearRightView() {
		rightLayout.removeAllViews();
	}
	
	/**
	 * ��������ָ����View���������.
	 * @param contentView  ָ����View
	 */
	public void setAbContentView(View contentView) {
		contentLayout.removeAllViews();
		contentLayout.addView(contentView,layoutParamsFF);
	}
	
	/**
	 * ��������ָ����ԴID��ʾ��View���������.
	 * @param resId  ָ����View����ԴID
	 */
	public void setAbContentView(int resId) {
		contentLayout.removeAllViews();
		contentLayout.addView(mInflater.inflate(resId, null),layoutParamsFF);
	}
	
	/**
	 * ����������Logo��ť�ķ����¼�.
	 * @param mOnClickListener  ָ���ķ����¼�
	 */
	public void setLogoBackOnClickListener(View.OnClickListener mOnClickListener) {
		 logoView.setOnClickListener(mOnClickListener);
	}

	/**
	 * ���������߳�����ʾ�ı���Ϣ.
	 * @param resId Ҫ��ʾ���ַ�����ԴID����ϢwhatֵΪ0,
	 */
	public void showToastInThread(int resId) {
		Message msg = baseHandler.obtainMessage(0);
		Bundle bundle = new Bundle();
		bundle.putString("Msg", this.getResources().getString(resId));
		msg.setData(bundle);
		baseHandler.sendMessage(msg);
	}
	
	/**
	 * ���������߳�����ʾ�ı���Ϣ.
	 * @param toast ��ϢwhatֵΪ0
	 */
	public void showToastInThread(String toast) {
		Message msg = baseHandler.obtainMessage(0);
		Bundle bundle = new Bundle();
		bundle.putString("Msg", toast);
		msg.setData(bundle);
		baseHandler.sendMessage(msg);
	}
	
	/**
	 * ��������ʾ���ȿ�.
	 */
	public void showProgressDialog() {
		showProgressDialog(null);
    }
	
	/**
	 * ��������ʾ���ȿ�.
	 * @param message the message
	 */
	public void showProgressDialog(String message) {
		closeMonitor();
		// ����һ����ʾ���ȵ�Dialog
		if(!AbStrUtil.isEmpty(message)){
			mProgressMessage = message;
		}
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);  
			mProgressDialog.setMessage(mProgressMessage); 
			/*Window window = mProgressDialog.getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			window.setGravity(Gravity.CENTER); 
			lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
			// ��Ӷ���
			window.setWindowAnimations(android.R.style.Animation_Dialog); 
			window.setAttributes(lp);*/
		}
		showDialog(AbConstant.DIALOGPROGRESS);
    }
	
	/**
	 * �������ڵײ���ʾһ��Dialog,idΪ1,���м���ʾidΪ2.
	 * @param id Dialog������
	 * @param view ָ��һ����View
	 * @param paddingWidth ���Dialog���ǳ�����Ļ��Ҫ�������ֵ
	 * @see   AbConstant.DIALOGBOTTOM
	 */
	public void showDialog(int id,View view,int paddingWidth) {
		
		if(id == AbConstant.DIALOGBOTTOM){
			mBottomDialogView = view;
			if(mBottomDialog == null){
				mBottomDialog = new Dialog(this);
				mBottomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				Window window = mBottomDialog.getWindow();
				WindowManager.LayoutParams lp = window.getAttributes();
				//�˴���������dialog��ʾ��λ��
				window.setGravity(Gravity.BOTTOM); 
				//���ÿ��
				lp.width = diaplayWidth-paddingWidth; 
				lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
				window.setAttributes(lp); 
				// ��Ӷ���
				window.setWindowAnimations(android.R.style.Animation_Dialog); 
			}
			mBottomDialog.setContentView(mBottomDialogView,new LayoutParams(diaplayWidth-paddingWidth,LayoutParams.WRAP_CONTENT));
			showDialog(id);
		}else if(id == AbConstant.DIALOGCENTER){
			mCenterDialogView = view;
			if(mCenterDialog == null){
				mCenterDialog = new Dialog(this);
				mCenterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				Window window = mCenterDialog.getWindow();
				WindowManager.LayoutParams lp = window.getAttributes();
				//�˴���������dialog��ʾ��λ��
				window.setGravity(Gravity.CENTER); 
				//���ÿ��
				lp.width = diaplayWidth-paddingWidth; 
				lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
				window.setAttributes(lp); 
				// ��Ӷ���
				window.setWindowAnimations(android.R.style.Animation_Dialog); 
			}
			mCenterDialog.setContentView(mCenterDialogView,new LayoutParams(diaplayWidth-paddingWidth,LayoutParams.WRAP_CONTENT));
			showDialog(id);
		}
	}
	
	/**
	 * �������Ի����ʼ��.
	 * @param id the id
	 * @return the dialog
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
			case AbConstant.DIALOGPROGRESS:
				if (mProgressDialog == null) {
					mProgressDialog = new ProgressDialog(this);  
					mProgressDialog.setMessage(mProgressMessage); 
				}
				return mProgressDialog;
			case AbConstant.DIALOGBOTTOM:
				return mBottomDialog;
			case AbConstant.DIALOGCENTER:
				return mCenterDialog;
			default:
				break;
		}
		return dialog;
	}
	
	/**
	 * �������Ƴ����ȿ�.
	 */
	public void removeProgressDialog() {
		removeDialog(AbConstant.DIALOGPROGRESS);
    }
	
	/**
	 * �������Ƴ�Dialog.
	 * @param id the id
	 * @see android.app.Activity#removeDialog(int)
	 */
	public void removeDialogInThread(int id){
		baseHandler.sendEmptyMessage(id);
	}
	
	/**
	 * �������Ի���dialog ��ȷ�ϣ�ȡ����.
	 * @param title �Ի����������
	 * @param msg  �Ի�����ʾ����
	 * @param mOkOnClickListener  ���ȷ�ϰ�ť���¼�����
	 */
	public void dialogOpen(String title,String msg,DialogInterface.OnClickListener mOkOnClickListener) {
		 AlertDialog.Builder builder = new Builder(this);
		 builder.setMessage(msg);
		 builder.setTitle(title);
		 builder.setPositiveButton("ȷ��",mOkOnClickListener);
		 builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			   @Override
			   public void onClick(DialogInterface dialog, int which) {
				   dialog.dismiss();
			   }
		 });
		 builder.create().show();
	}
	
	/**
	 * �������Ի���dialog ���ް�ť��.
	 * @param title �Ի����������
	 * @param msg  �Ի�����ʾ����
	 */
	public void dialogOpen(String title,String msg) {
		 AlertDialog.Builder builder = new Builder(this);
		 builder.setMessage(msg);
		 builder.setTitle(title);
		 builder.create().show();
	}
	
	/**
	 * Gets the title layout.
	 * @return the title layout
	 */
	public LinearLayout getTitleLayout() {
		return titleLayout;
	}

	/**
	 * Sets the title layout.
	 * @param titleLayout the new title layout
	 */
	public void setTitleLayout(LinearLayout titleLayout) {
		this.titleLayout = titleLayout;
	}
	
	/**
	 * �������������ı���ͼ.
	 * @param res  ����ͼ��ԴID
	 */
	public void setTitleLayoutBackground(int res) {
		titleLayout.setBackgroundResource(res);
	}
	
	/**
	 * �������������ֵĶ���.
	 * @param left the left
	 * @param top the top
	 * @param right the right
	 * @param bottom the bottom
	 */
	public void setTitleTextMargin(int left,int top,int right,int bottom) {
		titleTextLayoutParams.setMargins(left, top, right, bottom);
	}
	
	
	/**
	 * �������������ı���ͼ.
	 * @param color  ������ɫֵ
	 */
	public void setTitleLayoutBackgroundColor(int color) {
		titleLayout.setBackgroundColor(color);
	}

	/**
	 * ���������������ֺ�.
	 * @param titleTextSize  �����ֺ�
	 */
	public void setTitleTextSize(int titleTextSize) {
		this.titleTextBtn.setTextSize(titleTextSize);
	}
	
	/**
	 * ���������ñ������ֶ��뷽ʽ
	 * �����ұߵľ�������ж�����
	 * ��1���м俿�� Gravity.CENTER,Gravity.CENTER
	 * ��2����߾��� �ұ߾���Gravity.LEFT,Gravity.RIGHT
	 * ��3����߾��У��ұ߾���Gravity.CENTER,Gravity.RIGHT
	 * ��4����߾��ң��ұ߾���Gravity.RIGHT,Gravity.RIGHT
	 * ������addRightView(view)����������
	 * @param gravity1  ������뷽ʽ
	 * @param gravity2  �ұ߲��ֶ��뷽ʽ
	 */
	public void setTitleLayoutGravity(int gravity1,int gravity2) {
		AbViewUtil.measureView(this.rightLayout);
		AbViewUtil.measureView(this.logoView);
		int leftWidth = this.logoView.getMeasuredWidth();
		int rightWidth = this.rightLayout.getMeasuredWidth();
		if(D)Log.d(TAG, "�������ֵĿ�ȣ�"+leftWidth+","+rightWidth);
		this.titleTextLayoutParams.rightMargin = 0;
		this.titleTextLayoutParams.leftMargin = 0;
		//ȫ���м俿
		if((gravity1 == Gravity.CENTER_HORIZONTAL || gravity1 == Gravity.CENTER) ){
            if(leftWidth==0 && rightWidth==0){
            	this.titleTextLayout.setGravity(Gravity.CENTER_HORIZONTAL);
			}else{
				if(gravity2 == Gravity.RIGHT){
					if(rightWidth==0){
					}else{
						this.titleTextBtn.setPadding(rightWidth/3*2, 0, 0, 0);
					}
					this.titleTextBtn.setGravity(Gravity.CENTER);
					this.rightLayout.setHorizontalGravity(Gravity.RIGHT);
				}if(gravity2 == Gravity.CENTER || gravity2 == Gravity.CENTER_HORIZONTAL){
					this.titleTextLayout.setGravity(Gravity.CENTER_HORIZONTAL);
					this.rightLayout.setHorizontalGravity(Gravity.LEFT);
					this.titleTextBtn.setGravity(Gravity.CENTER);
					int offset = leftWidth-rightWidth;
					if(offset>0){
						this.titleTextLayoutParams.rightMargin = offset;
					}else{
						this.titleTextLayoutParams.leftMargin = Math.abs(offset);
					}
				}
			}
		//����
		}else if(gravity1 == Gravity.LEFT && gravity2 == Gravity.RIGHT){
			this.titleTextLayout.setGravity(Gravity.LEFT);
			this.rightLayout.setHorizontalGravity(Gravity.RIGHT);
		//ȫ���ҿ�
		}else if(gravity1 == Gravity.RIGHT && gravity2 == Gravity.RIGHT){
			this.titleTextLayout.setGravity(Gravity.RIGHT);
			this.rightLayout.setHorizontalGravity(Gravity.RIGHT);
		}else if(gravity1 == Gravity.LEFT && gravity2 == Gravity.LEFT){
			this.titleTextLayout.setGravity(Gravity.LEFT);
			this.rightLayout.setHorizontalGravity(Gravity.LEFT);
		}
		
	}
	
	/**
	 * ��������ȡ��ʾ�����ı���Button.
	 * @return the title Button view
	 */
	public Button getTitleTextButton() {
		return titleTextBtn;
	}
	
	/**
	 * ���������ñ����������
	 */
	public void setTitleTextBold(boolean bold){
		TextPaint paint = titleTextBtn.getPaint();  
		if(bold){
			//����
			paint.setFakeBoldText(true); 
		}else{
			paint.setFakeBoldText(false); 
		}
		
	}
	
	/**
	 * ���������ñ��ⱳ��
	 * @param resId
	 */
	public void setTitleTextBackgroundResource(int resId){
		titleTextBtn.setBackgroundResource(resId);
	}
	
	/**
	 * ���������ñ��ⱳ��.
	 * @param d  ����ͼ
	 */
	public void setTitleLayoutBackgroundDrawable(Drawable d) {
		titleLayout.setBackgroundDrawable(d);
	}
	
	/**
	 * ���������ñ��ⱳ��
	 * @param resId
	 */
	public void setTitleTextBackgroundDrawable(Drawable drawable){
		titleTextBtn.setBackgroundDrawable(drawable);
	}
	
	/**
	 * �������򿪹ر�֡������
	 * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
	 * lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG
	 *
	 */
	public void openMonitor(){
		if(!mMonitorOpened) {
	        mWindowManager.addView(mAbMonitorView, mMonitorParams);
	        mMonitorOpened = true;
			mMonitorRunnable = new Runnable() {
	
				@Override
				public void run() {
					mAbMonitorView.postInvalidate();
					mMonitorHandler.postDelayed(this,0);
				}
			};
			mMonitorHandler.postDelayed(mMonitorRunnable,0);
			
			mAbMonitorView.setOnTouchListener(new OnTouchListener() {
	        	int lastX, lastY;
	        	int paramX, paramY;
	        	
				public boolean onTouch(View v, MotionEvent event) {
					switch(event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						lastX = (int) event.getRawX();
						lastY = (int) event.getRawY();
						paramX = mMonitorParams.x;
						paramY = mMonitorParams.y;
						break;
					case MotionEvent.ACTION_MOVE:
						int dx = (int) event.getRawX() - lastX;
						int dy = (int) event.getRawY() - lastY;
						if ((paramX + dx) > diaplayWidth/2 ) {
							mMonitorParams.x = diaplayWidth;
						}else {
							mMonitorParams.x = 0;
						}
	                    mMonitorParams.x = paramX + dx;
						mMonitorParams.y = paramY + dy;
						// ����������λ��
						mWindowManager.updateViewLayout(mAbMonitorView, mMonitorParams);
						break;
					}
					return true;
				}
			});
			
		}
		
	}
	
	/**
	 * �������ر�֡������.
	 */
	public void closeMonitor(){
		if(mMonitorOpened) {
			if(mAbMonitorView!=null){
				mWindowManager.removeView(mAbMonitorView);
			}
			mMonitorOpened = false;
			if(mMonitorHandler!=null  && mMonitorRunnable!=null){
			    mMonitorHandler.removeCallbacks(mMonitorRunnable);
			}
		}
		
	}
	

	/**
	 * ����������.
	 *
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	
}
