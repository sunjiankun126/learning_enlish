package com.yuning.learning.english.entity.request;

import com.yuning.learning.english.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupRequest {
	private String id; // 组id
	private String name;  //  组名称
	private List<User> users; // 创建组时，至少有一个玩家；最多有5个玩家；
}
