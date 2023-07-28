package com.deni.app.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.deni.app.common.utils.NullEmptyChecker;
import com.deni.app.module.user.User;
import com.deni.app.module.user.UserRepo;
import com.deni.app.security.dto.UserPrincipalDTO;
import com.deni.app.security.response.TokenBadFormat;
import com.deni.app.security.response.TokenEmpty;
import com.deni.app.security.response.TokenExpired;
import com.google.gson.Gson;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.deni.app.security.filter.JwtConstants.TOKEN_PREFIX;
import static com.deni.app.security.filter.JwtConstants.TOKEN_PREFIX_BEARER;

/**
 * class ini tidak dipakai dulu karena ada issue jika jwtnya expired tidak bisa di handle pesannya
 * akibat dari tidak digunakannya class ini
 * - flow check authorized tidak menggunakan 'spring security flow'
 * - annotaion @Secured("ROLE_ADMIN") pada setiak endpoint pada class controller jadi tidak berfungsi lagi
 */


/**
 * 24/06/2023 Issue jwt expired handling [solved]
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepo userRepo;


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepo userRepo) {
        super(authenticationManager);
        this.userRepo = userRepo;

    }

    Boolean permitAllURI(String urlRequest) {
        List<String> list = new ArrayList<>();
        list.add("/v1/auth/login");
        list.add("/test/test_token_failed");

        boolean isPermit = false;
        for (String uri : list) {
            if (uri.equals(urlRequest)) {
                isPermit = true;
                break;
            }
        }
        return isPermit;
    }


    // new version code : 26/06/2023
    @CrossOrigin
    @Override // api public akan di filter di function ini, apakah header sudah sesuai dengan yg diminta oleh security
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        DecodedJWT jwt;

        /**
         get value token "Authorization" from header
         */
        String tokenFromHeader = request.getHeader(JwtConstants.TOKEN_X_KEY);


        /**
         validation permit all uri
         */
        if (permitAllURI(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        /**
         validation Authorization is empty
         */
        if (NullEmptyChecker.isNullOrEmpty(tokenFromHeader)) {
            TokenEmpty.response(request, response, null);
            onUnsuccessfulAuthentication(request, response, null);
            return;
        }


        /**
         * VALIDATE TOKEN : NORMAL
         */
        try {
            // validation : jwt expired
            jwt = JWT.decode(replaceBearerOnToken(tokenFromHeader));
            if (jwt.getExpiresAt().before(new Date(System.currentTimeMillis()))) {
                // handling token has expired
                TokenExpired.response(request, response, null);
                onUnsuccessfulAuthentication(request, response, null);
                return;
            }
        } catch (Exception e) {
            // handling token bad format
            TokenBadFormat.response(request, response, null);
            onUnsuccessfulAuthentication(request, response, null);
            return;
        }


        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = extractUsernameFromToken(tokenFromHeader);

        // handling token bad format
        if(NullEmptyChecker.isNullOrEmpty(authentication)){
            TokenBadFormat.response(request, response, null);
            onUnsuccessfulAuthentication(request, response, null);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);


    }

    String replaceBearerOnToken(String bearerToken) {
        String replaceBearer = bearerToken.replace(TOKEN_PREFIX_BEARER, "");
        return replaceBearer.replace(TOKEN_PREFIX, "");
    }


    private Authentication valueTokenFromRedis(Object valueTokenFromRedis) {
        if (NullEmptyChecker.isNullOrEmpty(valueTokenFromRedis)) {
            return null;
        }

        UserPrincipalDTO principal = new Gson().fromJson(valueTokenFromRedis.toString(), UserPrincipalDTO.class);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal.getUsername(), null, principal.getAuthorities());
        return auth;


    }

    private Authentication extractUsernameFromToken(String tokenFromHeader) {
        if (NullEmptyChecker.isNullOrEmpty(tokenFromHeader)) {
            return null;
        }

        // replace empty string
        String token = replaceBearerOnToken(tokenFromHeader);

        // parse the token and validate it
        String userName = "";
        try {
            userName = JWT.require(HMAC512(JwtConstants.TOKEN_SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
//            TokenBadFormat.response(request, response, null);
//            onUnsuccessfulAuthentication(request, response, null);
            return null;
        }


        // Search in the DB if we find the user by token subject (username)
        // If so, then grab user details and create spring auth token using username, pass, authorities/roles
        if (NullEmptyChecker.isNullOrEmpty(userName)) {
            return null;
        }


        // find user
        User user = userRepo.findByUsername(userName);

        // set userprincipal spring
        UserPrincipalDTO principal = new UserPrincipalDTO(user);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, null, principal.getAuthorities());
        return auth;

    }


    /**
     * this function for create response when token is (expired, bad format, etc)
     */
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.getWriter().flush();
    }


}
