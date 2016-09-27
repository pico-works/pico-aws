package org.pico.aws.sqs

import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.sqs.AmazonSQSAsyncClient
import com.amazonaws.services.sqs.model.{Message, ReceiveMessageResult}
import com.typesafe.config._
import org.pico.aws.sqs.std._
import org.pico.aws.sqs.syntax._
import org.pico.disposal.Auto
import org.pico.disposal.std.autoCloseable._
import org.pico.event.Bus
import org.pico.event.std.all._
import org.pico.event.syntax.future._
import org.specs2.mutable.Specification

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global

class SqsViaBusSpec extends Specification {
  "SQS via bus" in {
    val config = ConfigFactory.load("application.conf")
    val region = config.getString("aws.region")
    val sqsQueue1Name = config.getString("sqs.queue-1")
    val errorBus = Bus[Throwable]
    val successBus = Bus[SqsReceiveContext[ReceiveMessageResult]]
    val messageBus = Bus[SqsReceiveContext[Message]]

    for {
      client  <- Auto(new AmazonSQSAsyncClient)
      _       <- Auto(errorBus.subscribe(println))
      _       <- Auto(successBus.mapConcat(_.map(_.getMessages.asScala.toSeq).sequence) into messageBus)
      _       <- Auto(messageBus.subscribe(v => println(s"Message: ${v.value.getBody}")))
    } {
      client.setRegion(Region.getRegion(Regions.fromName(region)))
      val queueUrl = client.getQueueUrl(sqsQueue1Name).getQueueUrl

      client.asyncReceive(queueUrl).completeInto(successBus, errorBus)

      Thread.sleep(1000)

      ok
    }
  }
}
