package com.yuning.learning.english.controller;


import com.yuning.learning.english.common.controller.SuperController;
import com.yuning.learning.english.common.response.ApiResponses;
import com.yuning.learning.english.entity.request.CreateGroupRequest;
import com.yuning.learning.english.entity.response.CreateGroupResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@Slf4j
@Api(tags = {"游戏组控制器"})
public class GroupController extends SuperController {

	@ApiOperation(value = "创建组", notes = "创建组")
	@PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
	public ApiResponses<CreateGroupResponse> detectRoute(@RequestBody CreateGroupRequest reqVO) {
		CreateGroupResponse response = new CreateGroupResponse();
		return success(response);
	}
}
