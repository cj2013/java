package com.chmc.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import org.springframework.core.io.Resource;
import java.io.IOException;

@Configuration      //开启配置文件
public class JwtConfig {
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;  //用于JWT转换器

    @Bean
    @Qualifier("tokenStore")        //如果tokenStore合格
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter converter =  new JwtAccessTokenConverter();
        Resource resource = new ClassPathResource("public.cert");
        String publicKey ;
        try {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //设置JwtAccessTokenConverter的VerifierKey,VerifierKey为公钥，保存在public.cret中
        converter.setVerifierKey(publicKey);
        return converter;
    }
}
