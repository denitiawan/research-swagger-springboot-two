package com.deni.app.common.controller;


import org.springframework.http.ResponseEntity;

public interface BaseControllerContract {
    void success(UserInfoRequestDto userInfoRequestDto);

    void failed(ResponseEntity response);
}
