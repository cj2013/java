package com.chmc.userservice.client;

import com.chmc.userservice.entity.JWT;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceHystrix implements IAuthServiceClient {
    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
        return null;
    }
}
