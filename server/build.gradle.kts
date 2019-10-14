import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
}

group = "xyz.luan.crusher"
version = "0.1.0"

repositories {
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.sparkjava:spark-kotlin:1.0.0-alpha")
    implementation("org.slf4j:slf4j-simple:+")
    implementation("org.jetbrains.exposed:exposed:0.17.6")
    implementation("org.postgresql:postgresql:42.2.1")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.google.firebase:firebase-admin:6.10.0")
    implementation("com.h2database:h2:1.4.199")

    testImplementation("junit:junit:4.12")
}

task("copyToLib", Copy::class) {
    into("$buildDir/libs")
    from(configurations.compileClasspath)
}

task("decryptSecrets", Exec::class) {
    workingDir(projectDir)
    commandLine("sh", "./cmds/heroku-unpack.sh")
}

task("stage") {
    outputs.upToDateWhen { false }
    dependsOn("clean", "build", "copyToLib", "decryptSecrets")
    tasks.findByName("decryptSecrets")?.mustRunAfter("clean")
    tasks.findByName("build")?.mustRunAfter("decryptSecrets")
    tasks.findByName("copyToLib")?.mustRunAfter("build")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
