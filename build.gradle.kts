plugins {
    id("java")
}

group = "com.thyamix"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.minestom:minestom-snapshots:1_21_4-bb14804d42")
    implementation("ch.qos.logback:logback-classic:1.5.17")
    implementation("dev.hollowcube:schem:1.3.1")
}

tasks.test {
    useJUnitPlatform()
}