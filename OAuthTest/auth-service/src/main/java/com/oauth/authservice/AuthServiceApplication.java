package com.oauth.authservice;

import com.oauth.authservice.service.security.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/*
@EnableResourceServer来开启/关闭过滤器，使用ResourceServerConfigurer来配置它。 下面是可配置的属性：
        tokenServices：定义令牌服务的实体（ResourceServerTokenServices类的实例）。
        resourceId：资源ID（可选的，建议配置，如果不为空，授权服务器会对它进行验证）。
@EnableResourceServer注解把一个 OAuth2AuthenticationProcessingFilter 类型过滤器添加到Spring Security 过滤链中。
*/
@SpringBootApplication
@EnableResourceServer
@EnableEurekaClient
public class AuthServiceApplication {
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Configuration
    @EnableAuthorizationServer
    protected  class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

        JdbcTokenStore tokenStore=new JdbcTokenStore(dataSource);

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private UserServiceDetail userServiceDetail;

        //ClientDetailsServiceConfigurer 用来配置客户端详情服务
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("browser")
                    .authorizedGrantTypes("refresh_token", "password")
                    .scopes("ui")
                    .and()
                    .withClient("service-hi")
                    .secret("123456")
                    .authorizedGrantTypes("client_credentials", "refresh_token","password")
                    .scopes("server");
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(tokenStore)
                    .authenticationManager(authenticationManager)
                    .userDetailsService(userServiceDetail);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer
                    .tokenKeyAccess("permitAll()")
                    .checkTokenAccess("isAuthenticated()");
        }
    }
}
