package com.notedocs.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.notedocs.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToMany(mappedBy = "roles",cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<User> users;


    @ManyToMany
    @JoinTable(
            name = "roles_authorities",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    @JsonIgnore
    private Set<Authority> authorities;

}
