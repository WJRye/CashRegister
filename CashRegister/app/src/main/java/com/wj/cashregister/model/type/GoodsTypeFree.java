package com.wj.cashregister.model.type;

/**
 * Created by wangjiang on 2016/3/1.
 */
public class GoodsTypeFree extends GoodsType {
    private static final int RULE_FREE_COUNT = 1;
    private static final int RULE_MIN_COUNT = 2;

    private int mFreeCount = 0;

    public GoodsTypeFree() {
        super(TYPE_FREE);
    }

    public int getRuleFreeCount() {
        return RULE_FREE_COUNT;
    }

    public int getRuleMinCount() {
        return RULE_MIN_COUNT;
    }

    public void setFreeCount(int freeCount) {
        mFreeCount = freeCount;
    }

    public int getFreeCount() {
        return mFreeCount;
    }
}
