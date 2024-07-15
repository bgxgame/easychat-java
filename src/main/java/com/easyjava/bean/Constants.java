package com.easyjava.bean;

import com.easyjava.utils.PropertiesUtils;

public class Constants {

    /**
     * 是否忽略表前缀
     */
    public static Boolean IGNORE_TABLE_PREFIX;

    /**
     * 参数bean后缀
     */
    public static String SUFFIX_BEAN_PARAM;

    static {
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        SUFFIX_BEAN_PARAM = PropertiesUtils.getString("suffix.bean.param");
    }
}
