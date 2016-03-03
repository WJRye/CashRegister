package com.wj.cashregister.model;

import com.wj.cashregister.model.type.GoodsType;
import com.wj.cashregister.model.type.GoodsTypeDiscount;
import com.wj.cashregister.model.type.GoodsTypeDouble;
import com.wj.cashregister.model.type.GoodsTypeFree;
import com.wj.cashregister.utils.DecimalFormatUtil;

/**
 * Created by wangjiang on 2016/3/1.
 * <p/>
 * 该类定义了商品的数据结构，主要用户封装商品数据
 */
public class Goods {

    //单价字符，用于打印小票用
    private static final String PRICE_STRING = "单价";
    //小计字符，用于打印小票用
    private static final String SUBCOUNT_STRING = "小计";

    //商品名称
    private String name;
    //商品数量
    private int count;
    //商品单位
    private String unit;
    //商品单价
    private float price;
    //商品类型（普通，买二赠一，95折，买二赠一和95折）
    private GoodsType goodsType;
    //商品条形码
    private String barCode;
    //商品小计
    private float subTotal = 0.00f;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /*
    * 计算商品的价格
    * */
    public float getSubTotal() {
        if (Float.compare(subTotal, 0.00f) > 0) return subTotal;

        int type = goodsType.getType();
        if (GoodsType.TYPE_DISCOUNT == type) {
            GoodsTypeDiscount goodsTypeDiscount = (GoodsTypeDiscount) goodsType;
            subTotal = price * goodsTypeDiscount.getRate() * count;
            float discountSavings = price * count - subTotal;
            goodsTypeDiscount.setDiscountSavings(discountSavings);
        } else if (GoodsType.TYPE_FREE == type) {
            GoodsTypeFree goodsTypeFree = (GoodsTypeFree) goodsType;
            if (count >= goodsTypeFree.getRuleMinCount()) {
                goodsTypeFree.setFreeCount(goodsTypeFree.getRuleFreeCount());
                goodsTypeFree.setFreeSavings(goodsTypeFree.getRuleFreeCount() * price);
            }
            //买二赠一的商品，实际价格需要减去优惠商品的价格
            subTotal = price * (count - goodsTypeFree.getFreeCount());
        } else if (GoodsType.TYPE_NORMAL == type) {
            subTotal = price * count;
        } else if (GoodsType.TYPE_DOUBLE == type) {
            GoodsTypeDouble goodsTypeDouble = (GoodsTypeDouble) goodsType;
            GoodsTypeFree goodsTypeFree = goodsTypeDouble.getGoodsTypeFree();
            //计算优惠商品的个数
            int n = count / (goodsTypeFree.getRuleMinCount() + 1);
            goodsTypeFree.setFreeCount(n);
            goodsTypeFree.setFreeSavings(n * price);
            subTotal = price * (count - n);
        }
        return subTotal;
    }


    @Override
    public String toString() {
        //转换为类似名称：苹果，数量：2斤，单价：5.50(元)，小计：10.45(元)，节省0.55(元)这样的结果字符串
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.NAME_STRING);
        sb.append(Constant.COLON);
        sb.append(name);
        sb.append(Constant.COMMA);

        sb.append(Constant.COUNT_STRING);
        sb.append(Constant.COLON);
        sb.append(count + unit);
        sb.append(Constant.COMMA);

        sb.append(PRICE_STRING);
        sb.append(Constant.COLON);
        sb.append(DecimalFormatUtil.format(price) + Constant.YUAN);
        sb.append(Constant.COMMA);

        sb.append(SUBCOUNT_STRING);
        sb.append(Constant.COLON);
        sb.append(DecimalFormatUtil.format(getSubTotal()) + Constant.YUAN);

        int type = goodsType.getType();
        if (GoodsType.TYPE_DISCOUNT == type) {
            GoodsTypeDiscount goodsTypeDiscount = (GoodsTypeDiscount) goodsType;
            if (Float.compare(goodsTypeDiscount.getDiscountSavings(), 0.00f) > 0) {
                sb.append(Constant.COMMA);
                sb.append(Constant.SAVINGS_STRING);
                sb.append(DecimalFormatUtil.format(goodsTypeDiscount.getDiscountSavings()) + Constant.YUAN);
            }
        }

        return sb.toString();
    }
}
