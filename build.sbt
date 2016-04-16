name := "Crawler N Scraper"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq("net.codingwell" %% "scala-guice" % "4.0.1",
                            "com.typesafe.play" %% "play-ws" % "2.4.3",
                            "org.jsoup" % "jsoup" % "1.8.3",
                            "org.specs2" %% "specs2" % "2.3.13" % "test")

packAutoSettings
