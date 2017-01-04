package com.myh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.myh.security.CustomBasicAuthenticationEntryPoint;
import com.myh.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	private static String REALM="MY_TEST_REALM";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.headers().frameOptions().disable().and()
		.csrf().disable()
        .authorizeRequests()
            .antMatchers("/api/user/login",
            			 "/pic/**",
                         "/css/**",
            			 "/font-awesome/**",
            			 "/fonts/**",
            			 "/img/**",
            			 "/js/**",
            			 "/plugins/**",
            			 "/tools/**").permitAll()
            .anyRequest().authenticated()
            .and()
        .httpBasic()
        	.realmName(REALM)
        	.authenticationEntryPoint(getBasicAuthEntryPoint())
        	.and()
        .formLogin()
            .loginPage("/stock/login")
            .defaultSuccessUrl("/stock/index")
            .permitAll()
            .and()
        .logout()
            .permitAll();
	}
	
	public AuthenticationEntryPoint getBasicAuthEntryPoint() {
		return new CustomBasicAuthenticationEntryPoint();
	}
//	@Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }
//	
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Override  
    protected void configure(AuthenticationManagerBuilder auth)  
            throws Exception {  
        auth.userDetailsService(userDetailsService());  
    }  
	
}
