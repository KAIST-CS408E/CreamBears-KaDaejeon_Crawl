import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "services.xis.kadaejeon",
      scalaVersion := "2.12.7",
      version      := "0.0.0"
    )),
    name := "CreamBears-KaDaejeon_Crawl",
    libraryDependencies += scalaTest % Test
  )
