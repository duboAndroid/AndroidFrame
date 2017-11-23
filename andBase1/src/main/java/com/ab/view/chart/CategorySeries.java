/*
 * 
 */
package com.ab.view.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A series for the category charts like the pie ones.
 */
public class CategorySeries implements Serializable {
  /** The series title. */
  private String mTitle;
  /** The series categories. */
  private List<String> mCategories = new ArrayList<String>();
  /** The series values. */
  private List<Double> mValues = new ArrayList<Double>();
  /** ÿ�������ɫ. */
  private List<Integer> mColors = new ArrayList<Integer>();
  
  /** ÿ�����ݵ�ļ�Ҫ ˵��. */
  List<String> mExplains = new ArrayList<String>();
  /**
   * Builds a new category series.
   * 
   * @param title the series title
   */
  public CategorySeries(String title) {
    mTitle = title;
  }

  /**
   * Returns the series title.
   * 
   * @return the series title
   */
  public String getTitle() {
    return mTitle;
  }

  /**
   * Adds a new value to the series.
   *
   * @param value the new value
   */
  public synchronized void add(double value) {
    add(mCategories.size() + "", value);
  }
  
  /**
   * ����һ���µĵ��Լ���ɫֵ.
   *
   * @param value the new value
   * @param color the new color
   */
  public synchronized void add(double value,int color) {
    add(mCategories.size() + "", value,color);
  }
  
  /**
   * ����һ���µĵ��Լ���ɫֵ,�Լ���ɫ,����˵��.
   *
   * @param value the new value
   * @param color the new color
   * @param explain the explain
   */
  public synchronized void add(double value,int color,String explain) {
    add(mCategories.size() + "", value,color,explain);
  }

  /**
   * Adds a new value to the series.
   * @param category the category
   * @param value the new value
   */
  public synchronized void add(String category, double value) {
    mCategories.add(category);
    mValues.add(value);
  }
  
  /**
   * Adds a new value to the series. �Լ���ɫ
   * @param category the category
   * @param value the new value
   * @param color the new color
   */
  public synchronized void add(String category, double value,int color) {
    mCategories.add(category);
    mValues.add(value);
    mColors.add(color);
  }
  
  /**
   * Adds a new value to the series. �Լ���ɫ,����˵��
   *
   * @param category the category
   * @param value the new value
   * @param color the new color
   * @param explain the explain
   */
  public synchronized void add(String category, double value,int color,String explain) {
    mCategories.add(category);
    mValues.add(value);
    mColors.add(color);
    mExplains.add(explain);
  }

  /**
   * Replaces the value at the specific index in the series.
   * 
   * @param index the index in the series
   * @param category the category
   * @param value the new value
   */
  public synchronized void set(int index, String category, double value) {
    mCategories.set(index, category);
    mValues.set(index, value);
  }

  /**
   * Removes an existing value from the series.
   * 
   * @param index the index in the series of the value to remove
   */
  public synchronized void remove(int index) {
    mCategories.remove(index);
    mValues.remove(index);
  }

  /**
   * Removes all the existing values from the series.
   */
  public synchronized void clear() {
    mCategories.clear();
    mValues.clear();
  }

  /**
   * Returns the value at the specified index.
   * 
   * @param index the index
   * @return the value at the index
   */
  public synchronized double getValue(int index) {
    return mValues.get(index);
  }

  /**
   * Returns the category name at the specified index.
   * 
   * @param index the index
   * @return the category name at the index
   */
  public synchronized String getCategory(int index) {
    return mCategories.get(index);
  }
  
  /**
   * ��������ȡ����ɫ����.
   *
   * @return the colors
   */
  public List<Integer> getColors() {
    return mColors;
  }
  
  /**
   * ��������ȡ��˵��.
   *
   * @return the explains
   */
  public List<String> getExplains() {
    return mExplains;
  }

  /**
   * Returns the series item count.
   * 
   * @return the series item count
   */
  public synchronized int getItemCount() {
    return mCategories.size();
  }

  /**
   * Transforms the category series to an XY series.
   * @return the XY series
   */
  public XYSeries toXYSeries() {
    XYSeries xySeries = new XYSeries(mTitle);
    for (int i=0;i<mValues.size();i++) {
      double value = mValues.get(i);
      //���꣬��ɫ������
      xySeries.add(i+1, value,mColors.get(i),mExplains.get(i));
    }
    return xySeries;
  }
}
