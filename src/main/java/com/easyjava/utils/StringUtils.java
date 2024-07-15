package com.easyjava.utils;

public class StringUtils {

    /**
     * 第一个字母转为大写
     * @param field
     * @return
     */
    public static String upperCaseFirstLetter(String field) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(field)) {
            return field;
        }
        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    /**
     * 第一个字母转为小写
     * @param field
     * @return
     */
    public static String lowerCaseFirstLetter(String field) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(field)) {
            return field;
        }
        return field.substring(0, 1).toLowerCase() + field.substring(1);
    }

    public static void main(String[] args) {
        System.out.println(upperCaseFirstLetter("company"));
        System.out.println(lowerCaseFirstLetter("COMPANY"));
    }
}
