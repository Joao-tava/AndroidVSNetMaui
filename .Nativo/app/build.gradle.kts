plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.androidx.navigation.safeargs.kotlin)
    id("kotlin-parcelize") // Adicionado o plugin kotlin-parcelize
}

android {
    namespace = "com.example.nativo"
    compileSdk = 36
    // Alterado para 34

    defaultConfig {
        applicationId = "com.example.nativo"
        minSdk = 24
        targetSdk = 34 // Alterado para 34
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    // Necessário para Data Binding ou View Binding se usados, mas não explicitamente neste projeto.
    // buildFeatures {
    //    viewBinding = true
    // }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity) // Usa o alias de libs.versions.toml
    implementation(libs.androidx.constraintlayout)

    // ViewModel e LiveData usando aliases
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Navegação usando aliases
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Retrofit e Gson usando aliases
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Coroutines usando alias
    implementation(libs.kotlinx.coroutines.android)

    // Play Services Location usando alias
    implementation(libs.google.play.services.location)

    // Testes Unitários
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing) // Para InstantTaskExecutorRule
    testImplementation(libs.mockito.core)          // Para Mockito
    testImplementation(libs.mockito.kotlin)        // Para Mockito-Kotlin

    // Testes Instrumentados
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}