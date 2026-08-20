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
    compileSdk = 37

    defaultConfig {
        applicationId = "com.task.newsfeedapp"
        minSdk = 24
        targetSdk = 37
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



}