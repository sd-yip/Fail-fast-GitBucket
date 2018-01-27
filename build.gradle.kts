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
