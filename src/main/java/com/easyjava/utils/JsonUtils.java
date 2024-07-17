package com.easyjava.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Author brace
 * @Date 2024/7/16 16:50
 * @PackageName:com.easyjava.utils
 * @ClassName: JsonUtils
 * @Description: json处理类
 * @Version 1.0
 */
public class JsonUtils {
    public static String convertObj2Json(Object obj) {
        if (null == obj) return null;
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
    }
}
