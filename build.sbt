name := "scalaStocks"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-http" % "6.41.0",
  "com.twitter" %% "bijection-core" % "0.9.5",
  "org.typelevel" %% "cats-core" % "0.9.0",

  "org.scalatest" % "scalatest_2.11" % "3.0.1" % Test

)