package com.deni.app.adapter.swagger.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpResponse {
    String status;
    Object headers;
    Body body;


    @Schema
    @Hidden
     class Body {
        String code;
        String message;
        Object data;

         public String getCode() {
             return code;
         }



         public String getMessage() {
             return message;
         }



         public Object getData() {
             return data;
         }

     }

}



