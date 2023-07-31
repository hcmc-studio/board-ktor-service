plugins {
    kotlin("jvm") version "1.9.0"
}

group = "studio.hcmc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":kotlin-protocol-extension"))
    implementation(project(":data-domain"))
    implementation(project(":data-transfer-object"))
    implementation(project(":exposed-entity"))
    implementation(project(":exposed-table"))
    implementation(project(":exposed-transaction-extension"))

    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
}

kotlin {
    jvmToolchain(17)
}