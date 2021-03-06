package com.lanrui.andiy.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{

	public DBOpenHelper(Context context) {
		super(context, "model.db", null, 3); //super(context, name, factory, version);  name数据库的名称, factory指游标， version版本  第一个最好是1
	}

	
	/**
	 * 每一次创建数据库的时候会调用onCreate方法
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE model (id integer primary key autoincrement, no integer, type varchar(10), path varchar(50), cardmodelid NUMERIC)");
	}

	/**
	 * 当版本号发生变化的时候会调用onUpgrade方法
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("Alter table model add phone varchar(20) null");
	}
	

}



























