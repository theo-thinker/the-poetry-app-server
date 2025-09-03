import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.2.10"
    kotlin("plugin.spring") version "2.2.10"
}

group = "com.sakura"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Spring Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework.security:spring-security-web")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.7")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.7")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.7")

    // MinIO
    implementation("io.minio:minio:8.5.17")

    // IP2Region
    implementation("org.lionsoul:ip2region:2.7.0")

    // WebSocket
    implementation("org.java-websocket:Java-WebSocket:1.5.3")

    // MyBatis Plus
    implementation("com.baomidou:mybatis-plus-spring-boot3-starter:3.5.12")
    implementation("com.baomidou:mybatis-plus-generator:3.5.12")
    implementation("com.baomidou:mybatis-plus-annotation:3.5.12")
    implementation("com.baomidou:mybatis-plus-jsqlparser:3.5.14")

    // Database
    runtimeOnly("com.mysql:mysql-connector-j:9.4.0")
    implementation("com.alibaba:druid-spring-boot-starter:1.2.23")

    // API Documentation
    implementation("com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter:4.5.0")

    // JSON Processing
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // Commons
    implementation("org.apache.commons:commons-lang3:3.18.0")
    implementation("commons-codec:commons-codec:1.16.0")
    implementation("cn.hutool:hutool-all:5.8.40")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")

    // SLF4J for logging
    implementation("org.slf4j:slf4j-api:2.0.16")

    // Development Tools
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.0")
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-parameters", "--enable-preview", "-encoding", "UTF-8"))
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("--enable-preview")
}

tasks.withType<JavaExec> {
    jvmArgs("--enable-preview")
}

// 设置编译选项
tasks.compileJava {
    options.compilerArgs.addAll(
        listOf(
            "-parameters",
            "-Xlint:unchecked",
            "-Xlint:deprecation"
        )
    )
}

// Jar 配置
tasks.jar {
    enabled = false
    archiveClassifier = ""
}

tasks.bootJar {
    enabled = true
    archiveClassifier = ""
    archiveFileName = "${project.name}-${project.version}.jar"
}