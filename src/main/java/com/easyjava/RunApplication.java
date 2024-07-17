package com.easyjava;

import com.easyjava.bean.TableInfo;
import com.easyjava.builder.BuildTable;
import com.easyjava.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RunApplication {
    private static final Logger logger = LoggerFactory.getLogger(RunApplication.class);

    public static void main(String[] args) {
        List<TableInfo> tableInfoList = BuildTable.getTables();
        logger.info(JsonUtils.convertObj2Json(tableInfoList));
    }
}
