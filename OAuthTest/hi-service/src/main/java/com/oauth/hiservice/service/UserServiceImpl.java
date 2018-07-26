package com.oauth.hiservice.service;

import com.oauth.hiservice.domain.User;
import com.oauth.hiservice.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    //BCrypt强哈希方法，每次加密结果都不同
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserDao userDao;

    public User create(String username, String password) {

        User user=new User();
        user.setUsername(username);
        String hash = encoder.encode(password);
        user.setPassword(hash);
        User u = userDao.save(user);
        return u;

    }
}
