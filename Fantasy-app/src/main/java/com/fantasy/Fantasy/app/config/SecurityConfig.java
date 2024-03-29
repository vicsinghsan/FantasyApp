package com.fantasy.Fantasy.app.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fantasy.Fantasy.app.jwt.JwtFilter;
import com.fantasy.Fantasy.app.service.FantasyUserDetails;




@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired 
	private JwtFilter jwtFilter;
	
	@Autowired
	private FantasyUserDetails userDetailService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.getDefaultUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Override
	
    protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.csrf().disable().authorizeRequests().regexMatchers(".*")
//		antMatchers("/api/registration","/api/login","/api/confirmOtp","/user")
        	.permitAll().antMatchers(HttpMethod.OPTIONS, "/api/registration","/api/login","/api/confirmOtp","/user")
                .permitAll().anyRequest().authenticated()
                .and().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
    } 
    
	
	

}
