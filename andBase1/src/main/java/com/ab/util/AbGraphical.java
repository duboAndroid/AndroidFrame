/*
 * 
 */
package com.ab.util;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint.FontMetrics;
import android.text.Layout;
import android.text.TextPaint;
import android.util.FloatMath;


// TODO: Auto-generated Javadoc
/**
 * ������ͼ�δ�����.
 *
 * @author zhaoqp
 * @date��2013-1-17 ����10:07:09
 * @version v1.0
 */
public final class AbGraphical {
    
   

     /**
      * ��������ȡ�ַ�������λ�ã������ػ�ȡ��������ɵģ�.
      *
      * @param str ָ�����ַ���
      * @param maxPix Ҫȡ����λ�ã����أ�
      * @param paint the paint
      * @return �ַ�������λ��
      */
     public static int subStringLength(String str,int maxPix,TextPaint paint) {
    	 if(AbStrUtil.isEmpty(str)){
    		 return 0;
    	 }
    	 int currentIndex = 0;
         for (int i = 0; i < str.length(); i++) {
             //��ȡһ���ַ� 
             String temp = str.substring(0, i + 1);
             float valueLength  = paint.measureText(temp);
             if(valueLength > maxPix){
            	 currentIndex = i-1;
            	 break;
             }else if(valueLength == maxPix){
            	 currentIndex = i;
            	 break;
             }
         }
         //����������ط������һ���ַ�λ��
         if(currentIndex==0){
        	 currentIndex = str.length()-1 ;
         }
         return currentIndex;
     }
     
     /**
      * ��������ȡ���ֵ����ؿ�.
      *
      * @param str the str
      * @param paint the paint
      * @return the string width
      */
     public static float getStringWidth(String str,TextPaint paint) {
    	 float strWidth  = paint.measureText(str);
         return strWidth;
     }
     
     /**
      * Return how wide a layout must be in order to display
      * the specified text with one line per paragraph.
      * @param str the str
      * @param paint the paint
      * @return the string width2
      */
     public static float getDesiredWidth(String str,TextPaint paint) {
    	 float strWidth = Layout.getDesiredWidth(str, paint);
         return strWidth;
     }

     /**
      * Gets the draw row string.
      * @param text the text
      * @param maxWPix the max w pix
      * @param paint the paint
      * @return the draw row count
      */
     public static List<String> getDrawRowStr(String text,int maxWPix,TextPaint paint) {
     	String [] texts = null;
     	if(text.indexOf("\n")!=-1){
     		 texts = text.split("\n");
     	}else{
     		 texts  = new String [1];
     		 texts[0] = text;
     	}
     	//��������
     	List<String> mStrList  = new ArrayList<String>();
     	
     	for(int i=0;i<texts.length;i++){
   		    String textLine = texts[i];
   		    //��������ı���ʾΪ����
            while(true){
	           	 //�����ɵ����һ���ֵ�λ��
	           	 int endIndex = subStringLength(textLine,maxWPix,paint);
	           	 if(endIndex<=0){
	           		mStrList.add(textLine);
	           	 }else{
	           		if(endIndex == textLine.length()-1){
	           			mStrList.add(textLine);
	           		}else{
	           			mStrList.add(textLine.substring(0,endIndex+1));
	           		}
	           		 
	           	 }
	           	 //��ȡʣ�µ�
	           	 if(textLine.length()>endIndex+1){
	           		 //����ʣ�µ�
	           		 textLine = textLine.substring(endIndex+1);
	           	 }else{
	           		 break;
	           	 }
            }
   	     }
        
         return mStrList;
     }
     
     /**
      * Gets the draw row count.
      * @param text the text
      * @param maxWPix the max w pix
      * @param paint the paint
      * @return the draw row count
      */
     public static int getDrawRowCount(String text,int maxWPix,TextPaint paint) {
     	String [] texts = null;
     	if(text.indexOf("\n")!=-1){
     		 texts = text.split("\n");
     	}else{
     		 texts  = new String [1];
     		 texts[0] = text;
     	}
     	//��������
     	List<String> mStrList  = new ArrayList<String>();
     	
     	for(int i=0;i<texts.length;i++){
   		    String textLine = texts[i];
   		    //��������ı���ʾΪ����
            while(true){
	           	 //�����ɵ����һ���ֵ�λ��
	           	 int endIndex = subStringLength(textLine,maxWPix,paint);
	           	 if(endIndex<=0){
	           		mStrList.add(textLine);
	           	 }else{
	           		if(endIndex == textLine.length()-1){
	           			mStrList.add(textLine);
	           		}else{
	           			mStrList.add(textLine.substring(0,endIndex+1));
	           		}
	           		 
	           	 }
	           	 //��ȡʣ�µ�
	           	 if(textLine.length()>endIndex+1){
	           		 //����ʣ�µ�
	           		 textLine = textLine.substring(endIndex+1);
	           	 }else{
	           		 break;
	           	 }
            }
   	     }
        
         return mStrList.size();
     }
     
     /**
      * �����������ı���֧�ֻ���.
      *
      * @param canvas the canvas
      * @param text the text
      * @param maxWPix the max w pix
      * @param paint the paint
      * @param left the left
      * @param top the top
      * @return the int
      */
     public static int drawText(Canvas canvas,String text,int maxWPix,TextPaint paint,int left,int top) {
    	if(AbStrUtil.isEmpty(text)){
    		return 1;
    	}
    	//��Ҫ�������ֳ��ȿ��ƻ���
        //�������ֵĳ���
    	List<String> mStrList  = getDrawRowStr(text,maxWPix,paint);
         
         FontMetrics fm  = paint.getFontMetrics();
         int hSize = (int)Math.ceil(fm.descent - fm.ascent)+2;
         
         for(int i=0;i<mStrList.size();i++){
        	 //��������
        	 int x = left;
             int y = top+hSize/2+hSize*i;
    		 String textLine = mStrList.get(i);
             canvas.drawText(textLine,x,y, paint); 
             
         }
         return mStrList.size();
     }
     

}
