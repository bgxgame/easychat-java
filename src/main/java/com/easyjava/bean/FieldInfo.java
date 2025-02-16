package com.easyjava.bean;

public class FieldInfo {

    /**
     * 字段名
     */
    private String FieldName;

    /**
     * bean属性名称
     */
    private String propertyName;
    private String sqlType;

    /**
     * 字段类型
     */
    private String javaType;

    /**
     * 字段备注
     */
    private String comment;

    /**
     * 字段是否是自增
     */
    private Boolean isAutoIncrement;

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsAutoIncrement() {
        return isAutoIncrement;
    }

    public void setIsAutoIncrement(Boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "FieldName='" + FieldName + '\'' +
                ", propertyName='" + propertyName + '\'' +
                ", sqlType='" + sqlType + '\'' +
                ", javaType='" + javaType + '\'' +
                ", comment='" + comment + '\'' +
                ", isAutoIncrement='" + isAutoIncrement + '\'' +
                '}';
    }
}
