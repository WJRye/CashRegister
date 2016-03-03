package com.wj.cashregister.utils;

import java.text.DecimalFormat;

/**
 * Created by wangjiang on 2016/3/2.
 * <p/>
 * 将float数据保留两位小数的工具类
 */
public class DecimalFormatUtil {
    /*
    *  如果小数不足2位,会以0补足, 返回的是字符串
    * */
    public static String format(float value) {
        return new DecimalFormat("0.00").format(value);
    }
}
