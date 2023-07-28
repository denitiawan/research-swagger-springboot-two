package com.deni.app.security.config;

import com.deni.app.module.user.UserRepo;
import com.deni.app.security.filter.JwtAuthenticationFilter;
import com.deni.app.security.filter.JwtAuthorizationFilter;
import com.deni.app.security.response.TokenForbidden;
import com.deni.app.security.service.UserPrincipalDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // this setting for enable the annotation @Secured
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserPrincipalDetailsService userPrincipalDetailsService;
    private UserRepo userRepo;

    public SecurityConfiguration(UserPrincipalDetailsService service, UserRepo userRepo) {
        this.userPrincipalDetailsService = service;
        this.userRepo = userRepo;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // setting auth dari database  table user
        auth.authenticationProvider(authenticationProvider());

    }


    /**
     * this function for supporting permit all access from http.authorizeRequests()
     */
    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/actuator/**");
        webSecurity.ignoring().antMatchers("/swagger-ui", "/swagger-ui/**", "/v3/api-docs/**");
    }

    // registrasikan jwt disini
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // remove csrf and state in session because in jwt we do not need them
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // add jwt filters (1. authentication, 2. authorization)
        http.addFilter(new JwtAuthenticationFilter(authenticationManager()));
        http.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepo));
        http.authorizeRequests();


        // login
        http.authorizeRequests().antMatchers("/login").permitAll()
                .and().authorizeRequests().antMatchers("/v1/auth/login").permitAll()

                .and().authorizeRequests().antMatchers("/test/test_token_failed").permitAll()

                // spring actuator & prometheus
                .and().authorizeRequests().antMatchers("/actuator/**").permitAll()

                // swagger-ui
                .and().authorizeRequests().antMatchers("/swagger-ui/**").permitAll();


        // 25-06-2023 : add exceptionHandling (token forbidden 403)
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());


        // any endpoint authenticated
        http.authorizeRequests().anyRequest().authenticated();

    }


    // function untuk ambil data user dari table user untuk auth
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

    // untuk password encoder yg akan dipakai oleh configure AuthenticationManagerBuilder
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 403 Forbidden handler (example : some token canott access some APIs because ROLE doesent allowed)
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new TokenForbidden();
    }



    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
