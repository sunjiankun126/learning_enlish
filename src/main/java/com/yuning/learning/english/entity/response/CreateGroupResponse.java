package com.yuning.learning.english.entity.response;

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
public class CreateGroupResponse {
	private String groupId;
	private List<User> users;
}
