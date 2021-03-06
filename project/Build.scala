import sbt._
import Keys._

object Settings {
  val name = "swaggerkit"
  
  val buildSettings = Project.defaultSettings ++ Seq(
    version := "0.1-RC3",
    scalaVersion := "2.10.0",
    scalacOptions += "-feature")
}

object Resolvers {
  val typesafeRepo = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
}

object Dependencies {
  lazy val play = "play" %% "play" % "2.1-RC3"
  lazy val specs = "org.specs2" %% "specs2" % "1.13" % "test"
}

object ApplicationBuild extends Build {
  import Settings._
  import Resolvers._
  import Dependencies._

  lazy val pubTo = Some(Resolver.file("bigtoast.github.com", file(Path.userHome + "/Projects/BigToast/bigtoast.github.com/repo")) ) 

  lazy val root = Project(name, file("."), settings = buildSettings ++ Seq(
    //publish := {}
    publishTo := pubTo )
  ) aggregate (core, play2)

  lazy val core = Project(name + "-core", file("core"), settings = buildSettings ++ Seq(
    resolvers := Seq(typesafeRepo),
    libraryDependencies ++= Seq(specs),
    publishTo := pubTo  ))

  lazy val play2 = Project(name + "-play2", file("play2"), settings = buildSettings ++ Seq(
    resolvers := Seq(typesafeRepo),
    libraryDependencies ++= Seq(play, specs),
    publishTo := pubTo  )) dependsOn (core)
}


