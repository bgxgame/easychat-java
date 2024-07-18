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
    // 物料输出路径
    public static String PATH_BASE;
    public static String PATH_PO;

    // 物料输出父包名
    public static String PACKAGE_BASE;

    // PO包名 entity.po
    public static String PACKAGE_PO;

    // entity.query
    public static String PACKAGE_PARAM;

    public static String AUTHOR_COMMENT;


    // 包路径补充
    private static String PATH_JAVA = "java";

    // 资源路径补充
    private static String PATH_RESOURCE = "resource";

    static {
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        SUFFIX_BEAN_PARAM = PropertiesUtils.getString("suffix.bean.param");

        AUTHOR_COMMENT =  PropertiesUtils.getString("author.comment");

        // com.easyjava
        PACKAGE_BASE = PropertiesUtils.getString("package.base");

        // C:/Users/brace/easyjavas/easyjava-demo/src/main/java/com/easyjava
        PATH_BASE = (PropertiesUtils.getString("path.base") + PATH_JAVA + "/" + PACKAGE_BASE).replace(".", "/");

        // C:/Users/brace/easyjavas/easyjava-demo/src/main/java/com/easyjava/entity/po
        PATH_PO = PATH_BASE + "/" + PropertiesUtils.getString("package.po").replace(".", "/");

        // com.easyjava.entity.po
        // 设置包名
        PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");

    }

    public static void main(String[] args) {
        System.out.println(PACKAGE_PARAM);
    }
}
