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
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("org.mindrot:jbcrypt:0.4")
    testImplementation("jakarta.servlet:jakarta.servlet-api:6.1.0")
    testImplementation("com.h2database:h2:2.3.232")
    testImplementation("io.quarkus:quarkus-junit5:3.14.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")

}

tasks.withType<War> {
    archiveFileName.set("${project.name}.war")
}

tasks.test {
    useJUnitPlatform()
}