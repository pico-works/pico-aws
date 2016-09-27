package org.pico.aws.sqs

trait SqsDecode[A] {
  def sqsDecode(sqsMessageContext: SqsMessageContext): Either[SqsDecodeException, A]
}

object SqsDecode {
  def apply[A](f: SqsMessageContext => Either[SqsDecodeException, A]): SqsDecode[A] = {
    new SqsDecode[A] {
      override def sqsDecode(sqsMessageContext: SqsMessageContext): Either[SqsDecodeException, A] = {
        f(sqsMessageContext)
      }
    }
  }
}
