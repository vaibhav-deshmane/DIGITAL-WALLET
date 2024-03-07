package com.bank.security.configurations;


import com.bank.security.filters.JwtAuthenticationFilter;
import com.bank.security.filters.JwtAuthorizationFilter;
import com.bank.security.repositories.AppUserRepository;
import com.bank.security.services.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin("*")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private UserDetailsServiceImpl userDetailsService;
	@Autowired
    private AppUserRepository appUserRepository;

	/*
	 * public SecurityConfig(UserDetailsServiceImpl userDetailsService,
	 * AppUserRepository appUserRepository) { this.userDetailsService =
	 * userDetailsService; this.appUserRepository = appUserRepository; }
	 */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
        http.headers().frameOptions().disable();
        //http.authorizeRequests().antMatchers("/h2-console/**","/refreshToken/**","/login/**").permitAll().anyRequest().authenticated();
        http.authorizeRequests().antMatchers("/refreshToken/**","/login/**","/customers/save","/verifyOtp","/changepassword","/statement").permitAll().anyRequest().authenticated();
       
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean(), appUserRepository));
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
