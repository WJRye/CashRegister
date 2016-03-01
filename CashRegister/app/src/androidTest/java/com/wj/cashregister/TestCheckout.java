package com.wj.cashregister;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import java.util.Map;

/**
 * Created by wangjiang on 2016/3/1.
 */
public class TestCheckout extends AndroidTestCase {
    private Checkout mCheckout;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mCheckout = new Checkout();
    }

    public void testParse() {
        String json = "[" +
                "'ITEM000001'," +
                "'ITEM000001'," +
                "'ITEM000001'," +
                "'ITEM000001'," +
                "'ITEM000001'," +
                "'ITEM000003-2'," +
                "'ITEM000005'," +
                "'ITEM000005'," +
                "'ITEM000005'" +
                "]";

        try {
            Map<String, Integer> results = mCheckout.getParseResult(json);
            Assert.assertEquals(5, results.get("ITEM000001").intValue());
            Assert.assertEquals(2, results.get("ITEM000003").intValue());
            Assert.assertEquals(3, results.get("ITEM000005").intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
