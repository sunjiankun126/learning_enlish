package com.yuning.learning.english.controller;

import com.yuning.learning.english.entity.User;
import com.yuning.learning.english.entity.request.LoginRequest;
import com.yuning.learning.english.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = {"游戏组控制器"})
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.login(loginRequest.getCode(), loginRequest.getEncryptedData(), loginRequest.getIv());
        return ResponseEntity.ok(user);
    }
}
