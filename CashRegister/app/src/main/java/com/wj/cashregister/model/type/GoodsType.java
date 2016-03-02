package com.wj.cashregister.model.type;

/**
 * Created by wangjiang on 2016/3/1.
 */
public abstract class GoodsType {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_DISCOUNT = 1;
    public static final int TYPE_FREE = 2;
    public static final int TYPE_DOUBLE = 3;
    private int mType;

    public GoodsType(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }
}
