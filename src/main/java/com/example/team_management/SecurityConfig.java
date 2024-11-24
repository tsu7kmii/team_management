package com.example.team_management;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;


@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // https://zenn.dev/peishim/articles/c225ac5a5eedb0
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((requests) ->requests
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers("/sign_in","/register").permitAll()
            .requestMatchers("/admin/**").hasAuthority("ADMIN")
            .anyRequest().authenticated()
        )
            .formLogin((form) -> form
            // ログインページへのパスを指定→コントローラーにもGET、/loginでの処理を記載する必要がある
            .loginPage("/login")
            // ログイン成功時に表示される画面へのパス
            .defaultSuccessUrl("/",true)
            .permitAll()
        )
        .logout((logout) -> logout.permitAll());

        return http.build();
    }
     
}
