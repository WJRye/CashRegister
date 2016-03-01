package com.wj.cashregister;

import android.test.AndroidTestCase;

import com.wj.cashregister.db.GoodsDao;
import com.wj.cashregister.model.Goods;
import com.wj.cashregister.model.type.GoodsTypeDiscount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjiang on 2016/3/1.
 */
public class TestGoodsDao extends AndroidTestCase {

    private GoodsDao mGoodsDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mGoodsDao = GoodsDao.getInstance(getContext());
    }

    public void testInsertGoodses() {
        int size = 3;
        List<Goods> goodses = new ArrayList<>(size);
        Goods goods1 = new Goods();
        goods1.setBarCode("ITEM000001");
        goods1.setName("羽毛球");
        goods1.setUnit("个");
        goods1.setPrice(1.00f);
        goods1.setGoodsType(new GoodsTypeDiscount());
        goodses.add(goods1);
        Goods goods2 = new Goods();
        goods2.setBarCode("ITEM000003");
        goods2.setName("苹果");
        goods2.setUnit("斤");
        goods2.setPrice(5.50f);
        goods2.setGoodsType(new GoodsTypeDiscount());
        goodses.add(goods2);
        Goods goods3 = new Goods();
        goods3.setBarCode("ITEM000005");
        goods3.setName("可口可乐");
        goods3.setUnit("瓶");
        goods3.setPrice(3.00f);
        goods3.setGoodsType(new GoodsTypeDiscount());
        goodses.add(goods3);

    }
}
