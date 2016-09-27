package org.pico.aws.sqs

import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.sqs.AmazonSQSAsyncClient
import com.typesafe.config._
import org.pico.aws.sqs.std._
import org.pico.aws.sqs.syntax._
import org.pico.disposal.Auto
import org.specs2.mutable.Specification

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.control.NonFatal

class SqsViaFutureSpec extends Specification {
  "SQS via futures" in {
    val config = ConfigFactory.load("application.conf")
    val region = config.getString("aws.region")
    val sqsQueue1Name = config.getString("sqs.queue-1")

    for (client <- Auto(new AmazonSQSAsyncClient())) {
      client.setRegion(Region.getRegion(Regions.fromName(region)))

      val queueUrl = client.getQueueUrl(sqsQueue1Name).getQueueUrl

      client.asyncReceive(queueUrl).onComplete { result =>
        try {
          result.get.value.getMessages.asScala.foreach { message =>
            println(message.getBody)
          }
        } catch {
          case NonFatal(e) => e.printStackTrace(System.out)
        }
      }

      Thread.sleep(1000)

      ok
    }
  }
}
