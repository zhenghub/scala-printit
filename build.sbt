organization := "org.freefeeling"
name := "scala-printit"

version := "0.0.1-SNAPSHOT"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2" withSources(),
  "org.scalatest" %% "scalatest" % "2.2.4" % Test withSources()
)

libraryDependencies += "com.lihaoyi" % "ammonite" % "0.8.2" % "test" cross CrossVersion.full

initialCommands in (Test, console) := """ammonite.Main().run()"""
