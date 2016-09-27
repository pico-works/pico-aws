package org.pico.aws.std

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.kinesis.AmazonKinesisClient
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.sqs.AmazonSQSClient
import org.pico.aws.dynamodb.std._
import org.pico.aws.kinesis.std._
import org.pico.aws.s3.std._
import org.pico.aws.sqs.std._
import org.pico.disposal.Auto
import org.specs2.mutable.Specification

class PackageSpec extends Specification {
  "Support disposable syntax from std" in {
    for (sqsClient <- Auto(new AmazonKinesisClient())) {
      ok
    }

    for (sqsClient <- Auto(new AmazonSQSClient())) {
      ok
    }

    for (sqsClient <- Auto(new AmazonS3Client())) {
      ok
    }

    for (sqsClient <- Auto(new AmazonDynamoDBClient())) {
      ok
    }
  }
}
