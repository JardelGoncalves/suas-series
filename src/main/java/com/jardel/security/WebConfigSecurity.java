package com.jardel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthUserDetailService authUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/").permitAll() // permite acesso ao / para qualquer usuario
        .antMatchers(HttpMethod.GET, "/register").permitAll() // permite acesso ao form de cadastro
        .antMatchers(HttpMethod.POST, "/cadastro-usuario").permitAll() // permite qualquer user se cadastrar
        .anyRequest().authenticated()
        .and().formLogin().permitAll()
        .loginPage("/login")
        .defaultSuccessUrl("/dashboard")
        .failureUrl("/login")
        .and().logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login");

    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailService)
        .passwordEncoder(new BCryptPasswordEncoder());
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/img/**");
    }
}