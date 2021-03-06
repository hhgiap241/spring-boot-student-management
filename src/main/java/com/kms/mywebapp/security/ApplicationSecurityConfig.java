package com.kms.mywebapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.concurrent.TimeUnit;

import static com.kms.mywebapp.security.ApplicationUserPermission.*;
import static com.kms.mywebapp.security.ApplicationUserRole.*;

/**
 * @author : giaphoang
 * @mailto : hoanghuugiap241@gmail.com
 * @created : 6/17/2022, Friday
 * @project: MyWebApp
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index", "/static/**", "/webjars/**").permitAll()
//                .antMatchers("/students/add", "/students/edit/**", "/students/delete/**", "/books/add", "/books/edit/**", "/books/delete/**","/courses/add", "/courses/edit/**", "/courses/delete/**" )
//                .hasRole(ADMIN.name())
                .antMatchers("/students/add/**").hasAuthority(STUDENT_WRITE.getPermission())
                .antMatchers("/books/add/**").hasAuthority(BOOK_WRITE.getPermission())
                .antMatchers("/courses/add/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers("/students/edit/**").hasAuthority(STUDENT_WRITE.getPermission())
                .antMatchers("/students/delete/**").hasAuthority(STUDENT_WRITE.getPermission())
                .antMatchers("/books/edit/**").hasAuthority(BOOK_WRITE.getPermission())
                .antMatchers("/books/delete/**").hasAuthority(BOOK_WRITE.getPermission())
                .antMatchers("/courses/edit/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers("/courses/delete/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers("/students/*", "/books/*", "/courses/*").hasAnyRole(TEACHER.name(), ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                    .permitAll() // form based authentication
                    .defaultSuccessUrl("/")
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and()
                .rememberMe() // the default will be 30 minutes but by setting the parameter we can change it to 2 weeks
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("somethingverysecured")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails teacherUser = User.builder()
                .username("teacher")
                .password(passwordEncoder.encode("teacher"))
//                .roles(TEACHER.name())
                .authorities(TEACHER.getGrantedAuthorities())
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails adminTraineeUser = User.builder()
                .username("admin_trainee")
                .password(passwordEncoder.encode("admin_trainee"))
//                .roles(ADMINTRAINEE.name())
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(teacherUser, adminUser, adminTraineeUser);
    }
}
// role base authorization
// permission based on user permission