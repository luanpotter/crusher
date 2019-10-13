import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
}

group = "xyz.luan.crusher"
version = "0.1.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    compile("com.sparkjava:spark-kotlin:1.0.0-alpha")
    compile("org.slf4j:slf4j-simple:+")
    compile("org.jetbrains.exposed:exposed:0.17.6")
    compile("org.postgresql:postgresql:42.2.1")
    compile("com.google.code.gson:gson:2.8.6")
}

task("copyToLib", Copy::class) {
    into("$buildDir/libs")
    from(configurations.compile)
}

task("stage") {
    dependsOn("build", "copyToLib")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
