package com.andbase.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ab.activity.AbActivity;
import com.ab.view.chart.CategorySeries;
import com.ab.view.chart.ChartFactory;
import com.ab.view.chart.PointStyle;
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
public class XYLineChartActivity extends AbActivity {
	
	private MyApplication application;
	
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
		//����
	    List<double[]> values = new ArrayList<double[]>();
	    //ÿ�����ݵ����ɫ
	    List<int[]> colors = new ArrayList<int[]>();
	    //ÿ�����ݵ�ļ�Ҫ ˵��
	    List<String[]> explains = new ArrayList<String[]>();
	    
	    values.add(new double[] { 14230, 0, 0, 0, 15900, 17200, 12030});
	    values.add(new double[] { 5230, 0, 0, 0, 7900, 9200, 13030});
	    
	    colors.add(new int[] { Color.RED, 0, 0, 0, 0, 0, 0});
	    colors.add(new int[] { 0, 0, Color.BLUE, 0, Color.GREEN, 0, 0});
	    
	    explains.add(new String[] { "��ɫ", "��2", "��3", "��4", "", "��6", ""});
	    explains.add(new String[] { "û����ɫ���ǵ�ʱ�ĵ��ǵ�ʱ���ٵ������ĵĵ������յ��Ƕ�ɫ��ɫ�ĵط����҇������η�", "û����ɫ", "��ɫ�ĵ�\n�ڶ��е�����\n�����е�����", "û����ɫ\n�ڶ��е�����\n�����е�����\n�����е�����\n�����е�����", "û����ɫ", "û����ɫ", ""});
	    
	    //�������������ɫ
	    int[] mSeriescolors = new int[] { Color.rgb(153, 204, 0),Color.rgb(51, 181, 229) };
	    //������Ⱦ��
	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    int length = mSeriescolors.length;
	    for (int i = 0; i < length; i++) {
	      //����SimpleSeriesRenderer��һ��Ⱦ��
	      XYSeriesRenderer r = new XYSeriesRenderer();
	      //SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	      //������Ⱦ����ɫ
	      r.setColor(mSeriescolors[i]);
	      r.setFillPoints(true);
		  r.setPointStyle(PointStyle.CIRCLE);
		  r.setLineWidth(1);
		  r.setChartValuesTextSize(16);
	      //���뵽������
	      renderer.addSeriesRenderer(r);
	    }
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
	    renderer.setXAxisMin(0.5);
	    //X����������
	    renderer.setXAxisMax(7.5);
	    //Y����С�����
	    renderer.setYAxisMin(-1000);
	    //Y����������
	    renderer.setYAxisMax(24000);
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
	    //��ʾ��Ļ�ɼ�ȡ����XY�ָ���
	    renderer.setXLabels(7);
	    renderer.setYLabels(10);
	    //X�̶ȱ�ǩ���X��λ��
	    renderer.setXLabelsAlign(Align.CENTER);
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
	    renderer.setScaleRectHeight(10);
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
	    
	    //�ٽ���
	    double[] limit = new double[]{15000,12000,4000,9000};
	    renderer.setmYLimitsLine(limit);
	    int[] colorsLimit = new int[] { Color.rgb(100, 255,255),Color.rgb(100, 255,255),Color.rgb(0, 255, 255),Color.rgb(0, 255, 255) };
	    renderer.setmYLimitsLineColor(colorsLimit);
	    
	    //��ʾ�����
	    renderer.setShowGrid(true);
	    //���ֵ��0�Ƿ�Ҫ��ʾ
	    renderer.setDisplayValue0(true);
	    //������Ⱦ�����������
	    XYMultipleSeriesDataset mXYMultipleSeriesDataset = new XYMultipleSeriesDataset();
	    for (int i = 0; i < length; i++) {
	      CategorySeries series = new CategorySeries(titles[i]);
	      double[] v = values.get(i);
	      int[] c = colors.get(i);
	      String[] e = explains.get(i);
	      int seriesLength = v.length;
	      for (int k = 0; k < seriesLength; k++) {
	    	  //����ÿ�������ɫ
	          series.add(v[k],c[k],e[k]);
	      }
	      mXYMultipleSeriesDataset.addSeries(series.toXYSeries());
	    }
	    //����
	    renderer.setApplyBackgroundColor(true);
	    renderer.setBackgroundColor(Color.rgb(222, 222, 200));
	    renderer.setMarginsColor(Color.rgb(222, 222, 200));
	    
	    //��ͼ
	    View chart = ChartFactory.getLineChartView(this,mXYMultipleSeriesDataset,renderer);
        linearLayout.addView(chart);
	    
	    //��ȡͼ��View
	    /*View chart = ChartFactory.getBarChartView(this,mXYMultipleSeriesDataset,renderer,
                Type.DEFAULT);
        linearLayout.addView(chart);*/
	    
        
        /*Intent intent = ChartFactory.getLineChartIntent(this, mXYMultipleSeriesDataset, renderer);
		startActivity(intent);*/
		
      } 
    
}