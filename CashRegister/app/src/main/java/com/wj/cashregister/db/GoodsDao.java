package com.wj.cashregister.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import com.wj.cashregister.model.Goods;

import java.util.List;

/**
 * Created by wangjiang on 2016/3/1.
 */
public class GoodsDao {
    private DBOpenHelper mDbOpenHelper;

    private GoodsDao(Context context) {
        mDbOpenHelper = new DBOpenHelper(context);
    }

    public static GoodsDao getInstance(Context context) {
        return GoodsDaoHolder.getGoodsDao(context);
    }

    private static class GoodsDaoHolder {
        private static GoodsDao INSTANCE = null;

        private static GoodsDao getGoodsDao(Context context) {
            if (INSTANCE == null) INSTANCE = new GoodsDao(context);
            return INSTANCE;
        }
    }

    public void insertGoodses(List<Goods> goodses) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            SQLiteStatement sqLiteStatement = db.compileStatement("insert into goods values(?,?,?,?,?)");
            for (Goods goods : goodses) {
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(1, goods.getBarCode());
                sqLiteStatement.bindString(2, goods.getName());
                sqLiteStatement.bindString(3, goods.getUnit());
                sqLiteStatement.bindDouble(4, goods.getPrice());
                sqLiteStatement.bindLong(5, goods.getGoodsType().getType());
                sqLiteStatement.executeInsert();
            }
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
