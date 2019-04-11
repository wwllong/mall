package com.mall.utils;

/**
 * @author Jack Wen
 * @className StringUtils
 * @dsecription 字符串工具
 * @data 2019/4/10
 * @vserion 1.0
 */

public class StringUtils {

    /**
     * @description 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str){
        return str == null || "".equals(str);
    }

}
