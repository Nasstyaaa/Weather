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
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<War> {
    archiveFileName.set("${project.name}.war")
}

tasks.test {
    useJUnitPlatform()
}