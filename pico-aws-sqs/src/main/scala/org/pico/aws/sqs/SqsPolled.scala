package org.pico.aws.sqs

import java.util.concurrent.atomic.AtomicBoolean

import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.model.Message
import org.pico.aws.sqs.syntax._
import org.pico.disposal.SimpleDisposer
import org.pico.disposal.std.autoCloseable._
import org.pico.event.{Bus, Source}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.Duration
import scala.util.control.NonFatal

trait SqsPolled[A] extends SimpleDisposer {
  def messages: Source[SqsMessageEnvelope[A]]

  def errors: Source[Throwable]
}

object SqsPolled {
  def asyncPoll[A: SqsDecode](
      self: AmazonSQSAsync,
      queueUrl: String,
      attributeNames: Seq[String] = Seq.empty,
      messageAttributeNames: Seq[String] = Seq.empty,
      maxNumberOfMessages: Option[Int] = None,
      visibilityTimeout: Option[Duration] = None,
      waitTimeSeconds: Option[Duration] = None,
      parallelism: Int)(implicit ec: ExecutionContext): SqsPolled[A] = {
    val messagesBus = Bus[SqsMessageEnvelope[A]]
    val errorsBus   = Bus[Throwable]
    val done = new AtomicBoolean(false)

    def doReceive(): Unit = {
      if (!done.get) {
        val future = self.asyncReceive(
          queueUrl, attributeNames, messageAttributeNames, maxNumberOfMessages,
          visibilityTimeout, waitTimeSeconds)

        future.onComplete { completion =>
          try {
            val (request, result) = completion.get

            result.getMessages.asScala.foreach { (message: Message) =>
              try {
                val context = new SqsMessageContext(request, result, message)

                context.sqsDecode[A] match {
                  case Right(value) => messagesBus.publish(SqsMessageEnvelope(context, value))
                  case Left(e) => errorsBus.publish(e)
                }
              } catch {
                case NonFatal(e) => errorsBus.publish(e)
              }
            }
          } catch {
            case NonFatal(e) => errorsBus.publish(e)
          }

          doReceive()
        }
      }
    }

    (0 until parallelism).foreach(_ => doReceive())

    messagesBus.onClose(done.set(true))

    new SqsPolled[A] { self =>
      override def messages: Source[SqsMessageEnvelope[A]] = self.disposes(messagesBus)
      override def errors: Source[Throwable] = self.disposes(errorsBus)
    }
  }
}
