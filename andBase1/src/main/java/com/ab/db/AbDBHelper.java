/*
 * 
 */
package com.ab.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// TODO: Auto-generated Javadoc
/**
 * 描述：数据库操作类.
 * @author zhaoqp
 * @date：2013-3-13 下午4:30:10
 * @version v1.0
 */
public class AbDBHelper extends SQLiteOpenHelper {
	
	/** The Constant DBNAME. */
	private static final String DBNAME = "andbase.db";
	
	/** The Constant VERSION. */
	private static final int VERSION = 1;
	
	/**
	 * 构造器.
	 *
	 * @param context the context
	 */
	public AbDBHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}
	
	/**
	 * 描述：表的创建.
	 *
	 * @param db the db
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS FILEDOWN (_ID INTEGER PRIMARY KEY AUTOINCREMENT,ICON TEXT,NAME TEXT,DESCRIPTION TEXT, PAKAGENAME TEXT ,DOWNURL TEXT,DOWNPATH TEXT,STATE INTEGER,DOWNLENGTH INTEGER,TOTALLENGTH INTEGER)");
	}

	/**
	 * 描述：表的重建.
	 *
	 * @param db the db
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS FILEDOWN");
		onCreate(db);
	}
	
}