package com.deni.app.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {


    public static ResponseEntity createHttpResponse(String message, Object data, HttpStatus httpStatus) {
        return new ResponseEntity<>(
                new HttpServletCustomResponse(
                        httpStatus.toString(),
                        "",
                        new Response(
                                "",
                                message,
                                data)),
                httpStatus);
    }

}
