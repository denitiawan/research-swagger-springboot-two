package com.deni.app.module.product;


import com.deni.app.adapter.swagger.dto.HttpResponse;
import com.deni.app.common.controller.BaseController;
import com.deni.app.common.controller.Response;
import com.deni.app.common.controller.ResponseHandler;
import com.deni.app.common.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Tag(name = "product")
@RestController
@RequestMapping("/v1/product")
public class ProductController extends BaseController {
    @Autowired
    ProductServiceImpl service;


    public ProductController(ProductServiceImpl service) {
        this.service = service;
    }

    @Operation(summary = swagger_operation_name_save)
    @ApiResponses(value = {@ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))})})
    @CrossOrigin
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public ResponseEntity<Response> save(
            @RequestHeader(name = header_authorization_name, required = true,
                    defaultValue = header_authorization_description) String authorization,
            @RequestBody ProductDTO request) {
        ResponseService response = service.save(request);
        return ResponseHandler.createHttpResponse(response.getMessage(), response.getData(), response.getHttpStatus());
    }

    @Operation(summary = swagger_operation_name_update)
    @ApiResponses(value = {@ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))})})
    @CrossOrigin
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/update/{id}", method = {RequestMethod.PUT})
    public ResponseEntity<Response> update(
            @RequestHeader(name = header_authorization_name, required = true,
                    defaultValue = header_authorization_description) String authorization,
            @PathVariable Long id,
            @RequestBody ProductDTO request) {
        ResponseService response = service.update(id, request);
        return ResponseHandler.createHttpResponse(response.getMessage(), response.getData(), response.getHttpStatus());


    }

    @Operation(summary = swagger_operation_name_delete)
    @ApiResponses(value = {@ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))})})
    @CrossOrigin
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE})
    public ResponseEntity<Response> delete(@RequestHeader(name = header_authorization_name, required = true, defaultValue = header_authorization_description) String authorization,
                                           @PathVariable Long id) {
        ResponseService response = service.delete(id);
        return ResponseHandler.createHttpResponse(response.getMessage(), response.getData(), response.getHttpStatus());
    }


    @Operation(summary = swagger_operation_name_list)
    @ApiResponses(value = {@ApiResponse(content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Product.class)))})})
    @CrossOrigin
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public ResponseEntity<Response> getAll(
            @RequestHeader(name = header_authorization_name, required = true,
                    defaultValue = header_authorization_description) String authorization
    ) {
        ResponseService response = service.getAll();
        return ResponseHandler.createHttpResponse(response.getMessage(), response.getData(), response.getHttpStatus());
    }


    @Operation(summary = swagger_operation_name_view)
    @ApiResponses(value = {@ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))})})
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
