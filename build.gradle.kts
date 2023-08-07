// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        val navVersion = "2.6.0"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")

    }
}

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.11" apply false
}