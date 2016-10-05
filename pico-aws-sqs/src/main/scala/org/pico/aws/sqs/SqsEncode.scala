package org.pico.aws.sqs

trait SqsEncode[A] {
  def sqsEncode(a: A): SqsEncodedMessage
}

object SqsEncode {
  def apply[A](f: A => SqsEncodedMessage): SqsEncode[A] = {
    new SqsEncode[A] {
      override def sqsEncode(a: A): SqsEncodedMessage = f(a)
    }
  }
}
