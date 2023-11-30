package com.example.teamProject1.controller.api;

import com.example.teamProject1.Dto.ResponseDto;
import com.example.teamProject1.model.User;
import com.example.teamProject1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController : save 호출됨");
        userService.joinUser(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
