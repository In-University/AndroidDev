plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.googleservices)

//    id("com.google.gms.google-services") version "4.4.2" apply false
//    id("com.android.application")
//    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.shortvideo_firebase"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.shortvideo_firebase"
        minSdk = 24
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

    implementation("com.google.android.gms:play-services-auth:20.4.1")

    // Firebase dependencies
    implementation("com.google.firebase:firebase-auth:21.1.0")
    implementation("com.google.firebase:firebase-database:20.1.0")
    implementation(platform("com.google.firebase:firebase-bom:31.2.3"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-firestore:24.4.5")
    implementation("com.google.firebase:firebase-storage:20.1.0")
    implementation("com.firebaseui:firebase-ui-database:8.0.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//Gson
    implementation("com.google.code.gson:gson:2.10.1")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    apply(plugin = "com.google.gms.google-services")

}