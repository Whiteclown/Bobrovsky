import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

android {

    defaultConfig {
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

        compileSdkVersion(33)
        minSdk = 24
        targetSdk = 33
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName(BuildTypes.RELEASE) {
            buildConfigField("String", "BASE_URL", "\"https://kinopoiskapiunofficial.tech\"")
            isMinifyEnabled = true
            isDebuggable = false
        }

        getByName(BuildTypes.DEBUG) {
            buildConfigField("String", "BASE_URL", "\"https://kinopoiskapiunofficial.tech\"")
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    (this as ExtensionAware).configure<KotlinJvmOptions> {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    with(buildFeatures) {
        viewBinding = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

fun Project.android(configure: BaseExtension.() -> Unit) {
    extensions.configure("android", configure)
}