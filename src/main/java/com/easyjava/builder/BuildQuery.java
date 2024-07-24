package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author brace
 * @Date 2024/7/17 20:00
 * @PackageName:com.easyjava.builder
 * @ClassName: BuildPo
 * @Description: TODO
 * @Version 1.0
 */
public class BuildQuery {
    private static final Logger logger = LoggerFactory.getLogger(BuildQuery.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_QUERY);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String className = tableInfo.getBeanName() + Constants.SUFFIX_BEAN_QUERY;

        File poFile = new File(folder, className + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        try {
            out = Files.newOutputStream(poFile.toPath());
            outw = new OutputStreamWriter(out, "utf8");
            bw = new BufferedWriter(outw);
            // 开始生成头部内容
            bw.write("package " + Constants.PACKAGE_QUERY + ";");
            bw.newLine();
            bw.newLine();

            // 导包
            if (tableInfo.getHavaData() || tableInfo.getHaveDataTime()) {
                bw.write("import java.util.Date;");
                bw.newLine();
            }

            if (tableInfo.getHavaBigDecimal()) {
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }
            bw.newLine();

            // 构建类注解
            BuildComment.createClassComment(bw, tableInfo.getComment() + "查询对象");
            bw.newLine();
            bw.write("public class " + className + " {");
            bw.newLine();

            List<FieldInfo> fieldListTmp = new ArrayList();

            // 开始生成 Property
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                // 构建 Property 注解
                BuildComment.createFieldComment(bw, fieldInfo.getComment());
                bw.newLine();

                bw.write("\tprivate " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
                bw.newLine();

                if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, fieldInfo.getSqlType())) {
                    String fuzzyPropertyName = fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY;
                    bw.write("\tprivate " + fieldInfo.getJavaType() + " " + fuzzyPropertyName + ";");
                    bw.newLine();
                    bw.newLine();

                    // 新增 FieldInfo 便于setter getter方法生成
                    FieldInfo fuzzyFieldInfo = new FieldInfo();
                    fuzzyFieldInfo.setJavaType(fieldInfo.getJavaType());
                    fuzzyFieldInfo.setPropertyName(fuzzyPropertyName);
                    // 添加进字段列表
                    fieldListTmp.add(fuzzyFieldInfo);
                }

                if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType())) {
                    String queryTimeStartPropertyName = fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_START;
                    String queryTimeEndPropertyName = fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_END;

                    FieldInfo queryTimeStartFieldInfo = new FieldInfo();
                    queryTimeStartFieldInfo.setJavaType("String");
                    queryTimeStartFieldInfo.setPropertyName(queryTimeStartPropertyName);
                    // 添加进字段列表
                    fieldListTmp.add(queryTimeStartFieldInfo);

                    FieldInfo queryTimeEndFieldInfo = new FieldInfo();
                    queryTimeEndFieldInfo.setJavaType("String");
                    queryTimeEndFieldInfo.setPropertyName(queryTimeEndPropertyName);

                    // 添加进字段列表
                    fieldListTmp.add(queryTimeEndFieldInfo);

                    bw.write("\tprivate String" + " " + queryTimeStartPropertyName + ";");
                    bw.newLine();
                    bw.newLine();

                    bw.write("\tprivate String" + " " + queryTimeEndPropertyName + ";");
                    bw.newLine();
                    bw.newLine();
                }
            }

            // 新增 FieldInfo
            tableInfo.getFieldList().addAll(fieldListTmp);

            // 构建 setter getter
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                // setter
                String tempField = StringUtils.upperCaseFirstLetter(fieldInfo.getPropertyName());
                bw.write("\tpublic void set" + tempField + "(" + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ") {");
                bw.newLine();
                bw.write("\t\tthis." + fieldInfo.getPropertyName() + " = " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();
                // getter
                bw.write("\tpublic " + fieldInfo.getJavaType() + " get" + tempField + "(" + ") {");
                bw.newLine();
                bw.write("\t\treturn this." + fieldInfo.getPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();
            }
            bw.write("}");
            bw.flush();
        } catch (Exception e) {
            logger.error("创建po失败", e);
        } finally {
            if (null != bw) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (outw != null) {
                try {
                    outw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
