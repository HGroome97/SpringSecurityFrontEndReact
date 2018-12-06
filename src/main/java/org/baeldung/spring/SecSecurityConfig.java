package org.baeldung.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Profile("!https")
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;

    public SecSecurityConfig() {
        super();
    }

//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        // @formatter:off
//        auth.inMemoryAuthentication()
//        .withUser("user1").password("user1Pass").roles("USER")
//        .and()
//        .withUser("user2").password("user2Pass").roles("USER")
//        .and()
//        .withUser("admin").password("admin0Pass").roles("ADMIN");
//        // @formatter:on
//        
//    }
    
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
      auth.jdbcAuthentication().dataSource(dataSource)
     .usersByUsernameQuery("select username,password, enabled from users where username=?")
     .authoritiesByUsernameQuery("select username, role from user_roles where username=?");
    } 

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
        .csrf().disable()
        .authorizeRequests()
        	.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
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
        // @formatter:on
    }


}
