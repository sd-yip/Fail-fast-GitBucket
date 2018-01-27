import de.undercouch.gradle.tasks.download.Download

plugins {
  id("de.undercouch.download") version "3.3.0"
}
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath("org.ow2.asm:asm:5.2")
  }
}
tasks {
  "wrapper"(Wrapper::class) {
    gradleVersion = "4.5"
  }

  val download = "download"(Download::class) {
    src("https://github.com/gitbucket/gitbucket/releases/download/4.21.2/gitbucket.war")
    dest(".")
  }
  "patch" {
    dependsOn(download)

    doLast {
    }
  }
}
