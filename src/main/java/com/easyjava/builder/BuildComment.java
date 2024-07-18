package com.easyjava.builder;

import java.io.BufferedWriter;

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
         * @Description: TODO
         * @Date 2024/7/18 14:15
         */
        bw.write("/**");
        bw.newLine();
        bw.write(" * @Description: " + classComment);
        bw.newLine();
        bw.write(" * @Date: ");
        bw.newLine();
        bw.write(" */");

    }

    public static void createFieldComment(BufferedWriter bw) {

    }

    public static void createMethodComment(BufferedWriter bw) {
    }
}
