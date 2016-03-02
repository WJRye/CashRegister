package com.wj.cashregister;

import android.test.AndroidTestCase;

import com.wj.cashregister.db.GoodsDao;
import com.wj.cashregister.model.Goods;
import com.wj.cashregister.model.type.GoodsType;
import com.wj.cashregister.model.type.GoodsTypeFree;
import com.wj.cashregister.model.type.GoodsTypeNormal;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjiang on 2016/3/1.
 */
public class TestGoodsDao extends AndroidTestCase {
    private List<Goods> mGoodses = new ArrayList<>(3);
    private Goods mBadminton = new Goods();
    private Goods mApple = new Goods();
    private Goods mCocaCala = new Goods();
    private GoodsDao mGoodsDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mGoodsDao = GoodsDao.getInstance(getContext());


        mBadminton.setBarCode("ITEM000001");
        mBadminton.setName("羽毛球");
        mBadminton.setUnit("个");
        mBadminton.setPrice(1.00f);
        mGoodses.add(mBadminton);

        mApple.setBarCode("ITEM000003");
        mApple.setName("苹果");
        mApple.setUnit("斤");
        mApple.setPrice(5.50f);
        mGoodses.add(mApple);

        mCocaCala.setBarCode("ITEM000005");
        mCocaCala.setName("可口可乐");
        mCocaCala.setUnit("瓶");
        mCocaCala.setPrice(3.00f);
        mGoodses.add(mCocaCala);
    }

    public void testInsertFreeGoodses() throws Exception {
        mCocaCala.setGoodsType(new GoodsTypeFree());
        mBadminton.setGoodsType(new GoodsTypeFree());
        mApple.setGoodsType(new GoodsTypeNormal());

        mGoodsDao.insertGoodses(mGoodses);
        Goods goods = mGoodsDao.queryGoodsByBarCode("ITEM000003");
        Assert.assertEquals(GoodsType.TYPE_NORMAL, goods.getGoodsType().getType());

    }

    public void testQueryGoodsByBarCode() {
        Goods badminton = mGoodsDao.queryGoodsByBarCode("ITEM000001");
        Assert.assertEquals(1.00f, badminton.getPrice());
        Goods apple = mGoodsDao.queryGoodsByBarCode("ITEM000003");
        Assert.assertEquals(5.50f, apple.getPrice());
        Goods cocaCala = mGoodsDao.queryGoodsByBarCode("ITEM000005");
        Assert.assertEquals(3.00f, cocaCala.getPrice());
    }

    public void testDeleteDatabase() {
        boolean isDelete = getContext().deleteDatabase("cashregister.db");
        Assert.assertEquals(true, isDelete);
    }
}
