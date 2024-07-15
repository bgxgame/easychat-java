package com.easyjava.bean;

import com.easyjava.utils.PropertiesUtils;

public class Constants {

    /**
     * 是否忽略表前缀
     */
    public static Boolean IGNORE_TABLE_PREFIX;

    static {
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
    }
}
