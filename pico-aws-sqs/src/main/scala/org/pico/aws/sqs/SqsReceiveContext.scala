package org.pico.aws.sqs

import com.amazonaws.services.sqs.model.ReceiveMessageRequest

case class SqsReceiveContext[A](request: ReceiveMessageRequest, value: A) {
  def map[B](f: A => B): SqsReceiveContext[B] = SqsReceiveContext(request, f(value))
}
