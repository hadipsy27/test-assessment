package com.technical.test.controller;

import com.technical.test.dto.request.UserRegisterRequest;
import com.technical.test.service.UserService;
import com.technical.test.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserRegisterRequest request){
        return ResponseUtil.generateResponse("User registered successfully", HttpStatus.OK, userService.registerUser(request));
    }

}
