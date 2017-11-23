/*
 * 
 */
package com.ab.global;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.text.TextUtils;

// TODO: Auto-generated Javadoc
/**
 * ������ �����쳣��.
 *
 * @author zhaoqp
 * @date 2012-2-10
 * @version v1.0
 */
public class AbAppException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	
	/** The msg. */
	private String msg = null;

	/**
	 * Instantiates a new ab app exception.
	 *
	 * @param e the e
	 */
	public AbAppException(Exception e) {
		super();

		try {
			if (e instanceof ConnectException) {
				msg = "�޷��������磬������������";
			} 
			else if (e instanceof UnknownHostException) {
				msg = "���ܽ����ķ����ַ";
			}else if (e instanceof SocketException) {
				msg = "�����д���������";
			}else if (e instanceof SocketTimeoutException) {
				msg = "���ӳ�ʱ��������";
			} else {
				if (e == null || TextUtils.isEmpty(e.getMessage())) {
					msg = "��Ǹ����������ˣ�����ϵ����";
				}
				msg = " " + e.getMessage();
			}
		} catch (Exception e1) {
		}

	}

	/**
	 * Instantiates a new ab app exception.
	 *
	 * @param detailMessage the detail message
	 */
	public AbAppException(String detailMessage) {
		super(detailMessage);
		msg = detailMessage;
	}

	/**
	 * ��������ȡ�쳣��Ϣ.
	 *
	 * @return the message
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return msg;
	}

}
