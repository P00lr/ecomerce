package com.paul.ecomerce.model.entity;

import java.util.HashSet;
import java.util.Set;

import com.paul.ecomerce.dto.user.UserRequestDto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private boolean enabled = true;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public void fromRequestDto(UserRequestDto userDto) {
        if (!this.getName().equals(userDto.name()))
            this.setName(userDto.name());

        if (!this.getUsername().equals(userDto.username()))
            this.setUsername(userDto.username());

        if (!this.getEmail().equals(userDto.email()))
            this.setEmail(userDto.email());

    }
}
