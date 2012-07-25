name := "distiller"

version := "0.1"

scalaVersion := "2.9.1"

mainClass := Some("Distiller")

libraryDependencies ++= Seq(
  "xerces" % "xercesImpl" % "2.10.0",
  "net.sourceforge.nekohtml" % "nekohtml" % "1.9.15",
  "de.l3s.boilerpipe" % "boilerpipe" % "1.1.0"
)

seq(ProguardPlugin.proguardSettings :_*)

proguardOptions ++= Seq(
    keepMain("Distiller"),
    "-keep class org.cyberneko.html.xercesbridge.*"
)

makeInJarFilter <<= (makeInJarFilter) {
  (makeInJarFilter) => {
    (file) => file match {
      case "nekohtml-1.9.15.jar" => 
        makeInJarFilter(file) +",!org/cyberneko/html/HTMLElements*,!org/cyberneko/html/HTMLTagBalancer*"
      case _ => makeInJarFilter(file)
    }
  }
}
