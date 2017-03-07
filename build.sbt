name := "airport-near-me"

version := "0.0.1"

scalaVersion := "2.11.8"

unmanagedBase <<= baseDirectory { base => base / "libs" }

libraryDependencies += "org.apache.solr" % "solr-solrj" % "6.4.1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.15"

libraryDependencies += "io.spray" %% "spray-can" % "1.3.3"

libraryDependencies += "io.spray" %% "spray-routing" % "1.3.3"

libraryDependencies += "io.spray" %% "spray-json" % "1.3.2"

libraryDependencies += "com.google.code.gson" % "gson" % "2.3"

libraryDependencies += "org.json4s" %% "json4s-native" % "3.3.0"

libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.3.0"

libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.3.0"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

resolvers += "Bintray sbt plugin releases" at "http://dl.bintray.com/sbt/sbt-plugin-releases/"

organization := "com.okellonelson"

mainClass := Some("com.okellonelson.ta.airport_near_me.Boot")

mergeStrategy in assembly := {
	case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
	case m if m.toLowerCase.matches("meta-inf.*\\.sf$") => MergeStrategy.discard
	case "reference.conf" => MergeStrategy.concat
	case _ => MergeStrategy.first
}
