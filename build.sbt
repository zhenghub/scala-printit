organization := "org.freefeeling"
name := "scala-printit"

version := "0.0.1-SNAPSHOT"
scalaVersion := "2.11.8"

lazy val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % Test
)


libraryDependencies ++= testDependencies