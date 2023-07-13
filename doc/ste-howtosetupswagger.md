[Back](https://github.com/denitiawan/research-swagger-springboot-two)

## 3. How to implement Swagger-UI 
###  add OpenAPI librarry on Pom.xml
[Pom.xml](https://github.com/denitiawan/research-swagger-springboot-two/blob/main/backend/pom.xml)
```
   <!--  open api-->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-ui</artifactId>
            <version>1.6.10</version>
        </dependency>
```
### add Swagger setting on application.yml
[application.yml](https://github.com/denitiawan/research-swagger-springboot-two/blob/main/backend/src/main/resources/application.yml)
```
# http://localhost:5050/swagger-ui/index.html
spring:
  api-docs:
    enabled: true
```

### Create SwaggerConfig.java file
[SwaggerConfig.java](https://github.com/denitiawan/research-swagger-springboot-two/blob/main/backend/src/main/java/com/deni/app/adapter/swagger/SwaggerConfig.java)
```
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
                title = "NexSoft Demo Swagger Springboot",
                description = "Api Document & Spesification using Swagger-ui by OpenAPI",
                version = "0.0.0.1",

                // CONTACT
                contact = @Contact(
                        name = "Customer Support",
                        email = "cs@gmail.com",
                        url = "http://denitiawan.medium.com"
                ),


                // LICENSE
                license = @License(
                        name = "license name ex (MIT license)",
                        url = "http://denitiawan.medium.com"

                ),

                // TOS
                termsOfService = "Term of service"
        ),

        // LIST OF SERVER
        servers = {
                @Server(
                        description = "LOCAL ENV",
                        url = "http://localhost:5050"
                ),
                @Server(
                        description = "STAG ENV",
                        url = "http://111.222.333.444:5050"
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
// CREATE BUTTON "Authorize" on Swagger Page, for authorize all api
// Value on @SecurityScheme must be same with JWT generator when hit api (/v1/auth/login)
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

```




### Give public access for endpoint (http://localhost:5050/swagger-ui/index.html) on SecurityConfiguration.java 
[SecurityConfiguration.java](https://github.com/denitiawan/research-swagger-springboot-two/blob/main/backend/src/main/java/com/deni/app/security/config/SecurityConfiguration.java)
```
  /**
     * this function for supporting permit all access from http.authorizeRequests()
     */
    @Override
    public void configure(WebSecurity webSecurity) {
	...
	...
        webSecurity.ignoring().antMatchers("/swagger-ui", "/swagger-ui/**", "/v3/api-docs/**");
    }

 @Override
    protected void configure(HttpSecurity http) throws Exception {
	....
	....
	// swagger-ui
	http.authorizeRequests().antMatchers("/swagger-ui/**").permitAll();
	....	
	....
}
```

