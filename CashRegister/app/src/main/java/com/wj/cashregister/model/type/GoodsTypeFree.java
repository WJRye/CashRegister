package com.wj.cashregister.model.type;

/**
 * Created by wangjiang on 2016/3/1.
 */
public class GoodsTypeFree extends GoodsType {
    private static final int RULE_FREE_COUNT = 1;
    private static final int RULE_MIN_COUNT = 2;

    private int freeCount = 0;
    private float freeSavings = 0.00f;

    public GoodsTypeFree() {
        super(TYPE_FREE);
    }

    public int getRuleFreeCount() {
        return RULE_FREE_COUNT;
    }

    public int getRuleMinCount() {
        return RULE_MIN_COUNT;
    }

    public int getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(int freeCount) {
        this.freeCount = freeCount;
    }

    public float getFreeSavings() {
        return freeSavings;
    }

    public void setFreeSavings(float freeSavings) {
        this.freeSavings = freeSavings;
    }
}
