import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.23"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
//    checkstyle
//    id("com.github.spotbugs") version "5.0.14"
    id("org.openapi.generator") version "6.6.0"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
    kotlin("plugin.allopen") version "1.4.32"
//    kotlin("kapt") version "1.4.32"
}

group = "ge.carapp"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

sourceSets.main {
    java.srcDirs("src/main/java", "src/main/kotlin")
}

//configure<SourceSetContainer> {
//    named("main") {
//        java.srcDir("src/main/kotlin")
//    }
//}

repositories {
    mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")
//extra["springCloudVersion"] = "2022.0.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
//    implementation("org.springframework.boot:spring-boot-starter-batch")
//    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
//    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
//    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-integration")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
//    implementation("org.springframework.boot:spring-boot-starter-mail")
//    implementation("org.springframework.boot:spring-boot-starter-rsocket")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-cache")

    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    // DB migrations
//    implementation("org.flywaydb:flyway-core")
//    compileOnly("org.flywaydb:flyway-mysql")
    // JWT
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("commons-codec:commons-codec:1.15")
    // AWS SDK
    implementation(platform("software.amazon.awssdk:bom:2.20.65"))
    implementation("software.amazon.awssdk:s3")
    implementation("software.amazon.awssdk:sso")
    implementation("software.amazon.awssdk:ssooidc")
    implementation("software.amazon.awssdk:aws-crt-client")

    // Firebase - FCM
    implementation("com.google.firebase:firebase-admin:9.1.1")

    // ULID
    implementation("com.github.f4b6a3:ulid-creator:5.2.0")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
//    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
//    implementation("org.springframework.integration:spring-integration-jdbc")
//    implementation("org.springframework.integration:spring-integration-jpa")
//    implementation("org.springframework.integration:spring-integration-mail")
//    implementation("org.springframework.integration:spring-integration-r2dbc")
//    implementation("org.springframework.integration:spring-integration-rsocket")
    implementation("org.springframework.integration:spring-integration-security")
    implementation("org.springframework.integration:spring-integration-stomp")
    implementation("org.springframework.integration:spring-integration-webflux")
    implementation("org.springframework.integration:spring-integration-websocket")
    implementation("org.springframework.security:spring-security-messaging")
//    implementation("org.springframework.security:spring-security-rsocket")
//    implementation("org.springframework.session:spring-session-jdbc")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose") // TODO

    // DB drivers
//    runtimeOnly("mysql:mysql-connector-java:8.0.33")
    runtimeOnly("org.postgresql:postgresql")
//    runtimeOnly("org.postgresql:r2dbc-postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testImplementation("org.springframework.integration:spring-integration-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
    testImplementation("org.springframework.security:spring-security-test")
}

//dependencyManagement {
//    imports {
//        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
//    }
//}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

springBoot {
    buildInfo()
}

graalvmNative {
    toolchainDetection.set(true)
    binaries {
        named("main") {
            javaLauncher.set(javaToolchains.launcherFor {
                languageVersion.set(JavaLanguageVersion.of(17))
                vendor.set(JvmVendorSpec.matching("Oracle Corporation"))
            })
        }
    }
}

//graalvmNative {
//    binaries {
//        all {
//            resources.autodetect()
//        }
//    }
//}

//nativeBuild {
//    buildArgs('-H:ReflectionConfigurationFiles=../../../src/main/resources/reflection-config.json')
//}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    project.property("snippetsDir")?.let { outputs.dir(it) }
}

tasks.asciidoctor {
    project.property("snippetsDir")?.let { inputs.dir(it) }
    dependsOn(tasks.test)
}
