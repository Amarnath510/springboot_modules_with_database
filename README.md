# Spring Boot - Multi Modules application with Database

Reference: [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/) and [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

# Target 
- How each application (module) access it's own database.
- Modules: inventory - Mongo DB, account - MySQL, common - coomon modules that holds POJOs.

**NOTE:** If imports(Spring Boot classes) does not work then just refresh the project root by doing --> Goto tasks (on the right most panel, select gradle) --> Right click on root project --> Regresh Gradle Project + Refresh Dependencies.

# Setup Project
- Create project with name **springboot_modules_with_database**.
**Refer:** [springboot-multi-module](https://github.com/Amarnath510/springboot_multi_module) to setup multi module application using gradle if you find difficulty in creating the project.
- Setup Mongo and MySQL database.
- For Mongo, create database **inventory**
- For MySQL, create database **db_example**

# Setup common Module
- It holds all the POJO's.
- `common/build.gradle`
```java
dependencies {
    compile('org.springframework.boot:spring-boot-starter')
    compile("org.springframework.boot:spring-boot-starter-data-mongodb")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    testCompile('org.springframework.boot:spring-boot-starter-test')
}
``` 
We need mongo and jpa (for mysql) dependencies for using `@Id`, `@GeneratedValue`, `@Entity` annotations.
- Make sure you import the respective classes based on the usage of Id or Entity.

# Setup Inventory Module
- `inventory/build.gradle`
```java
dependencies {
    compile('org.springframework.boot:spring-boot-starter-actuator')
    compile('org.springframework.boot:spring-boot-starter-web')
    compile('org.springframework.boot:spring-boot-starter-data-mongodb')
    compile project(':common')
    testCompile('org.springframework.boot:spring-boot-starter-test')
}
```
- `inventory/src/main/resources/application.properties`
```java
server.port=8300
# mongo db
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=inventory
```
- Add CRUD operations in `InventoryApplication.java`.

# Setup Account Module
- account/build.gradle
```java
dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    compile 'mysql:mysql-connector-java'
    compile project(':common')
    testCompile('org.springframework.boot:spring-boot-starter-test')
}
```
- `account/src/main/resources/application.properties`
```java
server.port=8100
# mysql db
spring.jpa.hibernate.ddl-auto=create
spring.datasource.url=jdbc:mysql://localhost:3306/db_example
spring.datasource.username=root
spring.datasource.password=
```
- Add CRUD operations in `AccountApplication.java`.
NOTE: We have used `@Entity` for `Product` to make sure Hibernate will create a table in `db_example` db. In order to scan all the entities we need to use `@EntityScan` along with our SpringBootApplication scan.
```java
@RestController
@SpringBootApplication(scanBasePackages = "com")
@EntityScan("com.*") // This is needed if we use @Entity annotation for POJOs.
@RequestMapping("/account")
public class AccountApplication {
 ...
}
```

# Build and Run
- `./gradlew build && ./gradlew :inventory:bootRun :account:bootRun`

# Conclusion
- With this turotial we have leanrt about how to have database access for each service using mongo and MySQL.
