package com.bank.auth.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20)
    private String password;
    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 50)
    private String fullName;

}
