package com.library.demo.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @SequenceGenerator(name = "users_id_seq",sequenceName = "users_id_seq",allocationSize = 1)
    @GeneratedValue(
            generator = "users_id_seq"
    )
    @Column(name="id")
    private Integer id;


    @NotBlank(message = "Must Enter a username")
    @Column(name="user_name")
    private String username;

    @NotBlank(message = "Must Enter a password")
    @Column(name="user_passwd")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "You must enter a name")
    @Column(name="name")
    private String name;

    @NotBlank(message = "You must enter an email")
    @Column(name="user_email")
    @Email(message = "must enter a valid email")
    private String email;

    @NotBlank(message = "You must enter a phone")
    @Column(name="user_phone")
    @Pattern(regexp = "[+-]?\\d+", message = "must enter only digits")
    private String phone;

    @ElementCollection(fetch= FetchType.EAGER)
    @CollectionTable(
            name="roles",
            joinColumns = @JoinColumn(name="user_id")
    )
    @Column(name="user_role")
    private Set<String> roles;

}
