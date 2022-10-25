# Getting Started

## Unit Tests

A Set of Unit Testing Rules according to [Michael Feathers](https://www.artima.com/weblogs/viewpost.jsp?thread=126923):

A test is not a unit test if it:

* talks to the database
* communicates across the network
* touches the file system
* can't run at the same time as any of your other unit tests
* you have to do special things to your environment (such as editing config files) to run it

## Spring Boot Test Slices

### Web Layer With @WebMvcTest

What's part of the Spring Test
Context: `@Controller, @ControllerAdvice, @JsonComponent, Converter, Filter, WebMvcConfigurer`
What's not part of the Spring Test Context: `@Service, @Component, @Repository` beans

### JPA Components With @DataJpaTest

What's part of the Spring Test Context: `@Repository, EntityManager, TestEntityManager, DataSource`
What's not part of the Spring Test Context: `@Service, @Component, @Controller` beans

### JDBC Access With @JdbcTest

What's part of the Spring Test Context: `JdbcTemplate, DataSource`
What's not part of the Spring Test Context: `@Service, @Component, @Controller, @Repository` beans

### MongoDB Access With @DataMongoTest

What's part of the Spring Test Context: `MongoTemplate, CrudRepository` for MongoDB documents
What's not part of the Spring Test Context: `@Service, @Component, @Controller`

### JSON Serialization with @JsonTest

What's part of the Spring Test Context: `@JsonComponent, ObjectMapper, Module` from Jackson or similar components when
using JSONB or GSON
What's not part of the Spring Test Context: `@Service, @Component, @Controller, @Repository`

### Spring RestTemplate With @RestClientTest

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.2/maven-plugin/reference/html/#build-image)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#production-ready)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#using-boot-devtools)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

## Run with docker
* `docker pull dma1979/spring-boot-testing:latest` to pull the image from docker hub
* `docker build -t spring-boot-testing .` to build the image
* `docker run -p 8080:8080 spring-boot-testing` to run the container
* `docker stop -t 0 $(docker ps -q)` to stop all running containers


## CI/CD

Github actions are used for CI/CD.

* The workflow is defined in `.github/workflows/ci-cd.yml`. The workflow is triggered on push to master and on pull
  requests. The workflow will build the project, run the tests and push the image to the github container registry.
* `DOCKER_USERNAME` and `DOCKER_PASSWORD` secrets are required for the workflow to work.
* `https://hub.docker.com/repository/docker/dma1979/spring-boot-testing` is the docker image repository.
