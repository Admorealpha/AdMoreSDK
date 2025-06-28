plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.alpha.admoresdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        // Build config fields - FIXED: Separate host and base URL
        buildConfigField("String", "publicKeyBase64", "\"MCowBQYDK2VuAyEAq9m6BNi+QtbXyIm/SYmZmJqof1d6xdcv/+obsEHcqSI=\"")
        buildConfigField("String", "host", "\"https://datalake.admore.app/api/\"")
        buildConfigField("String", "baseUrl", "\"http://209.38.231.139:8080/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            tasks.whenTaskAdded {
                if (name.contains("lintVitalAnalyzeRelease")) {
                    enabled = false
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
        buildConfig = true
    }

    // Resource prefix to avoid conflicts
    resourcePrefix = "admore_sdk"

    // Packaging options to handle conflicts
    packaging {
        resources {
            excludes += listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/AL2.0",
                "META-INF/LGPL2.1",
                "META-INF/*.kotlin_module"
            )
        }
    }

    lint {
        disable += setOf("NullSafeMutableLiveData", "InvalidPackage")
        abortOnError = false
        checkReleaseBuilds = false
        ignoreWarnings = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Networking - with explicit exclusions to prevent conflicts
    implementation(libs.retrofit) {
        exclude(group = "com.squareup.okhttp3")
    }
    implementation(libs.converter.gson) {
        exclude(group = "com.squareup.okhttp3")
    }
    implementation(libs.gson)
    implementation(libs.logging.interceptor)

    // Add OkHttp explicitly to avoid version conflicts
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Google Play Services
    implementation(libs.play.services.ads.identifier)

    // Koin - only expose koin-android as API
    api(libs.koin.android)
    implementation("io.insert-koin:koin-core:3.5.3")
}


afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.alpha"
                artifactId = "AdMoreSDK"
                version = "1.1.0" // Increment version for fixes

                pom {
                    name.set("AdMore SDK")
                    description.set("AdMore SDK for Android - Data collection and analytics")
                    url.set("https://github.com/Admorealpha/AdMoreSDK")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("alpha")
                            name.set("admore")
                            email.set("a.eissa@blueride.co")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/Admorealpha/AdMoreSDK.git")
                        developerConnection.set("scm:git:ssh://github.com:Admorealpha/AdMoreSDK.git")
                        url.set("https://github.com/Admorealpha/AdMoreSDK/tree/main")
                    }
                }
            }
        }
    }
}