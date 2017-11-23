package com.andbase.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.ab.activity.AbActivity;
import com.ab.net.AbHttpCallback;
import com.ab.net.AbHttpItem;
import com.ab.net.AbHttpPool;
import com.andbase.R;
import com.andbase.global.Constant;
import com.andbase.global.MyApplication;
import com.andbase.model.User;

public class LoginActivity extends AbActivity {
	
	private EditText userName = null;
	private EditText userPwd = null;
	private User mUser = null;
	private MyApplication application;
	private AbHttpPool mAbHttpPool = AbHttpPool.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.login);
        application = (MyApplication)abApplication;
        this.setTitleText(R.string.login);
        this.setLogo(R.drawable.button_selector_back);
        this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
        
        userName = (EditText)this.findViewById(R.id.userName);
		userPwd = (EditText)this.findViewById(R.id.userPwd);
		CheckBox checkBox = (CheckBox) findViewById(R.id.login_check);
		userName.setText("²âÊÔ");
		userPwd.setText("123456");
		   
	    Button login = (Button)this.findViewById(R.id.loginBtn);
	    Button register = (Button)this.findViewById(R.id.registerBtn);
		login.setOnClickListener(new LoginOnClickListener());
		
		register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogOpen("ÌáÊ¾","Çë½øÈëÍøÕ¾×¢²á£¬ÍøÖ·ÊÇ:www.chxue8.com¡£",new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
			}
		});
        
        logoView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Editor editor = abSharedPreferences.edit();
				editor.putBoolean(Constant.USERPASSWORDREMEMBERCOOKIE, isChecked);
				editor.commit();
				application.userPasswordRemember = true;
			}
		});
    }
    
   public class  LoginOnClickListener implements View.OnClickListener{
		
		@Override
		public void onClick(View v) {
			final String mStr_name = userName.getText().toString();
			final String mStr_pwd = userPwd.getText().toString();
			if (TextUtils.isEmpty(mStr_name)) {
				userName.setError(getResources().getText(R.string.error_name));
				userName.setFocusable(true);
				userName.requestFocus();
				return;
			}
			if (TextUtils.isEmpty(mStr_pwd)) {
				userPwd.setError(getResources().getText(R.string.error_pwd));
				userPwd.setFocusable(true);
				userPwd.requestFocus();
				return;
			}
			showDialog(0);
			
			final AbHttpItem item = new AbHttpItem();
			item.callback = new AbHttpCallback() {

				@Override
				public void update() {
					removeDialog(0);
					if(mUser!=null){
							application.mUser = mUser;
					}else{
						showToast("µÇÂ¼Ê§°Ü");
					}
				}

				@Override
				public void get() {
		   		    try {
		   		    	//ÏÂÔØÊý¾Ý
		   		        mUser = new User();
		   		    } catch (Exception e) {
		   		    	showToastInThread(e.getMessage());
		   		    }
			  };
			};
			mAbHttpPool.download(item);
		}
	}
    
}


