import sbt.Keys._
import sbt._

object Build extends sbt.Build {  
  val pico_disposal           = "org.pico"            %%  "pico-disposal"           % "0.6.2"

  val aws_java_sdk_dynamodb   = "com.amazonaws"       %   "aws-java-sdk-dynamodb"   % "1.11.33"
  val aws_java_sdk_kinesis    = "com.amazonaws"       %   "aws-java-sdk-kinesis"    % "1.11.33"
  val aws_java_sdk_s3         = "com.amazonaws"       %   "aws-java-sdk-s3"         % "1.11.33"
  val aws_java_sdk_sqs        = "com.amazonaws"       %   "aws-java-sdk-sqs"        % "1.11.33"

  val specs2_core             = "org.specs2"          %%  "specs2-core"             % "3.7.2"

  implicit class ProjectOps(self: Project) {
    def standard(theDescription: String) = {
      self
          .settings(scalacOptions in Test ++= Seq("-Yrangepos"))
          .settings(publishTo := Some("Releases" at "s3://dl.john-ky.io/maven/releases"))
          .settings(description := theDescription)
          .settings(isSnapshot := true)
    }

    def notPublished = self.settings(publish := {}).settings(publishArtifact := false)

    def libs(modules: ModuleID*) = self.settings(libraryDependencies ++= modules)

    def testLibs(modules: ModuleID*) = self.libs(modules.map(_ % "test"): _*)
  }

  lazy val `pico-aws-dynamodb` = Project(id = "pico-aws-dynamodb", base = file("pico-aws-dynamodb"))
      .standard("pico-event shim library for aws-dynamodb")
      .libs(pico_disposal, aws_java_sdk_dynamodb)
      .testLibs(specs2_core)

  lazy val `pico-aws-kinesis` = Project(id = "pico-aws-kinesis", base = file("pico-aws-kinesis"))
      .standard("pico-event shim library for aws-dynamodb")
      .libs(pico_disposal)
      .testLibs(specs2_core, aws_java_sdk_kinesis)

  lazy val `pico-aws-s3` = Project(id = "pico-aws-s3", base = file("pico-aws-s3"))
      .standard("pico-event shim library for aws")
      .dependsOn(`pico-aws-dynamodb`, `pico-aws-sqs`)
      .libs(pico_disposal, aws_java_sdk_s3)
      .testLibs(specs2_core)

  lazy val `pico-aws-sqs` = Project(id = "pico-aws-sqs", base = file("pico-aws-sqs"))
      .standard("pico-event shim library for aws-sqs")
      .libs(pico_disposal, aws_java_sdk_sqs)
      .testLibs(specs2_core)

  lazy val all = Project(id = "pico-aws-project", base = file("."))
      .notPublished
      .aggregate(`pico-aws-dynamodb`, `pico-aws-kinesis`, `pico-aws-s3`, `pico-aws-sqs`)
}
