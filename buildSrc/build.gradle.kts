plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.3.1")
    implementation(kotlin("gradle-plugin", version = "1.6.10"))
    implementation("com.squareup:javapoet:1.13.0")
}