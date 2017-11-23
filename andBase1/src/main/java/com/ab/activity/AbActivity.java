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
 * 描述：继承这个Activity使你的Activity拥有一个程序框架.
 * @author zhaoqp
 * @date：2013-1-15 下午3:27:05
 * @version v1.0
 */

public abstract class AbActivity extends FragmentActivity {
	
	/** The tag. */
	private String TAG = "AbActivity";
	
	/** The debug. */
	private boolean D = AbAppData.DEBUG;

	/** 加载框的文字说明. */
	public String mProgressMessage = "请稍候...";
	
	/** 全局的LayoutInflater对象，已经完成初始化. */
	public LayoutInflater mInflater;
	
	/** 全局的加载框对象，已经完成初始化. */
	public ProgressDialog mProgressDialog;
	
	/** 底部弹出的Dialog. */
	private Dialog mBottomDialog;
	
	/** 居中弹出的Dialog. */
	private Dialog mCenterDialog;
	
	/** 底部弹出的Dialog的View. */
	private View mBottomDialogView = null;
	
	/** 居中弹出的Dialog的View. */
	private View mCenterDialogView = null;
	
	/** 全局的Application对象，已经完成初始化. */
	public Application abApplication = null;
	
	/** 全局的SharedPreferences对象，已经完成初始化. */
	public SharedPreferences  abSharedPreferences = null;
	
	/**
	 * LinearLayout.LayoutParams，已经初始化为FILL_PARENT, FILL_PARENT
	 */
	public LinearLayout.LayoutParams layoutParamsFF = null;
	
	/**
	 * LinearLayout.LayoutParams，已经初始化为FILL_PARENT, WRAP_CONTENT
	 */
	public LinearLayout.LayoutParams layoutParamsFW = null;
	
	/**
	 * LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, FILL_PARENT
	 */
	public LinearLayout.LayoutParams layoutParamsWF = null;
	
	/**
	 * LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, WRAP_CONTENT
	 */
	public LinearLayout.LayoutParams layoutParamsWW = null;
	
	/** 左侧的Logo图标View. */
	protected ImageView logoView = null;
	
	/** 左侧的Logo图标右边的分割线View. */
	protected ImageView logoLineView = null;
	
	/** 总布局. */
	public RelativeLayout ab_base = null;
	
	/** 标题栏布局. */
	protected LinearLayout titleLayout = null;
	
	/** 标题布局. */
	protected LinearLayout titleTextLayout = null;
	
	/** 标题文本的对齐参数. */
	private LinearLayout.LayoutParams titleTextLayoutParams = null;
	
	/** 右边布局的的对齐参数. */
	private LinearLayout.LayoutParams rightViewLayoutParams = null;
	
	/** 标题栏布局ID. */
	private static final int titleLayoutID = 1;
	
	/** 主内容布局. */
	protected RelativeLayout contentLayout = null;
	
	/** 显示标题文字的View. */
	protected Button titleTextBtn = null;
	
	/** 右边的View，可以自定义显示什么. */
	protected LinearLayout rightLayout = null;
	
	/** The diaplay width. */
	public int diaplayWidth  = 320;
	
	/** The diaplay height. */
	public int diaplayHeight  = 480;
	
	/** 性能测试. */
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
	 * 主要Handler类，在线程中可用
	 * what：0.提示文本信息,1.等待框   ,2.移除等待框 
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
	 * 描述：创建.
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
		
		// 加右边的按钮
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
        // 设置window type
		mMonitorParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        /*
         * 如果设置为params.type = WindowManager.LayoutParams.TYPE_PHONE;
         * 那么优先级会降低一些, 即拉下通知栏不可见
         */
		//设置图片格式，效果为背景透明
		mMonitorParams.format = PixelFormat.RGBA_8888; 
        // 设置Window flag
		mMonitorParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                              | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        /*
         * 下面的flags属性的效果形同“锁定”。
         * 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
        wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL
                               | LayoutParams.FLAG_NOT_FOCUSABLE
                               | LayoutParams.FLAG_NOT_TOUCHABLE;
         */
        // 设置悬浮窗的长得宽
		mMonitorParams.width = 60;
		mMonitorParams.height = 30;
		//if(D) this.openMonitor();
        setContentView(ab_base,layoutParamsFF);
	}
	
	/**
     * 描述：Toast提示文本.
     * @param text  文本
     */
	public void showToast(String text) {
		Toast.makeText(this,""+text, Toast.LENGTH_SHORT).show();
	}
	
	/**
     * 描述：Toast提示文本.
     * @param resId  文本的资源ID
     */
	public void showToast(int resId) {
		Toast.makeText(this,""+this.getResources().getText(resId), Toast.LENGTH_SHORT).show();
	}
	
	/**
     * 描述：设置标题文本.
     * @param text  文本
     */
	public void setTitleText(String text) {
		titleTextBtn.setText(text);
	}
	
	/**
     * 描述：设置标题文本.
     * @param resId  文本的资源ID
     */
	public void setTitleText(int resId) {
		titleTextBtn.setText(resId);
	}
	
	/**
     * 描述：设置Logo的背景图.
     * @param drawable  Logo资源Drawable
     */
	public void setLogo(Drawable drawable) {
		logoView.setVisibility(View.VISIBLE);
		logoView.setBackgroundDrawable(drawable);
	}
	
	/**
     * 描述：设置Logo的背景资源.
     * @param resId  Logo资源ID
     */
	public void setLogo(int resId) {
		logoView.setVisibility(View.VISIBLE);
		logoView.setBackgroundResource(resId);
	}
	
	/**
     * 描述：设置Logo分隔线的背景资源.
     * @param resId  Logo资源ID
     */
	public void setLogoLine(int resId) {
		logoLineView.setVisibility(View.VISIBLE);
		logoLineView.setBackgroundResource(resId);
	}
	
	/**
     * 描述：设置Logo分隔线的背景图.
     * @param drawable  Logo资源Drawable
     */
	public void setLogoLine(Drawable drawable) {
		logoLineView.setVisibility(View.VISIBLE);
		logoLineView.setBackgroundDrawable(drawable);
	}
	
	
	/**
     * 描述：把指定的View填加到标题栏右边.
     * @param rightView  指定的View
     */
	public void addRightView(View rightView) {
		rightLayout.setVisibility(View.VISIBLE);
		rightLayout.addView(rightView,layoutParamsWF);
	}
	
	/**
     * 描述：把指定资源ID表示的View填加到标题栏右边.
     * @param resId  指定的View的资源ID
     */
	public void addRightView(int resId) {
		rightLayout.setVisibility(View.VISIBLE);
		rightLayout.addView(mInflater.inflate(resId, null),layoutParamsWF);
	}
	
	/**
     * 描述：清除标题栏右边的View.
     */
	public void clearRightView() {
		rightLayout.removeAllViews();
	}
	
	/**
	 * 描述：用指定的View填充主界面.
	 * @param contentView  指定的View
	 */
	public void setAbContentView(View contentView) {
		contentLayout.removeAllViews();
		contentLayout.addView(contentView,layoutParamsFF);
	}
	
	/**
	 * 描述：用指定资源ID表示的View填充主界面.
	 * @param resId  指定的View的资源ID
	 */
	public void setAbContentView(int resId) {
		contentLayout.removeAllViews();
		contentLayout.addView(mInflater.inflate(resId, null),layoutParamsFF);
	}
	
	/**
	 * 描述：设置Logo按钮的返回事件.
	 * @param mOnClickListener  指定的返回事件
	 */
	public void setLogoBackOnClickListener(View.OnClickListener mOnClickListener) {
		 logoView.setOnClickListener(mOnClickListener);
	}

	/**
	 * 描述：在线程中提示文本信息.
	 * @param resId 要提示的字符串资源ID，消息what值为0,
	 */
	public void showToastInThread(int resId) {
		Message msg = baseHandler.obtainMessage(0);
		Bundle bundle = new Bundle();
		bundle.putString("Msg", this.getResources().getString(resId));
		msg.setData(bundle);
		baseHandler.sendMessage(msg);
	}
	
	/**
	 * 描述：在线程中提示文本信息.
	 * @param toast 消息what值为0
	 */
	public void showToastInThread(String toast) {
		Message msg = baseHandler.obtainMessage(0);
		Bundle bundle = new Bundle();
		bundle.putString("Msg", toast);
		msg.setData(bundle);
		baseHandler.sendMessage(msg);
	}
	
	/**
	 * 描述：显示进度框.
	 */
	public void showProgressDialog() {
		showProgressDialog(null);
    }
	
	/**
	 * 描述：显示进度框.
	 * @param message the message
	 */
	public void showProgressDialog(String message) {
		closeMonitor();
		// 创建一个显示进度的Dialog
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
			// 添加动画
			window.setWindowAnimations(android.R.style.Animation_Dialog); 
			window.setAttributes(lp);*/
		}
		showDialog(AbConstant.DIALOGPROGRESS);
    }
	
	/**
	 * 描述：在底部显示一个Dialog,id为1,在中间显示id为2.
	 * @param id Dialog的类型
	 * @param view 指定一个新View
	 * @param paddingWidth 如果Dialog不是充满屏幕，要设置这个值
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
				//此处可以设置dialog显示的位置
				window.setGravity(Gravity.BOTTOM); 
				//设置宽度
				lp.width = diaplayWidth-paddingWidth; 
				lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
				window.setAttributes(lp); 
				// 添加动画
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
				//此处可以设置dialog显示的位置
				window.setGravity(Gravity.CENTER); 
				//设置宽度
				lp.width = diaplayWidth-paddingWidth; 
				lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
				window.setAttributes(lp); 
				// 添加动画
				window.setWindowAnimations(android.R.style.Animation_Dialog); 
			}
			mCenterDialog.setContentView(mCenterDialogView,new LayoutParams(diaplayWidth-paddingWidth,LayoutParams.WRAP_CONTENT));
			showDialog(id);
		}
	}
	
	/**
	 * 描述：对话框初始化.
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
	 * 描述：移除进度框.
	 */
	public void removeProgressDialog() {
		removeDialog(AbConstant.DIALOGPROGRESS);
    }
	
	/**
	 * 描述：移除Dialog.
	 * @param id the id
	 * @see android.app.Activity#removeDialog(int)
	 */
	public void removeDialogInThread(int id){
		baseHandler.sendEmptyMessage(id);
	}
	
	/**
	 * 描述：对话框dialog （确认，取消）.
	 * @param title 对话框标题内容
	 * @param msg  对话框提示内容
	 * @param mOkOnClickListener  点击确认按钮的事件监听
	 */
	public void dialogOpen(String title,String msg,DialogInterface.OnClickListener mOkOnClickListener) {
		 AlertDialog.Builder builder = new Builder(this);
		 builder.setMessage(msg);
		 builder.setTitle(title);
		 builder.setPositiveButton("确认",mOkOnClickListener);
		 builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			   @Override
			   public void onClick(DialogInterface dialog, int which) {
				   dialog.dismiss();
			   }
		 });
		 builder.create().show();
	}
	
	/**
	 * 描述：对话框dialog （无按钮）.
	 * @param title 对话框标题内容
	 * @param msg  对话框提示内容
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
	 * 描述：标题栏的背景图.
	 * @param res  背景图资源ID
	 */
	public void setTitleLayoutBackground(int res) {
		titleLayout.setBackgroundResource(res);
	}
	
	/**
	 * 描述：标题文字的对齐.
	 * @param left the left
	 * @param top the top
	 * @param right the right
	 * @param bottom the bottom
	 */
	public void setTitleTextMargin(int left,int top,int right,int bottom) {
		titleTextLayoutParams.setMargins(left, top, right, bottom);
	}
	
	
	/**
	 * 描述：标题栏的背景图.
	 * @param color  背景颜色值
	 */
	public void setTitleLayoutBackgroundColor(int color) {
		titleLayout.setBackgroundColor(color);
	}

	/**
	 * 描述：标题文字字号.
	 * @param titleTextSize  文字字号
	 */
	public void setTitleTextSize(int titleTextSize) {
		this.titleTextBtn.setTextSize(titleTextSize);
	}
	
	/**
	 * 描述：设置标题文字对齐方式
	 * 根据右边的具体情况判定方向：
	 * （1）中间靠近 Gravity.CENTER,Gravity.CENTER
	 * （2）左边居左 右边居右Gravity.LEFT,Gravity.RIGHT
	 * （3）左边居中，右边居右Gravity.CENTER,Gravity.RIGHT
	 * （4）左边居右，右边居右Gravity.RIGHT,Gravity.RIGHT
	 * 必须在addRightView(view)方法后设置
	 * @param gravity1  标题对齐方式
	 * @param gravity2  右边布局对齐方式
	 */
	public void setTitleLayoutGravity(int gravity1,int gravity2) {
		AbViewUtil.measureView(this.rightLayout);
		AbViewUtil.measureView(this.logoView);
		int leftWidth = this.logoView.getMeasuredWidth();
		int rightWidth = this.rightLayout.getMeasuredWidth();
		if(D)Log.d(TAG, "测量布局的宽度："+leftWidth+","+rightWidth);
		this.titleTextLayoutParams.rightMargin = 0;
		this.titleTextLayoutParams.leftMargin = 0;
		//全部中间靠
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
		//左右
		}else if(gravity1 == Gravity.LEFT && gravity2 == Gravity.RIGHT){
			this.titleTextLayout.setGravity(Gravity.LEFT);
			this.rightLayout.setHorizontalGravity(Gravity.RIGHT);
		//全部右靠
		}else if(gravity1 == Gravity.RIGHT && gravity2 == Gravity.RIGHT){
			this.titleTextLayout.setGravity(Gravity.RIGHT);
			this.rightLayout.setHorizontalGravity(Gravity.RIGHT);
		}else if(gravity1 == Gravity.LEFT && gravity2 == Gravity.LEFT){
			this.titleTextLayout.setGravity(Gravity.LEFT);
			this.rightLayout.setHorizontalGravity(Gravity.LEFT);
		}
		
	}
	
	/**
	 * 描述：获取标示标题文本的Button.
	 * @return the title Button view
	 */
	public Button getTitleTextButton() {
		return titleTextBtn;
	}
	
	/**
	 * 描述：设置标题字体粗体
	 */
	public void setTitleTextBold(boolean bold){
		TextPaint paint = titleTextBtn.getPaint();  
		if(bold){
			//粗体
			paint.setFakeBoldText(true); 
		}else{
			paint.setFakeBoldText(false); 
		}
		
	}
	
	/**
	 * 描述：设置标题背景
	 * @param resId
	 */
	public void setTitleTextBackgroundResource(int resId){
		titleTextBtn.setBackgroundResource(resId);
	}
	
	/**
	 * 描述：设置标题背景.
	 * @param d  背景图
	 */
	public void setTitleLayoutBackgroundDrawable(Drawable d) {
		titleLayout.setBackgroundDrawable(d);
	}
	
	/**
	 * 描述：设置标题背景
	 * @param resId
	 */
	public void setTitleTextBackgroundDrawable(Drawable drawable){
		titleTextBtn.setBackgroundDrawable(drawable);
	}
	
	/**
	 * 描述：打开关闭帧数测试
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
						// 更新悬浮窗位置
						mWindowManager.updateViewLayout(mAbMonitorView, mMonitorParams);
						break;
					}
					return true;
				}
			});
			
		}
		
	}
	
	/**
	 * 描述：关闭帧数测试.
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
	 * 描述：返回.
	 *
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	
}
