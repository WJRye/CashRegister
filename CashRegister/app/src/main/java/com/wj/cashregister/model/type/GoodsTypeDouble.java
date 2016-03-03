package com.wj.cashregister.model.type;

/**
 * Created by wangjiang on 2016/3/2.
 * <p/>
 * 商品既符合买二赠一也符合95折类型，在这种情况下会将商品设置为买二赠一类型的商品
 */
public class GoodsTypeDouble extends GoodsType {
    private GoodsTypeFree mGoodsTypeFree;

    public GoodsTypeDouble() {
        super(TYPE_DOUBLE);
        mGoodsTypeFree = new GoodsTypeFree();
    }

    public GoodsTypeFree getGoodsTypeFree() {
        return mGoodsTypeFree;
    }
}
