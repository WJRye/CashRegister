package com.wj.cashregister;

import com.wj.cashregister.model.Goods;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjiang on 2016/3/1.
 */
public class Checkout {
    private final String STORE_NAME = "***<没钱赚商店>购物清单***";

    private Map<String, List<Goods>> totalGoodses = new LinkedHashMap<>();

    public void addGoods(String barCode, Goods goodses) {
        List<Goods> goodsList = totalGoodses.get(barCode);
        if (goodsList == null) {
            goodsList = new ArrayList<>();
        }
        goodsList.add(goodses);
        totalGoodses.put(barCode, goodsList);
    }

    private void printGoodsList() {

    }

    public void input(String json) throws Exception {
        Map<String, Integer> results = parse(json);
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

    public void output() {

    }

    public Map<String, Integer> getParseResult(String json) throws Exception {
        return parse(json);
    }
}
