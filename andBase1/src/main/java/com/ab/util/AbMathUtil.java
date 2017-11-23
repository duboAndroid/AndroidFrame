/*
 * 
 */
package com.ab.util;

import java.math.BigDecimal;

// TODO: Auto-generated Javadoc
/**
 * 描述：数学处理类.
 *
 * @author zhaoqp
 * @date：2013-1-18 上午10:14:44
 * @version v1.0
 */
public class AbMathUtil{

  /**
   * 四舍五入.
   *
   * @param number  原数
   * @param decimal 保留几位小数
   * @return 四舍五入后的值
   */
  public static BigDecimal round(double number, int decimal){
    return new BigDecimal(number).setScale(decimal, BigDecimal.ROUND_HALF_UP);
  }
  
  /**
   * 描述：字节数组转换成16进制串.
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
