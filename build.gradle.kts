import com.vanniktech.maven.publish.SonatypeHost

plugins {
  `java-library`
  id("com.vanniktech.maven.publish") version "0.17.0"
  id("org.jetbrains.dokka") version "1.6.10"
}

group = "com.lumiastream"

version = "0.2.0"

repositories { mavenCentral() }

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:4.2.4"))
  implementation("io.vertx:vertx-core")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.0")
  testImplementation("io.vertx:vertx-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")

  // Signing
  implementation("com.vanniktech:gradle-maven-publish-plugin:0.17.0")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {}
}

mavenPublish { sonatypeHost = SonatypeHost.S01 }
