package com.wj.cashregister.utils;

import java.text.DecimalFormat;

/**
 * Created by wangjiang on 2016/3/2.
 */
public class DecimalFormatUtil {

    public static String format(float value) {
        return new DecimalFormat("0.00").format(value);//构造方法的字符格式这里如果小数不足2位,会以0补足, 返回的是字符串
    }
}
