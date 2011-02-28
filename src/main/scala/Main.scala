
// scala-quickdraw
// josh.marcus@gmail.com

// create an executable jar & shell script from a scala script.

// NOTE: This script assumes a script with an object called 'Main' & a main() function.
// It doesn't support 'foo-scala.script' scripts or the Application trait.

import java.io.{File,FileWriter}

import Process._

object Main {
  def main(args: Array[String]) {
    println(args(0))
    if (args.length != 2) { 
      println("Usage: quickdraw [path to scala] [name of output script]") 
      exit(0)
    }
    val scalaPath = args(0)
    val scriptName = args(1)

    val workingPath = Config.workingDir + "/" + Config.template 
    val workingDir = new File(workingPath)

    println("quickdraw: building %s from %s".format(scriptName, scalaPath))

    // checkout this project to ~/.quickdraw
    println ("Setting up working directory: %s".format(workingDir.toString()))

    val runtime = Runtime.getRuntime()
    runtime.exec("mkdir -p " + workingPath)

    println ("  -- Checking out template sbt repository.")
    runtime.exec("git clone %s %s".format(Config.repository, workingPath))

    println ("  -- Installing %s.".format( scalaPath))
    runtime.exec("cp %s %s/src/main/scala/".format(scalaPath, workingPath)) 

    println("  -- Building jar")
    val p = new ProcessBuilder("sbt", "proguard").directory(workingDir).start()
    p.waitFor()

    println("  -- Copying jar to local directory") 
    runtime.exec("cp %s/target/scala_2.8.1/quickdraw-1.0.min.jar  ./%s.jar".format( workingPath, scriptName))

    println("  -- Generating shell script")
    val f = new FileWriter(scriptName)
    f.write("java -jar %s.jar".format(scriptName))
    f.close()

    runtime.exec("chmod 755 %s".format(scriptName))

    println("Generated %s.jar and %s.".format(scriptName,scriptName))
    println("To run %s, copy both files to the same directory in your path.".format(scriptName))

  }
}

object Config {
  val workingDir = "/home/jmarcus/.quickdraw"
  val template = "default"
  val repository = "git@github.com:joshmarcus/scala-quickdraw.git"
}
