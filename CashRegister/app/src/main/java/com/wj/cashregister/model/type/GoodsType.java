package com.wj.cashregister.model.type;

/**
 * Created by wangjiang on 2016/3/1.
 * <p/>
 * 该类用于区分商品的类型（普通，买二赠一，95折，买二赠一和95折）
 */
public abstract class GoodsType {
    //普通
    public static final int TYPE_NORMAL = 0;
    //95折
    public static final int TYPE_DISCOUNT = 1;
    //买二赠一
    public static final int TYPE_FREE = 2;
    //买二赠一和95折
    public static final int TYPE_DOUBLE = 3;
    //商品类型
    private int mType;

    public GoodsType(int type) {
        mType = type;
    }

    /*
    * 返回商品的类型（普通，买二赠一，95折，买二赠一和95折）
    * */
    public int getType() {
        return mType;
    }
}
