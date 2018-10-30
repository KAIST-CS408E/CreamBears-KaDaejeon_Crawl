import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "services.xis.kadaejeon",
      scalaVersion := "2.11.7",
      version      := "0.0.0"
    )),
    name := "CreamBears-KaDaejeon_Crawl",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.scala-lang" % "scala-library" % scalaVersion.value,
    libraryDependencies += "org.jsoup" % "jsoup" % "1.11.3",
	libraryDependencies += "com.twitter.penguin" % "korean-text" % "4.4"
  )
