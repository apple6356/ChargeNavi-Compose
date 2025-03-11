import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.seo.sesac.chargenavi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.seo.sesac.chargenavi"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "NAVER_CLIENT_ID", getApiKey("NAVER_CLIENT_ID"))
        addManifestPlaceholders(mapOf("NAVER_CLIENT_ID" to getApiKey("NAVER_CLIENT_ID")))

        buildConfigField("String", "NAVER_CLIENT_SCERET", getApiKey("NAVER_CLIENT_SCERET"))

        buildConfigField("String", "NAVER_OAUTH_CLIENT_ID", getApiKey("NAVER_OAUTH_CLIENT_ID"))
        buildConfigField("String", "NAVER_OAUTH_CLIENT_SCERET", getApiKey("NAVER_OAUTH_CLIENT_SCERET"))

        buildConfigField("String", "API_KEY", getApiKey("API_KEY"))

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.datastore.preferences)
    implementation(project(":domain"))
    implementation(project(":data:firebase"))
    implementation(libs.firebase.messaging.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":data"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.messaging)

    implementation(libs.play.services.location)

    // naver map compose
    implementation(libs.naver.map.compose)
    implementation(libs.naver.map.location)

    // naver login
    implementation(files("libs/oauth-5.10.0.aar"))
    implementation(libs.lottie)
    implementation(libs.androidx.security.crypto)

    // permission
    implementation(libs.tedpermission.normal)

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
}