package com.deni.app.module.auth;

import com.auth0.jwt.JWT;
import com.deni.app.common.service.ResponseService;
import com.deni.app.common.service.ResponseServiceHandler;
import com.deni.app.common.utils.NullEmptyChecker;
import com.deni.app.common.validator.Validator;
import com.deni.app.security.dto.TokenDTO;
import com.deni.app.security.dto.UserPrincipalDTO;
import com.deni.app.security.filter.JwtConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;


@Service
public class LoginServiceImpl {

    @Autowired
    LoginValidator loginValidator;


    @Autowired
    AuthenticationManager authenticationManager;

    // for unitest (mocking)
    public LoginServiceImpl(LoginValidator loginValidator) {
        this.loginValidator = loginValidator;
    }

    public ResponseService login(LoginDTO requestDTO) {
        Validator validator = loginValidator.requestValidator(requestDTO);
        if (!validator.isSuccess()) {
            return ResponseServiceHandler.createResponse(
                    validator.getMessage(),
                    requestDTO,
                    HttpStatus.BAD_REQUEST);
        }


        Authentication authentication = validateUsernameAndPassword(requestDTO);
        if (NullEmptyChecker.isNullOrEmpty(authentication)) {
            return ResponseServiceHandler.createResponse(
                    "Login Failed!, Username & Password is not Match (V2)",
                    requestDTO,
                    HttpStatus.FORBIDDEN);
        }

        String finalToken = generateJWTToken(authentication);
        return ResponseServiceHandler.createResponse(
                "Login Success",
                new TokenDTO(JwtConstants.TOKEN_X_KEY, finalToken),
                HttpStatus.OK);


    }


    public Authentication validateUsernameAndPassword(LoginDTO loginDTO) {
        //log.debug("Request login {}", "login & authentication filter");


        // Create login token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword(),
                new ArrayList<>());

        // Authenticate user
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            // handling login failed
            return null;
        }

        return auth;
    }

    public String generateJWTToken(Authentication authentication) {
        // Grab principal
        UserPrincipalDTO principal = (UserPrincipalDTO) authentication.getPrincipal();

        // Create JWT Token
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstants.TOKEN_EXPIRED_TIME))
                .sign(HMAC512(JwtConstants.TOKEN_SECRET.getBytes()));

        String finalToken = JwtConstants.TOKEN_PREFIX + token;

        return finalToken;
    }

}
