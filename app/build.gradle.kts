plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android") version "2.49" apply true
}

android {
    namespace = "com.example.tradernet_test_task_simplified"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tradernet_test_task_simplified"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.5"
    }
}

dependencies {
    // Core Android dependencies
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")

    // Test dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Android Architecture Components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-process:2.6.1")

    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.4.0-alpha03")
    implementation("androidx.compose.material:material:1.4.0-alpha03")
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0-alpha03")
    implementation("androidx.compose.ui:ui-tooling:1.4.0-alpha03")
    implementation("androidx.activity:activity-compose:1.8.0-alpha06")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.navigation:navigation-compose:2.5.0")
    implementation("androidx.compose.material:material-icons-extended:1.4.0-alpha03")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("androidx.compose.runtime:runtime:1.0.3")

    // Hilt for dependency injection
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-compiler:2.49")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Retrofit and OkHttp for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    // Scarlet for WebSocket connections
    implementation("com.tinder.scarlet:scarlet:0.1.10")
    implementation("com.tinder.scarlet:websocket-okhttp:0.1.10")
    implementation("com.tinder.scarlet:message-adapter-gson:0.1.10")
    implementation("com.tinder.scarlet:stream-adapter-coroutines:0.1.10")
    implementation("com.tinder.scarlet:lifecycle-android:0.1.10")
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = false
}