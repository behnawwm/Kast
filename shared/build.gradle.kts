plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.6.21"

    // Sqldeligt
    id("com.squareup.sqldelight")

    // Ksp
//    id("com.google.devtools.ksp") version "1.6.21-1.0.6"
}

kotlin {
    android()
//    ios()
    // Note: iosSimulatorArm64 target requires that all dependencies have M1 support
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }

    val ktorVersion = "2.0.2"
    val sqldelightVersion = "1.5.3"
    val lifecycleVersion = "2.6.0-alpha02"
    val koinCoreVersion = "3.2.1"
    val koinAnnotationsVersion = "1.0.3"

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

                // Ktor
                implementation("io.ktor:ktor-client-json:${ktorVersion}")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:${ktorVersion}")
                implementation("io.ktor:ktor-client-logging:${ktorVersion}")

                // Sqldelight
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.4.0")
                implementation("com.squareup.sqldelight:runtime:$sqldelightVersion")
                implementation("com.squareup.sqldelight:coroutines-extensions:$sqldelightVersion")

                // Koin
                // Koin Core
                implementation("io.insert-koin:koin-core:$koinCoreVersion")
                // Koin Annotations
//                implementation("io.insert-koin:koin-annotations:$koinAnnotationsVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                // Ktor
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
                // LiveData
                implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
                // Lifecycles only (without ViewModel or LiveData)
                implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")

                // Saved state module for ViewModel
                implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")

                // Sqldelight
                implementation("com.squareup.sqldelight:android-driver:$sqldelightVersion")
                implementation("com.squareup.sqldelight:android-paging-extensions:$sqldelightVersion")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidTest by getting
        val iosTest by creating {
            dependsOn(commonTest)
        }
        val iosMain by creating {
            dependsOn(commonMain)

            dependencies {
                // Ktor
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")

                // Sqldelight
                implementation("com.squareup.sqldelight:native-driver:$sqldelightVersion")
            }
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }
    }
}

android {
    namespace = "com.example.kast"
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}

kotlin.targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java) {
    binaries.all {
        binaryOptions["memoryModel"] = "experimental"
    }
}
sqldelight {
    database("KastDb") {
        packageName = "com.example.kast"
    }
}

//val koinAnnotationsVersion = "1.0.3"

//dependencies {
//    add("kspCommonMainMetadata", "io.insert-koin:koin-ksp-compiler:$koinAnnotationsVersion")
//    add("kspAndroid", "io.insert-koin:koin-ksp-compiler:$koinAnnotationsVersion")
//    add("kspIosX64", "io.insert-koin:koin-ksp-compiler:$koinAnnotationsVersion")
//    add("kspIosSimulatorArm64", "io.insert-koin:koin-ksp-compiler:$koinAnnotationsVersion")
//}