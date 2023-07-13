package com.deni.app.module.user;


import com.deni.app.common.controller.BaseController;
import com.deni.app.common.controller.Response;
import com.deni.app.common.controller.ResponseHandler;
import com.deni.app.common.service.ResponseService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user")
@Hidden
@RestController
@RequestMapping("/v1/user")
public class UserController extends BaseController {
    @Autowired
    UserServiceImpl service;


    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    @CrossOrigin
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public ResponseEntity<Response> save(
            @RequestHeader(name = header_authorization_name, required = true,
                    defaultValue = header_authorization_description) String authorization,
            @RequestBody UserDTO request) {
        ResponseService response = service.save(request);
        return ResponseHandler.createHttpResponse(response.getMessage(), response.getData(), response.getHttpStatus());
    }

    @CrossOrigin
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/update/{id}", method = {RequestMethod.PUT})
    public ResponseEntity<Response> update(
            @RequestHeader(name = header_authorization_name, required = true,
                    defaultValue = header_authorization_description) String authorization,
            @PathVariable Long id,
            @RequestBody UserDTO request) {
        ResponseService response = service.update(id, request);
        return ResponseHandler.createHttpResponse(response.getMessage(), response.getData(), response.getHttpStatus());


    }

    @CrossOrigin
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE})
    public ResponseEntity<Response> delete(
            @RequestHeader(name = header_authorization_name, required = true,
                    defaultValue = header_authorization_description) String authorization,
            @PathVariable Long id) {
        ResponseService response = service.delete(id);
        return ResponseHandler.createHttpResponse(response.getMessage(), response.getData(), response.getHttpStatus());
    }


    @CrossOrigin
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public ResponseEntity<Response> getAll(
            @RequestHeader(name = header_authorization_name, required = true,
                    defaultValue = header_authorization_description) String authorization) {
        ResponseService response = service.getAll();
        return ResponseHandler.createHttpResponse(response.getMessage(), response.getData(), response.getHttpStatus());
    }


    @CrossOrigin
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/view/{id}", method = {RequestMethod.GET})
    public ResponseEntity<Response> findById(
            @RequestHeader(name = header_authorization_name, required = true,
                    defaultValue = header_authorization_description) String authorization,
            @PathVariable Long id) {
        ResponseService response = service.findById(id);
        return ResponseHandler.createHttpResponse(response.getMessage(), response.getData(), response.getHttpStatus());
    }


}
