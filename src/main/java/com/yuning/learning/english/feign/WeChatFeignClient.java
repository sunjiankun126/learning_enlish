package com.yuning.learning.english.feign;

import com.yuning.learning.english.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "wechat-client", configuration = {FeignConfig.class}, url = "https://api.weixin.qq.com")
public interface WeChatFeignClient {

	@GetMapping("/sns/jscode2session")
	String getSession(
			@RequestParam("appid") String appid,
			@RequestParam("secret") String secret,
			@RequestParam("js_code") String jsCode,
			@RequestParam("grant_type") String grantType
	);
}
