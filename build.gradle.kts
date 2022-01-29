plugins {
  `java-library`
  `maven-publish`
  signing
}

group = "com.lumiastream"
version = "0.1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val vertxVersion = "4.2.4"
val mutinyVersion = "2.6.0"
val jacksonVersion = "2.13.0"
val junitJupiterVersion = "5.7.0"

val watchForChange = "src/**/*"
val doOnChange = "${projectDir}/gradlew classes"

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  implementation("io.vertx:vertx-core")
  implementation("io.smallrye.reactive:smallrye-mutiny-vertx-core:$mutinyVersion")
  implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
  testImplementation("io.vertx:vertx-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8

  withJavadocJar()
  withSourcesJar()
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
  }
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      artifactId = "lumiastream-websocket-sdk"
      from(components["java"])
      versionMapping {
        usage("java-api") {
          fromResolutionOf("runtimeClasspath")
        }
        usage("java-runtime") {
          fromResolutionResult()
        }
      }
      pom {
        name.set("Lumiastream Websocket SDK")
        description.set("A java websocket library for Lumiastream")
        url.set("http://www.github.com/library")
        properties.set(mapOf(
        ))
        licenses {
          license {
            name.set("The Apache License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
          }
        }
        developers {
          developer {
            id.set("johnd")
            name.set("John Doe")
            email.set("john.doe@github.com")
          }
        }
        scm {
          connection.set("scm:git:git://github.com/lumiastream-websocket-sdk.git")
          developerConnection.set("scm:git:ssh://github.com/lumiastream-websocket-sdk.git")
          url.set("http://github.com/lumiastream-websocket-sdk/")
        }
      }
    }
  }
  repositories {
    maven {
      // change URLs to point to your repos, e.g. http://my.org/repo
      val releasesRepoUrl = uri(layout.buildDirectory.dir("repos/releases"))
      val snapshotsRepoUrl = uri(layout.buildDirectory.dir("repos/snapshots"))
      url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
    }
  }
}

signing {
  sign(publishing.publications["mavenJava"])
}

tasks.javadoc {
  if (JavaVersion.current().isJava9Compatible) {
    (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
  }
}
