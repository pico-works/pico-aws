package org.pico.aws.sqs

import org.pico.disposal.SimpleDisposer
import org.pico.event.{Sink, Source}

trait Outbox[A] extends SimpleDisposer {
  def sink: Sink[A]

  def sent: Source[A]
}
