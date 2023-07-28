package com.deni.app.common.controller;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {

    String code;
    String message;
    Object data;


}
