package com.wj.cashregister.model;

import com.wj.cashregister.model.type.GoodsType;
import com.wj.cashregister.model.type.GoodsTypeDiscount;
import com.wj.cashregister.model.type.GoodsTypeDouble;
import com.wj.cashregister.model.type.GoodsTypeFree;
import com.wj.cashregister.utils.DecimalFormatUtil;

/**
 * Created by wangjiang on 2016/3/1.
 */
public class Goods {

    private static final String PRICE_STRING = "单价";
    private static final String SUBCOUNT_STRING = "小计";


    private String name;
    private int count;
    private String unit;
    private float price;
    private GoodsType goodsType;
    private String barCode;
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
            subTotal = price * (count - goodsTypeFree.getFreeCount());
        } else if (GoodsType.TYPE_NORMAL == type) {
            subTotal = price * count;
        } else if (GoodsType.TYPE_DOUBLE == type) {
            GoodsTypeDouble goodsTypeDouble = (GoodsTypeDouble) goodsType;
            GoodsTypeFree goodsTypeFree = goodsTypeDouble.getGoodsTypeFree();
            int n = count / (goodsTypeFree.getRuleMinCount() + 1);
            goodsTypeFree.setFreeCount(n);
            goodsTypeFree.setFreeSavings(n * price);
            subTotal = price * (count - n);
        }
        return subTotal;
    }


    @Override
    public String toString() {
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
