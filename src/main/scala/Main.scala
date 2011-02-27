
// scala-quickdraw
// josh.marcus@gmail.com

// create an executable jar & shell script from a scala script.

// NOTE: This script assumes a script with an object called 'Main' & a main() function.
// It doesn't support 'foo-scala.script' scripts or the Application trait.

import java.io.File

object Main {
  def main(args: Array[String]) {
    println(args(0))
    if (args.length != 1) { 
      println("Usage: quickdraw SCRIPT") 
      exit(0)
    }
    val scalaPath = args(0)
    val workingPath = Config.workingDir + "/" + Config.template 
    val workingDir = new File(workingPath)

    // checkout this project to ~/.quickdraw
    println ("Setting up working directory.  This might take a while.")
    val runtime = Runtime.getRuntime()
    runtime.exec("mkdir -p " + workingPath)

    println ("  -- Checking out template sbt repository.")
    runtime.exec("git clone %s %s".format(Config.repository, workingPath))

    println ("  -- Installing %s.".format( scalaPath))
    runtime.exec("cp %s %s/src/main/scala/".format(scalaPath, workingPath)) 

    println("  -- Building jar")
    runtime.exec("sbt update; sbt compile; sbt proguard".format(workingPath), null, workingDir)
  }
}

object Config {
  val workingDir = "/home/jmarcus/.quickdraw"
  val template = "default"
  val repository = "git@github.com:joshmarcus/scala-quickdraw.git"
}
