package com.lanrui.andiy.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{

	public DBOpenHelper(Context context) {
		super(context, "model.db", null, 3); //super(context, name, factory, version);  name���ݿ������, factoryָ�α꣬ version�汾  ��һ�������1
	}

	
	/**
	 * ÿһ�δ������ݿ��ʱ������onCreate����
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE model (id integer primary key autoincrement, no integer, type varchar(10), path varchar(50), cardmodelid NUMERIC)");
	}

	/**
	 * ���汾�ŷ����仯��ʱ������onUpgrade����
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("Alter table model add phone varchar(20) null");
	}
	

}


























