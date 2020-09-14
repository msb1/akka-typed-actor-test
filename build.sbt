name := "TestActors"
version := "0.1"
scalaVersion := "2.12.12"

val AkkaVersion = "2.6.9"

libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.1.6"
libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-stream-typed" % AkkaVersion

// akka sfl4j logging backed by logback (appenders are defined in logback.xml)
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "org.slf4j" % "slf4j-api" % "1.7.30",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

// test dependencies
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test


scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-Xfatal-warnings"
)

cancelable in Global := true