package com.etekcity.cloud.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etekcity.cloud.dto.response.result.VoidResult;
import com.etekcity.cloud.util.DateUtil;
import com.etekcity.cloud.util.StrUtil;
import com.etekcity.cloud.dal.redis.RedisService;
import com.etekcity.cloud.dto.response.Response;
import com.etekcity.cloud.constant.ResponseConstant;
import com.etekcity.cloud.constant.errorCode.ErrorCode;
import com.etekcity.cloud.exception.ServerException;

/**
 * token的相关操作，包括:
 * 1.token生成； 2.token存储； 3.token查找； 4.token有效性校验； 5.删除token; 6.删除特定的一个token
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */
@Slf4j
@Service
public class TokenService {
    @Autowired
    private RedisService redisDao;
    /**
     * 服务器设置过期时间expiredTime，单位：s
     * 对于生成并发高的数据（比如token），必须采用超时时间设置必须采用定时+随机数
     */
    private static final int EXPIRE_TIME_IN_SECOND = 7 * 24 * 3600 + (new Random().nextInt(3600));
    /**
     * redisKey设计规定：表名：主键列名：主键值：要存储的列名
     * E.g: account:userId:1:token
     */
    private static final String REDIS_TOKEN_KEY_PREFIX = "account:userId:";
    private static final String REDIS_TOKEN_KEY_SUFFIX = ":token";
    private static final String TOKEN_SEPARATOR = "@";
    /**
     * 一个用户最多可以有5个token
     */
    private static final int MAX_NUMBER_TOKEN = 5;

    private static int expiresIn;

    /**
     * token的生成规则为：生成时间戳 + “@” + 长度为10的随机字符串+ “@” + 过期时间戳
     *
     * @return token字符串
     */
    public static String generateToken() {
        String createTimeStamp = DateUtil.nowTimeString();
        //获取创建时间偏移后的过期时间
        Date dateOffset = DateUtil.offsetDate(new Timestamp(System.currentTimeMillis()),
                Calendar.SECOND, EXPIRE_TIME_IN_SECOND);
        expiresIn = EXPIRE_TIME_IN_SECOND;
        String expireTimeStamp = DateUtil.timeString(dateOffset);
        //生成随机字符串
        String randomStr = RandomStringUtils.randomAlphanumeric(10);
        //生成token
        return createTimeStamp + TOKEN_SEPARATOR + randomStr + TOKEN_SEPARATOR + expireTimeStamp;
    }

    public static int getExpiresIn() {
        return expiresIn;
    }

    /**
     * 保存token
     *
     * @param userId userId
     * @param token  token
     * @throws ServerException 服务异常
     */
    public synchronized void saveToken(long userId, String token) throws ServerException {
        String redisKey = REDIS_TOKEN_KEY_PREFIX + userId + REDIS_TOKEN_KEY_SUFFIX;
        log.info("the token is:{}, the redisKey is:{}", token, redisKey);
        try {
            redisDao.leftPush(redisKey, token);
            redisDao.trimValue(redisKey, 0, MAX_NUMBER_TOKEN - 1);
        } catch (ServerException e) {
            log.error("failed to save token:{}, the key is{}", token, redisKey);
            throw e;
        }
    }

    /**
     * 校验token，包括token的正确性校验和有效性校验
     *
     * @param userId userId
     * @param token  token
     * @return Response
     */
    public int validateToken(long userId, String token) throws ServerException {
        try {
            if (!judgeTokenExist(userId, token)) {
                log.info("user token does not exist, userId is:{}, token is: {}", userId, token);
                return ErrorCode.COMMON_TOKEN_INCORRECT_ERROR.getCode();
            }
        } catch (ServerException e) {
            log.error("error occur: {}", e.getMsg());
            throw e;
        }

        if (!judgeTokenExpires(token)) {
            log.info("user token has expired, userId is:{}, token is: {}", userId, token);
            return ErrorCode.COMMON_TOKEN_EXPIRED_ERROR.getCode();
        }
        return ErrorCode.SUCCESS.getCode();
    }

    /**
     * 在Redis中查找token是否存在
     *
     * @param userId   userId
     * @param tmpToken 要查找的token
     * @return boolean 找到为true，否则为false
     * @throws ServerException 服务异常
     */
    private boolean judgeTokenExist(long userId, String tmpToken) throws ServerException {
        String redisKey = REDIS_TOKEN_KEY_PREFIX + userId + REDIS_TOKEN_KEY_SUFFIX;
        try {
            if (!redisDao.hasKey(redisKey)) {
                return false;
            }
            List<String> tmp = redisDao.getList(redisKey, 0, MAX_NUMBER_TOKEN - 1);
            if (tmp == null) {
                return false;
            }
            for (String token : tmp) {
                if (token.equals(tmpToken)) {
                    return true;
                }
            }
        } catch (ServerException e) {
            log.error("error occurs : {}", e.getMsg(), e);
            throw e;
        }
        return false;
    }

    /**
     * 判断token是否有效
     *
     * @param token token
     * @return boolean token有效为true，负责为false
     */
    private boolean judgeTokenExpires(String token) {
        String[] tokenList = StrUtil.split(token, "@");
        long expireTime = Long.valueOf(tokenList[2]);
        long nowTime = Long.valueOf(DateUtil.nowTimeString());
        return expireTime > nowTime;
    }

    /**
     * 删除一个key对应的所有token
     *
     * @param userId userId
     * @throws ServerException 服务异常
     */
    public void deleteAllToken(long userId) throws ServerException {
        String redisKey = REDIS_TOKEN_KEY_PREFIX + userId + REDIS_TOKEN_KEY_SUFFIX;
        try {
            redisDao.deleteAllValue(redisKey);
        } catch (ServerException e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR, "delete all token failed");
        }

    }

    /**
     * 删除一个给定的token
     *
     * @param userId userId
     * @param token  待删除的token
     * @throws ServerException 服务异常
     */
    public void deleteOneToken(long userId, String token) throws ServerException {
        String redisKey = REDIS_TOKEN_KEY_PREFIX + userId + REDIS_TOKEN_KEY_SUFFIX;
        redisDao.deleteOneValue(redisKey, token);
    }
}



