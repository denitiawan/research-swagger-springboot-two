package com.deni.app.adapter.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(

        // API DOC INFORMATION
        info = @Info(

                // APP INFO
                title = "Demo Swagger-UI on Springboot2 for Nexsoft Project",
                description = "Implement swagger-ui on Go project with Gorilla Mux (web framework) + JWT Authorization",
                version = "0.0.0.1",

                // CONTACT
                contact = @Contact(
                        name = "",
                        email = "",
                        url = "http://denitiawan.medium.com"
                ),


                // LICENSE
                license = @License(
                        name = "",
                        url = "http://denitiawan.medium.com"

                ),

                // TOS
                termsOfService = "Term of service"
        ),

        // LIST OF SERVER
        servers = {
                @Server(
                        description = "LOCAL ENV",
                        url = "http://localhost:6060"
                ),
                @Server(
                        description = "STAG ENV",
                        url = "http://IPADDRESS:PORT"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "http://IPADDRESS:PORT"
                )
        },

        // REGISTER @SecurityRequirement to All API (endPoints)
        // name must be same with @SecurityScheme
        // will be added on http header when request api with swagger
        security = {
                @SecurityRequirement(name = "Authorization")
        },

        // MAKE TAG STRUCTURE
        tags = {
                @Tag(name = "login"),
                @Tag(name = "product"),


        }
)

/**
 * CREATE BUTTON "Authorize" on Swagger Page, for authorize all api
 * Value on @SecurityScheme must be same with JWT generator when hit api (/v1/auth/login)
 * Security Schema type : apiKey
 */
//@SecurityScheme(
//        name = "Authorization",
//        description = "Get JWT Token from hit api (v1/auth/login)",
//        scheme = "Authorization",
//        type = SecuritySchemeType.APIKEY,
//        in = SecuritySchemeIn.HEADER
//)


/**
 * CREATE BUTTON "Authorize" on Swagger Page, for authorize all api
 * Value on @SecurityScheme must be same with JWT generator when hit api (/v1/auth/login)
 * Security Schema type : http (Bearer)
 */
@SecurityScheme(
        name = "Authorization",
        description = "Get Bearer JWT Token from hit api (v1/auth/login)",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}