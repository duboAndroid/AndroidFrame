/*
 * 
 */
package com.ab.util;

import java.math.BigDecimal;

// TODO: Auto-generated Javadoc
/**
 * ��������ѧ������.
 *
 * @author zhaoqp
 * @date��2013-1-18 ����10:14:44
 * @version v1.0
 */
public class AbMathUtil{

  /**
   * ��������.
   *
   * @param number  ԭ��
   * @param decimal ������λС��
   * @return ����������ֵ
   */
  public static BigDecimal round(double number, int decimal){
    return new BigDecimal(number).setScale(decimal, BigDecimal.ROUND_HALF_UP);
  }
  
  /**
   * �������ֽ�����ת����16���ƴ�.
   *
   * @param b the b
   * @param length the length
   * @return the string
   */
  public static String byte2HexStr(byte[] b, int length){
    String hs = "";
    String stmp = "";
    for (int n = 0; n < length; ++n) {
      stmp = Integer.toHexString(b[n] & 0xFF);
      if (stmp.length() == 1)
        hs = hs + "0" + stmp;
      else {
        hs = hs + stmp;
      }
      hs = hs + ",";
    }
    return hs.toUpperCase();
  } 
 
}
