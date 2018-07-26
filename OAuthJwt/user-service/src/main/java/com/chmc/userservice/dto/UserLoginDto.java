package com.chmc.userservice.dto;

import com.chmc.userservice.entity.JWT;
import com.chmc.userservice.entity.User;

public class UserLoginDto {
    private JWT jwt;

    private User user;

    public JWT getJwt() {
        return jwt;
    }

    public void setJwt(JWT jwt) {
        this.jwt = jwt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
