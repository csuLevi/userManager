package com.etekcity.cloud.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;


/**
 * 密码加密与校验,密码使用PBKDF2进行加密
 *
 * @author Levi
 * @since 0.0.1
 */
@Component
public class EncryptPasswordUtil {
    /**
     * 加密使用的算法
     */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    /**
     * 强随机数生成器使用的算法
     */
    private static final String PRNG = "SHA1PRNG";

    /**
     * PBEKeySpec要导出的密钥长度
     */
    private static final int KEY_LENGTH = 64;

    /**
     * 密码加密
     *
     * @param password 输入的密码
     * @return 密码加密值
     * @throws NoSuchAlgorithmException SecretKeyFactory.getInstance()抛出的异常
     * @throws InvalidKeySpecException  PBEKeySpec抛出的异常
     */
    public static String encryptPassword(@NotNull String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 100;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();
        PBEKeySpec pbeKeySpec = new PBEKeySpec(chars, salt, iterations, KEY_LENGTH);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * 获取盐值
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance(PRNG);
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) {
        StringBuilder buf = new StringBuilder(array.length * 2);
        for (byte b : array) {
            buf.append(String.format("%02x", b & 0xff));
        }
        return buf.toString();
    }

    /**
     * 校验异常
     *
     * @param originalPassword 用户登录时输入的密码
     * @param storedPassword   数据库中存储的密码加密值
     * @return 校验通过为true，否则为false
     * @throws NoSuchAlgorithmException SecretKeyFactory.getInstance()抛出的异常
     * @throws InvalidKeySpecException  PBEKeySpec抛出的异常
     */
    public static boolean validatePassword(String originalPassword, String storedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] testHash = skf.generateSecret(spec).getEncoded();
        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
        //String originalHash = parts[2];
        //return originalHash.equals(toHex(testHash));
    }

    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
