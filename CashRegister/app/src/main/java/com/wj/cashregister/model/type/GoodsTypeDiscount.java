package com.wj.cashregister.model.type;

/**
 * Created by wangjiang on 2016/3/1.
 */
public class GoodsTypeDiscount extends GoodsType {

    private final float RATE = 0.95f;
    private float discountSavings = 0.00f;

    public GoodsTypeDiscount() {
        super(TYPE_DISCOUNT);
    }

    public float getRate() {
        return RATE;
    }

    public float getDiscountSavings() {
        return discountSavings;
    }

    public void setDiscountSavings(float discountSavings) {
        this.discountSavings = discountSavings;
    }
}
