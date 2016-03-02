package com.wj.cashregister.model.type;

/**
 * Created by wangjiang on 2016/3/2.
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
