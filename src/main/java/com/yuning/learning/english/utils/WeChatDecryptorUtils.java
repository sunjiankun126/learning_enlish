package com.yuning.learning.english.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WeChatDecryptorUtils {
    static {
        // 注册 BouncyCastle Provider
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 解密微信数据
     *
     * @param encryptedData 加密数据
     * @param sessionKey    会话密钥
     * @param iv            偏移量
     * @return 解密后的用户信息
     */
    public static Map<String, Object> decrypt(String encryptedData, String sessionKey, String iv) {
        try {
            // Base64 解码
            byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);
            byte[] sessionKeyBytes = Base64.getDecoder().decode(sessionKey);
            byte[] ivBytes = Base64.getDecoder().decode(iv);

            // 初始化密钥和偏移量
            SecretKeySpec keySpec = new SecretKeySpec(sessionKeyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            // 配置 Cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            // 解密
            byte[] decryptedBytes = cipher.doFinal(encryptedDataBytes);
            String decryptedString = new String(decryptedBytes, StandardCharsets.UTF_8);

            // 转换为 Map
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(decryptedString, HashMap.class);

        } catch (Exception e) {
            log.error("WeChatDecryptorUtils decrypt catch exception", e);
            throw new RuntimeException("解密失败", e);
        }
    }
}
