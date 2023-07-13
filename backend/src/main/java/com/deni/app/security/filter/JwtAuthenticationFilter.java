package com.deni.app.security.filter;

import com.auth0.jwt.JWT;
import com.deni.app.common.controller.Response;
import com.deni.app.module.auth.LoginDTO;
import com.deni.app.security.dto.TokenDTO;
import com.deni.app.security.dto.UserPrincipalDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private AuthenticationManager authenticationManager;



    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;


    }


    /**
     * @PostMapping (value = " localhost : 8080 / login ", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
     * @ReqBody {"username":"den", "password":"den123"}
     */
    @CrossOrigin
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug("Request login {}", "login & authentication filter");

        // Grab credentials and map them to login viewmodel
        LoginDTO credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create login token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>());

        // Authenticate user
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            // handling login failed
            unSuccessfulAuthentication(request, response);
        }


        return auth;
    }


    /**
     * Response when login is Success
     * login success and then generating token using JWT
     * format token : X-AUTH-TOKEN.xxxx.yyyyy
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Grab principal
        UserPrincipalDTO principal = (UserPrincipalDTO) authResult.getPrincipal();

        // Create JWT Token
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstants.TOKEN_EXPIRED_TIME))
                .sign(HMAC512(JwtConstants.TOKEN_SECRET.getBytes()));

        String finalToken = JwtConstants.TOKEN_PREFIX + token;
        // Add token in response
        response.addHeader(JwtConstants.TOKEN_X_KEY, finalToken);




        // add body
        Response statusResponse = Response.builder()
                .message("Login Success")
                .data(new TokenDTO(JwtConstants.TOKEN_X_KEY, finalToken))
                .build();
        Object body = new ResponseEntity<>(statusResponse, HttpStatus.OK);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new Gson().toJson(body));
        response.getWriter().flush();

        log.debug("Response Login {}", HttpStatus.OK);
    }

    /**
     * Response when login is Failed
     */
    protected void unSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            // add body
            Response statusResponse = Response.builder()
                    .message("Login Failed!, Username & Password is not Match")
                    .data("")
                    .build();
            Object body = new ResponseEntity<>(statusResponse, HttpStatus.FORBIDDEN);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(new Gson().toJson(body));
            response.getWriter().flush();

            log.debug("Response Login {}", HttpStatus.FORBIDDEN.toString());
        } catch (Exception e) {
            log.debug("Exception Login {}", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            log.debug("Exception Login {}", e.getMessage());
            log.debug("Exception Login {}", e.getCause());
        }
    }


}
