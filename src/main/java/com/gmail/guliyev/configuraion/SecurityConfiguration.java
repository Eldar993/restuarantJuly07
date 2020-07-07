package com.gmail.guliyev.configuraion;

import com.gmail.guliyev.enums.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/", "/main", "/index",
                        "/registration", "/login", "/sing-up",
                        "/error",
                        "/css/**",
                        "/images/**")
                .permitAll()
                .antMatchers("/user/**")
                .hasRole(UserRoles.USER.name())
                .antMatchers("/users/**", "/admin/**")
                .hasRole(UserRoles.ADMIN.name())
                .antMatchers("/cook/**")
                .hasRole(UserRoles.COOK.name())
                .antMatchers("/bills", "/ingredients")
                .hasAnyRole(UserRoles.ADMIN.name(), UserRoles.COOK.name())
                .anyRequest().authenticated()
                .and();

        http.exceptionHandling()
                .accessDeniedPage("/access-denied")
                .and();
        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/authorized", true)
                .failureUrl("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("j_login")
                .passwordParameter("j_password")
                .permitAll()
                .and();
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .invalidateHttpSession(true);
    }


    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
