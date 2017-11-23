/*
 * 
 */
package com.ab.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

// TODO: Auto-generated Javadoc
/**
 * The Class AbDBDao.
 */
public class AbDBDao {
	
	/**
	 * 得到列值.
	 * @param columnName the column name
	 * @param cursor the cursor
	 * @return the string column value
	 */
	public String getStringColumnValue(String columnName, Cursor cursor) {
		return cursor.getString(cursor.getColumnIndex(columnName));
	}
	
	/**
	 * 得到列值.
	 * @param columnName the column name
	 * @param cursor the cursor
	 * @return the int column value
	 */
	public int getIntColumnValue(String columnName, Cursor cursor) {
		return cursor.getInt(cursor.getColumnIndex(columnName));
	}
	
	/**
	 * 描述：关闭数据库与游标.
	 * @param cursor the cursor
	 * @param db the db
	 */
	public void closeDB(Cursor cursor, SQLiteDatabase db) {
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		if (db != null && db.isOpen()) {
			db.close();
			db = null;
		}
	}
}
