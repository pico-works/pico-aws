package org.pico.aws.s3

import java.io.{File, InputStream}

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model._
import scala.collection.JavaConverters._

package object syntax {
  implicit final class AmazonS3Ops_DfCZ6Rj(val self: AmazonS3) extends AnyVal {
    @inline
    def getObject(
        bucket: S3BucketName,
        key: S3Key): S3Object = {
      self.getObject(bucket.name, key.path)
    }

    @inline
    def getObject(
        location: S3Location): S3Object = {
      self.getObject(location.bucket, location.key)
    }

    @inline
    def downloadObject(
        bucket: S3BucketName,
        key: S3Key,
        destinationFile: File): ObjectMetadata = {
      val request = new GetObjectRequest(bucket.name, key.path)

      self.getObject(request, destinationFile)
    }

    @inline
    def downloadObject(
        location: S3Location,
        destinationFile: File): ObjectMetadata = {
      self.downloadObject(location.bucket, location.key, destinationFile)
    }

    @inline
    def putObject(
        bucket: S3BucketName,
        key: S3Key,
        content: String): PutObjectResult = {
      self.putObject(bucket.name, key.path, content)
    }

    @inline
    def putObject(
        bucket: S3BucketName,
        key: S3Key,
        file: File): PutObjectResult = {
      self.putObject(bucket.name, key.path, file)
    }

    @inline
    def putObject(
        bucket: S3BucketName,
        key: S3Key,
        is: InputStream,
        metadata: ObjectMetadata): PutObjectResult = {
      self.putObject(bucket.name, key.path, is, metadata)
    }

    @inline
    def putObject(
        location: S3Location,
        content: String): PutObjectResult = {
      self.putObject(location.bucket, location.key, content)
    }

    @inline
    def putObject(
        location: S3Location,
        file: File): PutObjectResult = {
      self.putObject(location.bucket, location.key, file)
    }

    @inline
    def putObject(
        location: S3Location,
        is: InputStream,
        metadata: ObjectMetadata): PutObjectResult = {
      self.putObject(location.bucket, location.key, is, metadata)
    }

    @inline
    def copyObject(
        sourceBucket: S3BucketName,
        sourceKey: S3Key,
        destinationBucket: S3BucketName,
        destinationKey: S3Key): CopyObjectResult = {
      self.copyObject(sourceBucket.name, sourceKey.path, destinationBucket.name, destinationKey.path)
    }

    @inline
    def copyObject(
        source: S3Location,
        destination: S3Location): CopyObjectResult = {
      self.copyObject(source.bucket,  source.key,  destination.bucket,  destination.key)
    }

    @inline
    def createBucket(
        bucket: S3BucketName): Bucket = {
      self.createBucket(bucket.name)
    }

    @inline
    def createBucket(
        bucket: S3BucketName,
        region: Region): Bucket = {
      self.createBucket(bucket.name, region)
    }

    @inline
    def deleteBucket(
        bucket: S3BucketName): Unit = {
      self.deleteBucket(bucket.name)
    }

    @inline
    def deleteObject(
        bucket: S3BucketName,
        key: S3Key): Unit = {
      self.deleteObject(bucket.name, key.path)
    }

    @inline
    def deleteObject(
        location: S3Location): Unit = {
      self.deleteObject(location.bucket, location.key)
    }

    @inline
    def deleteVersion(
        bucket: S3BucketName,
        key: S3Key,
        version: S3Version): Unit = {
      self.deleteVersion(bucket.name, key.path, version.value)
    }

    @inline
    def deleteVersion(
        location: S3Location,
        version: S3Version): Unit = {
      self.deleteVersion(location.bucket, location.key, version)
    }

    @inline
    def bucketNames: Seq[S3BucketName] = {
      self.listBuckets().asScala.map(bucket => S3BucketName(bucket.getName))
    }
  }
}
