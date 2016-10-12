import sbt.Keys._
import sbt._

object Build extends sbt.Build {
  val pico_disposal           = "org.pico"            %%  "pico-disposal"           % "1.0.6"
  val pico_event              = "org.pico"            %%  "pico-event"              % "3.0.4"

  val aws_java_sdk_core       = "com.amazonaws"       %   "aws-java-sdk-core"       % "1.11.39"
  val aws_java_sdk_dynamodb   = "com.amazonaws"       %   "aws-java-sdk-dynamodb"   % "1.11.39"
  val aws_java_sdk_kinesis    = "com.amazonaws"       %   "aws-java-sdk-kinesis"    % "1.11.39"
  val aws_java_sdk_s3         = "com.amazonaws"       %   "aws-java-sdk-s3"         % "1.11.39"
  val aws_java_sdk_sqs        = "com.amazonaws"       %   "aws-java-sdk-sqs"        % "1.11.39"
  val circe_core              = "io.circe"            %%  "circe-core"              % "0.5.1"

  val specs2_core             = "org.specs2"          %%  "specs2-core"             % "3.7.2"
  val typesafe_config         = "com.typesafe"        %   "config"                  % "1.3.1"

  implicit class ProjectOps(self: Project) {
    def standard(theDescription: String) = {
      self
          .settings(scalacOptions in Test ++= Seq("-Yrangepos"))
          .settings(publishTo := Some("Releases" at "s3://dl.john-ky.io/maven/releases"))
          .settings(description := theDescription)
          .settings(isSnapshot := true)
          .settings(addCompilerPlugin("org.spire-math" % "kind-projector" % "0.9.0" cross CrossVersion.binary))
    }

    def notPublished = self.settings(publish := {}).settings(publishArtifact := false)

    def it = self.configs(IntegrationTest).settings(Defaults.itSettings: _*)

    def libs(modules: ModuleID*) = self.settings(libraryDependencies ++= modules)

    def testLibs(modules: ModuleID*) = self.libs(modules.map(_ % "it,test"): _*)

    def itLibs(modules: ModuleID*) = self.libs(modules.map(_ % "it"): _*)
  }

  lazy val `pico-aws-core` = Project(id = "pico-aws-core", base = file("pico-aws-core"))
      .standard("pico-event shim library for aws-core").it
      .libs(pico_disposal, aws_java_sdk_core)
      .testLibs(specs2_core)
      .itLibs(typesafe_config)

  lazy val `pico-aws-dynamodb` = Project(id = "pico-aws-dynamodb", base = file("pico-aws-dynamodb"))
      .standard("pico-event shim library for aws-dynamodb").it
      .dependsOn(`pico-aws-core`)
      .libs(pico_disposal, aws_java_sdk_dynamodb, circe_core)
      .testLibs(specs2_core)
      .itLibs(typesafe_config)

  lazy val `pico-aws-kinesis` = Project(id = "pico-aws-kinesis", base = file("pico-aws-kinesis"))
      .standard("pico-event shim library for aws-dynamodb").it
      .dependsOn(`pico-aws-core`)
      .libs(pico_disposal, aws_java_sdk_kinesis)
      .testLibs(specs2_core)
      .itLibs(typesafe_config)

  lazy val `pico-aws-s3` = Project(id = "pico-aws-s3", base = file("pico-aws-s3"))
      .standard("pico-event shim library for aws").it
      .dependsOn(`pico-aws-core`)
      .libs(pico_disposal, aws_java_sdk_s3)
      .testLibs(specs2_core)
      .itLibs(typesafe_config)

  lazy val `pico-aws-sqs` = Project(id = "pico-aws-sqs", base = file("pico-aws-sqs"))
      .standard("pico-event shim library for aws-sqs").it
      .dependsOn(`pico-aws-core`)
      .libs(pico_disposal, pico_event, aws_java_sdk_sqs)
      .testLibs(specs2_core)
      .itLibs(typesafe_config)

  lazy val `pico-aws` = Project(id = "pico-aws", base = file("pico-aws"))
      .standard("pico-event shim library for aws").it
      .dependsOn(`pico-aws-dynamodb`, `pico-aws-kinesis`, `pico-aws-s3`, `pico-aws-sqs`)
      .testLibs(specs2_core)
      .itLibs(typesafe_config)

  lazy val all = Project(id = "pico-aws-project", base = file("."))
      .notPublished
      .aggregate(`pico-aws-dynamodb`, `pico-aws-kinesis`, `pico-aws-s3`, `pico-aws-sqs`)
}
