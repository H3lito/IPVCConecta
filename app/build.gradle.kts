
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Kotlin serialization plugin
    kotlin("plugin.serialization") version "2.0.21"
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    alias(libs.plugins.google.gms.google.services)
    id("kotlin-kapt")


}

android {
    namespace = "com.example.ipvcconecta"
    compileSdk {
        version = release(36)
    }

    secrets{
        propertiesFileName = "apikey.properties"
        defaultPropertiesFileName = "local.properties"
    }

    defaultConfig {
        applicationId = "com.example.ipvcconecta"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"

            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}
configurations.all {
    exclude(group = "xmlpull", module = "xmlpull")
    exclude(group = "xpp3", module = "xpp3")
}


dependencies {
    //Image
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation(libs.ui)
    implementation(libs.androidx.foundation.layout)
    implementation(libs.androidx.benchmark.traceprocessor)
    implementation(libs.androidx.runtime)

    // Room Database
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // Google Maps
    implementation("com.google.maps.android:maps-compose:2.11.4")
    implementation("com.google.android.gms:play-services-maps:19.1.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // Jetpack Compose & Lifecycle
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.safe.args.generator)

    // --- FIREBASE (CONFIGURAÇÃO CORRETA) ---
    // A Plataforma (BoM) que controla as versões (33.4.0)
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))

    // Firestore (Sem versão escrita, a BoM decide)
    implementation("com.google.firebase:firebase-firestore")

    // Auth (ADICIONEI ISTO PARA CORRIGIR O TEU ERRO DO LOGIN)
    implementation("com.google.firebase:firebase-auth")

    // Livedata
    implementation(libs.androidx.compose.runtime.livedata)
    // Storage
    implementation("com.google.firebase:firebase-storage")
    // ---------------------------------------

    val nav_version = "2.9.6"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("androidx.compose.material:material-icons-extended")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}