// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "2.1.20-1.0.31" apply false
    alias(libs.plugins.serialization) apply false
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.junit5)
        classpath("com.github.bjoernq:unmockplugin:0.9.0")

    }
}