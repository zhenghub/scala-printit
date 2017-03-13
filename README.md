# scala-printit
pretty print for tables in scala

## usage
- for sbt, add sbt resolver and dependency
```scala
resolvers += "freefeeling" at "http://maven.freefeeling.org"

libraryDependencies += "org.freefeeling" % "scala-printit" % "0.0.1-SNAPSHOT"
```
- for maven, add repository and dependency
```xml
		<repository>
			<id>freefeeling</id>
			<name>freefeeling.org</name>
			<url>http://maven.freefeeling.org</url>
		</repository>
```
```xml
		<dependency>
			<groupId>org.freefeeling</groupId>
			<artifactId>scala-printit_2.11</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
```
