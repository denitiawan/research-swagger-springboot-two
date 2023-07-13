In this repo, have example codes for how to implementation `Swagger-UI` on Springboot 2** Project.


## Overviews
- [Run docker compose](#1-run-docker-compose)
- [Run Application](#2-run-application)
- [How to implement Swagger-UI](#3-how-to-implement-swagger-ui)

## 1. Run Docker Compose
- run `docker-compose up -d` on terminal, for create `mysql docker container`
- make sure `Docker container` success to create	

## 2. Run Application
- open `backend` folder on `IntelijIDEA` or Other Editor
- run project
- open `Swagger-ui` page on link (http://localhost:5050/swagger-ui/index.html)
![image](https://github.com/denitiawan/research-swagger-springboot-two/assets/11941308/809dfb36-29da-450b-be53-08fe0a1269d3)




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








### SwaggerConfig file
- API DOC INFORMATION
	we can add information all about application such as (name, email, url web, license, etc)

- LIST OF SERVER
    we can have multiple list of server such as (Local ENV, Prod Env) for testing and execute the APIs
	
- REGISTER @SecurityRequirement to All API (endPoints), 
    name must be same with @SecurityScheme
	will be added on http header when request api with swagger

- CREATE BUTTON "Authorize" on Swagger Page, for authorize all api
    Value on @SecurityScheme must be same with JWT generator when hit api (/v1/auth/login)    
  
- MAKE TAG STRUCTURE
   for making structure and manage of controller / taging

### Annotation on Controller
- @Tag(name = "warehouse")
    give name /taging of controller
	
- @Operation(summary = swagger_operation_name_save)
    give name of endpoint

- @Hidden
   hidden controller will not showing on swagger	
   
   
   

# keuntungan
- swagger dapat mendapatkan update secara realtime ketika ada perubahan pada backend, seperti (url, requestBody, requestHeader, responseBody, httpMethod, dll)
- setiap end point dapat di test seperti halnya pada postman
- swagger support melakukan validasi authorize pada setiap endpoint yang sudah implementasi JWT authentication
- swagger support membuat list environtment untuk melakukan testing (local, prod, dll)
- swagger dapat melakukan import collection ke dalam postman
  copy url swagger(/v3/api-docs) > open postman > import > link > paste url swagger > continue >
   
   
   
   
# kekurangan & problem
- jika url swagger tidak di proteksi dengan security page (user & password), maka yang terjadi url swagger dapat diakses oleh siapapun, dan dapat di execute
- (tampilan swagger akan panjang kebawah)ketika jumlah controller sangat banyak pada aplikasi(misal lebih dari 100 controller), dan 1 controller memiliki 10 enpoint
- belum menemukan solusi untuk grouping tag atau nested tag(controller), grouping tag ini cukup membantu untuk mengatur susunan controllernya per modul
- import collection swagger, enpoint yang di generate berantakan penamaanya dan penempatan foldernya, dan juga ada beberapat yang missing seperti http header


# Kesimpulan
- swagger cocok jika digunakan sebagai media untuk documentasi API yang berupa web, dan butuh realtime untuk mendapatkan perubahan, untuk kepentingan (review, documentasi, dll)
	
	

