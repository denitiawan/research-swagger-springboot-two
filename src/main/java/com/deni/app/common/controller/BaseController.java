package com.deni.app.common.controller;


import com.auth0.jwt.JWT;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.deni.app.security.filter.JwtConstants.*;

@RestController
@RequestMapping
public class BaseController {


    public ResponseEntity responseEntity;
    public final String header_authorization_name = TOKEN_X_KEY;
    public final String header_authorization_description = "JWT Token from Authorize button";


    String headerIsNotValid(String header) {
        return String.format("%s value is not valid on header", header);
    }

    public static String authorizationTokenHasExpired() {
        return String.format("%s Token has expired", "");
    }


    public void headerValidation(String authorization, BaseControllerContract callback) {

        String replaceBearer = authorization.replace(TOKEN_PREFIX_BEARER, "");
        String token = replaceBearer.replace(TOKEN_PREFIX, "");

        // success
        UserInfoRequestDto dto;
        try {
            //String jetToken = authorization.replace(TOKEN_PREFIX, "");
            String jwtUsername = JWT.require(HMAC512(TOKEN_SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();

            dto = UserInfoRequestDto.builder()
                    .username(jwtUsername)
                    .requestDate(new Date())
                    .build();

        } catch (Exception e) {
            callback.failed(ResponseHandler.createHttpResponse(
                    headerIsNotValid(header_authorization_name),
                    "", HttpStatus.BAD_REQUEST));

            return;
        }
        callback.success(dto);

    }


    /**
     * SWAGGER STANDARIZATIONS
     */

    public final String swagger_operation_name_save = "Save Data";
    public final String swagger_operation_name_update = "Update Data";
    public final String swagger_operation_name_delete = "Delete Data";
    public final String swagger_operation_name_view = "View Data by ID";
    public final String swagger_operation_name_list = "List All";
    public final String swagger_operation_name_delete_all = "Delete All";
    public final String swagger_operation_name_search = "Search";
    public final String swagger_operation_name_search_filter = "Search With Filter";
    public final String swagger_operation_name_initpagging = "Initialization For Pagging";
    public final String swagger_operation_name_paggingsort = "Pagging and Sort";
    public final String swagger_operation_name_dropdown = "Dropdown List";

}
