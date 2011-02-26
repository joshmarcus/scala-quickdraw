import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) with ProguardProject {
  
  //project name
  override val artifactID = "myprogram"

  //program entry point
  override def mainClass: Option[String] = Some("ikvmtest")

  //proguard
  override def proguardOptions = List(
    "-keepclasseswithmembers public class * { public static void main(java.lang.String[]); }",
    "-dontoptimize",
    "-dontobfuscate",
    proguardKeepLimitedSerializability,
    proguardKeepAllScala,
    "-keep interface scala.ScalaObject"
  )
  override def proguardInJars = Path.fromFile(scalaLibraryJar) +++ super.proguardInJars
}

