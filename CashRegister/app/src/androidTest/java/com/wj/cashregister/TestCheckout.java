package com.wj.cashregister;

import android.test.AndroidTestCase;

import com.wj.cashregister.checkout.Checkout;

import junit.framework.Assert;

/**
 * Created by wangjiang on 2016/3/1.
 * <p/>
 * 该类用于测试Checkout类
 */
public class TestCheckout extends AndroidTestCase {
    //换行
    private final char ENTER = '\n';
    //中间分隔符
    private final String SPLIT_CENNTER = "---------------";
    //结尾分隔符
    private final String SPLIT_END = "***************";
    //传入后台的JSON数据
    private final String JSON = "[" +
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
    //结账类
    private Checkout mCheckout;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mCheckout = new Checkout(getContext());
    }

    /*
    *  测试JSON数据出错或者JSON数据不符合要求时的返回结果
    * */
    public void testOutput() throws Exception {
        mCheckout.input(JSON);
        String actualResult = mCheckout.output();
        String expectedResult = "***<没钱赚商店>购物清单***";
        Assert.assertEquals(expectedResult, actualResult);
    }

    /*
    * 测试当购买的商品中，有符合“买二赠一”优惠条件的商品时的返回结果
    * */
    public void testOutputFreeGoodses() throws Exception {
        mCheckout.input(JSON);
        StringBuilder expectedResult = new StringBuilder();
        expectedResult.append("***<没钱赚商店>购物清单***");
        expectedResult.append(ENTER);
        expectedResult.append("名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：6.00(元)");
        expectedResult.append(ENTER);
        expectedResult.append("名称：羽毛球，数量：5个，单价：1.00(元)，小计：4.00(元)");
        expectedResult.append(ENTER);
        expectedResult.append("名称：苹果，数量：2斤，单价：5.50(元)，小计：11.00(元)");
        expectedResult.append(ENTER);
        expectedResult.append(SPLIT_CENNTER);
        expectedResult.append(ENTER);
        expectedResult.append("买二赠一商品：");
        expectedResult.append(ENTER);
        expectedResult.append("名称：可口可乐，数量：1瓶");
        expectedResult.append(ENTER);
        expectedResult.append("名称：羽毛球，数量：1个");
        expectedResult.append(ENTER);
        expectedResult.append(SPLIT_CENNTER);
        expectedResult.append(ENTER);
        expectedResult.append("总计：21.00(元)");
        expectedResult.append(ENTER);
        expectedResult.append("节省：4.00(元)");
        expectedResult.append(ENTER);
        expectedResult.append(SPLIT_END);

        String actualResult = mCheckout.output();
        Assert.assertEquals(expectedResult.toString(), actualResult);

    }

    /*
      * 测试当购买的商品中，没有符合“买二赠一”优惠条件的商品时的返回结果
      * */
    public void testOutputNoFreeGoodses() throws Exception {
        mCheckout.input(JSON);
        StringBuilder expectedResult = new StringBuilder();
        expectedResult.append("***<没钱赚商店>购物清单***");
        expectedResult.append(ENTER);
        expectedResult.append("名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：9.00(元)");
        expectedResult.append(ENTER);
        expectedResult.append("名称：羽毛球，数量：5个，单价：1.00(元)，小计：5.00(元)");
        expectedResult.append(ENTER);
        expectedResult.append("名称：苹果，数量：2斤，单价：5.50(元)，小计：11.00(元)");
        expectedResult.append(ENTER);
        expectedResult.append(SPLIT_CENNTER);
        expectedResult.append(ENTER);
        expectedResult.append("总计：25.00(元)");
        expectedResult.append(ENTER);
        expectedResult.append(SPLIT_END);

        String actualResult = mCheckout.output();
        Assert.assertEquals(expectedResult.toString(), actualResult);

    }

    /*
     * 测试当购买的商品中，有符合“95折”优惠条件的商品时的返回结果
     * */
    public void testOutputDiscountGoodses() throws Exception {
        mCheckout.input(JSON);
        StringBuilder expectedResult = new StringBuilder();
        expectedResult.append("***<没钱赚商店>购物清单***");
        expectedResult.append(ENTER);
        expectedResult.append("名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：9.00(元)");
        expectedResult.append(ENTER);
        expectedResult.append("名称：羽毛球，数量：5个，单价：1.00(元)，小计：5.00(元)");
        expectedResult.append(ENTER);
        expectedResult.append("名称：苹果，数量：2斤，单价：5.50(元)，小计：10.45(元)，节省0.55(元)");
        expectedResult.append(ENTER);
        expectedResult.append(SPLIT_CENNTER);
        expectedResult.append(ENTER);
        expectedResult.append("总计：24.45(元)");
        expectedResult.append(ENTER);
        expectedResult.append("节省：0.55(元)");
        expectedResult.append(ENTER);
        expectedResult.append(SPLIT_END);

        String actualResult = mCheckout.output();
        Assert.assertEquals(expectedResult.toString(), actualResult);

    }

    /*
    * 测试当购买的商品中，有符合“95折”优惠条件的商品，又有符合“买二赠一”优惠条件的商品时的返回结果
    * */
    public void testOutputFreeAndDiscountGoodses() throws Exception {
        String json = "[" +
                "'ITEM000001'," +
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
        mCheckout.input(json);
        StringBuilder expectedResult = new StringBuilder();
        expectedResult.append("***<没钱赚商店>购物清单***");
        expectedResult.append(ENTER);
        expectedResult.append("名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：6.00(元)");
        expectedResult.append(ENTER);
        expectedResult.append("名称：羽毛球，数量：6个，单价：1.00(元)，小计：4.00(元)");
        expectedResult.append(ENTER);
        expectedResult.append("名称：苹果，数量：2斤，单价：5.50(元)，小计：10.45(元)，节省0.55(元)");
        expectedResult.append(ENTER);
        expectedResult.append(SPLIT_CENNTER);
        expectedResult.append(ENTER);
        expectedResult.append("买二赠一商品：");
        expectedResult.append(ENTER);
        expectedResult.append("名称：可口可乐，数量：1瓶");
        expectedResult.append(ENTER);
        expectedResult.append("名称：羽毛球，数量：2个");
        expectedResult.append(ENTER);
        expectedResult.append(SPLIT_CENNTER);
        expectedResult.append(ENTER);
        expectedResult.append("总计：20.45(元)");
        expectedResult.append(ENTER);
        expectedResult.append("节省：5.55(元)");
        expectedResult.append(ENTER);
        expectedResult.append(SPLIT_END);

        String actualResult = mCheckout.output();
        Assert.assertEquals(expectedResult.toString(), actualResult);

    }
}
