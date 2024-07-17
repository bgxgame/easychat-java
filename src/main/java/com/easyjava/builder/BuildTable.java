package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.JsonUtils;
import com.easyjava.utils.PropertiesUtils;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildTable {
    private static final Logger logger = LoggerFactory.getLogger(BuildTable.class);
    private static Connection conn = null;
    private static String SQL_SHOW_TABLE_STATUS = "show table status";
    private static String SQL_SHOW_TABLE_FIELDS = "show full fields from %s";
    private static String SQL_SHOW_TABLE_INDEX = "show index from %s";

    static {
        String driverName = PropertiesUtils.getString("db.driver-class-name");
        String url = PropertiesUtils.getString("db.url");
        String username = PropertiesUtils.getString("db.username");
        String password = PropertiesUtils.getString("db.password");

        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            logger.error("数据库连接失败", e);
        }
    }

    /**
     * 得到表基本信息
     */
    public static List<TableInfo> getTables() {
        PreparedStatement ps = null;
        ResultSet tableResult = null;

        List<TableInfo> tableInfoList = new ArrayList();

        try {
            ps = conn.prepareStatement(SQL_SHOW_TABLE_STATUS);
            tableResult = ps.executeQuery();
            while (tableResult.next()) {
                String tableName = tableResult.getString("name");
                String comment = tableResult.getString("comment");
                // logger.info("tableName:{},comment:{}", tableName, comment);
                String beanName = tableName;
                // 如果忽略表前缀，就只取后半部分表名
                if (Constants.IGNORE_TABLE_PREFIX) {
                    beanName = tableName.substring(beanName.indexOf("_") + 1);
                }
                beanName = processField(beanName, true);

                TableInfo tableInfo = new TableInfo();
                // 表基本信息
                tableInfo.setTableName(tableName);
                tableInfo.setBeanName(beanName);
                tableInfo.setComment(comment);
                tableInfo.setBeanParamName(beanName + Constants.SUFFIX_BEAN_PARAM);

                // 得到表字段信息清单
                readFieldInfo(tableInfo);

                // 添加表索引集合
                getKeyIndexInfo(tableInfo);

                // 添加表详细信息到清单
                tableInfoList.add(tableInfo);

                // logger.info(JsonUtils.convertObj2Json(tableInfo));
            }

        } catch (Exception e) {
            logger.error("读取表失败", e);
        } finally {
            if (tableResult != null) {
                try {
                    tableResult.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return tableInfoList;
    }

    /**
     * 得到表详细信息
     *
     * @param tableInfo
     * @return
     */
    private static void readFieldInfo(TableInfo tableInfo) {
        PreparedStatement ps = null;
        ResultSet fieldResult = null;

        List<FieldInfo> fieldInfoList = new ArrayList();

        try {
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_FIELDS, tableInfo.getTableName()));
            fieldResult = ps.executeQuery();
            while (fieldResult.next()) {
                String fieldName = fieldResult.getString("field");
                // po propertyName 首字母小写
                String propertyName = processField(fieldName, false);
                String sqlType = fieldResult.getString("type");
                String comment = fieldResult.getString("comment");
                String isAutoIncrement = fieldResult.getString("extra");

                if (sqlType.indexOf("(") > 0) {
                    sqlType = sqlType.substring(0, sqlType.indexOf("("));
                }

                FieldInfo fieldInfo = new FieldInfo();
                fieldInfo.setFieldName(fieldName);
                fieldInfo.setSqlType(sqlType);
                fieldInfo.setComment(comment);
                fieldInfo.setIsAutoIncrement(BooleanUtils.isTrue(isAutoIncrement.contains("auto_increment")));
                fieldInfo.setPropertyName(propertyName);
                // 将sql类型转换为java类型
                fieldInfo.setJavaType(processJavaType(sqlType));

                fieldInfoList.add(fieldInfo);

                // 设置时间相关 Property
                tableInfo.setHaveDataTime(ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, sqlType));
                tableInfo.setHavaData(ArrayUtils.contains(Constants.SQL_DATE_TYPES, sqlType));
                tableInfo.setHavaBigDecimal(ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE, sqlType));

            }

            // 设置字段详细信息
            tableInfo.setFieldList(fieldInfoList);

        } catch (Exception e) {
            logger.error("读取表失败", e);
        } finally {
            if (fieldResult != null) {
                try {
                    fieldResult.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 得到表唯一索引合集
     *
     * @param tableInfo
     * @return
     */
    private static void getKeyIndexInfo(TableInfo tableInfo) {
        PreparedStatement ps = null;
        ResultSet fieldResult = null;

        try {
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_INDEX, tableInfo.getTableName()));
            fieldResult = ps.executeQuery();
            while (fieldResult.next()) {
                String keyName = fieldResult.getString("key_name");
                Integer nonUnique = fieldResult.getInt("non_unique");
                String columnName = fieldResult.getString("column_name");
                // 唯一索引 码值为0
                if (nonUnique != 0) continue;

                // 通过传入的 tableInfo 得到 keyFieldList
                List<FieldInfo> keyFieldList = tableInfo.getKeyIndexMap().computeIfAbsent(keyName, k -> new ArrayList<>());
                // /\
                // ||    功能等价
                // || 第一次拿到map后有可能没值
                // if (null == keyFieldList) {
                //     keyFieldList = new ArrayList();
                //     tableInfo.getKeyIndexMap().put(keyName, keyFieldList);
                // }

                // 遍历表字段 通过列名带出列清单添加到keyFieldList
                for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                    if (fieldInfo.getFieldName().equals(columnName)) {
                        // 向 tableInfo 添加 索引信息
                        keyFieldList.add(fieldInfo);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("读取索引失败", e);
        } finally {
            if (fieldResult != null) {
                try {
                    fieldResult.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 处理字段 bean
     *
     * @param field                字段名
     * @param upperCaseFirstLetter 首字母是否需要大写
     * @return 驼峰命名字段
     */
    private static String processField(String field, Boolean upperCaseFirstLetter) {
        StringBuffer sb = new StringBuffer();
        String[] fileds = field.split("_");
        // 处理首单词是否转大写
        sb.append(upperCaseFirstLetter ? StringUtils.upperCaseFirstLetter(fileds[0]) : fileds[0]);

        for (int i = 1, len = fileds.length; i < len; i++) {
            sb.append(StringUtils.upperCaseFirstLetter(fileds[i]));
        }
        return sb.toString();
    }

    private static String processJavaType(String type) {
        if (ArrayUtils.contains(Constants.SQL_INTEGER_TYPE, type)) {
            return "Integer";
        } else if (ArrayUtils.contains(Constants.SQL_LONG_TYPE, type)) {
            return "Long";
        } else if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, type)) {
            return "String";
        } else if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, type)) {
            return "Date";
        } else if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE, type)) {
            return "BigDecimal";
        } else {
            throw new RuntimeException("无法识别的类型: " + type);
        }
    }

}
