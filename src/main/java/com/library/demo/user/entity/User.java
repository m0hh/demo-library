package com.library.demo.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Must Enter a username")
    @Column(name="user_name")
    private String username;

    @NotNull
    @Column(name="user_passwd")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name="user_email")
    private String email;

    @Column(name="user_phone")
    private String phone;

    @ElementCollection(fetch= FetchType.EAGER)
    @CollectionTable(
            name="roles",
            joinColumns = @JoinColumn(name="user_id")
    )
    @Column(name="user_role")
    @NotNull
    private Set<String> roles;

}
