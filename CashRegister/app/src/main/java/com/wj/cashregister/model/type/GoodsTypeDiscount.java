package com.wj.cashregister.model.type;

/**
 * Created by wangjiang on 2016/3/1.
 * <p/>
 * 该类用于处理95折的商品
 */
public class GoodsTypeDiscount extends GoodsType {

    //折扣率
    private final float RATE = 0.95f;
    //折扣节省的钱
    private float discountSavings = 0.00f;

    public GoodsTypeDiscount() {
        super(TYPE_DISCOUNT);
    }

    /*
    * 获得商品的折扣率
    * */
    public float getRate() {
        return RATE;
    }

    /*
    * 获得折扣商品节省的钱
    * */
    public float getDiscountSavings() {
        return discountSavings;
    }

    /*
     * 设置折扣商品节省的钱
     * */
    public void setDiscountSavings(float discountSavings) {
        this.discountSavings = discountSavings;
    }
}
