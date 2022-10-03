buildscript {
    val sqlDelightVersion = "1.5.3"

    dependencies {
        classpath("com.android.tools.build:gradle:7.3.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.43.2")
        // Sqldelight
        classpath("com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion")
    }
}
plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.2.1").apply(false)
    id("com.android.library").version("7.2.1").apply(false)
    kotlin("android").version("1.7.10").apply(false)
    kotlin("multiplatform").version("1.7.10").apply(false)
    id("com.squareup.sqldelight").version("1.5.3").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
