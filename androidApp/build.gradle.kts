plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.6.21"

//    id("dagger.hilt.android.plugin")
//    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.kast.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.kast.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    val composeUiVersion = "1.2.1"
    val composeActivityVersion = "1.6.0"
    val composeMaterial3Version = "1.0.0-beta03"
    val composeMaterialVersion = "1.2.1"
    val composeNavigationVersion = "2.5.2"
    val hiltComposeNavigationVersion = "1.0.0"
    val coilVersion = "2.2.1"
    val coroutinesVersion = "1.6.4"
    val accompanistVersion = "0.25.1"
    val lifecycleVersion = "2.6.0-alpha02"
    val hiltVersion = "2.43.2"
    val koinCoreVersion = "3.2.1"
    val koinAndroidComposeVersion = "3.2.1"
    val kotlinSerializationVersion = "1.4.0"
    val paging3ComposeVersion = "1.0.0-alpha16"

    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")
    implementation("androidx.compose.foundation:foundation:$composeUiVersion")
    implementation("androidx.activity:activity-compose:$composeActivityVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeUiVersion")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // Coil
    implementation("io.coil-kt:coil-compose:$coilVersion")

    //------------------------- Accompanist -------------------------
    // SystemUiController
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
    // Navigation Material
    implementation("com.google.accompanist:accompanist-navigation-material:$accompanistVersion")
    // Navigation Animation
    implementation("com.google.accompanist:accompanist-navigation-animation:$accompanistVersion")
    // Insets
    implementation("com.google.accompanist:accompanist-insets:$accompanistVersion")
    // insets-ui
    implementation("com.google.accompanist:accompanist-insets-ui:$accompanistVersion")
    //pager
    implementation("com.google.accompanist:accompanist-pager:$accompanistVersion")
    //------------------------- Accompanist -------------------------

    // Material 3 Compose
    implementation("androidx.compose.material3:material3:$composeMaterial3Version")
    implementation("androidx.compose.material3:material3-window-size-class:$composeMaterial3Version")
    implementation("androidx.compose.material:material:$composeMaterialVersion")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:$composeNavigationVersion")

//    //Hilt
//    implementation("com.google.dagger:hilt-android:$hiltVersion")
//    kapt("com.google.dagger:hilt-compiler:$hiltVersion")
//    kapt("androidx.hilt:hilt-compiler:1.0.0")
//
//    // Hilt Navigation Compose
//    implementation("androidx.hilt:hilt-navigation-compose:$hiltComposeNavigationVersion")

    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    // Lifecycle-aware flow collecting
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")

    // Koin
    implementation("io.insert-koin:koin-core:$koinCoreVersion")
    implementation("io.insert-koin:koin-android:$koinCoreVersion")
    // Koin for Compose
    implementation("io.insert-koin:koin-androidx-compose:$koinAndroidComposeVersion")

    //Kotlin Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")

    // Paging3 for Compose
    implementation("androidx.paging:paging-compose:$paging3ComposeVersion")
}