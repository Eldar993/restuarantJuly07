package com.gmail.guliyev.dto;

import com.gmail.guliyev.enums.UserRoles;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {
    private Long id;


    @NotEmpty(message = "User name is null or empty")
    @Pattern(regexp = "^[A-Za-z]+$", message = "User name can't contain numbers")
    private String name;

    @Size(min = 1, max = 25)
    @NotEmpty(message = "Password is null or empty")
    //TODO: change "^[A-Za-z]+$" in pattern or remove it
    //@Pattern(regexp = "^[A-Za-z]+$", message = "Password must contain numbers and letters")
    private String password;

    private UserRoles role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoles getUserRole() {
        return role;
    }

    public void setUserRole(UserRoles role) {
        this.role = role;
    }
}
