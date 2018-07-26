package com.oauth.authservice.service;

import com.oauth.authservice.domain.User;
import com.oauth.authservice.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service      //标注业务层组件
public class UserServiceImpl implements IUserService {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserDao userDao;

    @Override
    public void create(User user) {

        User existing = userDao.findByUsername(user.getUsername());
        //Assert.isNull(existing, "user already exists: " + user.getUsername());

        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);

    }
}
