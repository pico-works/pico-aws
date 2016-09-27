package org.pico.aws.sqs

import org.pico.event.Source

trait SqsPolled[A] extends AutoCloseable {
  def messages: Source[SqsMessageEnvelope[A]]

  def errors: Source[Throwable]
}
