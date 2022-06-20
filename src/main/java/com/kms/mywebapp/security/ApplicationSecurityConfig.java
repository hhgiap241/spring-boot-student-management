package com.kms.mywebapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.kms.mywebapp.security.ApplicationUserRole.*;

/**
 * @author : giaphoang
 * @mailto : hoanghuugiap241@gmail.com
 * @created : 6/17/2022, Friday
 * @project: MyWebApp
 **/
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index", "/static/**", "/webjars/**").permitAll()
                .antMatchers("/**").hasRole(ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(); // basic http authentication
    }
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails teacherUser = User.builder()
                .username("teacher")
                .password(passwordEncoder.encode("teacher"))
                .roles(TEACHER.name())
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(ADMIN.name())
                .build();

        UserDetails adminTraineeUser = User.builder()
                .username("admin_trainee")
                .password(passwordEncoder.encode("admin_trainee"))
                .roles(ADMINTRAINEE.name())
                .build();

        return new InMemoryUserDetailsManager(teacherUser, adminUser, adminTraineeUser);
    }
}
