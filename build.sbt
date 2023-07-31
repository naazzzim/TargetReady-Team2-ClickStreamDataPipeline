ThisBuild / scalaVersion := "2.13.11"

ThisBuild / organization := "com.start"

lazy val root = (project in file("."))
  .settings(
    name := "ClickStreamDataPipeline",
    libraryDependencies +="org.apache.spark" %% "spark-core" % "3.4.1",
    libraryDependencies +="org.apache.spark" %% "spark-streaming" % "3.4.1",
    libraryDependencies +="org.apache.spark" %% "spark-sql" % "3.4.1",
    libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.5.0",
    libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.4.1" excludeAll(
      ExclusionRule(organization = "org.spark-project.spark", name = "unused"),
      ExclusionRule(organization = "org.apache.spark", name = "spark-streaming"),
      ExclusionRule(organization = "org.apache.hadoop")
    ),
//    libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.4.1" % Test,
      // https://mvnrepository.com/artifact/org.apache.spark/spark-sql-kafka-0-10
      libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.4.1"


  )

