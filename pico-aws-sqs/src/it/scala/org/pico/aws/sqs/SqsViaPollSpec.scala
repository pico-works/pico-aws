package org.pico.aws.sqs

import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.sqs.AmazonSQSAsyncClient
import com.typesafe.config._
import org.pico.aws.sqs.std._
import org.pico.aws.sqs.syntax._
import org.pico.disposal.std.autoCloseable._
import org.pico.disposal.{Auto, Eval, OnClose}
import org.pico.event.Bus
import org.pico.event.syntax.source._
import org.specs2.mutable.Specification

import scala.concurrent.ExecutionContext.Implicits.global

class SqsViaPollSpec extends Specification {
  "SQS via bus" in {
    val config = ConfigFactory.load("application.conf")
    val region = config.getString("aws.region")
    val sqsQueue1Name = config.getString("sqs.queue-1")
    val messageBus = Bus[SqsMessageEnvelope[UpperCased]]

    for {
      client    <- Auto(new AmazonSQSAsyncClient)
      _         <- Eval(client.setRegion(Region.getRegion(Regions.fromName(region))))
      queueUrl  <- Eval(client.getQueueUrl(sqsQueue1Name).getQueueUrl)
      poll      <- Auto(client.asyncPoll[UpperCased](queueUrl, parallelism = 5))
      doneBus   <- Auto(client.doneBus[Any])
      doneCount <- Auto(doneBus.asSource.eventCount)
      _         <- Auto(OnClose(println(s"Messages done: ${doneCount.value}")))
      _         <- Auto(poll.messages into messageBus)
      _         <- Auto(messageBus.map{v => println(s"Message: ${v.value}"); v}.into(doneBus))
    } {
      Thread.sleep(5000)

      ok
    }
  }
}
