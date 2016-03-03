package com.wj.cashregister;

import android.test.AndroidTestCase;

import com.wj.cashregister.db.GoodsDao;
import com.wj.cashregister.model.Goods;
import com.wj.cashregister.model.type.GoodsType;
import com.wj.cashregister.model.type.GoodsTypeDiscount;
import com.wj.cashregister.model.type.GoodsTypeDouble;
import com.wj.cashregister.model.type.GoodsTypeFree;
import com.wj.cashregister.model.type.GoodsTypeNormal;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjiang on 2016/3/1.
 * <p/>
 * 该类主要用于测试类GoodsDao中的方法，以及在测试中根据购买的商品改变时，做一些数据的更改。
 */
public class TestGoodsDao extends AndroidTestCase {
    private final String BARCODE_BADMINTON = "ITEM000001";
    private final String BARCODE_APPLE = "ITEM000003";
    private final String BARCODE_COCACALA = "ITEM000005";
    private List<Goods> mGoodses = new ArrayList<>(3);
    private Goods mBadminton = new Goods();
    private Goods mApple = new Goods();
    private Goods mCocaCala = new Goods();
    private GoodsDao mGoodsDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mGoodsDao = GoodsDao.getInstance(getContext());
    }

    /*
    * 向数据库中插入测试的数据
    * */
    public void testInsertGoodses() throws Exception {
        mBadminton.setBarCode(BARCODE_BADMINTON);
        mBadminton.setName("羽毛球");
        mBadminton.setUnit("个");
        mBadminton.setPrice(1.00f);
        mGoodses.add(mBadminton);

        mApple.setBarCode(BARCODE_APPLE);
        mApple.setName("苹果");
        mApple.setUnit("斤");
        mApple.setPrice(5.50f);
        mGoodses.add(mApple);

        mCocaCala.setBarCode(BARCODE_COCACALA);
        mCocaCala.setName("可口可乐");
        mCocaCala.setUnit("瓶");
        mCocaCala.setPrice(3.00f);
        mGoodses.add(mCocaCala);

        mGoodsDao.insertGoodses(mGoodses);

        Goods goods = mGoodsDao.queryGoodsByBarCode(BARCODE_APPLE);
        Assert.assertEquals(GoodsType.TYPE_NORMAL, goods.getGoodsType().getType());
    }

    /*
    * 插入当购买的商品中，有符合“买二赠一”优惠条件的商品时的测试数据
    * */
    public void testFreeGoodses() throws Exception {
        Map<String, GoodsType> content = new HashMap<>();
        content.put(BARCODE_BADMINTON, new GoodsTypeFree());
        content.put(BARCODE_COCACALA, new GoodsTypeFree());
        content.put(BARCODE_APPLE, new GoodsTypeNormal());
        testUpdate(content);
    }

    /*
    * 插入当购买的商品中，没有符合“买二赠一”优惠条件的商品时的测试数据
    * */
    public void testNoFreeGoodses() throws Exception {
        Map<String, GoodsType> content = new HashMap<>();
        content.put(BARCODE_BADMINTON, new GoodsTypeNormal());
        content.put(BARCODE_COCACALA, new GoodsTypeNormal());
        content.put(BARCODE_APPLE, new GoodsTypeNormal());
        testUpdate(content);
    }

    /*
    * 插入当购买的商品中，有符合“95折”优惠条件的商品时的测试数据
    * */
    public void testDiscountGoodses() throws Exception {
        Map<String, GoodsType> content = new HashMap<>();
        content.put(BARCODE_BADMINTON, new GoodsTypeNormal());
        content.put(BARCODE_COCACALA, new GoodsTypeNormal());
        content.put(BARCODE_APPLE, new GoodsTypeDiscount());
        testUpdate(content);
    }

    /*
       * 插入当购买的商品中，有符合“95折”优惠条件的商品，又有符合“买二赠一”优惠条件的商品时的测试数据
       * */
    public void testFreeAndDiscountGoodses() throws Exception {
        Map<String, GoodsType> content = new HashMap<>();
        content.put(BARCODE_BADMINTON, new GoodsTypeDouble());
        content.put(BARCODE_COCACALA, new GoodsTypeFree());
        content.put(BARCODE_APPLE, new GoodsTypeDiscount());
        testUpdate(content);
    }

    /*
    *
    * 测试GoodsDao类中update()方法
    * */
    public void testUpdate(Map<String, GoodsType> content) {
        Iterator iterator = content.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, GoodsType> entry = (Map.Entry<String, GoodsType>) iterator.next();
            mGoodsDao.update(entry.getKey(), entry.getValue());
            Goods goods = mGoodsDao.queryGoodsByBarCode(entry.getKey());
            Assert.assertEquals(entry.getValue().getType(), goods.getGoodsType().getType());
            iterator.remove();
        }

    }

    /*
    *
    * 测试GoodsDao类中QueryGoodsByBarCode()方法
    * */
    public void testQueryGoodsByBarCode() {
        Goods badminton = mGoodsDao.queryGoodsByBarCode(BARCODE_BADMINTON);
        Assert.assertEquals(1.00f, badminton.getPrice());

        Goods apple = mGoodsDao.queryGoodsByBarCode(BARCODE_APPLE);
        Assert.assertEquals(5.50f, apple.getPrice());

        Goods cocaCala = mGoodsDao.queryGoodsByBarCode(BARCODE_COCACALA);
        Assert.assertEquals(3.00f, cocaCala.getPrice());

    }

    /*
    *  删除数据库，只有测试时使用
    * */
    public void testDeleteDatabase() {
        boolean isDelete = getContext().deleteDatabase("cashregister.db");
        Assert.assertEquals(true, isDelete);
    }
}
