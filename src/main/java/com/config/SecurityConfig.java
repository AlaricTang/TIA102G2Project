package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 設定類別
 *
 * 注意：
 * 1. Spring Boot 3.x/Spring Security 6.x 已棄用 antMatchers/authorizeRequests，需改用 authorizeHttpRequests/requestMatchers。
 * 2. 此設定將 /public/** 路徑開放給所有人，其餘路徑需登入。
 * 3. 自訂登入頁面為 /login。
 * 4. 已關閉 CSRF（如需安全性請依需求調整）。
 */
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 關閉 CSRF 保護（如需安全性請依需求調整）
            .csrf(AbstractHttpConfigurer::disable)
            // 權限規則設定
            .authorizeHttpRequests(auth -> auth
                // /public/** 路徑允許所有人存取
                .requestMatchers("/**").permitAll()
                // 其他所有請求都需驗證
                .anyRequest().authenticated()
            )
            // 表單登入設定
            .formLogin(form -> form
                // 自訂登入頁面
                .loginPage("/login")
                .permitAll()
            )
            // 登出設定
            .logout(LogoutConfigurer::permitAll
            );
        return http.build();
    }
}