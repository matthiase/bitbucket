name := "scala-tailref"

version := "1.0"

scalaVersion := "2.9.1"

mainClass := Some("Application")

libraryDependencies ++= Seq(
  "commons-io" % "commons-io" % "2.1"
)

seq(ProguardPlugin.proguardSettings :_*)

proguardOptions += keepMain("Application")

