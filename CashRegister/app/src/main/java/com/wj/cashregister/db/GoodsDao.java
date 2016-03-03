package com.wj.cashregister.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.wj.cashregister.model.Goods;
import com.wj.cashregister.model.type.GoodsType;
import com.wj.cashregister.model.type.GoodsTypeDiscount;
import com.wj.cashregister.model.type.GoodsTypeDouble;
import com.wj.cashregister.model.type.GoodsTypeFree;
import com.wj.cashregister.model.type.GoodsTypeNormal;

import java.util.List;

/**
 * Created by wangjiang on 2016/3/1.
 * <p/>
 * 该类用于管理对表goods的增删改查操作
 */
public class GoodsDao {
    private DBOpenHelper mDBOpenHelper;

    private GoodsDao(Context context) {
        mDBOpenHelper = new DBOpenHelper(context);
    }

    /*
    * 获取GoodsDao对象
    * */
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

    /*
    * 插入goods 数据
    * */
    public void insertGoodses(List<Goods> goodses) throws Exception {
        SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            SQLiteStatement sqLiteStatement = db.compileStatement("insert into goods values(?,?,?,?,?)");
            for (Goods goods : goodses) {
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(1, goods.getBarCode());
                sqLiteStatement.bindString(2, goods.getName());
                sqLiteStatement.bindString(3, goods.getUnit());
                sqLiteStatement.bindDouble(4, goods.getPrice());
                if (goods.getGoodsType() != null) {
                    sqLiteStatement.bindLong(5, goods.getGoodsType().getType());
                } else {
                    sqLiteStatement.bindLong(5, GoodsType.TYPE_NORMAL);
                }
                sqLiteStatement.executeInsert();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /*
    * 根据条形码查询Goods
    * */
    public Goods queryGoodsByBarCode(String barCode) {
        SQLiteDatabase db = mDBOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from goods where barCode=?", new String[]{barCode});
        Goods goods = new Goods();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                goods.setBarCode(cursor.getString(0));
                goods.setName(cursor.getString(1));
                goods.setUnit(cursor.getString(2));
                goods.setPrice(cursor.getFloat(3));
                int goodsType = cursor.getInt(4);
                if (goodsType == GoodsType.TYPE_DISCOUNT) {
                    goods.setGoodsType(new GoodsTypeDiscount());
                } else if (goodsType == GoodsType.TYPE_FREE) {
                    goods.setGoodsType(new GoodsTypeFree());
                } else if (goodsType == GoodsType.TYPE_NORMAL) {
                    goods.setGoodsType(new GoodsTypeNormal());
                } else if (goodsType == GoodsType.TYPE_DOUBLE) {
                    goods.setGoodsType(new GoodsTypeDouble());
                }
            }
            cursor.close();
            cursor = null;
        }
        db.close();
        return goods;
    }

    /*
    * 根据条形码修改GoodsType
    * */
    public void updateGoodsTypeByBarCode(String barCode, GoodsType goodsType) {
        SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        db.execSQL("update goods set goodsType=? where barCode=?", new String[]{Integer.toString(goodsType.getType()), barCode});
        db.close();
    }
}

