package com.deni.app.common.service;

import org.springframework.http.HttpStatus;

public class ResponseServiceHandler {


    public static ResponseService createResponse(String message, Object data, HttpStatus httpStatus) {
        return new ResponseService(
                httpStatus,
                httpStatus.value(),
                httpStatus.toString(),
                message,
                data);
    }


}
