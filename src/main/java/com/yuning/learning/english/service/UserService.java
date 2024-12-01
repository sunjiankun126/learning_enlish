package com.yuning.learning.english.service;

import com.alibaba.fastjson.JSON;
import com.yuning.learning.english.entity.User;
import com.yuning.learning.english.feign.WeChatFeignClient;
import com.yuning.learning.english.feign.entity.WeChatSessionResponse;
import com.yuning.learning.english.utils.AesCbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private WeChatFeignClient weChatFeignClient;

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;

    @Value("${wechat.grantType}")
    private String grantType;

    public User login(String jsCode, String encryptedData, String iv) {
        // 调用 Feign 获取 session 信息
        String responseStr = weChatFeignClient.getSession(appid, secret, jsCode, grantType);
        WeChatSessionResponse response = JSON.parseObject(responseStr, WeChatSessionResponse.class);


        if (response.getErrCode() != null) {
            throw new RuntimeException("微信登录失败: " + response.getErrMsg());
        }

        // 解密 encryptedData 获取用户信息（你需要实现解密逻辑）
        String userInfoJson = decryptUserInfo(response.getSessionKey(), iv, encryptedData);
        User user = JSON.parseObject(userInfoJson, User.class);

        // 根据openId生成token，将token存到redis，并设置过期时间；
        // 前端每次请求，都要带token过来
        // 后端先进行token校验，token存在，延长token的过期时间，根据token解析出openId查看openId是否存在，不存在保存




        // 保存用户信息到数据库（你需要根据业务需求保存到数据库）
//        saveUser(user);

        return user;
    }

    private String decryptUserInfo(String sessionKey, String iv, String encryptedData)  {
        // 使用 session_key 和 iv 对 encryptedData 解密，返回解密后的用户信息
        // 你可以使用微信提供的 SDK 或手动实现解密算法
        return AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");
    }

    private void saveUser(User user) {
        // 将用户信息保存到数据库
    }
}
