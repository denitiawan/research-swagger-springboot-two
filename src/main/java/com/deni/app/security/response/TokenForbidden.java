package com.deni.app.security.response;


import com.deni.app.common.controller.Response;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * used for handling 403 FORBIDDEN (token cant access some API)
 * this class has implemented on SecurityConfiguration.java
 * see the method name : accessDeniedHandler()
 */
public class TokenForbidden implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException {
        //response.sendRedirect("/access-denied");

        Response statusResponse = Response.builder()
                .code("")
                .message("Token is forbidden for access thi API")
                .data("")
                .build();
        Object body = new ResponseEntity<>(statusResponse, HttpStatus.FORBIDDEN);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(new Gson().toJson(body));
        response.getWriter().flush();

    }
}
