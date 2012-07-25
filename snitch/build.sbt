
name := "snitch"

version := "1.0"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "commons-io" % "commons-io" % "2.1",
  "org.xerial" % "sqlite-jdbc" % "3.7.2",
  "org.scalatest" %% "scalatest" % "1.6.1" % "test"
)
