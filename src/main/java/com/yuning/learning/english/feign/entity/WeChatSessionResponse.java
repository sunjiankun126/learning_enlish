package com.yuning.learning.english.feign.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeChatSessionResponse {
    @JSONField(name= "openid")
    private String openId;

    @JSONField(name= "session_key")
    private String sessionKey;

    @JSONField(name= "unionid")
    private String unionId;

    @JSONField(name= "errcode")
    private String errCode;

    @JSONField(name= "errmsg")
    private String errMsg;
}
