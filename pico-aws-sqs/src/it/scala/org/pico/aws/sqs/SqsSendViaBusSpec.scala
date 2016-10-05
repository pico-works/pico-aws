package org.pico.aws.sqs

import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.sqs.AmazonSQSAsyncClient
import com.typesafe.config._
import org.pico.aws.sqs.std._
import org.pico.aws.sqs.syntax._
import org.pico.disposal.std.autoCloseable._
import org.pico.disposal.{Auto, Eval}
import org.pico.event.syntax.source._
import org.specs2.mutable.Specification

import scala.concurrent.ExecutionContext.Implicits.global

class SqsSendViaBusSpec extends Specification {
  "SQS via bus" in {
    val config = ConfigFactory.load("application.conf")
    val region = config.getString("aws.region")
    val sqsQueue1Name = config.getString("sqs.queue-1")

    for {
      client        <- Auto(new AmazonSQSAsyncClient)
      _             <- Eval(client.setRegion(Region.getRegion(Regions.fromName(region))))
      queueUrl      <- Eval(client.getQueueUrl(sqsQueue1Name).getQueueUrl)
      outbox        <- Auto(client.sendTo[SqsMessageBody](queueUrl))
      blockInFlight <- Auto(WaitCompleteSink[Any])
      _             <- Auto(outbox.justSome into blockInFlight)
    } {
      (0 until 10).foreach { i =>
        outbox.publish(SqsMessageBody(s"Message $i"))
      }

      ok
    }
  }
}
