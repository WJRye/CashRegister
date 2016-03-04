package com.wj.cashregister.checkout;

import android.content.Context;

import com.wj.cashregister.db.GoodsDao;
import com.wj.cashregister.model.Constant;
import com.wj.cashregister.model.Goods;
import com.wj.cashregister.model.type.GoodsType;
import com.wj.cashregister.model.type.GoodsTypeDiscount;
import com.wj.cashregister.model.type.GoodsTypeDouble;
import com.wj.cashregister.model.type.GoodsTypeFree;
import com.wj.cashregister.utils.DecimalFormatUtil;

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
 * <p/>
 * 该类将解析输入的JSON数据，并返回商品的计算结果
 */
public class Checkout {
    //商店名字
    private final String STORE_NAME = "***<没钱赚商店>购物清单***";
    //换行
    private final char ENTER = '\n';
    //中间分隔符
    private final String SPLIT_CENNTER = "---------------";
    //结尾分隔符
    private final String SPLIT_END = "***************";
    //字符买二赠一商品，用于打印小票用
    private final String FREE_GOODS = "买二赠一商品";
    //字符总计，用于打印小票用
    private final String TOTAL = "总计";
    //上下文对象
    private Context mContext;
    //存储所有的商品
    private Set<Goods> mTotalGoodses = new LinkedHashSet<>();
    //存储“买二赠一”的商品
    private Set<Goods> mFreeGoodses = new LinkedHashSet<>();

    public Checkout(Context context) {
        mContext = context;
    }

    /*
    *  输入JSON数据，并对商品信息进行封装
    * */
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
            //添加商品
            mTotalGoodses.add(goods);
            iterator.remove();
        }
    }

    /*
    * 解析JSON数据，并返回解析后的条形码和商品个数
    * */
    private Map<String, Integer> parse(String json) throws IOException, JSONException {
        Map<String, Integer> results = new HashMap<>();
        org.json.JSONArray jsonArray = new JSONArray(json);
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            String value = jsonArray.getString(i);
            String[] values = value.split(Constant.JSON_SPLIT_STRING);
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

    /*
    * 输出最后的结果
    * */
    public String output() {
        StringBuilder results = new StringBuilder();
        results.append(STORE_NAME);
        results.append(getGoodsList());
        results.append(getFreeGoodsList());
        results.append(getTotal());
        return results.toString();
    }

    /*
    * 计算总计和总的节省，并返回最后结果字符
    * */
    private String getTotal() {
        StringBuilder result = new StringBuilder();
        if (!mTotalGoodses.isEmpty()) {
            result.append(SPLIT_CENNTER);
            result.append(ENTER);
            result.append(TOTAL);
            result.append(Constant.COLON);
            float sumTotal = 0.00f;
            float sumSavings = 0.00f;
            for (Goods goods : mTotalGoodses) {
                sumTotal += goods.getSubTotal();
                GoodsType goodsType = goods.getGoodsType();
                if (goodsType.getType() == GoodsType.TYPE_FREE) {
                    GoodsTypeFree goodsTypeFree = (GoodsTypeFree) goodsType;
                    sumSavings += goodsTypeFree.getFreeSavings();
                } else if (goods.getGoodsType().getType() == GoodsType.TYPE_DISCOUNT) {
                    GoodsTypeDiscount goodsTypeFree = (GoodsTypeDiscount) goodsType;
                    sumSavings += goodsTypeFree.getDiscountSavings();
                } else if (goods.getGoodsType().getType() == GoodsType.TYPE_DOUBLE) {
                    GoodsTypeDouble goodsTypeDouble = (GoodsTypeDouble) goodsType;
                    GoodsTypeFree goodsTypeFree = goodsTypeDouble.getGoodsTypeFree();
                    sumSavings += goodsTypeFree.getFreeSavings();
                }
            }
            result.append(DecimalFormatUtil.format(sumTotal));
            result.append(Constant.YUAN);
            result.append(ENTER);
            if (Float.compare(sumSavings, 0.00f) > 0) {
                result.append(Constant.SAVINGS_STRING);
                result.append(Constant.COLON);
                result.append(DecimalFormatUtil.format(sumSavings));
                result.append(Constant.YUAN);
                result.append(ENTER);
            }
            result.append(SPLIT_END);
        }
        return result.toString();
    }

    /*
    * 获取“买二赠一商品”的列表
    * */
    private String getFreeGoodsList() {
        StringBuilder result = new StringBuilder();
        if (!mFreeGoodses.isEmpty()) {
            result.append(SPLIT_CENNTER);
            result.append(ENTER);
            result.append(FREE_GOODS);
            result.append(Constant.COLON);
            result.append(ENTER);
            //判断折扣商品
            for (Goods goods : mFreeGoodses) {
                GoodsType goodsType = goods.getGoodsType();
                int type = goodsType.getType();
                GoodsTypeFree goodsTypeFree = null;
                if (type == GoodsType.TYPE_FREE) {
                    goodsTypeFree = (GoodsTypeFree) goodsType;
                } else if (type == GoodsType.TYPE_DOUBLE) {
                    goodsTypeFree = ((GoodsTypeDouble) goodsType).getGoodsTypeFree();
                }
                result.append(Constant.NAME_STRING);
                result.append(Constant.COLON);
                result.append(goods.getName());
                result.append(Constant.COMMA);
                result.append(Constant.COUNT_STRING);
                result.append(Constant.COLON);
                result.append(goodsTypeFree.getFreeCount());
                result.append(goods.getUnit());
                result.append(ENTER);
            }
        }
        return result.toString();
    }

    /*
    * 获取商品列表
    * */
    private String getGoodsList() {
        StringBuilder result = new StringBuilder();
        if (!mTotalGoodses.isEmpty()) {
            result.append(ENTER);
            for (Goods goods : mTotalGoodses) {
                int type = goods.getGoodsType().getType();
                if (type == GoodsType.TYPE_FREE || type == GoodsType.TYPE_DOUBLE) {
                    //存储“买二赠一”的商品
                    mFreeGoodses.add(goods);
                }
                result.append(goods.toString());
                result.append(ENTER);
            }
        }
        return result.toString();
    }

    //记住删掉
    public Map<String, Integer> getParseResult(String json) throws Exception {
        return parse(json);
    }
}
