package com.etekcity.cloud.dal.redis;

import java.util.List;

import com.etekcity.cloud.exception.ServerException;

/**
 * token在Redis中的操作，包括：
 * 1.检查key是否存在； 2.获取key对应的value列表； 3.list操作左插入； 4.list操作右取出；
 * 5.获取key对应列表的大小； 6.删除key对应的列表； 7.删除列表中的某一个元素
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */
public interface RedisService {
    /**
     * 判断redis中是否存在key
     *
     * @param key key
     * @return key存在返回true，否则返回false
     * @throws ServerException 服务器异常
     * @throws ServerException 内部异常
     */
    boolean hasKey(String key) throws ServerException;

    /**
     * 获取同一个key所存储的所有token
     *
     * @param key   key
     * @param start 取的开始位置
     * @param end   取的结束位置
     * @return key对应的value列表
     * @throws ServerException 内部异常
     */
    List<String> getList(String key, int start, int end) throws ServerException;

    /**
     * 存储token,左插入
     *
     * @param key   key
     * @param value token
     * @throws ServerException 内部异常
     */
    void leftPush(String key, String value) throws ServerException;

    /**
     * 获取token,右获取并删除第一个token
     *
     * @param key key
     * @throws ServerException 内部异常
     */
    void rightPop(String key) throws ServerException;

    /**
     * 获取key对应的token个数
     *
     * @param key key
     * @throws ServerException 内部异常
     */
    long getSize(String key) throws ServerException;

    /**
     * 删除key对应所有token
     *
     * @param key key
     * @throws ServerException 内部异常
     */
    void deleteAllValue(String key) throws ServerException;

    /**
     * 删除某一个token
     *
     * @param key   key
     * @param token token
     * @throws ServerException 内部异常
     */
    void deleteOneValue(String key, String token) throws ServerException;

    /**
     * 截取给定范围的value
     *
     * @param key   key
     * @param start start 截取开始位置
     * @param end   end 截取结束位置
     * @throws ServerException 内部异常
     */
    void trimValue(String key, int start, int end) throws ServerException;
}
