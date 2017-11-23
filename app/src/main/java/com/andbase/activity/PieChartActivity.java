package com.andbase.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ab.activity.AbActivity;
import com.ab.view.chart.CategorySeries;
import com.ab.view.chart.ChartFactory;
import com.ab.view.chart.DefaultRenderer;
import com.ab.view.chart.SimpleSeriesRenderer;
import com.andbase.R;
import com.andbase.global.MyApplication;
/**
 * Copyright (c) 2011 All rights reserved��
 * ���ƣ�PieChartActivity
 * ��������ͼ
 * @author zhaoqp
 * @date 2011-12-13
 * @version
 */
public class PieChartActivity extends AbActivity {
	
	private MyApplication application;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setAbContentView(R.layout.chart);
        this.setTitleText(R.string.chart_pie);
        this.setLogo(R.drawable.button_selector_back);
        this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
	    
        application = (MyApplication)abApplication;
        
    	//Ҫ��ʾͼ�ε�View
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.chart01);
		
		//ÿ�������ɫ
		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN };
		//��Ⱦ��
		DefaultRenderer renderer = new DefaultRenderer();
		//ͼ�����
	    renderer.setChartTitle("����ͼ��ı���");
	    //ͼ�α������ִ�С
  		renderer.setChartTitleTextSize(25);
  		//�����ϱ�ǩ���ִ�С
  		renderer.setLabelsTextSize(15);
  		//˵�����ִ�С
  		renderer.setLegendTextSize(15);
  	    //����ͼ���ϱ�����X����Y���˵��������ɫ
	    renderer.setLabelsColor(Color.GRAY);
		//˵�����ִ�С
		renderer.setLegendTextSize(15);
		for (int color : colors) {
			  //����SimpleSeriesRenderer����
		      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		      r.setColor(color);
		      renderer.addSeriesRenderer(r);
		}
		//�������������
		CategorySeries series = new CategorySeries("֧�����");
		series.add("ס��", 28);
		series.add("ʳ��", 25);
		series.add("ˮ��", 2);
		series.add("����", 20);
		series.add("��װ", 25);
		
		//����ͼ��ı�����ɫ
	    renderer.setApplyBackgroundColor(true);
	    renderer.setBackgroundColor(Color.rgb(222, 222, 200));
	    //renderer.setMargins(new int[]{5,5,5,5});
		//��ȡͼ��View
	    View chart = ChartFactory.getPieChartView(this,series,renderer);
        linearLayout.addView(chart);
		
      } 
    
}