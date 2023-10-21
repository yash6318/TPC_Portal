package com.dbms.project.config;

import com.dbms.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                Only enable api routes while developing the application
                .antMatchers("/signup").permitAll()
//                .antMatchers("/api","/api/**").permitAll()
//                .antMatchers("/api", "/api/**").denyAll()
                .antMatchers("/assets/**").permitAll()
//                .antMatchers("/").hasAuthority("Student")
//                .antMatchers("/employee","/employee/**").hasAuthority("Student")
//                .antMatchers("/supplier", "/supplier/**").hasAuthority("Student")
//                .antMatchers("/order/supplier", "/order/supplier/**").hasAuthority("Student")
                .anyRequest().authenticated()
            .and()
                .csrf()
//                Only for testing rest api
//                .disable()
                .ignoringAntMatchers("/login", "/logout")
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .permitAll()
            .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
    }
}
