package com.technical.test.controller;

import com.technical.test.dto.request.UserLoginRequest;
import com.technical.test.dto.request.UserRegisterRequest;
import com.technical.test.dto.response.LoginResponse;
import com.technical.test.entity.User;
import com.technical.test.entity.UserPrincipal;
import com.technical.test.service.AuthenticationService;
import com.technical.test.service.JwtService;
import com.technical.test.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private AuthenticationService authenticationService;
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserRegisterRequest request){
        return ResponseUtil.generateResponse("User registered successfully", HttpStatus.OK, authenticationService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody UserLoginRequest request){
        User authenticatedUser = authenticationService.authenticate(request);
        UserDetails userDetails = new UserPrincipal(authenticatedUser);
        String jwtToken = jwtService.generateToken(userDetails);
        LoginResponse response = LoginResponse.builder()
                .token(jwtToken)
                .type("Bearer")
                .username(userDetails.getUsername())
                .role(authenticatedUser.getRole())
                .expiresIn(jwtService.getExpirationTime())
                .build();
        return ResponseUtil.generateResponse("Success Login", HttpStatus.OK, response);
    }

}
