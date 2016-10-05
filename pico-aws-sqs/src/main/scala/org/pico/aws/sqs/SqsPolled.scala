package org.pico.aws.sqs

import org.pico.disposal.SimpleDisposer
import org.pico.event.Source

trait SqsPolled[A] extends SimpleDisposer {
  def messages: Source[SqsMessageEnvelope[A]]

  def errors: Source[Throwable]
}
