package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.utils.DateUtils;

import java.io.BufferedWriter;
import java.util.Date;

/**
 * @Author brace
 * @Date 2024/7/18 14:15
 * @PackageName:com.easyjava.builder
 * @ClassName: BuildComment
 * @Description: TODO
 * @Version 1.0
 */
public class BuildComment {
    public static void createClassComment(BufferedWriter bw, String classComment) throws Exception {
        /**
         * @Description: 用户信息表
         * @author: brace
         * @date: 2024/07/18
         */
        bw.write("/**");
        bw.newLine();
        bw.write(" * @Description: " + classComment);
        bw.newLine();
        bw.write(" * @author: " + Constants.AUTHOR_COMMENT);
        bw.newLine();
        bw.write(" * @date: " + DateUtils.format(new Date(), DateUtils._YYYYMMDD));
        bw.newLine();
        bw.write(" */");
    }

    public static void createFieldComment(BufferedWriter bw, String fieldComment) throws Exception {
        bw.write("\t/**");
        bw.newLine();
        bw.write("\t *" + (fieldComment == null ? "" : fieldComment));
        bw.newLine();
        bw.write("\t */");
    }

    public static void createMethodComment(BufferedWriter bw) {
    }
}
