package com.qa.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Profile("!https")
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	PasswordEncoder passwordEncoder;

    public SecSecurityConfig() {
        super();
    }
    
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
      auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
     .usersByUsernameQuery("select username,password, enabled from users where username=?")
     .authoritiesByUsernameQuery("select username, role from users where username=?");
    } 


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .authorizeRequests()
        	.antMatchers("/trainingmanager/**").access("hasRole('ROLE_TRAINING_MANAGER')")
        	.antMatchers("/anonymous*").anonymous()
        	.antMatchers(HttpMethod.GET, "/index*", "/static/**", "/*.js", "/*.json", "/*.ico").permitAll()
        	.anyRequest().authenticated()
        .and()
        	.formLogin()
        	.loginPage("/index.html")
        	.loginProcessingUrl("/perform_login")
        	.defaultSuccessUrl("/homepage.html",true)
        	.failureUrl("/index.html?error=true")
        .and()
        	.logout()
        	.logoutUrl("/perform_logout")
        	.deleteCookies("JSESSIONID");
    }


}
