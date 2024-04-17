import com.android.build.api.dsl.BuildFeatures

plugins {

    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    buildFeatures {
        viewBinding = true
    }
    namespace = "com.example.power"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.power"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //noinspection UseTomlInstead
    implementation("androidx.core:core-splashscreen:1.0.1")

    //noinspection UseTomlInstead
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-analytics")
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-auth:22.3.1")
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-database:20.3.1")

}