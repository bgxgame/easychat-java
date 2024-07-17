package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.TableInfo;

import java.io.File;
import java.io.IOException;

/**
 * @Author brace
 * @Date 2024/7/17 20:00
 * @PackageName:com.easyjava.builder
 * @ClassName: BuildPo
 * @Description: TODO
 * @Version 1.0
 */
public class BuildPo {
    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_PO);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, tableInfo.getBeanName() + ".java");

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
