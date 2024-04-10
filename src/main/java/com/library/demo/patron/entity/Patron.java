package com.library.demo.patron.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.demo.borrow.entity.Borrow;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name="patrons")
public class Patron {
    @Id
    @SequenceGenerator(name = "patrons_id_seq",sequenceName = "patrons_id_seq",allocationSize = 1)
    @GeneratedValue(
            generator = "patrons_id_seq"
    )
    @Column(name="id")
    private Integer id;

    @NotBlank(message = "You must enter a name")
    @Column(name="name")
    private String name;

    @NotBlank(message = "You must enter an email")
    @Column(name="email")
    @Email(message = "Must enter a valid email")
    private String email;

    @NotBlank(message = "You must enter a phone")
    @Column(name="phone")
    @Pattern(regexp = "[+-]?\\d+", message = "must enter only digits")
    private String phone;

    @OneToMany(
            mappedBy = "patron",
            orphanRemoval = false,
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Borrow> borrows = new ArrayList<>();




}
