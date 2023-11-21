# Getting Started

### HELP
* [general help](http://localhost:8080/api/help)
* [license](http://localhost:8080/api/help/license)
* [survey](http://localhost:8080/api/help/survey)

### Todo First
First thing to do is download Maven
* [download the latest version](https://maven.apache.org/download.cgi)
* Next put the jar file in a location on your local (example: c:/tools)
* In RAD, go to Window -> Preferences ion the upper tool bar.
* Find Maven -> Installations in the menu to the right.
* add your Maven jar and make sure it is the default selected.
* In the project, find the pom.xml
* right click the file and choose "Run As" -> "maven build..."
* In the Goals textbox, enter "clean install"
* Press the Run button at the bottom of the dialog.


### To Run The Project
* Under src/main/java right click the file NextGenPocApplication.java
* In the context menu select "Run As" -> "Java Application"
* The application will start
* ***(NOTE: each time you run the application you will need to delete the contents under the data/ directory)***

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.0-SNAPSHOT/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.0-SNAPSHOT/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.0-SNAPSHOT/reference/htmlsingle/index.html#using.devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.0-SNAPSHOT/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.0-SNAPSHOT/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.2.0-SNAPSHOT/reference/htmlsingle/index.html#actuator)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Swagger
The following URL's are for viewing Swagger UI Rest Documentation

* [swagger-ui](http://localhost:8080/api/swagger-ui/index.html)
* [API Docs rest JSon](http://localhost:8080/api/api-docs)

### Actuator
To view the Spring Boot actuator monitoring

* Example - http://localhost:9001/actuuator/health
* [end-points](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
* [actuator](http://localhost:8080/actuator)
* [actuator open-api](http://localhost:8080/actuator/openapi)
* [actuator swagger-ui](http://localhost:8080/actuator/swagger-ui)

### H2 Database

* [h2-console](http://localhost:8080/api/h2-console/)
* username = app_user
* password = tmrs_next_gen

### Login info for seed users
* User [ nextgenAdmin ] with password [ TMRS@dm1n ] : [ 6EHcy36TapgXHnN6fM7cjg== ] key [ I@mTh3W0rld ] salt [ salt_of_the_earth ] vi [ 0192837465564738 ]
* User [ lweyrich ] with password [ I@mTh3Myth! ] : [ ktHO4WqlLQ612S7xKkFysA== ] key [ I@mTh3W0rld ] salt [ salt_of_the_earth ]vi [ 0192837465564738 ]
* User [ testUserOne ] with password [ T3st1User! ] : [ uYMc2eDt+W16mZdUxf+x1g== ] key [ I@mTh3W0rld ] salt [ salt_of_the_earth ]vi [ 0192837465564738 ]
* Database User [ nextgen_user ] with password [ tmrs_next_gen ] : [ A0GZ7vAZVGQFxlcIlwO9Wg== ] key [ next_gen_database ] salt [ salt_of_the_earth ] vi [ 8164093857836501 ]

### Social Security Numbers
* SSN [ 000-00-0000 ] encrypted [ ZtDk2BGPPv7/ECNw/Ru+IA== ] key [ I@mTh3W0rld ] salt [ salt_of_the_earth ] vi [ 0192837465564738 ]
* SSN [ 666-66-6666 ] encrypted [ yLScNWvoxrv7UNAMUCGUHA== ] key [ I@mTh3W0rld ] salt [ salt_of_the_earth ] vi [ 0192837465564738 ]
* SSN [ 333-33-3333 ] encrypted [ pG7P4G6CiUOcMPc8J603LA== ] key [ I@mTh3W0rld ] salt [ salt_of_the_earth ] vi [ 0192837465564738 ]

