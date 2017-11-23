package com.andbase.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ab.activity.AbActivity;
import com.ab.view.chart.ChartFactory;
import com.ab.view.chart.MathHelper;
import com.ab.view.chart.PointStyle;
import com.ab.view.chart.TimeSeries;
import com.ab.view.chart.XYMultipleSeriesDataset;
import com.ab.view.chart.XYMultipleSeriesRenderer;
import com.ab.view.chart.XYSeriesRenderer;
import com.andbase.R;
import com.andbase.global.MyApplication;

/**
 * Copyright (c) 2011 All rights reserved��
 * ���ƣ�XYLineChartActivity
 * ��������ͼ
 * @author zhaoqp
 * @date 2011-12-13
 * @version
 */
public class TimeChartActivity extends AbActivity {
	
	private MyApplication application;
	
	private static final long HOUR = 3600 * 1000;
	private static final long DAY = HOUR * 24;
	private static final int HOURS = 24;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setAbContentView(R.layout.chart);
        this.setTitleText(R.string.chart_line);
        this.setLogo(R.drawable.button_selector_back);
        this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
	    
        application = (MyApplication)abApplication;
        
    	//Ҫ��ʾͼ�ε�View
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.chart01);
		//˵������
		String[] titles = new String[] { "��һ����", "�ڶ�����" };
		
		List<Date[]> x = new ArrayList<Date[]>();
		List<double[]> values = new ArrayList<double[]>();
		
	    long now = Math.round(new Date().getTime() / DAY) * DAY;
	    for (int i = 0; i < titles.length; i++) {
	      Date[] dates = new Date[HOURS];
	      for (int j = 0; j < HOURS; j++) {
	        dates[j] = new Date(now - (HOURS - j) * HOUR);
	      }
	      x.add(dates);
	    }

	    values.add(
	    new double[] { 15, 16, 17, 18, 19, 20, 19, 18, 17, 16, 15,15, 16, 17, 18, 19, 20, 19, 18, 17, 16, 15,16,17});
	    values.add(
	    new double[] {0, 1, 2, 3, 4, 5, 4,3,2,1,0,0, 1, 2, 3, 4, 5, 4,3,2,1,0,1,2});

	    int[] mSeriescolors = new int[] { Color.rgb(153, 204, 0),Color.rgb(51, 181, 229) };
	    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND };
	    
	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    int length = mSeriescolors.length;
	    for (int i = 0; i < length; i++) {
		      XYSeriesRenderer r = new XYSeriesRenderer();
		      r.setColor(mSeriescolors[i]);
		      r.setPointStyle(styles[i]);
		      r.setLineWidth(1);
		      r.setFillPoints(true);
			  r.setChartValuesTextSize(16);
			  
		      renderer.addSeriesRenderer(r);
	    }
	    //��Ĵ�С
	    renderer.setPointSize(2f);
	    //������������ִ�С
  		renderer.setAxisTitleTextSize(16);
  		//ͼ�α������ִ�С
  		renderer.setChartTitleTextSize(25);
  		//�����ϱ�ǩ���ִ�С
  		renderer.setLabelsTextSize(15);
  		//˵�����ִ�С
  		renderer.setLegendTextSize(15);
	    //ͼ�����
	    renderer.setChartTitle("����ͼ��ı���");
	    //X�����
	    renderer.setXTitle("X��");
	    //Y�����
	    renderer.setYTitle("Y��");
	    //X����С�����
	    renderer.setXAxisMin(x.get(0)[0].getTime());
	    //X����������
	    renderer.setXAxisMax(x.get(0)[HOURS - 1].getTime());
	    //Y����С�����
	    renderer.setYAxisMin(-5);
	    //Y����������
	    renderer.setYAxisMax(30);
	    //��������ɫ
	    renderer.setAxesColor(Color.rgb(51, 181, 229));
	    renderer.setXLabelsColor(Color.rgb(51, 181, 229));
	    renderer.setYLabelsColor(0,Color.rgb(51, 181, 229));
	    //����ͼ���ϱ�����X����Y���˵��������ɫ
	    renderer.setLabelsColor(Color.GRAY);
	    //renderer.setGridColor(Color.GRAY);
	    //��������Ӵ�
		renderer.setTextTypeface("sans_serif", Typeface.BOLD);
		//������ͼ�����Ƿ���ʾֵ��ǩ
	    renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
	    renderer.getSeriesRendererAt(1).setDisplayChartValues(true);
	    renderer.setMargins(new int[] { 20, 30, 15, 20 });
	    renderer.setXLabels(6);
	    renderer.setYLabels(10);
	    renderer.setShowGrid(true);
	    renderer.setXLabelsAlign(Align.CENTER);
	    renderer.setYLabelsAlign(Align.RIGHT);
	    
	    // ������Ⱦ�����������
		XYMultipleSeriesDataset mXYMultipleSeriesDataset = new XYMultipleSeriesDataset();
	    for (int i = 0; i < length; i++) {
		      TimeSeries series = new TimeSeries(titles[i]);
		      Date[] xV = x.get(i);
		      double[] yV = values.get(i);
		      int seriesLength = xV.length;
		      for (int k = 0; k < seriesLength; k++) {
		        series.add(xV[k], yV[k]);
		      }
		      mXYMultipleSeriesDataset.addSeries(series);
	    }
	    
	    //Y�̶ȱ�ǩ���Y��λ��
	    renderer.setYLabelsAlign(Align.LEFT);
	    renderer.setPanEnabled(true, true);
	    renderer.setZoomEnabled(true);
	    renderer.setZoomButtonsVisible(true);
	    renderer.setZoomRate(1.1f);
	    renderer.setBarSpacing(0.5f);
	    
	    //��߿���
	    renderer.setScaleLineEnabled(true);
	    //���ñ����ʾ���
	    renderer.setScaleRectHeight(60);
	    //���ñ����ʾ���
	    renderer.setScaleRectWidth(150);
	    //���ñ����ʾ�򱳾�ɫ
	    renderer.setScaleRectColor(Color.argb(150, 52, 182, 232));
	    renderer.setScaleLineColor(Color.argb(175, 150, 150, 150));
	    renderer.setScaleCircleRadius(35);
	    //��һ�����ֵĴ�С
	    renderer.setExplainTextSize1(20);
	    //�ڶ������ֵĴ�С
	    renderer.setExplainTextSize2(20);
	    
	    
	    double[] limit = new double[]{0,5,15,20};
		renderer.setmYLimitsLine(limit);
		int[] colorsLimit = new int[] { Color.rgb(221, 241,248),Color.rgb(221, 241,248),Color.rgb(233, 242,222),Color.rgb(233, 242,222) };
		renderer.setmYLimitsLineColor(colorsLimit);
	    
	    //����
	    renderer.setApplyBackgroundColor(true);
	    renderer.setBackgroundColor(Color.rgb(222, 222, 200));
	    renderer.setMarginsColor(Color.rgb(222, 222, 200));
	    
	    //��ͼ
	    View chart = ChartFactory.getTimeChartView(this,mXYMultipleSeriesDataset,renderer,"MM-dd HH:mm");
        linearLayout.addView(chart);
		
      } 
    
}