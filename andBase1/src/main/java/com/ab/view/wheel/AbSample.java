/*
 * 
 */
package com.ab.view.wheel;


/**
 * The Class AbSample.
 */
public class AbSample {
	
	/*//ʱ��ѡ���������ʵ��
	Calendar calendar = Calendar.getInstance();
	int year = calendar.get(Calendar.YEAR);
	int month = calendar.get(Calendar.MONTH);
	int day = calendar.get(Calendar.DATE);
	// ��Ӵ�С���·ݲ�����ת��Ϊlist,����֮����ж�
	String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
	String[] months_little = { "4", "6", "9", "11" };
	final int START_YEAR = 1990, END_YEAR = 2100;

	final List<String> list_big = Arrays.asList(months_big);
	final List<String> list_little = Arrays.asList(months_little);
	
	
	final AbWheelView mWheelView41 = (AbWheelView)mTimeView.findViewById(R.id.wheelView1);
	final AbWheelView mWheelView42 = (AbWheelView)mTimeView.findViewById(R.id.wheelView2);
	final AbWheelView mWheelView43 = (AbWheelView)mTimeView.findViewById(R.id.wheelView3);
	
	// ��
	mWheelView41.setAdapter(new AbNumericWheelAdapter(START_YEAR, END_YEAR));// ����"��"����ʾ����
	mWheelView41.setCyclic(true);// ��ѭ������
	mWheelView41.setLabel("��");  // �������
	mWheelView41.setCurrentItem(year - START_YEAR);// ��ʼ��ʱ��ʾ������
	
	// ��
	mWheelView42.setAdapter(new AbNumericWheelAdapter(1, 12));
	mWheelView42.setCyclic(true);
	mWheelView42.setLabel("��");
	mWheelView42.setCurrentItem(month);
	
	// ��
	// �жϴ�С�¼��Ƿ�����,����ȷ��"��"������
	if (list_big.contains(String.valueOf(month + 1))) {
		mWheelView43.setAdapter(new AbNumericWheelAdapter(1, 31));
	} else if (list_little.contains(String.valueOf(month + 1))) {
		mWheelView43.setAdapter(new AbNumericWheelAdapter(1, 30));
	} else {
		// ����
		if (AbDateUtil.isLeapYear(year)){
			mWheelView43.setAdapter(new AbNumericWheelAdapter(1, 29));
		}else{
			mWheelView43.setAdapter(new AbNumericWheelAdapter(1, 28));
		}
	}
	mWheelView43.setCyclic(true);
	mWheelView43.setLabel("��");
	mWheelView43.setCurrentItem(day - 1);
	
	// ���"��"����
	AbOnWheelChangedListener wheelListener_year = new AbOnWheelChangedListener() {

		public void onChanged(AbWheelView wheel, int oldValue, int newValue) {
			int year_num = newValue + START_YEAR;
			// �жϴ�С�¼��Ƿ�����,����ȷ��"��"������
			if (list_big.contains(String.valueOf(mWheelView42.getCurrentItem() + 1))) {
				mWheelView43.setAdapter(new AbNumericWheelAdapter(1, 31));
			} else if (list_little.contains(String.valueOf(mWheelView42.getCurrentItem() + 1))) {
				mWheelView43.setAdapter(new AbNumericWheelAdapter(1, 30));
			} else {
				if (AbDateUtil.isLeapYear(year_num))
					mWheelView43.setAdapter(new AbNumericWheelAdapter(1, 29));
				else
					mWheelView43.setAdapter(new AbNumericWheelAdapter(1, 28));
			}
		}
	};
	// ���"��"����
	AbOnWheelChangedListener wheelListener_month = new AbOnWheelChangedListener() {

		public void onChanged(AbWheelView wheel, int oldValue, int newValue) {
			int month_num = newValue + 1;
			// �жϴ�С�¼��Ƿ�����,����ȷ��"��"������
			if (list_big.contains(String.valueOf(month_num))) {
				mWheelView43.setAdapter(new AbNumericWheelAdapter(1, 31));
			} else if (list_little.contains(String.valueOf(month_num))) {
				mWheelView43.setAdapter(new AbNumericWheelAdapter(1, 30));
			} else {
				int year_num = mWheelView41.getCurrentItem() + START_YEAR;
				if (AbDateUtil.isLeapYear(year_num))
					mWheelView43.setAdapter(new AbNumericWheelAdapter(1, 29));
				else
					mWheelView43.setAdapter(new AbNumericWheelAdapter(1, 28));
			}
		}
	};
	mWheelView41.addChangingListener(wheelListener_year);
	mWheelView42.addChangingListener(wheelListener_month);*/
	
	
	
	
	
	
	
	
	
	
	
	
}
