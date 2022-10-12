import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.7.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.4.30"
    application
}

group = "loli.ball"
version = "1.0.0"

val projectMainClass = "loli.ball.mail.RestfulServerKt"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
    implementation("org.nanohttpd:nanohttpd:2.3.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set(projectMainClass)
}

tasks.jar {
    excludes += "META-INF/BC1024KE.DSA"
    excludes += "META-INF/BC1024KE.SF"
    excludes += "META-INF/BC2048KE.DSA"
    excludes += "META-INF/BC2048KE.SF"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes(mapOf("Main-Class" to "loli.ball.mail.MailHttpPlugin"))
    }
    from(configurations.runtimeClasspath.get().mapNotNull {
        if (it.name.contains("annotations") ||
            it.name.contains("kotlin-stdlib")
        ) {
            println("excludes $it")
            null
        } else {
            if (it.isDirectory) it else zipTree(it)
        }
    })
    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
    from(sourcesMain.output)
}