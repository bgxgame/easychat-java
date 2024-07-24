package com.easyjava.bean;

import com.easyjava.utils.PropertiesUtils;

public class Constants {

    // 时间类型
    public final static String[] SQL_DATE_TIME_TYPES = new String[]{"datetime", "timestamp"};

    // 日期类型
    public final static String[] SQL_DATE_TYPES = new String[]{"date"};
    public final static String[] SQL_DECIMAL_TYPE = new String[]{"decimal", "double", "float"};
    public final static String[] SQL_STRING_TYPE = new String[]{"char", "varchar", "text", "mediumtext", "longtext"};
    public final static String[] SQL_INTEGER_TYPE = new String[]{"int", "tinyint"};
    public final static String[] SQL_LONG_TYPE = new String[]{"bigint"};
    // 是否忽略表前缀
    public static Boolean IGNORE_TABLE_PREFIX;
    // 参数bean后缀
    public static String SUFFIX_BEAN_PARAM;

    // 需要忽略的属性
    public static String IGNORE_BEAN_TOJSON_FILED;
    public static String IGNORE_BEAN_TOJSON_EXPRESSION;
    public static String IGNORE_BEAN_TOJSON_CLASS;

    // 日期序列化、反序列化
    public static String BEAN_DATE_FORMAT_EXPRESSION;
    public static String BEAN_DATE_FORMAT_CLASS;
    public static String BEAN_DATE_UNFORMAT_EXPRESSION;
    public static String BEAN_DATE_UNFORMAT_CLASS;
    public static String AUTHOR_COMMENT;

    // 物料输出父包名
    public static String PACKAGE_BASE;

    // PO包名 entity.po
    public static String PACKAGE_PO;
    public static String PACKAGE_UTILS;
    public static String PACKAGE_ENUMS;

    // entity.query
    public static String PACKAGE_PARAM;

    // 物料输出路径
    public static String PATH_BASE;
    public static String PATH_PO;
    public static String PATH_UTILS;
    public static String PATH_ENUMS;
    // 包路径补充
    private static String PATH_JAVA = "java";
    // 资源路径补充
    private static String PATH_RESOURCE = "resource";

    static {
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        SUFFIX_BEAN_PARAM = PropertiesUtils.getString("suffix.bean.param");

        IGNORE_BEAN_TOJSON_FILED = PropertiesUtils.getString("ignore.bean.tojson.filed");

        IGNORE_BEAN_TOJSON_EXPRESSION = PropertiesUtils.getString("ignore.bean.tojson.expression");
        IGNORE_BEAN_TOJSON_CLASS = PropertiesUtils.getString("ignore.bean.tojson.class");

        BEAN_DATE_FORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.format.expression");
        BEAN_DATE_FORMAT_CLASS = PropertiesUtils.getString("bean.date.format.class");
        BEAN_DATE_UNFORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.unformat.expression");
        BEAN_DATE_UNFORMAT_CLASS = PropertiesUtils.getString("bean.date.unformat.class");

        AUTHOR_COMMENT = PropertiesUtils.getString("author.comment");

        // 基本包路径 com.easyjava
        PACKAGE_BASE = PropertiesUtils.getString("package.base");

        // com.easyjava.entity.po
        // 设置po的包路径
        PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");

        PACKAGE_UTILS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.utils");
        PACKAGE_ENUMS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.enums");


        // C:/Users/brace/easyjavas/easyjava-demo/src/main/java
        PATH_BASE = PropertiesUtils.getString("path.base") + PATH_JAVA;

        // C:/Users/brace/easyjavas/easyjava-demo/src/main/java/com/easyjava/entity/po
        PATH_PO = PATH_BASE + "/" + PACKAGE_PO.replace(".", "/");

        PATH_UTILS = PATH_BASE + "/" + PACKAGE_UTILS.replace(".", "/");
        PATH_ENUMS = PATH_BASE + "/" + PACKAGE_ENUMS.replace(".", "/");
    }

    public static void main(String[] args) {
        System.out.println(PACKAGE_PARAM);
    }
}
