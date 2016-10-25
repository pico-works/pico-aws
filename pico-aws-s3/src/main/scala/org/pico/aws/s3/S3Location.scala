package org.pico.aws.s3

import com.amazonaws.services.s3.AmazonS3URI

case class S3Location(bucket: S3BucketName, key: S3Key) {
  override def toString: String = s"s3://${bucket.name}/${key.path}"
}

object S3Location {
  @inline
  def from(s3Uri: String): S3Location = {
    val amazon3Uri = new AmazonS3URI(s3Uri, true)

    S3Location(S3BucketName(amazon3Uri.getBucket), S3Key(amazon3Uri.getKey))
  }

  @inline
  def from(s3Uri: S3Uri): S3Location = from(s3Uri.uri)
}
