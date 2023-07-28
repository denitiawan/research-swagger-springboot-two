package com.deni.app.module;

import com.deni.app.common.controller.BaseController;
import com.deni.app.common.controller.Response;
import com.deni.app.common.controller.ResponseHandler;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Tag(name = "test")
@Hidden
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {


    @Operation(summary = "List of Token (FAILED)")
    @CrossOrigin
    @RequestMapping(method = {RequestMethod.GET}, value = "/test_token_failed")
    public ResponseEntity<Response> login() {

        HashMap<String, String> tokenFailed = new HashMap<String, String>();
        tokenFailed.put("expired", "c0bb6ebafe8c13d20028172c813dd2bb.eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NzYzMzM0OH0.lmN4_mVd8mYnPEQI4sd1-09E7_4EPoflDrW8RbPXYCTO8OMYAHDU7iiMQnHghBSOixI9kAj1HDd9k1RY_k00Sg");
        tokenFailed.put("bad_format", "1111111111111111111111");

        ResponseEntity<Response> httpResponse = ResponseHandler.createHttpResponse(HttpStatus.OK.toString(), tokenFailed, HttpStatus.OK);
        return httpResponse;

    }


}
