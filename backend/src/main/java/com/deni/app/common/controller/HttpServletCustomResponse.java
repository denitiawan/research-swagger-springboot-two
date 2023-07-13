package com.deni.app.common.controller;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpServletCustomResponse {


    String status;
    Object headers;
    Object body;

}
