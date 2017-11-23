/*
 * 
 */
package com.ab.view.level;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;

import com.ab.util.AbGraphical;
import com.ab.util.AbViewUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class AbLevelChart.
 */
public class AbLevelChart extends AbLevelAbstractChart {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The m dataset. */
	protected AbLevelSeriesDataset mDataset;
	
	/** The m renderer. */
	protected AbLevelSeriesRenderer mRenderer;
	
	/** �ȼ�ͼ�εĿ�. */
	private int measureWidth;
	
	/** �ȼ�ͼ�εĸ�. */
	private int measureHeight;
	
	/** ���ȼ��ε���ɫ. */
	private int [] color = null;
	
	/** ���� ÿ�εİٷֱ�  2  3  2  1  1  1. */
	private float [] part = null;
	
	/** ���ȼ��ε�ֵ. */
	private float [] partValue = null;
	
	/** ��ǰֵ. */
	private String textValue = null;
	
	/** ��ǰֵ����. */
	private String textDesc = null;
	
	/** ��ǰֵ�ĵȼ�. */
	private int textlevelIndex = 0;
	
	/** ��ǰֵ���ִ�С. */
	private int textLevelSize = 30;
	
	/** ��ǰֵ�����붥���ľ���. */
	private int marginTop = 30;
	
	/** ָʾ�����εĿ��. */
	private int arrowWidth  = 20;
	
	/** ָʾ�����εĸ߶�. */
	private int arrowHeight = 10;
	
	/** �ȼ����ĸ߶�. */
	private int levelHeight = 20;
	
	/** ָʾ���������������. */
	private int arrowMarginTop = 10;
	
	/** �ȼ��������ִ�С. */
	private int partTextSize = 15;
	
	/** �ȼ�˵�����ִ�С. */
	private int textDescSize = 22;

	/**
	 * Instantiates a new ab level chart.
	 */
	protected AbLevelChart() {
	}

	/**
	 * Instantiates a new ab level chart.
	 *
	 * @param mDataset the m dataset
	 * @param mRenderer the m renderer
	 */
	public AbLevelChart(AbLevelSeriesDataset mDataset,AbLevelSeriesRenderer mRenderer) {
		super();
		this.mDataset = mDataset;
		this.mRenderer = mRenderer;
		this.measureWidth = mRenderer.getWidth();
		this.measureHeight = mRenderer.getHeight();
	}

	/**
	 * Sets the dataset renderer.
	 *
	 * @param dataset the dataset
	 * @param renderer the renderer
	 */
	protected void setDatasetRenderer(AbLevelSeriesDataset dataset,AbLevelSeriesRenderer renderer) {
		mDataset = dataset;
		mRenderer = renderer;
	}

	/**
	 * ����������.
	 *
	 * @param canvas the canvas
	 * @param x the x
	 * @param y the y
	 * @param measureWidth the measure width
	 * @param measureHeight the measure height
	 * @param screenWidth the screen width
	 * @param screenHeight the screen height
	 * @param paint the paint
	 * @see com.ab.view.level.AbLevelAbstractChart#draw(android.graphics.Canvas, int, int, int, int, int, int, android.graphics.Paint)
	 */
	public void draw(Canvas canvas, int x, int y,int measureWidth,int measureHeight,int screenWidth, int screenHeight,
			Paint paint) {
		
		//���ȼ��ε���ɫ
		color = mRenderer.getColor();
		//���� ÿ�εİٷֱ�  2  3  2  1  1  1
		part = mRenderer.getPart();
		//���ȼ��ε�ֵ
		partValue = mRenderer.getPartValue();
		//��ǰֵ
		textValue = mRenderer.getTextValue();
		//��ǰֵ����
		textDesc = mRenderer.getTextDesc();
		//��ǰֵ�ĵȼ�
		textlevelIndex = mRenderer.getTextlevelIndex();
		//��ǰֵ���ִ�С
		textLevelSize = mRenderer.getTextLevelSize();
		textLevelSize = AbViewUtil.resizeTextSize(screenWidth,screenHeight, textLevelSize);
		//��ǰֵ�����붥���ľ���
		marginTop = mRenderer.getMarginTop();
		//ָʾ�����εĿ��
		arrowWidth  = mRenderer.getArrowWidth();
		//ָʾ�����εĸ߶�
		arrowHeight = mRenderer.getArrowHeight();
		//�ȼ����ĸ߶�
		levelHeight = mRenderer.getLevelHeight();
		//ָʾ���������������
		arrowMarginTop = mRenderer.getArrowMarginTop();
		//�ȼ����������ִ�С
		partTextSize = mRenderer.getPartTextSize();
		textDescSize = mRenderer.getTextDescSize();
		//��ȡֵ���ı��ĸ߶�
        TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextSize(textLevelSize);
        FontMetrics fm  = mTextPaint.getFontMetrics();
        //�õ��и�
        int textHeight = (int)Math.ceil(fm.descent - fm.ascent)+2-20;
        int textWidth = (int)AbGraphical.getStringWidth(textValue,mTextPaint);
        
        int left = (screenWidth-measureWidth)/2;
		
        //����level��
		int topLevel = marginTop+textHeight+arrowHeight+arrowMarginTop;
		RectF mLevelRect = new RectF(left,topLevel ,left+measureWidth,topLevel+levelHeight);
		paint.setStyle(Paint.Style.FILL);  
        //���û��ʵľ��Ч��  
		paint.setAntiAlias(true);
		paint.setStrokeWidth(2);
		paint.setColor(Color.rgb(228, 228, 228));
		canvas.drawRoundRect(mLevelRect, 1, 1, paint);
		
		int partWidth = measureWidth/10;
		RectF mLevelRectPart = null;
		float sumLeft = 0;
		float sumRight = 0;
		for(int i=0;i<color.length;i++){
			if(i==0){
				sumLeft = left;
				sumRight = sumLeft + part[i]*partWidth;
				mLevelRectPart = new RectF(sumLeft,topLevel ,sumRight,topLevel+levelHeight);
			}else{
				sumLeft  += part[i-1]*partWidth;
				sumRight += part[i]*partWidth;
				mLevelRectPart = new RectF(sumLeft+1,topLevel ,sumRight,topLevel+levelHeight);
			}
			paint.setColor(color[i]);
			//��ǰ��ֵ
			if(textlevelIndex == i){
				
				paint.setFlags(Paint.ANTI_ALIAS_FLAG);
				paint.setTextSize(textLevelSize);
				paint.setTypeface(Typeface.DEFAULT_BOLD);
				float textLeftOffset = (part[i]*partWidth-textWidth)/2;
				canvas.drawText(textValue,sumLeft+textLeftOffset,marginTop, paint);
				float arrowLeftOffset = (part[i]*partWidth-arrowWidth)/2;
				float center = sumLeft+arrowLeftOffset+arrowWidth/2;
				paint.setStyle(Paint.Style.FILL);  
				paint.setColor(Color.rgb(153, 234, 71));  
				Path path1 = new Path();  
				path1.moveTo(center,marginTop+textHeight+arrowHeight);  
				path1.lineTo(sumLeft+arrowLeftOffset,marginTop+textHeight);  
				path1.lineTo(sumLeft+arrowLeftOffset+arrowWidth,marginTop+textHeight);  
				path1.close();
				canvas.drawPath(path1, paint);
				
				//���Ƶȼ����·��ĵ�����
				paint.setColor(Color.rgb(227, 227, 227));  
				paint.setStyle(Paint.Style.FILL);  
				Path path2 = new Path();  
				path2.moveTo(center,marginTop+textHeight+arrowHeight+levelHeight+2*arrowMarginTop);  
				path2.lineTo(sumLeft+arrowLeftOffset,marginTop+textHeight+levelHeight+2*arrowHeight+2*arrowMarginTop);  
				path2.lineTo(sumLeft+arrowLeftOffset+arrowWidth,marginTop+textHeight+levelHeight+2*arrowHeight+2*arrowMarginTop);  
				path2.close();
				canvas.drawPath(path2, paint);
				
				//���Ƶȼ����·�����������
				int topDesc = marginTop+textHeight+2*arrowHeight+2*arrowMarginTop+levelHeight;
				RectF mLevelDescRect = new RectF(center-mRenderer.getTextRectWidth()/2,topDesc ,center+mRenderer.getTextRectWidth()/2,topDesc+mRenderer.getTextRectHeight());
				canvas.drawRoundRect(mLevelDescRect,5, 5, paint);
				paint.setTypeface(Typeface.DEFAULT_BOLD);
				paint.setTextSize(textDescSize);
				paint.setColor(Color.rgb(157, 157, 157));
				mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
		        mTextPaint.setTextSize(textDescSize);
		        FontMetrics textDescFm  = mTextPaint.getFontMetrics();
		        //�õ��и�
		        int textDescHeight = (int)Math.ceil(textDescFm.descent - textDescFm.ascent)+2;
		        int textDescWidth = (int)AbGraphical.getStringWidth(textDesc,mTextPaint);
				canvas.drawText(textDesc,center-textDescWidth/2,topDesc+20+((mRenderer.getTextRectHeight()-textDescHeight)/2),paint);
				paint.setColor(color[i]);
				
			}
			//���Ƶȼ���
			canvas.drawRoundRect(mLevelRectPart, 1, 1, paint);
			
			//���ƶ�����
			if(partValue!=null && partValue.length == color.length){
				paint.setTextSize(partTextSize);
		        mTextPaint.setTextSize(partTextSize);
		        int partValueWidth = (int)AbGraphical.getStringWidth(String.valueOf(partValue[i]),mTextPaint);
				canvas.drawText(String.valueOf(partValue[i]),mLevelRectPart.left-partValueWidth/2,mLevelRectPart.top+levelHeight+15,paint);
			}
			
		}
		
	}
	
	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return measureWidth;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return measureHeight;
	}

}
