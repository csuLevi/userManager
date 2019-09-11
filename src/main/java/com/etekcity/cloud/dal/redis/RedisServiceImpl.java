package com.etekcity.cloud.dal.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.etekcity.cloud.constant.errorCode.ErrorCode;
import com.etekcity.cloud.exception.ServerException;


/**
 * token在Redis中的存取等操作
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */
@Component
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 服务器设置Redis中key的过期时间
     */
    private static final int KEY_EXPIRE_TIME_IN_HOUR = 10 * 24;

    @Override
    public boolean hasKey(String key) throws ServerException {
        try {
            return stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR, "error occurs at redis", e);
        }
    }

    @Override
    public List<String> getList(String key, int start, int end) throws ServerException {
        try {
            return stringRedisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR, "failed to get data list from redis", e);
        }
    }

    @Override
    public void leftPush(String key, String value) throws ServerException {
        try {
            stringRedisTemplate.opsForList().leftPush(key, value);
            stringRedisTemplate.expire(key, KEY_EXPIRE_TIME_IN_HOUR, TimeUnit.HOURS);
        } catch (Exception e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR, "failed to save value", e);
        }
    }

    @Override
    public void rightPop(String key) throws ServerException {
        try {
            stringRedisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR, "failed to rightPop token", e);
        }
    }

    @Override
    public long getSize(String key) throws ServerException {
        try {
            return stringRedisTemplate.opsForList().size(key);
        } catch (Exception e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR, "failed to obtain the size of key", e);
        }
    }

    @Override
    public synchronized void deleteAllValue(String key) throws ServerException {
        try {
            stringRedisTemplate.delete(key);
        } catch (Exception e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR, "failed to delete all value", e);
        }
    }

    @Override
    public synchronized void deleteOneValue(String key, String tmpToken) throws ServerException {
        try {
            stringRedisTemplate.opsForList().remove(key, 0, tmpToken);
        } catch (Exception e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR, "failed to delete value", e);
        }
    }

    @Override
    public void trimValue(String key, int start, int end) throws ServerException {
        try {
            stringRedisTemplate.opsForList().trim(key, start, end);
        } catch (Exception e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR, "failed to trim value", e);
        }
    }
}
