import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.3.70"
}

kotlin {
    //select iOS target platform depending on the Xcode environment variables
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "SharedCode"
            }
        }
    }

    jvm("android")

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")

        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.1.1")
        implementation ("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.20.0")
        implementation ("io.ktor:ktor-client-core:1.1.2")
        //
        //implementation ("com.squareup.retrofit2:retrofit:2.7.1")
        //implementation ("com.squareup.retrofit2:converter-gson:2.7.1")
        //implementation ("android.arch.lifecycle:extensions:1.1.1")
        //implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        //
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1")
        implementation ("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
        implementation ("io.ktor:ktor-client-android:1.1.1")
    }

    sourceSets["iosMain"].dependencies {
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.1.1")
        implementation ("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:0.20.0")
        implementation ("io.ktor:ktor-client-ios:1.1.2")
    }
}

val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")

    /// selecting the right configuration for the iOS
    /// framework depending on the environment
    /// variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
        .getByName<KotlinNativeTarget>("ios")
        .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\n"
                + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                + "cd '${rootProject.rootDir}'\n"
                + "./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)