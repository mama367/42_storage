package com.mbc.day03.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration으로 어노테이션이된 클래스는
// 빈(Bean:스프링컨테이너가 관리하는 객체)
// 정의를 위한 팩토리 역할

// 클래스 내에 @Bean 어노테이션된 메서드는
// 스프링 컨테이너가 관리할 빈 인스턴스를 생성

@Configuration
public class SecurityConfig
        extends WebSecurityConfigurerAdapter
{
    // security 내장 인증 처리를 안하도록 설정
    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        http.csrf().disable();
        http.formLogin().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
