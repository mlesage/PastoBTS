package DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper{
	String sqltable = "create table pizza (id INT NOT NULL PRIMARY KEY, NOM TEXT NOT NULL, CATEGORIE TEXT NOT NULL, STJOUR INT DEFAULT '0', STSEMAINE INT DEFAULT '0', STMOIS INT DEFAULT '0', STANNEE INT DEFAULT '0', STTOTAL INT DEFAULT '0', DATEJOUR INT DEFAULT '0', DATESEMAINE INT DEFAULT '0', DATEMOIS INT DEFAULT '0', DATEANNEE INT DEFAULT '0' )";
	public static final String METIER_TABLE_DROP = "DROP TABLE pizza;";
	
	public DBManager(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sqltable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(METIER_TABLE_DROP);
		onCreate(db);
	}
	
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(METIER_TABLE_DROP);
		onCreate(db);
	}

}
