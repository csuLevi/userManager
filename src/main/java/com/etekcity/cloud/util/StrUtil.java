package com.etekcity.cloud.util;

/**
 * 字符串工具类
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */
public class StrUtil {
    public static final String SPLIT_CHAR_SPACE = " ";

    /**
     * 切分字符串<br/>
     * a#b#c -> [a,b,c]
     * a##b#c -> [a,"",b,c]
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符串
     * @return 切分后的集合
     */
    public static String[] split(String str, String separator) {
        if (str == null) {
            return null;
        }
        return str.split(separator);
    }


}
