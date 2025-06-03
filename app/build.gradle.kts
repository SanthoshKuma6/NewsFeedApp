plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
//    alias(libs.plugins.kotlin.parcelize)
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
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
            isDebuggable = false
            isMinifyEnabled = false // true when application is launch
            isShrinkResources = false
            android.buildFeatures.buildConfig = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            applicationVariants.all {
                var outputFileName = "CustomerApp_Release"
                if (buildType.name == "release") {
                    outputs.all {
                        outputFileName = "CustomerApp_Release.apk"
                    }
                }
            }
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false // true when application is launch
            isShrinkResources = false  // true when application is launch
            android.buildFeatures.buildConfig = true
            buildConfigField(
                "String", "Saved_Signature", "\"0252d7582af33c37d50155a0ec3420d911fc085e\""
            )
        }

            flavorDimensions += listOf("environment")
            productFlavors {
                create("DEVELOPMENT") {
                    dimension = "environment"
                    applicationId = "com.task.newsfeedapp"
                    externalNativeBuild {
                        cmake {
                            cppFlags.add("-DDEVELOPMENT")
                        }
                    }
                }
                create("QA") {
                    dimension = "environment"
                    applicationId = "com.task.newsfeedapp"
                    externalNativeBuild {
                        cmake {
                            cppFlags.add("-DQA")
                        }
                    }
                }
                create("UAT") {
                    dimension = "environment"
                    applicationId = "com.task.newsfeedapp"
                    externalNativeBuild {
                        cmake {
                            cppFlags.add("-DUAT")
                        }
                    }
                }
                create("PRODCTION") {  // Add production here as well
                    dimension = "environment"
                    applicationId = "com.task.newsfeedapp"
                    externalNativeBuild {
                        cmake {
                            cppFlags.add("-DPRODCTION")
                        }
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
            viewBinding = true
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
    // Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // Retrofit and OkHttp for networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.adapter.rxjava2)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Gson for JSON parsing
    implementation(libs.gson)

    // ViewModel and LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //noinspection GradleDependency
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Paging for pagination
    //noinspection GradleDependency,GradleDependency
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    // Activity Compose
    //noinspection GradleDependency
    implementation(libs.androidx.activity.compose)

    // Coil for image loading
    implementation(libs.coil.compose)

    // ConstraintLayout for Compose
    implementation(libs.androidx.constraintlayout.compose)

    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    //noinspection GradleDependency,GradleDependency
    ksp(libs.androidx.room.compiler)


    // Compose Runtime
    implementation(libs.androidx.runtime)

    // Coroutines for async programming
    implementation(libs.kotlinx.coroutines.core)

    // RootBeer for root detection
    implementation(libs.rootbeer.lib)

    // Swipe Refresh
    //noinspection UseTomlInstead
    implementation(libs.accompanist.swiperefresh)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.messaging.ktx)

    // Permissions
    implementation(libs.accompanist.permissions)

    // Additionally, plain Dagger if needed
    //noinspection UseTomlInstead
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    // Material Compose
    implementation(libs.androidx.material)

    // Payment Gateway
    implementation(libs.checkout)

    // Agora SDK
    implementation(libs.full.sdk)
    implementation(libs.rtm.sdk)
    //noinspection UseTomlInstead,UseTomlInstead
    implementation("commons-codec:commons-codec:1.17.1")

    // Appcompat and Core
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.appcompat.resources)
    implementation(libs.androidx.core.ktx.v1120)
    implementation(libs.androidx.recyclerview)
    implementation(libs.material.v1110)
    implementation(libs.androidx.constraintlayout)

    // ReactiveX
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    // System UI Controller
    implementation(libs.accompanist.systemuicontroller)



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


}