import org.gradle.internal.declarativedsl.parsing.main

plugins {
    id("java")
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter") // Testing con JUnit
    implementation("org.springframework.boot:spring-boot-starter-security") // Para el auth
    implementation("org.springframework.boot:spring-boot-starter-web") // Para el mapeo de metodos HTTP
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // Para la persistencia de datos/entidades
    implementation("org.springframework.boot:spring-boot-starter-validation") // Para validar parametros
    testImplementation("org.springframework.boot:spring-boot-starter-test") // Para pruebas unitarias
    testImplementation("org.springframework.security:spring-security-test") // Para security en pruebas
    testImplementation("org.mockito:mockito-core")
    implementation("org.postgresql:postgresql")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
}

tasks.test {
    useJUnitPlatform()
}