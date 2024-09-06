plugins {
    id("java")
    id("war")
}

group = "org.nastya"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_18
    targetCompatibility = JavaVersion.VERSION_18
}

dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    implementation("org.hibernate.orm:hibernate-core:6.6.0.Final")
    implementation("org.thymeleaf:thymeleaf:3.1.2.RELEASE")
}

tasks.withType<War> {
    archiveFileName.set("${project.name}.war")
}

tasks.test {
    useJUnitPlatform()
}