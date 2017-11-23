package com.andbase.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ab.activity.AbActivity;
import com.ab.view.level.AbLevelChartFactory;
import com.ab.view.level.AbLevelSeriesDataset;
import com.ab.view.level.AbLevelSeriesRenderer;
import com.ab.view.level.AbLevelView;
import com.andbase.R;
import com.andbase.global.MyApplication;

public class LevelChartActivity extends AbActivity {
	private MyApplication application;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.level_view);
        this.setTitleText(R.string.level_name);
        this.setLogo(R.drawable.button_selector_back);
        this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
	    this.setLogoLine(R.drawable.line);
        application = (MyApplication)abApplication;
        
        //�ȼ�ͼ�ο�
        int width = 300;
        //�ȼ�ͼ�θ�
        int height = 200;
        //���ȼ��ε���ɫ
    	int [] color = new int[]{Color.rgb(71, 190, 222),Color.rgb(153, 234, 71),Color.rgb(153, 234, 71),Color.rgb(249, 135, 65),Color.rgb(249, 135, 65),Color.rgb(249, 135, 65)};
    	//���� ÿ�εİٷֱ�  2  3  2  1  1  1
    	float [] part = new float[]{2,3,2,1,1,1};
    	//���ȼ��ε�ֵ
    	float [] partValue = new float[]{12.1f,15.0f,20.0f,30.0f,50.0f,60.0f};
    	//��ǰֵ
    	String textValue = "126/76";
    	//��ǰֵ����
    	String textDesc = "����";
    	//��ǰֵ�ĵȼ�
    	int textlevelIndex = 2;
    	//��ǰֵ���ִ�С
    	int textLevelSize = 30;
    	//��ǰֵ�����붥���ľ���
    	int marginTop = 30;
    	//ָʾ�����εĿ��
    	int arrowWidth  = 20;
    	//ָʾ�����εĸ߶�
    	int arrowHeight = 10;
    	//�ȼ����ĸ߶�
    	int levelHeight = 20;
    	//ָʾ���������������
    	int arrowMarginTop = 10;
    	//�ȼ��������ִ�С
    	int partTextSize = 15;
    	//�ȼ�˵�����ִ�С
    	int textDescSize = 22;
        
        //Ҫ��ʾͼ�ε�View
        LinearLayout chartLayout = (LinearLayout) findViewById(R.id.chartLayout);
        
        
        AbLevelSeriesRenderer renderer = new AbLevelSeriesRenderer();
        
        renderer.setWidth(width);
        renderer.setHeight(height);
        renderer.setColor(color);
        renderer.setPart(part);
        renderer.setPartValue(partValue);
        renderer.setTextValue(textValue);
        renderer.setTextDesc(textDesc);
        renderer.setTextlevelIndex(textlevelIndex);
        renderer.setTextLevelSize(textLevelSize);
        renderer.setMarginTop(marginTop);
        renderer.setArrowWidth(arrowWidth);
        renderer.setArrowHeight(arrowHeight);
        renderer.setArrowMarginTop(arrowMarginTop);
        renderer.setLevelHeight(levelHeight);
        renderer.setPartTextSize(partTextSize);
        renderer.setTextDescSize(textDescSize);
        renderer.setTextRectWidth(120);
        renderer.setTextRectHeight(50);
        
        AbLevelSeriesDataset mDataset = new AbLevelSeriesDataset();
        AbLevelView mAbLevelView = AbLevelChartFactory.getLevelChartView(this,mDataset,renderer);
        
        chartLayout.addView(mAbLevelView, layoutParamsFW);
       
       
    }
    
}


