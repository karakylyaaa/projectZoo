plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.projectzoo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.projectzoo"
        minSdk = 30
        targetSdk = 35
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

/*Новые библиотеки*/

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    implementation(libs.maps)
    implementation(libs.location)

    implementation ("com.jakewharton.threetenabp:threetenabp:1.3.1")
    implementation ("com.google.zxing:core:3.4.1")  // Для генерации QR-кодов
    implementation ("com.journeyapps:zxing-android-embedded:4.2.0")  // Для интеграции с Android
    implementation ("jp.wasabeef:glide-transformations:4.3.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")
    implementation ("androidx.cardview:cardview:1.0.0")

}