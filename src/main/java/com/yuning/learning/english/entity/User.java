package com.yuning.learning.english.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private String id;  //玩家ID
	private String nickName; // 昵称
	private int gender; // 性别
	private String language; // 语言
	private String city;
	private String province;
	private String country;
	private String avatarUrl; //头像地址
	@JSONField(name="is_demote")
	private boolean isDemote;
}
