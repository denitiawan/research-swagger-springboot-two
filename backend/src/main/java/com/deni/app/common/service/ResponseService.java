package com.deni.app.common.service;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseService {


    HttpStatus httpStatus;
    int code;
    String status;
    String message;
    Object data;
}
