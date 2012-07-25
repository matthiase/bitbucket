import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "playtime"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "com.netflix.astyanax" % "astyanax" % "0.8.10",
      "com.typesafe.akka" % "akka-slf4j" % "2.0",
      "org.slf4j" % "slf4j-api" % "1.6.4",
      "xerces" % "xercesImpl" % "2.10.0",
      "net.sourceforge.nekohtml" % "nekohtml" % "1.9.15",
      "de.l3s.boilerpipe" % "boilerpipe" % "1.1.0"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here
    )

}
