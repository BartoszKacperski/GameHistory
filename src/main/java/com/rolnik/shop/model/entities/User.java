package com.rolnik.shop.model.entities;

import com.rolnik.shop.model.entities.base.SimpleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "users")
public class User extends SimpleEntity {
    @Length(min = 3, max = 255)
    @Column(unique=true)
    private String username;
    @Length(min = 3, max = 255)
    @Email
    @Column(unique=true)
    private String email;
    @Length(min = 5, max = 255)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

}
