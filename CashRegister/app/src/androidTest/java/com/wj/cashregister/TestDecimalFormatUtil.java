package com.wj.cashregister;

import android.test.AndroidTestCase;

import com.wj.cashregister.utils.DecimalFormatUtil;

import junit.framework.Assert;

/**
 * Created by wangjiang on 2016/3/3.
 * <p/>
 * 该类用于测试小数转化类DecimalFormatUtil
 */
public class TestDecimalFormatUtil extends AndroidTestCase {

    public void testFormat() throws Exception {

        String result = DecimalFormatUtil.format(0.55f);
        Assert.assertEquals("0.55", result);
    }
}
