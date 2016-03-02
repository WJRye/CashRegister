package com.wj.cashregister.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangjiang on 2016/3/1.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String NAME = "cashregister.db";
    private static final int VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists goods(" + "barCode text(64) PRIMARY KEY," + "name text(32)," + "unit text(16)," + "price real," + "goodsType integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
