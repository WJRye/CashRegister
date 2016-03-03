package com.wj.cashregister.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangjiang on 2016/3/1.
 * <p/>
 * 该类用于对数据库的创建和版本管理
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    //数据库名字
    private static final String NAME = "cashregister.db";
    //数据库版本
    private static final int VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //字段含义：barCode(条形码)；name(名称)；unit(数量单位)；price(单价)；goodsType(类别)
        db.execSQL("create table if not exists goods(" + "barCode text(64) PRIMARY KEY," + "name text(32)," + "unit text(16)," + "price real," + "goodsType integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
