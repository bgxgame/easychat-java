package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildTable {
    private static final Logger logger = LoggerFactory.getLogger(BuildTable.class);
    private static Connection conn = null;
    private static String SQL_SHOW_TABLE_STATUS = "show table status";

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

    public static void getTables() {
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
                TableInfo tableInfo = new TableInfo();
                tableInfo.setTableName(tableName);

                String beanName = tableName;
                // 如果忽略表前缀，就只取后半部分表名
                if (Constants.IGNORE_TABLE_PREFIX) {
                    beanName = tableName.substring(beanName.indexOf("_") + 1);
                }

                logger.info(beanName);
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
    }
}
