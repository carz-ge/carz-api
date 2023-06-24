# Read Me First

The following was discovered as part of building this project:

* Spring Cloud Gateway requires Spring WebFlux, your choice of Spring Web has been replaced accordingly.

# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.6/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.6/gradle-plugin/reference/html/#build-image)
* [GraalVM Native Image Support](https://docs.spring.io/spring-boot/docs/3.0.6/reference/html/native-image.html#native-image)
* [Distributed Tracing Reference Guide](https://micrometer.io/docs/tracing)
* [Getting Started with Distributed Tracing](https://docs.spring.io/spring-boot/docs/3.0.6/reference/html/actuator.html#actuator.micrometer-tracing.getting-started)
* [Spring Integration JDBC Module Reference Guide](https://docs.spring.io/spring-integration/reference/html/jdbc.html)
* [Spring Integration JPA Module Reference Guide](https://docs.spring.io/spring-integration/reference/html/jpa.html)
* [Spring Integration R2DBC Module Reference Guide](https://docs.spring.io/spring-integration/reference/html/r2dbc.html)
* [Spring Integration Test Module Reference Guide](https://docs.spring.io/spring-integration/reference/html/testing.html)
* [Spring Integration Mail Module Reference Guide](https://docs.spring.io/spring-integration/reference/html/mail.html)
* [Spring Integration RSocket Module Reference Guide](https://docs.spring.io/spring-integration/reference/html/rsocket.html)
* [Spring Integration Security Module Reference Guide](https://docs.spring.io/spring-integration/reference/html/security.html)
* [Spring Integration WebFlux Module Reference Guide](https://docs.spring.io/spring-integration/reference/html/webflux.html)
* [Spring Integration STOMP Module Reference Guide](https://docs.spring.io/spring-integration/reference/html/stomp.html)
* [Spring Integration WebSocket Module Reference Guide](https://docs.spring.io/spring-integration/reference/html/web-sockets.html)
* [WebSocket](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#messaging.websockets)
* [Spring Batch](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#howto.batch)
* [Gateway](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/)
* [Spring Session](https://docs.spring.io/spring-session/reference/)
* [Validation](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#io.validation)
* [Spring REST Docs](https://docs.spring.io/spring-restdocs/docs/current/reference/html5/)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#howto.data-access.exposing-spring-data-repositories-as-rest)
* [Java Mail Sender](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#io.email)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Spring HATEOAS](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#web.spring-hateoas)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#appendix.configuration-metadata.annotation-processor)
* [RSocket](https://rsocket.io/)
* [Spring Data JDBC](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#data.sql.jdbc)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#using.devtools)
* [Spring Integration](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#messaging.spring-integration)
* [JDBC API](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#data.sql)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#web.reactive)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#web.security)
* [Spring Data R2DBC](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#data.sql.r2dbc)
* [Spring for GraphQL](https://docs.spring.io/spring-boot/docs/3.0.6/reference/html/web.html#web.graphql)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#howto.data-initialization.migration-tool.flyway)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#actuator)

### Guides

The following guides illustrate how to use some features concretely:

* [Using WebSocket to build an interactive web application](https://spring.io/guides/gs/messaging-stomp-websocket/)
* [Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)
* [Using Spring Cloud Gateway](https://github.com/spring-cloud-samples/spring-cloud-gateway-sample)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a Hypermedia-Driven RESTful Web Service](https://spring.io/guides/gs/rest-hateoas/)
* [Using Spring Data JDBC](https://github.com/spring-projects/spring-data-examples/tree/master/jdbc/basics)
* [Integrating Data](https://spring.io/guides/gs/integration/)
* [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access/)
* [Managing Transactions](https://spring.io/guides/gs/managing-transactions/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing data with R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)
* [Building a GraphQL service](https://spring.io/guides/gs/graphql-server/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [Configure AOT settings in Build Plugin](https://docs.spring.io/spring-boot/docs/3.0.6/gradle-plugin/reference/htmlsingle/#aot)
* [R2DBC Homepage](https://r2dbc.io)

## GraalVM Native Support

This project has been configured to let you generate either a lightweight container or a native executable.
It is also possible to run your tests in a native image.

### Lightweight Container with Cloud Native Buildpacks

If you're already familiar with Spring Boot container images support, this is the easiest way to get started.
Docker should be installed and configured on your machine prior to creating the image.

To create the image, run the following goal:

```
$ ./gradlew bootBuildImage
```

Then, you can run the app like any other container:

```
$ docker run --rm car-app-api:0.0.1-SNAPSHOT
```

### Executable with Native Build Tools

Use this option if you want to explore more options such as running your tests in a native image.
The GraalVM `native-image` compiler should be installed and configured on your machine.

NOTE: GraalVM 22.3+ is required.

To create the executable, run the following goal:

```
$ ./gradlew nativeCompile
```

Then, you can run the app as follows:

```
$ build/native/nativeCompile/car-app-api
```

You can also run your existing tests suite in a native image.
This is an efficient way to validate the compatibility of your application.

To run your existing tests in a native image, run the following goal:

```
$ ./gradlew nativeTest
```

