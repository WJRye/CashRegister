package com.wj.cashregister;

import android.content.Context;

import com.wj.cashregister.db.GoodsDao;
import com.wj.cashregister.model.Goods;
import com.wj.cashregister.model.type.GoodsType;
import com.wj.cashregister.model.type.GoodsTypeFree;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangjiang on 2016/3/1.
 */
public class Checkout {
    private final String STORE_NAME = "***<没钱赚商店>购物清单***";
    private final char ENTER = '\n';
    private final String SPLIT_CENNTER = "---------------";
    private final String SPLIT_END = "***************";
    private Context mContext;
    private Set<Goods> mTotalGoodses = new LinkedHashSet<>();

    public Checkout(Context context) {
        mContext = context;
    }


    public void input(String json) throws Exception {
        if (json == null || "".equals(json.trim()))
            throw new IllegalArgumentException("JSON is null or empty!");
        Map<String, Integer> results = parse(json);
        GoodsDao goodsDao = GoodsDao.getInstance(mContext);
        Iterator iterator = results.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iterator.next();
            String barCode = entry.getKey();
            Goods goods = goodsDao.queryGoodsByBarCode(barCode);
            goods.setCount(entry.getValue());
            mTotalGoodses.add(goods);
            iterator.remove();
        }
    }

    private Map<String, Integer> parse(String json) throws IOException, JSONException {
        Map<String, Integer> results = new HashMap<>();
        org.json.JSONArray jsonArray = new JSONArray(json);
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            String value = jsonArray.getString(i);
            String[] values = value.split("-");
            int number = 0;
            if (values.length > 1) {
                number = Integer.parseInt(values[1]);
            }
            String barCode = values[0];
            Integer count = results.get(barCode);
            if (count == null) {
                count = new Integer(0);
            }
            if (number != 0) {
                count += number;
            } else {
                count++;
            }
            results.put(barCode, count);
        }
        return results;
    }

    public String output() {
        StringBuilder results = new StringBuilder();
        results.append(STORE_NAME);
        results.append(getGoodsList());
        results.append(getDiscountGoodsList());
//        results.append(getFreeGoodsList());
        results.append(getTotal());
        return results.toString();
    }

    private String getTotal() {
        StringBuilder result = new StringBuilder();
        if (!mTotalGoodses.isEmpty()) {
            result.append(ENTER);
            //总计
            result.append(SPLIT_END);
        }
        return result.toString();
    }

    private String getFreeGoodsList() {
        StringBuilder result = new StringBuilder();
        if (!mTotalGoodses.isEmpty()) {
            result.append(SPLIT_CENNTER);
            result.append(ENTER);
            //判断买二送一商品
            result.append(ENTER);
        }
        return result.toString();
    }

    private String getDiscountGoodsList() {
        StringBuilder result = new StringBuilder();
        if (!mTotalGoodses.isEmpty()) {
            result.append(SPLIT_CENNTER);
            result.append(ENTER);
            result.append("买二赠一商品：");
            result.append(ENTER);
            //判断折扣商品
            for (Goods goods : mTotalGoodses) {
                GoodsType goodsType = goods.getGoodsType();
                if (goodsType.getType() == GoodsType.TYPE_FREE) {
                    GoodsTypeFree goodsTypeFree = (GoodsTypeFree) goodsType;
                    result.append(Goods.NAME_STRING);
                    result.append(Goods.COLON);
                    result.append(goods.getName());
                    result.append(Goods.COMMA);
                    result.append(Goods.COUNT_STRING);
                    result.append(Goods.COLON);
                    result.append(goodsTypeFree.getFreeCount());
                    result.append(goods.getUnit());
                    result.append(ENTER);
                }
            }
        }
        return result.toString();
    }

    private String getGoodsList() {
        StringBuilder result = new StringBuilder();
        if (!mTotalGoodses.isEmpty()) {
            result.append(ENTER);
            for (Goods goods : mTotalGoodses) {
                result.append(goods.toString());
                result.append(ENTER);
            }
        }
        return result.toString();
    }

    public Map<String, Integer> getParseResult(String json) throws Exception {
        return parse(json);
    }
}
