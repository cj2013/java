package com.chmc.useroauthservice.dao;

import com.chmc.useroauthservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
