package com.wj.cashregister.model.type;

/**
 * Created by wangjiang on 2016/3/1.
 */
public class GoodsTypeDiscount extends GoodsType {
    public static final int TYPE = 1;
    private float rate;

    public GoodsTypeDiscount() {
        super(TYPE);
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
