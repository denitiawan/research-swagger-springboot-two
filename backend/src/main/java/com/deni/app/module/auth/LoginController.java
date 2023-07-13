package com.deni.app.module.auth;

import com.deni.app.common.controller.BaseController;
import com.deni.app.common.controller.Response;
import com.deni.app.common.controller.ResponseHandler;
import com.deni.app.common.service.ResponseService;
import com.deni.app.module.product.ProductDTO;
import com.deni.app.security.dto.TokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Tag(name = "login")
@RestController
@RequestMapping("/v1/auth")
public class LoginController extends BaseController {

    @Autowired
    LoginServiceImpl loginService;

    @Operation(summary = "Generate JWT Token")
    @ApiResponses(value = {@ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TokenDTO.class))})})
    @CrossOrigin
    @RequestMapping(method = {RequestMethod.POST}, value = "/login")
    public ResponseEntity<Response> login(@RequestBody LoginDTO request) {
        ResponseService response = loginService.login(request);
        ResponseEntity<Response> httpResponse = ResponseHandler.createHttpResponse(response.getMessage(), response.getData(), response.getHttpStatus());
        return httpResponse;

    }


}
