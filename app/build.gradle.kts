plugins {
    id("com.android.application")
}

android {
    signingConfigs {
        create("reless") {
            storeFile = file("C:\\Users\\20gns\\Desktop\\lotto\\key\\lottonakey.jks")
            storePassword = "lottona!gns214"
            keyAlias = "lottona"
            keyPassword = "lottona!gns214"
        }
    }
    namespace = "yeong.wish.lotto"
    compileSdk = 34

    defaultConfig {
        applicationId = "yeong.wish.lotto"
        minSdk = 24
        targetSdk = 34
        versionCode = 5
        versionName = "1.1.1.1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("reless")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("com.google.android.gms:play-services-ads:22.6.0")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.10.0")
    implementation("com.android.volley:volley:1.2.0")
    implementation("org.jsoup:jsoup:1.17.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}