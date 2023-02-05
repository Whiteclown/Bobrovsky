plugins {
    id(Dependencies.Plugins.ANDROID_LIBRARY)
    id(Dependencies.Plugins.KOTLIN_ANDROID)
    kotlin(Dependencies.Plugins.KAPT)
    id(Dependencies.Plugins.HILT)
    `android-kotlin-convention`
}

android {
    namespace = "com.example.core"
}

dependencies {

    kapt(Dependencies.Hilt.COMPILER)
    implementation(Dependencies.Hilt.ANDROID)

    implementation(Dependencies.Network.RETROFIT)
}