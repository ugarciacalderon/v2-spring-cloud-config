## Skeleton API Rest

This api contains the basics of config that contains a project rest api 
following the MVC pattern.

### Packages
- audit: This package make update the movements over records, getting the user and time of movement on the endpoint, are specified as @Component("auditAwareImpl").
- constants: This classes contains variables what no change through the time as Strings messages of error or successful, not required specific annotations spring.
- controller: This classes is responsible for processing incoming REST API requests, preparing a model, and returning the view to be rendered as a response, are specified as @RestController.
- dto: This classes contains data structures based on properties of entities discarding properties that are not necessary to display, and that returned by the endpoint, only required @Getter and @Setter.
- entity: This classes is an abstract object of reality, is correlated with tables in database and can be persisted in the database, are specified as @Entity, @Table(name = "table_name") and required @Getter and @Setter.
- exceptionHandling (GlobalExceptionHandling): This class contains global Spring security exceptions for  exception intercept of the application, are specified as @ControllerAdvice
- exceptionHandling (GlobalExceptionHandling): This class contains global Spring security exceptions for  exception intercept of the application, are specified as @ControllerAdvice
- mapper: This classes help to transform an entity to a DTO class, not required specific annotations spring.
- respository: This classes contains the method for manipulate the records of database (methods CRUD), are specified as @Repository.
- responses: This classes contains the information of result response of endpoint, success and fail, only required @Getter and @Setter.
- services: This classes contains the logic business of application, are specified as @Service.
- resources/application.yml: This file contains the configuration of the application as server config, database connection, jpa config.
- resources/schema.sql: This file contains sql sentences to create tables.

### Notes:
- To create queries with jpa, can be created with natural language with camel case, specifying the column name on the name of method.
- This api contains H2 database: http://localhost:8080/h2-console/
- This api contains springdoc-openapi: http://localhost:8080/swagger-ui/index.html
