package com.wj.cashregister.model.type;

/**
 * Created by wangjiang on 2016/3/1.
 * <p/>
 * 该类用于处理买二赠一的商品
 */
public class GoodsTypeFree extends GoodsType {
    //赠送数量
    private static final int RULE_FREE_COUNT = 1;
    //需要购买的最小的数量
    private static final int RULE_MIN_COUNT = 2;

    //赠送商品的数量
    private int freeCount = 0;
    //节省的钱
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

    /*
    * 获得赠送商品的数量
    * */
    public int getFreeCount() {
        return freeCount;
    }

    /*
    * 设置赠送商品的数量
    * */
    public void setFreeCount(int freeCount) {
        this.freeCount = freeCount;
    }

    /*
    * 获得节省的钱
    * */
    public float getFreeSavings() {
        return freeSavings;
    }

    /*
    * 设置节省的钱
    * */
    public void setFreeSavings(float freeSavings) {
        this.freeSavings = freeSavings;
    }
}
