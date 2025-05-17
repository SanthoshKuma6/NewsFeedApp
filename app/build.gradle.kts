plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
//    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
//    id("com.google.dagger.hilt.android")
//    id("kotlin-kapt")
    id("com.google.devtools.ksp") version "2.0.21-1.0.27"
//    id("com.google.devtools.ksp")

}

android {
    namespace = "com.task.newsfeedapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.task.newsfeedapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {


        /**
         * False is best for debug builds.
         * True is best for release builds.
         */
        release {
            isMinifyEnabled = false // true when application is launch
            android.buildFeatures.buildConfig = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "https://api.nytimes.com/")
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            android.buildFeatures.buildConfig = true
            isDebuggable = true
            isMinifyEnabled = false // true when application is launch
            isShrinkResources = false  // true when application is launch
            buildConfigField("String", "BASE_URL", "\"https://api.nytimes.com/\"")
            buildConfigField(
                "String", "Saved_Signature", "\"0252d7582af33c37d50155a0ec3420d911fc085e\""
            )
            buildConfigField("boolean", "IS_DEV", "true")
            buildConfigField("boolean", "IS_QA", "false")
            buildConfigField("boolean", "IS_UAT", "false")
            buildConfigField("boolean", "IS_LIVE", "false")
            externalNativeBuild {
                cmake {
                    cppFlags

                }
            }
//        }
            flavorDimensions += listOf("customer", "environment")
            productFlavors {
                create("dev") {

                    externalNativeBuild.cmake {
                        cppFlags("-DDEVELOPMENT")
                    }
                    dimension = "environment"
//                applicationIdSuffix = ".dev"
                    versionNameSuffix = "-dev"
                    buildConfigField("String", "BASE_URL", "\"https://api.nytimes.com1\"")
                    buildConfigField("boolean", "IS_DEV", "true")
                    buildConfigField("boolean", "IS_QA", "false")
                    buildConfigField("boolean", "IS_UAT", "false")
                    buildConfigField("boolean", "IS_LIVE", "false")

                }
                create("qa") {
//                externalNativeBuild.cmake{
//                    cppFlags("-DQA")
//                }
                    dimension = "environment"
//                applicationIdSuffix = ".qa"
                    versionNameSuffix = "-qa"
                    buildConfigField("String", "BASE_URL", "\"https://api.nytimes.com2\"")
                    buildConfigField("boolean", "IS_DEV", "false")
                    buildConfigField("boolean", "IS_QA", "true")
                    buildConfigField("boolean", "IS_UAT", "false")
                    buildConfigField("boolean", "IS_LIVE", "false")

                }
                create("uat") {
//                externalNativeBuild {
//                    cmake {
//                        cppFlags("UAT")
//                    }
//                }
                    dimension = "environment"
//                applicationIdSuffix = ".uat"
                    versionNameSuffix = "-uat"
                    buildConfigField("String", "BASE_URL", "\"https://api.nytimes.com3\"")
                    buildConfigField("boolean", "IS_DEV", "false")
                    buildConfigField("boolean", "IS_QA", "false")
                    buildConfigField("boolean", "IS_UAT", "true")
                    buildConfigField("boolean", "IS_LIVE", "false")

                }
                create("production") {
//                externalNativeBuild {
//                    cmake {
//                        cppFlags("production")
//                    }
//                }
                    dimension = "customer"
//                applicationIdSuffix = ".production"
                    versionNameSuffix = "-production"
                    buildConfigField("String", "BASE_URL", "\"https://api.nytimes.com4\"")
                    buildConfigField("boolean", "IS_DEV", "false")
                    buildConfigField("boolean", "IS_QA", "false")
                    buildConfigField("boolean", "IS_UAT", "false")
                    buildConfigField("boolean", "IS_LIVE", "true")

                }


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
            buildConfig = true
        }

        externalNativeBuild {
            cmake {
                path = file("src/main/cpp/CMakeLists.txt")
            }
        }
        signingConfigs {
            debug {
            }
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.database.ktx)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.generativeai)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//
//    // Navigation for Compose
//    implementation("androidx.navigation:navigation-compose:2.8.8")
//
//    // Retrofit for network requests
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//
//    // OkHttp for HTTP requests
//    implementation("com.squareup.okhttp3:okhttp:4.9.3")
//
//    // OkHttp Logging Interceptor for debugging network requests
//    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
//
//    // Gson converter for Retrofit
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//
//    // Gson for JSON parsing
//    implementation("com.google.code.gson:gson:2.8.8")
//
//    // ViewModel KTX for easy usage of ViewModels
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")
//
//    // Paging for pagination handling
//    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
//    implementation("androidx.paging:paging-compose:1.0.0-alpha14")
//
//    // LiveData for data observation
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0")
//
//    // Activity Compose (for Compose-based activities)
//    implementation("androidx.activity:activity-compose:1.4.0")
//
//    // ViewModel Compose (for Compose-based ViewModels)
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0")
//
//    // Coil for image loading in Compose
//    implementation("io.coil-kt:coil-compose:2.1.0")
//
//    // ConstraintLayout for Compose
//    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
//
//    // Room Database for local persistence
//    implementation("androidx.room:room-runtime:2.4.2")
//    implementation("androidx.room:room-ktx:2.4.2")
//    annotationProcessor("androidx.room:room-compiler:2.4.2")
//    kapt("androidx.room:room-compiler:2.4.3")
//
//    // Compose Runtime (needed for Compose-based projects)
//    implementation("androidx.compose.runtime:runtime:1.4.0")
//
//    // Coroutines Core for asynchronous programming
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
//
//
//    implementation("androidx.paging:paging-compose:3.2.0")
//
//    implementation("com.scottyab:rootbeer-lib:0.1.0")
//
//    implementation("com.google.accompanist:accompanist-swiperefresh:0.33.2-alpha")
//
//    implementation(platform("com.google.firebase:firebase-bom:33.11.0"))
//    implementation("com.google.firebase:firebase-analytics")
//
//    // messaging
//    implementation("com.google.firebase:firebase-messaging:24.1.1")
//    implementation("com.google.firebase:firebase-messaging-ktx:24.1.1")
//
//    // notification permission
//    implementation("com.google.accompanist:accompanist-permissions:0.31.1-alpha")
//
//    // dagger
//    implementation("com.google.dagger:hilt-android:2.48")
//    kapt("com.google.dagger:hilt-compiler:2.48")
//
//    implementation("androidx.compose.material:material:1.7.8")
//    //payment gate way
//    implementation("com.razorpay:checkout:1.6.41")
//
//    // sdk
//     implementation("io.agora.rtc:full-sdk:4.0.1")
//     implementation("io.agora.rtm:rtm-sdk:1.5.0")
//     implementation("commons-codec:commons-codec:1.17.1")
//
//
//    val appcompat_version = "1.7.0"
//
//    implementation("androidx.appcompat:appcompat:$appcompat_version")
//    // For loading and tinting drawables on older versions of the platform
//    implementation("androidx.appcompat:appcompat-resources:$appcompat_version")
//
//    implementation ("com.google.dagger:dagger:2.51")
//    kapt ("com.google.dagger:dagger-compiler:2.51")
//
////    implementation("com.google.dagger:hilt-android:2.56.1")
////    ksp("com.google.dagger:hilt-android-compiler:2.56.1")




    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")

// Retrofit and OkHttp for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

// Gson for JSON parsing
    implementation("com.google.code.gson:gson:2.8.8")

// ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0")

// Paging for pagination
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("androidx.paging:paging-compose:3.2.0")

// Activity Compose
    implementation("androidx.activity:activity-compose:1.4.0")

// Coil for image loading
    implementation("io.coil-kt:coil-compose:2.1.0")

// ConstraintLayout for Compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

// Room Database
    implementation("androidx.room:room-runtime:2.4.2")
    implementation("androidx.room:room-ktx:2.4.2")
    ksp("androidx.room:room-compiler:2.7.1")
//    kapt("androidx.room:room-compiler:2.4.3")


// Compose Runtime
    implementation("androidx.compose.runtime:runtime:1.4.0")

// Coroutines for async programming
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

// RootBeer for root detection
    implementation("com.scottyab:rootbeer-lib:0.1.0")

// Swipe Refresh
    implementation("com.google.accompanist:accompanist-swiperefresh:0.33.2-alpha")

// Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.11.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-messaging-ktx")

// Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.31.1-alpha")

// Dagger
//    implementation("com.google.dagger:hilt-android:2.48")
//    kapt("com.google.dagger:hilt-compiler:2.48")

// Additionally, plain Dagger if needed
    implementation("com.google.dagger:dagger:2.51")
//    ksp("com.google.dagger:dagger-compiler:2.51")
    ksp("androidx.room:room-compiler:2.5.0")

// Material Compose
    implementation("androidx.compose.material:material:1.7.8")

// Payment Gateway
    implementation("com.razorpay:checkout:1.6.41")

// Agora SDK
    implementation("io.agora.rtc:full-sdk:4.0.1")
    implementation("io.agora.rtm:rtm-sdk:1.5.0")
    implementation("commons-codec:commons-codec:1.17.1")

// Appcompat and Core
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.appcompat:appcompat-resources:1.7.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

// ReactiveX
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

// System UI Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")



}