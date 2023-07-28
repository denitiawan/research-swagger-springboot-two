package com.deni.app.security.response;


import com.deni.app.common.controller.Response;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenBadFormat {
    public static void response(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        Response statusResponse = Response.builder()
                .code("")
                .message("Token has bad format")
                .data("")
                .build();
        Object body = new ResponseEntity<>(statusResponse, HttpStatus.BAD_REQUEST);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(new Gson().toJson(body));
    }
}
