package com.technical.test.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRegisterRequest {

    @NotBlank(message = "Username wajib diisi")
    @Size(min = 3, message = "Username minimal 3 karakter")
    private String username;

    @NotBlank(message = "Email wajib diisi")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Password wajib diisi")
    @Size(min = 6, message = "Password minimal 6 karakter")
    private String password;

    @NotBlank(message = "Role wajib diisi")
    @Pattern(
            regexp = "ADMIN|USER",
            message = "Role hanya boleh ADMIN atau USER"
    )
    private String role;

}
