import de.undercouch.gradle.tasks.download.Download
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode
import java.net.URI
import java.nio.file.*

plugins {
  id("de.undercouch.download") version "3.3.0"
  java
}
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath("org.ow2.asm:asm-tree:5.2")
  }
}

repositories {
  jcenter()
}
dependencies {
  compileOnly("org.eclipse.jetty:jetty-webapp:9.4.7.v20170914")
  compileOnly("org.slf4j:slf4j-api:1.7.25")
}

fun fileSystem(uri: String) = fileSystem(URI.create(uri))
fun fileSystem(uri: URI) = FileSystems.newFileSystem(uri, mapOf<String, String>())

tasks {
  "wrapper"(Wrapper::class) {
    gradleVersion = "4.5"
  }

  "download"(Download::class) {
    src("https://github.com/gitbucket/gitbucket/releases/download/4.21.2/gitbucket.war")
    dest(".")
  }
  "patch" {
    dependsOn("compileJava")

    doLast {
      fileSystem("jar:" + File("gitbucket.war").toURI()).use {
        val cls = ClassNode()
        val jettyLauncher = it.getPath("JettyLauncher.class")

        Files.newInputStream(jettyLauncher).use {
          ClassReader(it).accept(cls, 0)
        }
        cls
            .methods
            .map { it as MethodNode }
            .filter {
              it.name == "main" && it.desc == "([Ljava/lang/String;)V"
            }
            .forEach {
              val instructions = it.instructions

              instructions
                  .toArray()
                  .filterIsInstance(MethodInsnNode::class.java)
                  .filter {
                    it.opcode == Opcodes.INVOKEVIRTUAL
                        && it.owner == "org/eclipse/jetty/server/Server"
                        && it.name == "start"
                        && it.desc == "()V"
                  }
                  .forEach {
                    instructions.insertBefore(it, MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "JettyServers_2imxUOOdfRpt",
                        "startOrExitJvm",
                        "(Lorg/eclipse/jetty/server/Server;)V",
                        false
                    ))
                    instructions.remove(it)
                  }
            }

        val writer = ClassWriter(0)
        cls.accept(writer)

        Files.write(jettyLauncher, writer.toByteArray(), StandardOpenOption.TRUNCATE_EXISTING)
        val jettyServersName = "JettyServers_2imxUOOdfRpt.class"
        val jettyServers = File("build")
            .resolve("classes")
            .resolve("java")
            .resolve("main")
            .resolve(jettyServersName)
            .absoluteFile
            .toPath()

        Files.copy(jettyServers, it.getPath(jettyServersName))
      }
    }
  }
}
