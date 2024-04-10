package com.library.demo.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Data
public class UserAdminSaveDTO {


    @NotBlank(message = "Must Enter a username")
    private String username;

    @NotBlank(message = "Must Enter a password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Email(message = "must enter a valid email")
    @NotBlank(message = "Must Enter an email")
    private String email;

    @NotBlank(message = "Must Enter a phone")
    @Pattern(regexp = "[+-]?\\d+", message = "must enter only digits")
    private String phone;

    @NotBlank(message = "Must Enter a name")
    private String name;

    private Set<String> roles;

    @NotBlank(message = "Must Enter a key")
    private String key;

}

