package com.andbase.web;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.ab.global.AbAppException;
import com.andbase.global.Constant;

public class SettingWeb {

	private static final String TAG = "SettingWeb";
	private static final boolean D = Constant.DEBUG;

	public static String adSetting() throws AbAppException {
		String mValue = null;
		try {
			// ʹ��httppost�����ύ����
			HttpPost httpRequest = new HttpPost(Constant.ADURL);
			// ��ʱ����
			HttpParams params = new BasicHttpParams();
			// �����ӳ���ȡ���ӵĳ�ʱʱ�䣬����Ϊ1��
			ConnManagerParams.setTimeout(params, Constant.timeOut);
			// ͨ��������������������ӵĳ�ʱʱ��
			HttpConnectionParams.setConnectionTimeout(params, Constant.connectOut);
			// ����Ӧ���ݵĳ�ʱʱ��
			HttpConnectionParams.setSoTimeout(params, Constant.getOut);
			// �����������
			httpRequest.setParams(params);
			// �����������
			List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
			paramsList.add(new BasicNameValuePair("key", "android_ad"));
			UrlEncodedFormEntity mUrlEncodedFormEntity = new UrlEncodedFormEntity(paramsList, HTTP.UTF_8);
			httpRequest.setEntity(mUrlEncodedFormEntity);
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
			int ret = httpResponse.getStatusLine().getStatusCode();
			if (ret == HttpStatus.SC_OK) {
				mValue = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
				if (D) Log.d(TAG, "��濪�ط���:" + mValue);
			} else {
				throw new ConnectException();
			}
		} catch (Exception e) {
			AbAppException mAbAppException = new AbAppException(e);
			throw mAbAppException;
		}
		return mValue;
	}

	
	
}
