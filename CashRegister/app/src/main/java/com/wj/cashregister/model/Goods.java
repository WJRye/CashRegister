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
    public static final String NAME_STRING = "名称";
    public static final String COUNT_STRING = "数量";
    public static final String PRICE_STRING = "单价";
    public static final String YUAN = "(元)";
    public static final String SUBCOUNT_STRING = "小计";
    public static final String SAVINGS_STRING = "节省";
    public static final char COLON = '：';
    public static final char COMMA = '，';

    private String name;
    private int count;
    private String unit;
    private float price;
    private GoodsType goodsType;
    private String barCode;
    private float savings = 0.00f;

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
        int type = goodsType.getType();
        float subTotal = 0.00f;
        if (GoodsType.TYPE_DISCOUNT == type) {
            GoodsTypeDiscount goodsTypeDiscount = (GoodsTypeDiscount) goodsType;
            subTotal = price * goodsTypeDiscount.getRate() * count;
            savings = price * count - subTotal;
        } else if (GoodsType.TYPE_FREE == type) {
            GoodsTypeFree goodsTypeFree = (GoodsTypeFree) goodsType;
            if (count >= goodsTypeFree.getRuleMinCount()) {
                goodsTypeFree.setFreeCount(1);
            }
            subTotal = price * (count - goodsTypeFree.getFreeCount());
        } else if (GoodsType.TYPE_NORMAL == type) {
            subTotal = price * count;
        } else if (GoodsType.TYPE_DOUBLE == type) {
            GoodsTypeDouble goodsTypeDouble = (GoodsTypeDouble) goodsType;
            GoodsTypeFree goodsTypeFree = goodsTypeDouble.getGoodsTypeFree();
            int n = count % (goodsTypeFree.getRuleMinCount() + 1);
            goodsTypeFree.setFreeCount(n);
            subTotal = price * (count - n);
        }
        return subTotal;
    }

    public float getSavings() {
        return savings;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NAME_STRING);
        sb.append(COLON);
        sb.append(name);
        sb.append(COMMA);

        sb.append(COUNT_STRING);
        sb.append(COLON);
        sb.append(count + unit);
        sb.append(COMMA);

        sb.append(PRICE_STRING);
        sb.append(COLON);
        sb.append(DecimalFormatUtil.format(price) + YUAN);
        sb.append(COMMA);

        sb.append(SUBCOUNT_STRING);
        sb.append(COLON);
        sb.append(DecimalFormatUtil.format(getSubTotal()) + YUAN);

        if (Float.compare(savings, 0.00f) > 0) {
            sb.append(COMMA);
            sb.append(SAVINGS_STRING);
            sb.append(DecimalFormatUtil.format(savings) + YUAN);
        }

        return sb.toString();
    }
}
