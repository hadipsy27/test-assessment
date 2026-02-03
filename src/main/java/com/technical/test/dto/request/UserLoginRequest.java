package com.technical.test.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {

    @NotBlank(message = "Username wajib diisi")
    private String username;

    @NotBlank(message = "Password wajib diisi")
    private String password;
}
