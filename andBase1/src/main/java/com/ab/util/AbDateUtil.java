/*
 * 
 */
package com.ab.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

// TODO: Auto-generated Javadoc
/**
 * ���������ڴ�����.
 *
 * @author zhaoqp
 * @date��2013-1-18 ����8:48:25
 * @version v1.0
 */
public class AbDateUtil {
	
	/** ʱ�����ڸ�ʽ����������ʱ����. */
	public static String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";
	
	/** ʱ�����ڸ�ʽ����������. */
	public static String dateFormatYMD = "yyyy-MM-dd";
	
	/** ʱ�����ڸ�ʽ��������. */
	public static String dateFormatYM = "yyyy-MM";
	
	/** ʱ�����ڸ�ʽ����������ʱ��. */
	public static String dateFormatYMDHM = "yyyy-MM-dd HH:mm";
	
	/** ʱ�����ڸ�ʽ��������. */
	public static String dateFormatMD = "MM/dd";
	
	/** ʱ����. */
	public static String dateFormatHMS = "HH:mm:ss";
	
	/** ʱ��. */
	public static String dateFormatHM = "HH:mm";

	/**
	 * ������String���͵�����ʱ��ת��ΪDate����.
	 *
	 * @param strDate String��ʽ������ʱ��
	 * @param format ��ʽ���ַ������磺"yyyy-MM-dd HH:mm:ss"
	 * @return Date Date��������ʱ��
	 */
	public static Date getDateByFormat(String strDate, String format) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = mSimpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * ��������ȡƫ��֮���Date.
	 * @param date ����ʱ��
	 * @param calendarField Calendar���ԣ���Ӧoffset��ֵ�� ��(Calendar.DATE,��ʾ+offset��,Calendar.HOUR_OF_DAY,��ʾ��offsetСʱ)
	 * @param offset ƫ��(ֵ����0,��ʾ+,ֵС��0,��ʾ��)
	 * @return Date ƫ��֮�������ʱ��
	 */
	public Date getDateByOffset(Date date,int calendarField,int offset) {
		Calendar c = new GregorianCalendar();
		try {
			c.setTime(date);
			c.add(calendarField, offset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c.getTime();
	}
	
	/**
	 * ��������ȡָ������ʱ����ַ���(��ƫ��).
	 *
	 * @param strDate String��ʽ������ʱ��
	 * @param format ��ʽ���ַ������磺"yyyy-MM-dd HH:mm:ss"
	 * @param calendarField Calendar���ԣ���Ӧoffset��ֵ�� ��(Calendar.DATE,��ʾ+offset��,Calendar.HOUR_OF_DAY,��ʾ��offsetСʱ)
	 * @param offset ƫ��(ֵ����0,��ʾ+,ֵС��0,��ʾ��)
	 * @return String String���͵�����ʱ��
	 */
	public static String getStringByOffset(String strDate, String format,int calendarField,int offset) {
		String mDateTime = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			c.setTime(mSimpleDateFormat.parse(strDate));
			c.add(calendarField, offset);
			mDateTime = mSimpleDateFormat.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mDateTime;
	}
	
	/**
	 * ������Date����ת��ΪString����(��ƫ��).
	 *
	 * @param date the date
	 * @param format the format
	 * @param calendarField the calendar field
	 * @param offset the offset
	 * @return String String��������ʱ��
	 */
	public static String getStringByOffset(Date date, String format,int calendarField,int offset) {
		String strDate = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			c.setTime(date);
			c.add(calendarField, offset);
			strDate = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	

	/**
	 * ������Date����ת��ΪString����.
	 *
	 * @param date the date
	 * @param format the format
	 * @return String String��������ʱ��
	 */
	public static String getStringByFormat(Date date, String format) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
		String strDate = null;
		try {
			strDate = mSimpleDateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	/**
	 * ��������ȡָ������ʱ����ַ���,���ڵ�����Ҫ�ĸ�ʽ.
	 *
	 * @param strDate String��ʽ������ʱ�䣬����Ϊyyyy-MM-dd HH:mm:ss��ʽ
	 * @param format �����ʽ���ַ������磺"yyyy-MM-dd HH:mm:ss"
	 * @return String ת�����String���͵�����ʱ��
	 */
	public static String getStringByFormat(String strDate, String format) {
		String mDateTime = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS);
			c.setTime(mSimpleDateFormat.parse(strDate));
			SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
			mDateTime = mSimpleDateFormat2.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mDateTime;
	}
	
	/**
	 * ��������ȡmilliseconds��ʾ������ʱ����ַ���.
	 * @param format  ��ʽ���ַ������磺"yyyy-MM-dd HH:mm:ss"
	 * @return String ����ʱ���ַ���
	 */
	public static String getStringByFormat(long milliseconds,String format) {
		String thisDateTime = null;
		try {
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			thisDateTime = mSimpleDateFormat.format(milliseconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return thisDateTime;
	}
	
	/**
	 * ��������ȡ��ʾ��ǰ����ʱ����ַ���.
	 *
	 * @param format  ��ʽ���ַ������磺"yyyy-MM-dd HH:mm:ss"
	 * @return String String���͵ĵ�ǰ����ʱ��
	 */
	public static String getCurrentDate(String format) {
		String curDateTime = null;
		try {
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			Calendar c = new GregorianCalendar();
			curDateTime = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return curDateTime;

	}

	/**
	 * ��������ȡ��ʾ��ǰ����ʱ����ַ���(��ƫ��).
	 *
	 * @param format ��ʽ���ַ������磺"yyyy-MM-dd HH:mm:ss"
	 * @param calendarField Calendar���ԣ���Ӧoffset��ֵ�� ��(Calendar.DATE,��ʾ+offset��,Calendar.HOUR_OF_DAY,��ʾ��offsetСʱ)
	 * @param offset ƫ��(ֵ����0,��ʾ+,ֵС��0,��ʾ��)
	 * @return String String���͵�����ʱ��
	 */
	public static String getCurrentDateByOffset(String format,int calendarField,int offset) {
		String mDateTime = null;
		try {
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			Calendar c = new GregorianCalendar();
			c.add(calendarField, offset);
			mDateTime = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mDateTime;

	}

	/**
	 * ���������������������������.
	 *
	 * @param date1 ��һ��ʱ��ĺ����ʾ
	 * @param date2 �ڶ���ʱ��ĺ����ʾ
	 * @return int ���������
	 */
	public static int getOffectDay(long date1, long date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(date2);
		//���ж��Ƿ�ͬ��
		int y1 = calendar1.get(Calendar.YEAR);
		int y2 = calendar2.get(Calendar.YEAR);
		int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
		int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
		int maxDays = 0;
		int day = 0;
		if (y1 - y2 > 0) {
			maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
			day = d1 - d2 + maxDays;
		} else if (y1 - y2 < 0) {
			maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
			day = d1 - d2 - maxDays;
		} else {
			day = d1 - d2;
		}
		return day;
	}
	
	/**
	 * �����������������������Сʱ��.
	 *
	 * @param date1 ��һ��ʱ��ĺ����ʾ
	 * @param date2 �ڶ���ʱ��ĺ����ʾ
	 * @return int �����Сʱ��
	 */
	public static int getOffectHour(long date1, long date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(date2);
		int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
		int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
		int h = 0;
		int day = getOffectDay(date1, date2);
		h = h1-h2+day*24;
		return h;
	}
	
	/**
	 * ����������������������ķ�����.
	 *
	 * @param date1 ��һ��ʱ��ĺ����ʾ
	 * @param date2 �ڶ���ʱ��ĺ����ʾ
	 * @return int ����ķ�����
	 */
	public static int getOffectMinutes(long date1, long date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(date2);
		int m1 = calendar1.get(Calendar.MINUTE);
		int m2 = calendar2.get(Calendar.MINUTE);
		int h = getOffectHour(date1, date2);
		int m = 0;
		m = m1-m2+h*60;
		return m;
	}

	/**
	 * ��������ȡ����һ.
	 *
	 * @param format the format
	 * @return String String��������ʱ��
	 */
	public static String getFirstDayOfWeek(String format) {
		return getDayOfWeek(format,Calendar.MONDAY);
	}

	/**
	 * ��������ȡ������.
	 *
	 * @param format the format
	 * @return String String��������ʱ��
	 */
	public static String getLastDayOfWeek(String format) {
		return getDayOfWeek(format,Calendar.SUNDAY);
	}

	/**
	 * ��������ȡ���ܵ�ĳһ��.
	 *
	 * @param format the format
	 * @param calendarField the calendar field
	 * @return String String��������ʱ��
	 */
	private static String getDayOfWeek(String format,int calendarField) {
		String strDate = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			int week = c.get(Calendar.DAY_OF_WEEK);
			if (week == calendarField){
				strDate = mSimpleDateFormat.format(c.getTime());
			}else{
				int offectDay = calendarField - week;
				if (calendarField == Calendar.SUNDAY) {
					offectDay = 7-Math.abs(offectDay);
				} 
				c.add(Calendar.DATE, offectDay);
				strDate = mSimpleDateFormat.format(c.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	/**
	 * ��������ȡ���µ�һ��.
	 *
	 * @param format the format
	 * @return String String��������ʱ��
	 */
	public static String getFirstDayOfMonth(String format) {
		String strDate = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			//��ǰ�µĵ�һ��
			c.set(GregorianCalendar.DAY_OF_MONTH, 1);
			strDate = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;

	}

	/**
	 * ��������ȡ�������һ��.
	 *
	 * @param format the format
	 * @return String String��������ʱ��
	 */
	public static String getLastDayOfMonth(String format) {
		String strDate = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			// ��ǰ�µ����һ��
			c.set(Calendar.DATE, 1);
			c.roll(Calendar.DATE, -1);
			strDate = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}

	

	/**
	 * ��������ȡ��ʾ��ǰ���ڵ�0��ʱ�������.
	 *
	 * @return the first time of day
	 */
	public static long getFirstTimeOfDay() {
			Date date = null;
			try {
				String currentDate = getCurrentDate(dateFormatYMD);
				date = getDateByFormat(currentDate+" 00:00:00",dateFormatYMDHMS);
				return date.getTime();
			} catch (Exception e) {
			}
			return -1;
	}
	
	/**
	 * ��������ȡ��ʾ��ǰ����24��ʱ�������.
	 *
	 * @return the last time of day
	 */
	public static long getLastTimeOfDay() {
			Date date = null;
			try {
				String currentDate = getCurrentDate(dateFormatYMD);
				date = getDateByFormat(currentDate+" 24:00:00",dateFormatYMDHMS);
				return date.getTime();
			} catch (Exception e) {
			}
			return -1;
	}
	
	/**
	 * �������ж��Ƿ�������()
	 * <p>(year�ܱ�4���� ���� ���ܱ�100����) ���� year�ܱ�400����,�����Ϊ����.
	 *
	 * @param year �������2012��
	 * @return boolean �Ƿ�Ϊ����
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0 && year % 400 != 0) || year % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * ����������ʱ�䷵�ؼ���ǰ�򼸷��ӵ�����.
	 *
	 * @param strDate the str date
	 * @return the string
	 */
	public static String formatDateStr2Desc(String strDate,String outFormat) {
		
		DateFormat df = new SimpleDateFormat(dateFormatYMDHMS);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c2.setTime(df.parse(strDate));
			c1.setTime(new Date());
			int d = getOffectDay(c1.getTimeInMillis(), c2.getTimeInMillis());
			if(d==0){
				int h = getOffectHour(c1.getTimeInMillis(), c2.getTimeInMillis());
				if(h>0){
					return h + "Сʱǰ";
				}else if(h<0){
					return Math.abs(h) + "Сʱ��";
				}else if(h==0){
					int m = getOffectMinutes(c1.getTimeInMillis(), c2.getTimeInMillis());
					if(m>0){
						return m + "����ǰ";
					}else if(m<0){
						return Math.abs(m) + "���Ӻ�";
					}else{
						return "�ո�";
					}
				}
			}else if(d>0){
				if(d == 1){
					return "����";
				}else if(d==2){
					return "ǰ��";
				}
			}else if(d<0){
				if(d == -1){
					return "����";
				}else if(d== -2){
					return "����";
				}
				return Math.abs(d) + "���";
			}
			
			String out = getStringByFormat(strDate,outFormat);
			if(!AbStrUtil.isEmpty(out)){
				return out;
			}
		} catch (Exception e) {
		}
		
		return strDate;
	}
	
	
	/**
     * ȡָ������Ϊ���ڼ�
     * @param strDate ָ������
     * @param inFormat ָ�����ڸ�ʽ
     * @return String   ���ڼ�
     */
    public static String getWeekNumber(String strDate,String inFormat) {
      String week = "������";
      Calendar calendar = new GregorianCalendar();
      DateFormat df = new SimpleDateFormat(inFormat);
      try {
		   calendar.setTime(df.parse(strDate));
	  } catch (Exception e) {
		  return "����";
	  }
      int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
      switch (intTemp){
        case 0:
          week = "������";
          break;
        case 1:
          week = "����һ";
          break;
        case 2:
          week = "���ڶ�";
          break;
        case 3:
          week = "������";
          break;
        case 4:
          week = "������";
          break;
        case 5:
          week = "������";
          break;
        case 6:
          week = "������";
          break;
      }
      return week;
    }
	
	/**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
		System.out.println(formatDateStr2Desc("2012-3-2 12:2:20","MM��dd��  HH:mm"));
	}

}
