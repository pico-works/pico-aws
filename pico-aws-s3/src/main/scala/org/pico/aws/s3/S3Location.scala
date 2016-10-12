package org.pico.aws.s3

import com.amazonaws.services.s3.AmazonS3URI

case class S3Location(bucket: S3BucketName, key: S3Key)

object S3Location {
  def from(s3Uri: S3Uri): S3Location = {
    val amazon3Uri = new AmazonS3URI(s3Uri.uri, true)

    S3Location(S3BucketName(amazon3Uri.getBucket), S3Key(amazon3Uri.getKey))
  }
}
