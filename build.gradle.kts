// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application")  apply false
    id("com.android.library") apply false
    id("org.jetbrains.kotlin.android") apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.Plugins.ANDROID_GRADLE)
        classpath(Dependencies.Plugins.KOTLIN_GRADLE)
        classpath(Dependencies.Plugins.HILT_GRADLE)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}