organization := "org.freefeeling"
name := "scala-printit"

version := "0.0.1-SNAPSHOT"
scalaVersion := "2.11.8"

lazy val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % Test withSources()
)

libraryDependencies ++= testDependencies

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2" withSources()
)

libraryDependencies += "com.lihaoyi" % "ammonite" % "0.8.2" % "test" cross CrossVersion.full

initialCommands in (Test, console) := """ammonite.Main().run()"""
