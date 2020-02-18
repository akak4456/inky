package org.akak4456.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.extern.java.Log;
@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource dataSource;
	@Autowired
	Akak4456UsersService akak4456UsersService;
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		log.info("security config......");
		//Board 관련 URL mapping
		http.authorizeRequests().antMatchers("/*/list").permitAll();
		http.authorizeRequests().antMatchers("/*/getOne").permitAll();
		http.authorizeRequests().antMatchers("/*/write").hasAnyRole("BASIC","ADMIN");
		http.authorizeRequests().antMatchers("/*/modify").hasRole("BASIC");
		http.authorizeRequests().antMatchers("/*/delete").hasRole("BASIC");
		//Reply 관련 URL mapping
		
		//로그인관련 설정
		http.formLogin().loginPage("/login");
		http.exceptionHandling().accessDeniedPage("/accessDenied");
		http.logout().logoutUrl("/logout").invalidateHttpSession(true);
		//rememberMe 설정
		http.rememberMe().key("akak4456").userDetailsService(akak4456UsersService)
		.tokenRepository(getJDBCRepository()).tokenValiditySeconds(60*60*24);
	}
	
	private PersistentTokenRepository getJDBCRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		log.info("build Auth global......");
		auth.userDetailsService(akak4456UsersService).passwordEncoder(passwordEncoder());
	}
	
}
