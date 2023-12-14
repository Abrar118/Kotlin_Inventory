plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "me.abrar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")
    implementation("io.github.cdimascio:java-dotenv:5.2.2")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.11.0")
    implementation("ch.qos.logback:logback-classic:1.4.7")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}