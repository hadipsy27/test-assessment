package com.technical.test.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRegisterResponse {

    private Long id;
    private String username;
    private String email;
    private String role;

}
