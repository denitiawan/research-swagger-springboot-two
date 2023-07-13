# research-swagger-springboot-two
research swagger in springboot 2


# install lib on pom

# setting on application.yml

# SwaggerConfig file
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

# Annotation on Controller
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
	
	

