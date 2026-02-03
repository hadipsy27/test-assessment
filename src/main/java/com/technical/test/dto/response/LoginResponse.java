package com.technical.test.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {
    private String token;
    private String type;
    private String username;
    private String role;
    private long expiresIn;
}
