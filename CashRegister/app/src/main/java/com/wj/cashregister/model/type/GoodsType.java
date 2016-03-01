package com.wj.cashregister.model.type;

/**
 * Created by wangjiang on 2016/3/1.
 */
public abstract class GoodsType {

    private int mType;

    public GoodsType(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }
}
