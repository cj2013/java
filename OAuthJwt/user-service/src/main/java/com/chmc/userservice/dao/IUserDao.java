package com.chmc.userservice.dao;

import com.chmc.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
